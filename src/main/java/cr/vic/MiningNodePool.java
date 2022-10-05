package cr.vic;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MiningNodePool {
    @Getter
    private final Set<MiningNode> pool;

    public MiningNodePool() {
        pool = new HashSet<>();
    }

    public int size() {
        return pool.size();
    }

    public void printAllNodeNames() {
        System.out.println("Print all node names");
        pool.forEach(node -> System.out.println("\t" + node.getName()));
        System.out.println("Finish all node names");
    }

    public static MiningNodePool fixedInstancesNodePool(int size) {
        MiningNodePool fixedPool = new MiningNodePool();
        for (int i = 1; i <= size; i++) fixedPool.pool.add(ProofOfWorkMiningNode.builder().name("Node Pool " + i).build());
        return fixedPool;
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }

    public void consensual(Block block, int prefixLength) {
        if (block.getStatus() == BlockStatusTypeEnum.MINED) throw new AlreadyMinedException();
        if (!isEmpty()) {
            setMiningConsensusRequestInPool(new MiningConsensusRequest(block, prefixLength));
            ExecutorService threadPool = Executors.newCachedThreadPool();
            List<Future<MiningConsensusResponse>> futures = startThreads(threadPool);
            awaitToFinish(futures);
            int nonce = futures.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).min((f1, f2) -> {
                if (f1.nonce == f2.nonce) return 0;
                return f1.nonce < f2.nonce ? -1 : 1;
            }).orElse(new MiningConsensusResponse(-1, 0)).nonce;
            finishBlock(block, nonce);
            setMiningConsensusRequestInPool(null);
            threadPool.shutdown();
        }
    }

    private List<Future<MiningConsensusResponse>> startThreads(ExecutorService threadPool) {
        List<Future<MiningConsensusResponse>> futures;
        try {
            futures = threadPool.invokeAll(pool);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return futures;
    }

    private static void awaitToFinish(List<Future<MiningConsensusResponse>> futures) {
        boolean areAllFinished;
        do {
            areAllFinished = futures.stream().filter(Future::isDone).count() == futures.size();
        } while(!areAllFinished);
    }

    private void setMiningConsensusRequestInPool(MiningConsensusRequest block) {
        pool.forEach(miningNode -> miningNode.setMiningRequest(block));
    }

    private static void finishBlock(Block block, int nonce) {
        block.setNonce(nonce);
        block.setStatus(BlockStatusTypeEnum.MINED);
    }
}

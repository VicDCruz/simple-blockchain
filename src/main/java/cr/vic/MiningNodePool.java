package cr.vic;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public static MiningNodePool fixedMiningNodePool(int size) {
        MiningNodePool fixedPool = new MiningNodePool();
        for (int i = 1; i <= size; i++) fixedPool.pool.add(new MiningNode("Mining node " + i));
        return fixedPool;
    }

    public void mine(ConsensusProtocol consensusProtocol) {
        consensusProtocol.verifyMiningStatus();
        MiningResponseManager.start();
        if (!pool.isEmpty()) {
            setMiningConsensusRequestInPool(consensusProtocol);
            notifyMining();
            consensusProtocol.finishBlock();
        }
    }

    private void setMiningConsensusRequestInPool(ConsensusProtocol consensusProtocol) {
        pool.forEach(miningNode -> miningNode.setProtocol(consensusProtocol));
    }

    private void notifyMining() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Future<Boolean>> futures;
        boolean areAllFinished;

        try {
            futures = threadPool.invokeAll(pool);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        do {
            areAllFinished = futures.stream().filter(Future::isDone).count() == futures.size();
        } while(!areAllFinished);

        threadPool.shutdown();
    }

}

package cr.vic;

import lombok.Builder;

public class ProofOfWorkMiningNode extends MiningNode {
    @Builder
    public ProofOfWorkMiningNode(String name, MiningConsensusRequest miningRequest) {
        super(name, miningRequest);
    }

    @Override
    public MiningConsensusResponse mine(MiningConsensusRequest request) {
        long startTimer = System.currentTimeMillis();
        int nonce = computationalPuzzle(request.block, request.prefixLength);
        MiningConsensusResponse miningConsensusResponse = new MiningConsensusResponse(nonce, System.currentTimeMillis() - startTimer);
        System.out.println("Results from " + this.getName() + ": " + miningConsensusResponse);
        return miningConsensusResponse;
    }

    private int computationalPuzzle(Block block, int prefixLength) {
        String prefixString = StringCommons.createPrefixString(prefixLength);
        int nonce = 0;
        while(!block.testHash(nonce).substring(0, prefixLength).equals(prefixString)) nonce = (int) (Math.random() * 50_000);
        return nonce;
    }
}

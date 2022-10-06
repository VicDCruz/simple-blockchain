package cr.vic;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProofOfWorkConsensus implements ConsensusProtocol {
    private Block block;
    private int prefixLength;

    @Override
    public MiningConsensusResponse consensual() {
        long startTimer = System.currentTimeMillis();
        int nonce = computationalPuzzle(block, prefixLength);
        return new MiningConsensusResponse(nonce, System.currentTimeMillis() - startTimer);
    }

    @Override
    public void verifyMiningStatus() {
        if (block.getStatus() == BlockStatusTypeEnum.MINED) throw new AlreadyMinedException();
    }

    @Override
    public void finishBlock() {
        block.setNonce(MiningResponseManager.getWinner().nonce);
    }

    private int computationalPuzzle(Block block, int prefixLength) {
        String prefixString = StringCommons.createPrefixString(prefixLength);
        int nonce = 0;
        while(!block.testHash(nonce).substring(0, prefixLength).equals(prefixString)) nonce = (int) (Math.random() * 50_000);
        return nonce;
    }
}

package cr.vic;

public class ProofOfWorkConsensus extends AbstractConsensusProtocol {
    private final int prefixLength;

    public ProofOfWorkConsensus(Block block, int prefixLength) {
        super(block);
        this.prefixLength = prefixLength;
    }

    @Override
    public MiningConsensusResponse consensual() {
        long startTimer = System.currentTimeMillis();
        int nonce = computationalPuzzle(block, prefixLength);
        return new MiningConsensusResponse(nonce, System.currentTimeMillis() - startTimer);
    }

    private int computationalPuzzle(Block block, int prefixLength) {
        String prefixString = StringCommons.createPrefixString(prefixLength);
        int nonce = 0;
        while(!block.testHash(nonce).substring(0, prefixLength).equals(prefixString)) nonce = (int) (Math.random() * 50_000);
        return nonce;
    }
}

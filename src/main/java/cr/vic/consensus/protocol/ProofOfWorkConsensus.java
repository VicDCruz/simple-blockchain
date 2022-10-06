package cr.vic.consensus.protocol;

import cr.vic.block.Block;
import cr.vic.consensus.protocol.abstracts.AbstractConsensusProtocol;
import cr.vic.consensus.protocol.mining.ConsensusResponse;
import cr.vic.utils.string.StringCommons;

public class ProofOfWorkConsensus extends AbstractConsensusProtocol {
    private final int prefixLength;

    public ProofOfWorkConsensus(Block block, int prefixLength) {
        super(block);
        this.prefixLength = prefixLength;
    }

    @Override
    public ConsensusResponse consensual() {
        long startTimer = System.currentTimeMillis();
        int nonce = computationalPuzzle(block, prefixLength);
        return new ConsensusResponse(nonce, System.currentTimeMillis() - startTimer);
    }

    private int computationalPuzzle(Block block, int prefixLength) {
        String prefixString = StringCommons.createPrefixString(prefixLength);
        int nonce = 0;
        while(!block.testHash(nonce).substring(0, prefixLength).equals(prefixString)) nonce = (int) (Math.random() * 50_000);
        return nonce;
    }
}

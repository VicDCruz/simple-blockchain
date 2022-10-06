package cr.vic.consensus.protocol.interfaces;

import cr.vic.consensus.protocol.mining.ConsensusResponse;

public interface ConsensusProtocol {
    ConsensusResponse consensual();

    void verifyMiningStatus();

    void finishBlock();
}

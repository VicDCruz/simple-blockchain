package cr.vic;

public interface ConsensusProtocol {
    MiningConsensusResponse consensual();

    void verifyMiningStatus();

    void finishBlock();
}

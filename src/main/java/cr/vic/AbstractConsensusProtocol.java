package cr.vic;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractConsensusProtocol implements ConsensusProtocol {
    protected Block block;

    @Override
    public void verifyMiningStatus() {
        if (block.getStatus() == BlockStatusTypeEnum.MINED) throw new AlreadyMinedException();
    }

    @Override
    public void finishBlock() {
        block.setNonce(MiningResponseManager.getWinner().nonce);
    }
}

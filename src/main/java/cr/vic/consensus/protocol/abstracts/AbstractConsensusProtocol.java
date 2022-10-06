package cr.vic.consensus.protocol.abstracts;

import cr.vic.block.exceptions.AlreadyMinedException;
import cr.vic.block.Block;
import cr.vic.block.enums.BlockStatusTypeEnum;
import cr.vic.consensus.protocol.interfaces.ConsensusProtocol;
import cr.vic.consensus.protocol.mining.ConsensusResponseManager;
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
        block.setNonce(ConsensusResponseManager.getWinner().nonce);
    }
}

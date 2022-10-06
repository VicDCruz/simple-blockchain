package cr.vic;

import cr.vic.block.exceptions.AlreadyMinedException;
import cr.vic.block.Block;
import cr.vic.block.enums.BlockStatusTypeEnum;
import cr.vic.consensus.protocol.ProofOfWorkConsensus;
import cr.vic.mining.MiningNodePool;
import cr.vic.utils.string.StringCommons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsensusTest {
    private final int prefixLength = 4;
    private Block block;
    private MiningNodePool miningNodePool;

    @BeforeEach
    void setUp() {
        block = Block.builder().previousBlock(Block.createGenesisBlock()).content("test content").build();
        miningNodePool = MiningNodePool.fixedMiningNodePool(1);
    }

    @Test
    void shouldKeepPendingStatus_whenNodePoolIsEmpty() {
        miningNodePool = MiningNodePool.fixedMiningNodePool(0);
        miningNodePool.mine(new ProofOfWorkConsensus(block, prefixLength));

        assertEquals(BlockStatusTypeEnum.PENDING_MINING, block.getStatus());
    }

    @Test
    void shouldThrowMiningError_whenBlockIsAlreadyMined() {
        block.setStatus(BlockStatusTypeEnum.MINED);

        assertThrows(AlreadyMinedException.class, () -> miningNodePool.mine(new ProofOfWorkConsensus(block, prefixLength)));
    }

    @Test
    void shouldChangeBlockStatus_whenOneMinerUsesConsensus() {
        miningNodePool = MiningNodePool.fixedMiningNodePool(16);
        miningNodePool.mine(new ProofOfWorkConsensus(block, prefixLength));

        assertEquals(BlockStatusTypeEnum.MINED, block.getStatus());
        assertEquals(block.getHash().substring(0, prefixLength), StringCommons.createPrefixString(prefixLength));
    }

}
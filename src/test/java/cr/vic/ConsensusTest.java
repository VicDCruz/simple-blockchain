package cr.vic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsensusTest {
    private Block block;
    private MiningNodePool miningNodePool;

    @BeforeEach
    void setUp() {
        block = Block.builder().previousBlock(Block.createGenesisBlock()).content("test content").build();
        miningNodePool = MiningNodePool.fixedInstancesNodePool(1);
    }

    @Test
    void shouldKeepPendingStatus_whenNodePoolIsEmpty() {
        miningNodePool = MiningNodePool.fixedInstancesNodePool(0);
        miningNodePool.consensual(block, 4);

        assertEquals(BlockStatusTypeEnum.PENDING_MINING, block.getStatus());
    }

    @Test
    void shouldThrowMiningError_whenBlockIsAlreadyMined() {
        block.setStatus(BlockStatusTypeEnum.MINED);

        assertThrows(AlreadyMinedException.class, () -> miningNodePool.consensual(block, 4));
    }

    @Test
    void shouldChangeBlockStatus_whenOneMinerUsesConsensus() {
        miningNodePool = MiningNodePool.fixedInstancesNodePool(16);
        int prefixLength = 4;
        miningNodePool.consensual(block, prefixLength);

        assertEquals(BlockStatusTypeEnum.MINED, block.getStatus());
        assertEquals(block.getHash().substring(0, prefixLength), StringCommons.createPrefixString(prefixLength));
    }

}
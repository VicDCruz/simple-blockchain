package cr.vic;

import cr.vic.block.Block;
import cr.vic.block.enums.BlockTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    private final Block genesisBlock = Block.createGenesisBlock();

    @Test
    void shouldGetTrue_whenIsGenesisBlock() {
        assertTrue(genesisBlock.isGenesis());
    }

    @Test
    void shouldGetString_whenHashGenesisBlock() {
        assertEquals("GENESIS", genesisBlock.getHash());
    }

    @Test
    void shouldReturnEmptyPreviousString_whenGenesisBlock() {
        assertNull(genesisBlock.getPreviousHash());
    }

    @Test
    void shouldBuildNewBlock_whenHaveGenesisBlockAsPredecessor() {
        Block block = Block.builder().previousBlock(genesisBlock).build();

        assertEquals(genesisBlock.getHash(), block.getPreviousHash());
        assertNotEquals(genesisBlock, block);
        assertNotEquals(genesisBlock.getHash(), block.getHash());
    }

    @Test
    void shouldInvalidateBlock_whenBlockChangeContent() {
        Block block = Block.builder().previousBlock(genesisBlock).build();

        block.setContent("new content");
        block.validate();

        assertEquals(BlockTypeEnum.INVALID, block.getType());
    }

    @Test
    void shouldReturnContent_whenSaveInBlock() {
        String content = "test content";
        Block block = Block.builder().previousBlock(genesisBlock).content(content).build();

        block.validate();

        assertEquals(BlockTypeEnum.GENERAL, block.getType());
        assertEquals(content, block.getContent());
    }
}
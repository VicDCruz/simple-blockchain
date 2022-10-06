package cr.vic;

import cr.vic.block.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BlockchainTest {
    private Blockchain blockchain;

    @BeforeEach
    void setUp() {
        blockchain = new Blockchain();
    }

    private String insertTestContent() {
        String content = "test content";
        blockchain.insert(content);
        return content;
    }

    @Test
    void shouldReturnGenesis_whenChainIsEmpty() {
        assertEquals(Block.createGenesisBlock().getHash(), blockchain.getLatest().getHash());
    }

    @Test
    void shouldHaveTwoBlocks_whenInsertNewBlock() {
        insertTestContent();

        assertEquals(2, blockchain.size());
    }

    @Test
    void shouldReturnLatestBlock_whenChainIsNotEmpty() {
        String content = insertTestContent();

        assertEquals(content, blockchain.getLatest().getContent());
    }

    @Test
    void shouldReturnNull_whenContentIsNotFound() {
        assertNull(blockchain.getByContent("unreal content"));
    }

    @Test
    void shouldReturnBlockWithContent_whenContentIsInBlockchain() {
        String content = insertTestContent();

        assertEquals(content, blockchain.getByContent(content).getContent());
    }

}
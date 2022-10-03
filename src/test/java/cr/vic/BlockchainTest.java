package cr.vic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockchainTest {
    private Blockchain blockchain;

    @BeforeEach
    void setUp() {
        blockchain = new Blockchain();
    }

    @Test
    void shouldReturnGenesis_whenChainIsEmpty() {
        assertEquals(Block.createGenesisBlock().getHash(), blockchain.getLatest().getHash());
    }

    @Test
    void shouldHaveTwoBlocks_whenInsertNewBlock() {
        blockchain.insert("test content");

        assertEquals(2, blockchain.size());
    }

    @Test
    void shouldReturnLatestBlock_whenChainIsNotEmpty() {
        String content = "test content";
        blockchain.insert(content);

        assertEquals(content, blockchain.getLatest().getContent());
    }
}
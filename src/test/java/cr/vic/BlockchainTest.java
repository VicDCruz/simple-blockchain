package cr.vic;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {
    @Test
    void shouldReturnNull_whenChainIsEmpty() {
        assertNull(getBlock());
    }

    private static Object getBlock() {
        List<Object> chain = new LinkedList<>();
        return null;
    }
}
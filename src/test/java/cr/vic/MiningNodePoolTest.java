package cr.vic;

import cr.vic.mining.MiningNodePool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiningNodePoolTest {
    @Test
    void shouldBeEmpty_whenJustInstantiated() {
        MiningNodePool miningNodePool = new MiningNodePool();

        assertEquals(0, miningNodePool.size());
    }

    @Test
    void shouldHaveSize10_whenFixedPoolIsCreated() {
        int size = 10;

        MiningNodePool miningNodePool = MiningNodePool.fixedMiningNodePool(size);

        assertEquals(size, miningNodePool.size());
        miningNodePool.printAllNodeNames();
    }
}
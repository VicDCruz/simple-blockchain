package cr.vic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodePoolTest {
    @Test
    void shouldBeEmpty_whenJustInstantiated() {
        NodePool nodePool = new NodePool();

        assertEquals(0, nodePool.size());
    }

    @Test
    void shouldHaveSize10_whenFixedPoolIsCreated() {
        int size = 10;

        NodePool nodePool = NodePool.fixedInstancesNodePool(size);

        assertEquals(size, nodePool.size());
        nodePool.printAllNodeNames();
    }
}
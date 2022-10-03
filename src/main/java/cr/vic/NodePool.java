package cr.vic;

import java.util.HashSet;
import java.util.Set;

public class NodePool {
    private final Set<Node> pool;

    public NodePool() {
        pool = new HashSet<>();
    }

    public int size() {
        return pool.size();
    }

    public void printAllNodeNames() {
        System.out.println("Print all node names");
        pool.forEach(node -> System.out.println("\t" + node.getName()));
        System.out.println("Finish all node names");
    }

    public static NodePool fixedInstancesNodePool(int size) {
        NodePool fixedPool = new NodePool();
        for (int i = 1; i <= size; i++) fixedPool.pool.add(Node.builder().name("Node Pool " + i).build());
        return fixedPool;
    }
}

package cr.vic;

import java.util.LinkedList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        chain = new LinkedList<>();
        chain.add(Block.createGenesisBlock());
    }

    public Block getLatest() {
        return chain.get(chain.size() - 1);
    }

    public int size() {
        return chain.size();
    }

    public void insert(Object content) {
        chain.add(Block.builder().content(content).previousBlock(getLatest()).build());
    }
}

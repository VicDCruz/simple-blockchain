package cr.vic;

import java.util.LinkedList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;

    public Blockchain() {
        chain = new LinkedList<>();
        chain.add(Block.createGenesisBlock());
    }

    public Block getLatest() {
        return chain.get(chain.size() - 1);
    }

    public Block getByContent(Object content) {
        return chain.stream().filter(block -> !block.isGenesis() && block.getContent().equals(content)).findFirst().orElse(null);
    }

    public int size() {
        return chain.size();
    }

    public void insert(Object content) {
        chain.add(Block.builder().content(content).previousBlock(getLatest()).build());
    }
}

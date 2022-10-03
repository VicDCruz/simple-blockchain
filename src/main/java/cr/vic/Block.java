package cr.vic;

import lombok.*;

import java.time.LocalDateTime;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@ToString
@NoArgsConstructor
public class Block {
    @Getter
    private String previousHash;
    @Getter
    private String hash;
    @Getter @Setter
    private Object content;
    @Getter @Setter
    private BlockTypeEnum type;
    @Getter
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Builder
    public Block(Block previousBlock, Object content) {
        this.previousHash = previousBlock.getHash();
        this.type = BlockTypeEnum.GENERAL;
        this.content = content;
        this.hash = generateHash();
    }

    public static Block createGenesisBlock() {
        Block genesis = new Block();
        genesis.setType(BlockTypeEnum.GENESIS);
        genesis.hash = genesis.type.toString();
        return genesis;
    }

    public String generateHash() {
        return sha256Hex(this.previousHash + '|' +
                                     this.content + '|' +
                                     this.type);
    }

    public void validate() {
        if (!generateHash().equals(hash)) invalidateBlock();
    }

    private void invalidateBlock() {
        System.out.println("Hash doesn't match actual content... invalidating");
        type = BlockTypeEnum.INVALID;
    }

    public boolean isGenesis() {
        return BlockTypeEnum.GENESIS == type;
    }
}

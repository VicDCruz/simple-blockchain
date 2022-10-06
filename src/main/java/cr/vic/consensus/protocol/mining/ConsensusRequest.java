package cr.vic.consensus.protocol.mining;

import cr.vic.block.Block;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsensusRequest {
    Block block;
    int prefixLength;
}

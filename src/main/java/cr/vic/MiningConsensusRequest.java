package cr.vic;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiningConsensusRequest {
    Block block;
    int prefixLength;
}

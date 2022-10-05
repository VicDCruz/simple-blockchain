package cr.vic;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MiningConsensusResponse {
    public int nonce;
    public long duration;
}

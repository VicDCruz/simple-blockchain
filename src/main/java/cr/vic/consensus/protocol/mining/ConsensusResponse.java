package cr.vic.consensus.protocol.mining;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ConsensusResponse {
    public int nonce;
    public long duration;
}

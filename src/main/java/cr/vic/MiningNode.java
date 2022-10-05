package cr.vic;

import lombok.*;

import java.util.concurrent.Callable;

@ToString
@AllArgsConstructor
public abstract class MiningNode implements Callable<MiningConsensusResponse>, Consensus {
    @Getter @Setter
    private String name;
    @Setter
    private MiningConsensusRequest miningRequest;

    @Override
    public MiningConsensusResponse call() {
        return mine(miningRequest);
    }
}

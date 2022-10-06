package cr.vic.mining;

import cr.vic.consensus.protocol.interfaces.ConsensusProtocol;
import cr.vic.consensus.protocol.mining.ConsensusResponseManager;
import lombok.*;

import java.util.concurrent.Callable;

@ToString
@RequiredArgsConstructor
public class MiningNode implements Callable<Boolean> { // Command pattern is similar to Callable/Runnable
    @Getter
    private final String name;
    @Setter
    private ConsensusProtocol protocol;

    @Override
    public Boolean call() {
        try {
            ConsensusResponseManager.addResponse(protocol.consensual());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

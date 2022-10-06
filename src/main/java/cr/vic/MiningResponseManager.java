package cr.vic;

import java.util.ArrayList;
import java.util.List;

public class MiningResponseManager {
    private static final List<MiningConsensusResponse> responses = new ArrayList<>();

    public static void start() {
        responses.clear();
    }

    public static synchronized void addResponse(MiningConsensusResponse response) {
        responses.add(response);
    }

    public static MiningConsensusResponse getWinner() {
        return responses.stream().min((m1, m2) -> {
            if (m1.nonce == m2.nonce) {
                if (m1.duration == m2.duration) return 0;
                return m1.duration < m2.duration ? -1 : 1;
            }
            return m1.nonce < m2.nonce ? -1 : 1;
        }).orElse(null);
    }
}

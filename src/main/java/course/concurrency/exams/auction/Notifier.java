package course.concurrency.exams.auction;

import java.util.concurrent.*;

public class Notifier {
    public void sendOutdatedMessage(Bid bid) {
        imitateSending(bid);
    }

    private void imitateSending(Bid bid) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
        });
    }

    public void shutdown() {}
}

package course.concurrency.exams.auction;

import java.util.concurrent.*;

public class Notifier {
    public void sendOutdatedMessage(Bid bid) {
        imitateSending(bid);
    }

    private void imitateSending(Bid bid) {
        CompletableFuture.runAsync(() -> {
            //System.out.println("Что-то шлем куда-то!" + bid.getPrice());
        });
    }

    public void shutdown() {}
}

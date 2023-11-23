package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AuctionPessimistic implements Auction {

    private Notifier notifier;

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
    }
    private final AtomicReference<Bid> latestBid = new AtomicReference<>();
    private final Lock lock = new ReentrantLock();

    public boolean propose(Bid bid) {
        if (latestBid.get() == null) {
            notifier.sendOutdatedMessage(bid);
            latestBid.set(bid);
        }
        lock.lock();
        try {
            if ( bid.getPrice() > latestBid.get().getPrice()) {
                notifier.sendOutdatedMessage(latestBid.get());
                latestBid.set(bid);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    public Bid getLatestBid() {
        return latestBid.get();
    }
}

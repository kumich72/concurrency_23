package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AuctionPessimistic implements Auction {

    private Notifier notifier;

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
    }
    private volatile Bid latestBid ;
    private final Lock lock = new ReentrantLock();

    public boolean propose(Bid bid) {
        if (latestBid == null) {
            notifier.sendOutdatedMessage(bid);
            latestBid = bid;
        }
        lock.lock();
        try {
            if ( bid.getPrice() > latestBid.getPrice()) {
                notifier.sendOutdatedMessage(latestBid);
                latestBid=bid;
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

    public Bid getLatestBid() {
        return latestBid;
    }
}

package course.concurrency.exams.auction;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AuctionPessimistic implements Auction {

    private Notifier notifier;

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private Bid latestBid;
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public boolean propose(Bid bid) {
        readWriteLock.writeLock().lock();
        try {
            if (latestBid == null || bid.getPrice() > latestBid.getPrice()) {
                notifier.sendOutdatedMessage(latestBid);
                latestBid = bid;
                return true;
            }
            return false;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public Bid getLatestBid() {
        readWriteLock.readLock().lock();
        try {
            return latestBid;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

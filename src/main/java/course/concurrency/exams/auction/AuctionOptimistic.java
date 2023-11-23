package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicReference;

public class AuctionOptimistic implements Auction {

    private Notifier notifier;

    public AuctionOptimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private final AtomicReference<Bid> latestBid = new AtomicReference<>();

    public boolean propose(Bid bid) {
        if (latestBid.get() == null) {
            latestBid.compareAndSet(null, bid);
            return true;
        }
        do {
            Bid currentBid = latestBid.get();
            if (bid.getPrice() > currentBid.getPrice()) {
                if (latestBid.compareAndSet(currentBid, bid)) {
                    return true;
                }
            } else {
                notifier.sendOutdatedMessage(currentBid);
                return false;
            }
        }
        while (true);
    }

    public Bid getLatestBid() {
        return latestBid.get();
    }
}
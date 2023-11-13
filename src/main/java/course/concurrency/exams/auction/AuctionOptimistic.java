package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicReference;

public class AuctionOptimistic implements Auction {

    private Notifier notifier;

    public AuctionOptimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private AtomicReference<Bid> latestBid;

    public boolean propose(Bid bid) {
        if (latestBid == null) {
            latestBid = new AtomicReference<>();
            latestBid.set(bid);
            return true;
        }
        while (true) {
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
    }

    public Bid getLatestBid() {
        return latestBid.get();
    }
}

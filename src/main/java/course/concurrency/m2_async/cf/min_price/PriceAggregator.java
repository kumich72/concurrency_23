package course.concurrency.m2_async.cf.min_price;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();
    private ExecutorService executor = Executors.newCachedThreadPool();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        List<CompletableFuture<Double>> pricesFutures = shopIds
                .stream()
                .map(shopId -> getPrice(itemId, shopId))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(pricesFutures.toArray(CompletableFuture[]::new))
                .thenApply(v -> pricesFutures
                        .stream()
                        .map(value -> value.join())
                        .min(Double::compare)
                        .get())
                .join();
    }

    private CompletableFuture<Double> getPrice(long itemId, long shopId) {
        return CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(shopId, itemId), executor)
                .exceptionally(x -> Double.NaN)
                .completeOnTimeout(Double.NaN, 2800, TimeUnit.MILLISECONDS);
    }
}

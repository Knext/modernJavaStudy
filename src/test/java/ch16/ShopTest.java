package ch16;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;

public class ShopTest {

    @Test
    public void test_shop_async() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invokeTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invokeTime return after: " + invokeTime + " msecs");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retriveTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after: " + retriveTime + " msecs");
    }

    @Test
    public void test_multiple_shop() {
        List<Shop> shops = List.of(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFaviriteShop"),
                new Shop("BuyItAll"));

        long start = System.nanoTime();
        String product = "product";
        List<CompletableFuture<String>> futurePrices =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->String
                        .format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        System.out.println(futurePrices);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in duration: " + duration + " msecs");

        List<String> result = futurePrices.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(result);
    }

}

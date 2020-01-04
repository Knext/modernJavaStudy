package ch16;

import java.util.*;
import java.util.concurrent.*;

public class Shop {
    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    private Random random = new Random(10000L);

    public double getPrice(String product) {
        return this.calculatePrice(product);
    }


    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        /*
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = this.calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
         */
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);

    }

    public String getName() {
        return this.name;
    }
}

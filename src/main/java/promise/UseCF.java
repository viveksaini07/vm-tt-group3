package promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class UseCF {
    public static void delay() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static String supplier() {
        System.out.println(Thread.currentThread().getName() + " supplier");
        delay();
        System.out.println("supplier completing");
        return "Supplied value";
    }

    public static String dodgy(String s) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException("Broken!!!!");
        } else return s;
    }
    public static void main(String[] args) {
        var cf = CompletableFuture.supplyAsync(UseCF::supplier);

        var cf1 = cf
                .thenApplyAsync(UseCF::dodgy)
                .thenApplyAsync(s -> s.toUpperCase())
                .handleAsync((v, p) -> {
                    if (v != null) return "Successful: " + v;
                    else return "Recovered: " + p.getMessage();
                })
                .thenAcceptAsync(System.out::println);

        var cf2 = cf
                .thenApplyAsync(s -> s.length())
                .thenAcceptAsync(n -> System.out.println("length is " + n));

        System.out.println("Pipeline constructed");

        cf1.join();
        cf2.join();
    }
}

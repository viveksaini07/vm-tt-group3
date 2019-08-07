package promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class NonBlocking {
    public static void delay() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> readAFile(String name) {
        CompletableFuture<String> cfs = new CompletableFuture<>();
        System.out.println("Starting background read");
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " blocking to read a file");
            delay();
            System.out.println("data read from file :)");
            cfs.complete("This is the contents of file " + name);
        }).start();
        System.out.println("background read launched..");
        return cfs;
    }

    public static void main(String[] args) {
        var cf = CompletableFuture.supplyAsync(() -> {
            int x = ThreadLocalRandom.current().nextInt(1, 10);
            return "Filename" + x + ".txt";
        })
//                .thenApplyAsync(NonBlocking::readAFile)
                .thenComposeAsync(NonBlocking::readAFile)
                .thenAcceptAsync(System.out::println);

        System.out.println("Pipeline constructed");
        cf.join();
    }
}

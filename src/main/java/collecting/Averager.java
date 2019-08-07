package collecting;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class Average {
    private double sum = 0;
    private long count = 0;

    public Average() {
    }

    public void include(double d) {
        this.sum += d;
        this.count++;
    }

    public void merge(Average other) {
        this.sum += other.sum;
        this.count += other.count;
    }

    public Optional<Double> get() {
//        return sum / count; // => Double.POSITIVE_INFINITY or something like that: Sentinel value!!!
        if (count > 0) return Optional.of(sum / count);
        else return Optional.empty();
    }
}

public class Averager {
    public static void main(String[] args) {
        long start = System.nanoTime();

        ThreadLocalRandom.current().doubles(-Math.PI, Math.PI)
                .limit(1_500_000_000L)
                .parallel()
                .map(Math::sin)
                .collect(() -> new Average(), (a, d) -> a.include(d), (aFinal, a) -> aFinal.merge(a))
                .get()
                .ifPresent(v -> System.out.println("Average is " + v));
        ;
        long time = System.nanoTime() - start;
        System.out.printf("Time taken %8.6f\n", (time / 1_000_000_000.0));
    }
}

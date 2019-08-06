package problems;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@FunctionalInterface
interface ExFunction<E, F> {
    F apply(E e) throws Throwable;

    static <E,F> Function<E, Optional<F>> wrap(ExFunction<E, F> op) {
        return e -> {
            try {
                return Optional.of(op.apply(e));
            } catch (Throwable t) {
                return Optional.empty();
            }
        };
    }
}

public class UseStream {
    static final Pattern WORD_BREAK = Pattern.compile("\\W+");

//    public static Optional<Stream<String>> getLines(String filename) {
//        try {
//            return Optional.of(Files.lines(Paths.get(filename)));
//        } catch (Throwable t) {
////            throw new RuntimeException(t);
//            return Optional.empty();
//        }
//    }

    public static void main(String[] args) {
//        Stream.of("Hello there", "here is more text")
//        Stream.<String>of()
        Stream.of("A.txt", "B.txt", "C.txt")
//                .flatMap(n -> Files.lines(Paths.get(n)))
//                .flatMap(n -> UseStream.getLines(n))
//                .map(n -> UseStream.getLines(n))
                .map(ExFunction.wrap(n -> Files.lines(Paths.get(n))))
                .peek(opt -> {if (opt.isEmpty()) System.out.println("PROBLEM");})
                .filter(Optional::isPresent)
                .flatMap(Optional::get)
                .flatMap(WORD_BREAK::splitAsStream)
                .map(String::toUpperCase)
                .filter(s -> s.length() > 2)
                .forEach(System.out::println);
    }
}

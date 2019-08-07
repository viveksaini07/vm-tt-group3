package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

class LongString implements Predicate<String> {
    public boolean test(String s) {
        return s.length() > 3;
    }
}

public class UsePredicate {
    public static Predicate<String> negate(Predicate<String> ps) {
        return s -> !ps.test(s);
    }

    public static Predicate<String> earlyFirstLetter(/*final */char threshold) {
//        threshold++;
        return s -> s.charAt(0) < threshold;
    }


    public static List<String> filter(List<String> in, Predicate<String> pred) {
        List<String> out = new ArrayList<>();
        for (String s : in) {
            if (pred.test(s)) {
                out.add(s);
            }
        }
        return out;
    }

    public static void main(String[] args) {
        List<String> ls = Arrays.asList("Fred", "Jim", "Sheila");

        ls.forEach(x -> System.out.println(x));
        System.out.println("---------------------");

        filter(ls, new LongString()).forEach(x -> System.out.println(x));
        System.out.println("---------------------");

        filter(ls, x -> x.length() < 6).forEach(x -> System.out.println(x));
        System.out.println("---------------------");

        Predicate<String> early = earlyFirstLetter('J');
        filter(ls, early).forEach(x -> System.out.println(x));
        System.out.println("---------------------");

        filter(ls, negate(early)).forEach(x -> System.out.println(x));
        System.out.println("---------------------");
    }
}

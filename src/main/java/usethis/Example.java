package usethis;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Example {
    public static void main(String[] args) {
        ToIntFunction<String> strlen = new ToIntFunction<>() {
            public int applyAsInt(String s) {
                if ("".equals(s)) return 0;
                return this.applyAsInt(s.substring(1)) + 1;
            }
        };

//        ToIntFunction<String> strlen2 = (String s) -> {
//            if ("".equals(s)) return 0;
//            return this.applyAsInt(s.substring(1)) + 1;
//        };

        System.out.println("length of hello is " + strlen.applyAsInt("hello"));

    }
}

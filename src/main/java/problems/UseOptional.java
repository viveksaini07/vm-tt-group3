package problems;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UseOptional {
    public static void main(String[] args) {
        Map<String, String> names = new HashMap<>();
        names.put("Fred", "Jones");

        final String firstName = "Fred";

        String lastName = names.get(firstName);
        if (lastName != null) {
            lastName = lastName.toUpperCase();
            String greeting = "Dear " + lastName;
            System.out.println(greeting);
        }

        Optional.of(names)
                .map(m -> m.get(firstName))
                .map(n -> n.toUpperCase())
                .map(n -> "Dear " + n)
                .ifPresent(System.out::println);

    }
}

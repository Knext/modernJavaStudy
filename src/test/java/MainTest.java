import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MainTest {

    @Test
    public void findFirst() {
        List<Integer> numbers = List.of(1,2,3,4,5);
        numbers.parallelStream().map(n -> n *n)
                .filter(n -> n %3 == 0)
                .findFirst()
                .ifPresent( i -> System.out.println("FindFirst:" + i));

        int total = numbers.parallelStream().reduce(0, Integer::sum);
        System.out.println("Sum:" + total);


        Optional<Integer> sum = numbers.parallelStream().reduce(Integer::sum);
        sum.ifPresent( i -> System.out.println("Reduce Sum Result:" + i));

        Optional<Integer> max = numbers.parallelStream().reduce(Integer::max);
        max.ifPresent( i -> System.out.println("Reduce Max Result:" + i));
    }
}

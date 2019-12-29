package ch7;

import java.util.function.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;

public class ParallelTest {

    @Test
    public void test_parallel() {
        calculatorCheck(100_000_000L, this::forLoopSum);
        calculatorCheck(100_000_000L, this::sequentialSum);
        calculatorCheck(100_000_000L, this::parallelSum);
    }

    private void calculatorCheck(long limit, Function<Long, Long> sumFunc) {
        long start = System.currentTimeMillis();
        long sum = sumFunc.apply(limit);
        long end = System.currentTimeMillis() - start;
        System.out.println(sumFunc.toString() + " result:" + sum + " takes: " + end);
    }

    private long forLoopSum(long n) {
        long sum = 0L;
        for (long i= 1L; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    private long sequentialSum(long n) {
        return LongStream.rangeClosed(1L,n)
                .reduce(0L, Long::sum);
    }

    private long parallelSum(long n) {
        return LongStream.rangeClosed(1L,n)
                .parallel()
                .reduce(0L, Long::sum);
    }
}

package ch8;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ch5.*;
import java.util.*;
import java.util.Map.*;
import org.junit.jupiter.api.*;

public class CollectionsTest {

    @Test
    @DisplayName("Map.ofEnties() 중복 key를 넣었을 때 Duplicate Exception이 발생하는지 확인")
    public void test_duplicate_key() {
        assertThrows(IllegalArgumentException.class, () -> Map.ofEntries(
                entry("a", 10), entry("a", 11)));
    }

    @Test
    @DisplayName("동기화 에러 발생 코드, UnsupportedOperationException 발생")
    public void test_concurrent_exception() {
        //before java8
        List<Transaction> transactions = CollectResultTest.transactionBuilder();
        assertThrows(UnsupportedOperationException.class, () -> {
                    for (Transaction transaction : transactions) {
                        if (transaction.getTrader().getName().equals("Raoul")) {
                            transactions.remove(transaction);
                        }
                    }
                }
        );

        //after java8, exception
        transactions.removeIf(transaction -> transaction.getTrader().getName().equals("Raoul"));
    }

    @Test
    public void test_map_sort() {
        Map<String, String> result = Map
                .ofEntries(entry("C", "beta"), entry("A", "test"), entry("B", "alpha"));

        result.entrySet().stream()
                .sorted(Entry.comparingByKey())
                .forEachOrdered(System.out::println);
    }
}

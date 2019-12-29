package ch5;

import static java.util.Comparator.*;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;

public class CollectResultTest {
    private List<Transaction> transactions;

    public static List<Transaction> transactionBuilder() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        return Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @BeforeEach
    public void setup() {
        transactions = transactionBuilder();
    }

    @Test
    @DisplayName("2011년에 일어난 모든 트랜잭선을 찾아 오름차순으로 정리")
    public void findAllin2011() {
        transactions.stream()
                .filter(t -> Objects.equals(t.getYear(), 2011))
                .sorted(comparing(Transaction::getValue))
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("거래자 근무 도시를 중복없이 나열")
    public void findDistinctCityOfEmployee() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("케임브리지 근무하는 모든 거래자 이름순 정렬")
    public void findAllEmployee() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .map(Trader::getName)
                .sorted()
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("모든 거래자 이름순 정렬")
    public void findAllEmployeeOrderByName() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("밀라노에 거래자가 존재하는가?")
    public void findInMilano() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .filter(city -> city.equals("Milan"))
                .findAny()
                .ifPresentOrElse(City -> System.out.println("Exist " + City), () -> System.out.println("No Milan"));
    }

    @Test
    @DisplayName("전체 트랜잭션중 최대값?")
    public void findMaxTransaction() {
        transactions.stream()
                .max(comparing(Transaction::getValue))
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("전체 트랜잭션중 최소값?")
    public void findMinTransaction() {
        transactions.stream()
                .min(comparing(Transaction::getValue))
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("피타고라스 수 집합 생성")
    public void mamePythaNumber() {
        Stream<double[]> pytha = IntStream.rangeClosed(1,100).boxed()
                .flatMap(a -> IntStream.rangeClosed(1, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
                        .filter(t-> t[2] %1 == 0));

        pytha.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

}

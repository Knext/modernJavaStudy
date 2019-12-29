package ch6;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

import ch6.Dish.*;
import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;

public class DishTest {

    @Test
    @DisplayName("reduce, redicing을 이용해서 Joining을 하는 다양한 방법.")
    public void test_joining_by_resucing() {
        String result = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println(result);

        String resultByReducing = Dish.menu.stream()
                .map(Dish::getName)
                .reduce((s1, s2) -> s1 + s2)
                //collect(reducing((s1, s2) -> s1 + s2)) //This works well too.
                .orElse("");
        assertEquals( resultByReducing, result);

        String resultByReducing2 = Dish.menu.stream()
                //.map(Dish::getName)
                //.reduce("", (s1, s2) -> s1 + s2);
                .collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2)); //This works well too.

        assertEquals( resultByReducing2, result);
    }

    @Test
    @DisplayName("Grouping")
    public void test_grouping() {
        Map<Type, List<Dish>> result = Dish.menu.stream()
                .collect(groupingBy(Dish::getType));

        System.out.println("Size:" + result.size());
        System.out.println(result);
    }

    @Test
    @DisplayName("Grouping With Filtering")
    public void test_grouping_with_filter() {
        //Type : MEAT, FISH, OTHER
        Map<Type, List<Dish>> resultExcludeFish = Dish.menu.stream()
                .filter(dish -> dish.getCalories() > 500)
                .collect(groupingBy(Dish::getType));

        System.out.println(resultExcludeFish);

        Map<Type, List<Dish>> resultWithFish = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));

        System.out.println(resultWithFish);

        Map<Type, List<String>> dishNameByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(Dish::getName, toList())));


        System.out.println(dishNameByType);
    }

    @Test
    @DisplayName("Grouping With Flatmapping")
    public void test_grouping_with_flatmapping() {
        Map<Type, Set<String>> dishNamesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType
                        , flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream(),
                                toSet())));
        System.out.println(dishNamesByType);

        //flatmapping Stream<List<String>> tp Stream<String>
        List<String> list = Dish.dishTags.keySet().stream()
                .map(key -> Dish.dishTags.get(key))
                .flatMap(Collection::stream)
                .collect(toList());

        System.out.println(list);
    }

    @Test
    public void test_partitioning() {
        //Map<Boolean, Map<Boolean, List<Dish>>
        Map<Boolean, Map<Boolean, List<Dish>>> vegeByCaloriesOver500 = Dish.menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getCalories() > 500)));

        //Map<Boolean, Map<Type, List<Dish>>
        Map<Boolean, Map<Type, List<Dish>>> vegeByType = Dish.menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));

        //Map<Boolean, Long>
        Map<Boolean, Long> vegeCount = Dish.menu.stream().collect(
                partitioningBy(Dish::isVegetarian, counting()));
    }

    @Test
    public void test_prime() {
        Map<Boolean, List<Integer>> partitionedByPrime = partitionByPrime(100);
        System.out.println(partitionedByPrime);

    }

    @Test
    public void test_custom_collector() {
        List<Dish> result = Dish.menu.stream().collect(new ToListCollector<>());
        System.out.println(result);

        List<Dish> result2 = Dish.menu.stream()
                .collect(ArrayList::new, List::add, List::addAll);
        System.out.println(result2);
    }

    private boolean isPrime(int number) {
        int candidate = (int)Math.sqrt((double)number);
        return IntStream.rangeClosed(2, candidate)
                .takeWhile(i -> i <= number)
                .noneMatch(i -> number % i == 0);
    }

    private Map<Boolean, List<Integer>> partitionByPrime(int number) {
        return IntStream.rangeClosed(2, number).boxed().collect(
                partitioningBy(this::isPrime)
        );
    }

}

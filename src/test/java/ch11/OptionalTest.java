package ch11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.*;

public class OptionalTest {

    @Test
    @DisplayName("두개의 optional을 하나로 합치기")
    public void mergeOptional() {
        Optional<Insurance> insurance = Optional.of(new Insurance("Ever Ever"));
        insurance.filter(ins -> "Ever Ever".equals(ins.getName()))
                .ifPresent(System.out::println);
    }


    @Test
    public void testMap() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        assertEquals(5, readDurationImperative(props, "a"));
        assertEquals(0, readDurationImperative(props, "b"));
        assertEquals(0, readDurationImperative(props, "c"));
        assertEquals(0, readDurationImperative(props, "d"));

        assertEquals(5, readDurationWithOptional(props, "a"));
        assertEquals(0, readDurationWithOptional(props, "b"));
        assertEquals(0, readDurationWithOptional(props, "c"));
        assertEquals(0, readDurationWithOptional(props, "d"));
    }

    private static int readDurationImperative(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) {
            }
        }
        return 0;
    }

    private int readDurationWithOptional(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalTest::strToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

    private static Optional<Integer> strToInt(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

    private String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() > minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}

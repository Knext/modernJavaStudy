package ch7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;

public class WordCounterTest {

    String input = "Nel mezzo del cammin di nostra vita "
            + "Nel mezzo del cammin di nostra vita "
            + "Nel mezzo del cammin di nostra vita";

    @Test
    public void wordCountTest() {
        System.out.println(WordCounter.countWordsIteratively(input));
    }

    @Test
    public void wordCountClassTest() {
        Stream<Character> stream = IntStream.range(0, input.length())
                .mapToObj(input::charAt);
        int expected = countWords(stream); //sequential

        Spliterator<Character> spliterator = new WordCounterSpliterator(input);
        Stream<Character> stream1 = StreamSupport.stream(spliterator, true);
        int actual = countWords(stream1); //parallel

        assertEquals(expected, actual);
    }

    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }


}

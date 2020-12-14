package com.github.rkharisov.task2;

import com.github.rkharisov.task2.Deduplicator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeduplicatorTest {

    public static final Random RANDOM = new Random();

    @Test
    void whenInputIsEmptyThenOutputIsEmptyToo_sequential() {
        Deduplicator sut = new Deduplicator();

        final var actualResult = sut.deduplicateSequential(List.of(), 6);
        assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void whenInputIsEmptyThenOutputIsEmptyToo_noneSequential() {
        Deduplicator sut = new Deduplicator();

        final var actualResult = sut.deduplicateNoneSequential(List.of(), 6);
        assertEquals(Collections.emptyList(), actualResult);
    }

    @RepeatedTest(10)
    void whenAllElementsUniqueThenOutputEqualsInput_sequential() {
        Deduplicator sut = new Deduplicator();

        final var input = Stream.generate(RANDOM::nextInt)
                .distinct()
                .limit(1000)
                .collect(Collectors.toList());
        final var actualResult = sut.deduplicateSequential(input, 2);
        Assertions.assertEquals(input, actualResult);
    }

    @RepeatedTest(10)
    void whenAllElementsUniqueThenOutputEqualsInput_noneSequential() {
        Deduplicator sut = new Deduplicator();

        final var input = Stream.generate(RANDOM::nextInt)
                .distinct()
                .limit(1000)
                .collect(Collectors.toList());
        final var actualResult = sut.deduplicateNoneSequential(input, 2);
        Assertions.assertEquals(input, actualResult);
    }

    @Test
    void whenInputIsNullThenExceptionIsThrown() {
        Deduplicator sut = new Deduplicator();
        assertThrows(NullPointerException.class, () -> sut.deduplicateSequential(null, 3));
        assertThrows(NullPointerException.class, () -> sut.deduplicateNoneSequential(null, 3));
    }

    @Test
    void whenDedupFactorIsNegativeThenExceptionIsThrown() {
        Deduplicator sut = new Deduplicator();
        assertThrows(IllegalArgumentException.class, () -> sut.deduplicateSequential(List.of(), -3));
        assertThrows(IllegalArgumentException.class, () -> sut.deduplicateNoneSequential(List.of(), -3));
    }

    @Test
    void whenDedupFactorIsZeroThenEmptyOutput_sequential() {
        final Deduplicator deduplicator = new Deduplicator();
        final var actualResult = deduplicator.deduplicateSequential(List.of(1, 2, 3, 4, 5), 0);
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void whenDedupFactorIsZeroThenEmptyOutput_noneSequential() {
        final Deduplicator deduplicator = new Deduplicator();
        final var actualResult = deduplicator.deduplicateNoneSequential(List.of(1, 2, 3, 4, 5), 0);
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void whenDuplicatesExistsThenTheyAreRemoved_sequential() {
        Deduplicator sut = new Deduplicator();
        final var input = List.of(-5, -5, -5, -5, 88, 88, 88, 77, 77, 1, 9, 9, 11, 11, 11);

        final var actualResult = sut.deduplicateNoneSequential(input, 3);
        final var expectedResult = List.of(77, 77, 1, 9, 9);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void whenDuplicatesExistsThenTheyAreRemoved_nonSequential() {
        Deduplicator sut = new Deduplicator();
        final var input = List.of(-12, -12, 0, 54, 101, 54, 0, -12, 37, 86, 37, 37, -12, 54, 1, 54, 86);

        final var actualResult = sut.deduplicateNoneSequential(input, 3);
        final var expectedResult = List.of(0, 101, 0, 86, 1, 86);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}

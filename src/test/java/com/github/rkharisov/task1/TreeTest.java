package com.github.rkharisov.task1;

import com.github.rkharisov.task1.Tree;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeTest {

    public static final Random RANDOM = new Random();

    @Test
    void whenTreeIsEmptyThenHeightIsZero() {
        Tree sut = new Tree();
        assertEquals(0, sut.getHeight());
    }

    @Test
    void whenTreeIsNotEmptyThenCorrectHeightIsComputed() {
        Tree sut = new Tree(
                new Tree(
                        new Tree()
                ),
                new Tree(
                        new Tree(
                                new Tree(),
                                new Tree()
                        )
                )
        );
        assertEquals(3, sut.getHeight());
    }

    @Test
    void whenChildsIsNullThenErrorIsThrown() {
        assertThrows(NullPointerException.class, () -> new Tree((Tree) null));
        assertThrows(NullPointerException.class, () -> new Tree((Set<Tree>) null));
    }

    @RepeatedTest(1000)
    void randomTest() {
        final int expectedHeight = RANDOM.nextInt(90) + 10;
        Tree sut = new Tree(generateTreeWithHeight(expectedHeight));

        assertEquals(expectedHeight, sut.getHeight());
    }

    private Collection<Tree> generateTreeWithHeight(int height) {
        if (height <= 0) {
            return Set.of();
        }
        final int childsCount = RANDOM.nextInt(99) + 1;
        final var childs = Stream.generate(Tree::new)
                .limit(childsCount)
                .collect(Collectors.toList());
        final Tree tree = childs.get(RANDOM.nextInt(childsCount));
        tree.setChilds(generateTreeWithHeight(height - 1));
        return childs;
    }
}



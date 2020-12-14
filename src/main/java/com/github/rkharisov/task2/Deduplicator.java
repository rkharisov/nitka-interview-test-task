package com.github.rkharisov.task2;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

public class Deduplicator {

    /**
     * Remove duplicates from list if their total count (in any place) greater or equals to {@code dedupFactor}
     * This implementation preserve original order of list
     * Examples with deduplication factor = 2
     * [1, 2, 3, 2, 3, 3] -> [1]
     * [1, 2, 3] -> [1, 2, 3]
     * [] -> []
     *
     * @param source - list with possible duplicates to remove
     * @param dedupFactor - threshold of duplicates count to remove
     * @return - list of removed duplicates
     */
    public List<Integer> deduplicateNoneSequential(List<Integer> source, int dedupFactor) {
        checkArgs(source, dedupFactor);
        if (dedupFactor == 0) {
            return Collections.emptyList();
        }
        Map<Integer, Long> valuesCount = source.stream()
                .collect(groupingBy(identity(), HashMap::new, Collectors.counting()));

        var resultList = new LinkedList<Integer>();
        for (Integer value : source) {
            if (valuesCount.get(value) < dedupFactor) {
                resultList.add(value);
            }
        }
        return resultList;
    }

    /**
     * Remove duplicates from list if their sequentially  total count  greater or equals to {@code dedupFactor}
     * This implementation preserve original order of list
     * Examples with deduplication factor = 2
     * [1, 2, 2, 3, 3, 3] -> [1]
     * [1, 2, 3] -> [1, 2, 3]
     * [] -> []
     *
     * @param source - list with possible duplicates to remove
     * @param dedupFactor - threshold of duplicates count to remove
     * @return - list of removed duplicates
     */
    public List<Integer> deduplicateSequential(List<Integer> source, int dedupFactor) {
        checkArgs(source, dedupFactor);
        if (dedupFactor == 0) {
            return Collections.emptyList();
        }
        var listIterator = source.listIterator();
        List<Integer> resultList = new ArrayList<>(source.size());
        Integer cursor = null;
        Integer prevCursor = null;
        while (listIterator.hasNext()) {
            int repetitions = 1;
            while (listIterator.hasNext() && ((cursor = listIterator.next()).equals(prevCursor))) {
                repetitions++;
            }
            if (repetitions < dedupFactor && prevCursor != null) {
                //here if no more repetitions
                resultList.addAll(Collections.nCopies(repetitions, prevCursor));
            }
            if (!listIterator.hasNext() && !cursor.equals(prevCursor)) {
                //special case for the last element
                resultList.add(cursor);
            }
            prevCursor = cursor;
        }
        return resultList;
    }

    private void checkArgs(List<Integer> source, int dedupFactor) {
        Objects.requireNonNull(source, "Source can not be null");
        if (dedupFactor < 0) {
            throw new IllegalArgumentException("Dedup factor can not be negative");
        }
    }
}

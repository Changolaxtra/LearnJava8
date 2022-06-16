package com.epam.cdp.m2.hw2.aggregator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.epam.cdp.m2.hw2.aggregator.comparator.StringLengthComparator;
import javafx.util.Pair;

public class Java8ParallelAggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return Optional.ofNullable(numbers)
            .orElse(new ArrayList<>())
            .parallelStream()
            .reduce(0, Integer::sum);
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        return Optional.ofNullable(words)
            .orElse(new ArrayList<>())
            .parallelStream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .parallelStream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
            .limit(limit)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        final Set<String> controlSet = Collections.synchronizedSet(new HashSet<>());
        return Optional.ofNullable(words)
            .orElse(new ArrayList<>())
            .parallelStream()
            .map(String::toUpperCase)
            .filter(word -> !controlSet.add(word))
            .sorted(new StringLengthComparator())
            .limit(limit)
            .collect(Collectors.toList());
    }
}

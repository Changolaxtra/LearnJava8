package com.epam.cdp.m2.hw2.aggregator;

import com.epam.cdp.m2.hw2.aggregator.comparator.StringLengthComparator;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Java7Aggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {
    int sum = 0;
    if (numbers != null) {
      for (Integer number : numbers) {
        sum += number != null ? number : 0;
      }
    }
    return sum;
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    final TreeMap<String, Long> wordMap = new TreeMap<>();
    final List<Pair<String, Long>> result = new ArrayList<>();
    for (String word : words) {
      if (wordMap.containsKey(word)) {
        wordMap.put(word, wordMap.get(word) + 1L);
      } else {
        wordMap.put(word, 1L);
      }
    }
    for (Map.Entry<String, Long> entry : wordMap.entrySet()) {
      if (result.size() < limit) {
        result.add(new Pair<>(entry.getKey(), entry.getValue()));
      }
    }
    return result;
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    final TreeSet<String> resultSet = new TreeSet<>(new StringLengthComparator());
    final Set<String> controlSet = new HashSet<>();
    final List<String> result = new ArrayList<>();
    for (String word : words) {
      final String upperWord = word.toUpperCase();
      if (!controlSet.add(upperWord) && resultSet.size() < limit) {
        resultSet.add(upperWord);
      }
    }
    while (!resultSet.isEmpty()) {
      result.add(resultSet.pollFirst());
    }
    return result;
  }
}

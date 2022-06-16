package com.epam.cdp.m2.hw2.aggregator.fjp;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;

@RequiredArgsConstructor
public class WordFrequencyRecursiveTask extends RecursiveTask<SortedMap<String, Long>> {

  private static final int LIMIT = 10;

  private final List<String> words;

  @Override
  protected SortedMap<String, Long> compute() {
    final SortedMap<String, Long> wordMap = Collections.synchronizedSortedMap(new TreeMap<>());
    if (CollectionUtils.isNotEmpty(words)) {
      if (words.size() <= LIMIT) {
        for (String word : words) {
          if (wordMap.containsKey(word)) {
            wordMap.put(word, wordMap.get(word) + 1L);
          } else {
            wordMap.put(word, 1L);
          }
        }
      } else {
        final List<List<String>> partitions = ListUtils.partition(words, LIMIT);
        for (List<String> partition : partitions) {
          final WordFrequencyRecursiveTask subTask = new WordFrequencyRecursiveTask(partition);
          subTask.fork();
          mergeMaps(wordMap, subTask.join());
        }
      }
    }
    return wordMap;
  }

  private void mergeMaps(final SortedMap<String, Long> targetMap, final SortedMap<String, Long> newMap) {
    for (String word : newMap.keySet()) {
      if (targetMap.containsKey(word)) {
        targetMap.put(word, targetMap.get(word) + 1L);
      } else {
        targetMap.put(word, 1L);
      }
    }
  }

}

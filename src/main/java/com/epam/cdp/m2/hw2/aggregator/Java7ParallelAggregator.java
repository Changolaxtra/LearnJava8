package com.epam.cdp.m2.hw2.aggregator;

import com.epam.cdp.m2.hw2.aggregator.fjp.SumListRecursiveTask;
import com.epam.cdp.m2.hw2.aggregator.fjp.WordFrequencyRecursiveTask;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

public class Java7ParallelAggregator implements Aggregator {

  @Override
  public int sum(List<Integer> numbers) {
    int sum;
    try {
      final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
      final Future<Integer> result = forkJoinPool.submit(new SumListRecursiveTask(numbers));
      sum = result.get();
    } catch (InterruptedException | ExecutionException e) {
      sum = -1;
    }
    return sum;
  }

  @Override
  public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
    List<Pair<String, Long>> resultList = new ArrayList<>();
    try {
      final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
      final ForkJoinTask<SortedMap<String, Long>> result =
          forkJoinPool.submit(new WordFrequencyRecursiveTask(words));
      final SortedMap<String, Long> wordCountTreeMap = result.get();
      for (Map.Entry<String, Long> entry : wordCountTreeMap.entrySet()) {
        if (resultList.size() < limit) {
          resultList.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      resultList = null;
    }
    return resultList;
  }

  @Override
  public List<String> getDuplicates(List<String> words, long limit) {
    throw new UnsupportedOperationException();
  }
}

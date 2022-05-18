package com.epam.cdp.m2.hw2.aggregator.fjp;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.concurrent.RecursiveTask;

@RequiredArgsConstructor
public class SumListRecursiveTask extends RecursiveTask<Integer> {

  private static final int LIMIT = 4;

  private final List<Integer> numbers;

  @Override
  protected Integer compute() {
    int result = 0;
    if (CollectionUtils.isNotEmpty(numbers)) {
      if (numbers.size() <= LIMIT) {
        for (Integer number : numbers) {
          result += number;
        }
      } else {
        final List<List<Integer>> partitions = ListUtils.partition(numbers, LIMIT);
        for (List<Integer> partition : partitions) {
          final SumListRecursiveTask subTask = new SumListRecursiveTask(partition);
          subTask.fork();
          result += subTask.join();
        }
      }
    }
    return result;
  }

}

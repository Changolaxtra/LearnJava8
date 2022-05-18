package com.epam.cdp.m2.hw2.aggregator.comparator;

import java.util.Comparator;

public class StringLengthComparator implements Comparator<String> {
  @Override
  public int compare(String o1, String o2) {
    int result = Integer.compare(o1.length(), o2.length());
    if (result == 0) {
      result = o1.compareTo(o2);
    }
    return result;
  }
}

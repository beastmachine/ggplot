package org.beastmachine.util;

import static java.lang.Math.max;

public class NumberFormatter {
  private static final int ZEROS_LIMIT=5;

  public static String format(double d) {
    String naive = String.format("%f",d);
    int decimal = naive.length();
    for (int ii=0; ii<naive.length(); ii++) {
      char c = naive.charAt(ii);
      if (c == '.') {
        decimal = ii;
        break;
      }
    }
    
    int firstZero = -1;
    int zeroCount = 0;
    for (int ii=0; ii<naive.length(); ii++) {
      char c = naive.charAt(ii);
      if (c == '.') {
        continue;
      } else if (c=='0') {
        if (zeroCount==0) {
          firstZero = ii;
        }
        zeroCount++;
      } else if (zeroCount >= ZEROS_LIMIT) {
        naive = naive.substring(0,max(firstZero,decimal));
        if (naive.endsWith(".")) {
          naive = naive.substring(0,naive.length()-1);
        }
        return naive;
      } else {
        zeroCount = 0;
        firstZero = -1;
      }
    }
    return naive;
  }
}

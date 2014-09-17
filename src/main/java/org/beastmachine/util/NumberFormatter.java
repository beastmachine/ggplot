package org.beastmachine.util;

import static java.lang.Math.max;

public class NumberFormatter {
	private static final int ZEROS_LIMIT=5;


	public static String format(double d) {
		String naive = String.valueOf(d);
		String postFix = "";
		if(naive.contains("E")){
			postFix = naive.subSequence(naive.indexOf("E"), naive.length()).toString();
			naive = naive.subSequence(0, naive.indexOf("E")).toString();
		}
		
		boolean previousNonzero = false;
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
			System.out.println("car "+c);
			if (c == '.' || c == '-') {
				continue;
			} else if (c=='0') {
				if(previousNonzero){
					if (zeroCount==0) {
						firstZero = ii;
					}
					zeroCount++;
				}

			} else {
				System.out.println(c);
				previousNonzero = true;
				zeroCount = 0;
				firstZero = -1;
			}
			
			if (zeroCount >= ZEROS_LIMIT) {
				naive = naive.substring(0,max(firstZero, decimal));
				if (naive.endsWith(".")) {
					naive = naive.substring(0,naive.length()-1);
				}
				return naive+postFix;
			}
		}
		return naive+postFix;
	}

	public static void main(String[] args) {
		System.out.println(format(-11));
	}

}

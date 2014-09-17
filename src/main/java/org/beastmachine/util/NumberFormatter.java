package org.beastmachine.util;

import static java.lang.Math.max;

public class NumberFormatter {
	private static final int ZEROS_LIMIT=5;


	public static String format(double d) {
		String naive = String.valueOf(d);
		String postFix = "";
		System.out.println(naive);
		if(naive.contains("E")){
			//special case for #.0E#
			if(naive.subSequence(naive.indexOf("."),naive.indexOf(".") + 2).toString().equals(".0")){
				postFix = ".0";
			}
			//Otherwise just keep the E#
			postFix += naive.subSequence(naive.indexOf("E"), naive.length()).toString();
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
			if (c == '.' || c == '-') {
				continue;
			} else if (c=='0') {
				if(previousNonzero){
					if (zeroCount==0) {
						firstZero = ii;
					}
					zeroCount++;
					if(ii == naive.length()-1){
						naive = naive.substring(0,Math.min(firstZero, decimal));
					}
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
		System.out.println(format(-1.0));
	}

}

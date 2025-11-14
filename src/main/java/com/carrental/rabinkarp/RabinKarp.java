package com.carrental.rabinkarp;

import java.util.ArrayList;
import java.util.List;

/**
 * Rabin–Karp string matching using polynomial rolling hash.
 * Time: O(n + m) average
 * Space: O(n)
 */
public class RabinKarp {

    private static final int P = 31;  // base for polynomial hash
    private static final int M = 1_000_000_009;  // large prime modulus
    private static int hashComparisons = 0; // counter for operations

    public static int getHashComparisons() {
        return hashComparisons;
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        hashComparisons = 0; // reset before each test

        // Guard clause
        if (text == null || pattern == null || pattern.isEmpty()
                || text.isEmpty() || pattern.length() > text.length()) {
            return matches;
        }

        int n = text.length(), m = pattern.length();

        long patternHash = computeHash(pattern);
        long[] prefixHash = new long[n + 1];
        long[] pow = new long[n + 1];
        pow[0] = 1;

        // Precompute prefix hashes and powers
        for (int i = 0; i < n; i++) {
            prefixHash[i + 1] =
                    (prefixHash[i] + (long) text.charAt(i) * pow[i]) % M;
            pow[i + 1] = (pow[i] * P) % M;
        }

        // Slide window and compare hashes
        for (int i = 0; i + m <= n; i++) {
            hashComparisons++;

            long currentHash = (prefixHash[i + m] - prefixHash[i] + M) % M;
            long adjustedPatternHash = (patternHash * pow[i]) % M;

            if (currentHash == adjustedPatternHash) {
                if (text.substring(i, i + m).equals(pattern)) {
                    matches.add(i);
                }
            }
        }

        return matches;
    }

    private static long computeHash(String s) {
        long hash = 0, pow = 1;
        for (char c : s.toCharArray()) {
            hash = (hash + (long) c * pow) % M;
            pow = (pow * P) % M;
        }
        return hash;
    }
}
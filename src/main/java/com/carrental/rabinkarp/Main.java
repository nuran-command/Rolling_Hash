package com.carrental.rabinkarp;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[][] tests = {
                {"abcdabcabc", "abc"},          // short
                {"aaaaaaaaaaaaaaaaab", "aaab"}, // medium
                {"abababababababababababababababab", "aba"} // long
        };

        for (String[] test : tests) {
            String text = test[0];
            String pattern = test[1];

            // Start timer
            long startTime = System.nanoTime();
            List<Integer> matches = RabinKarp.search(text, pattern);
            long endTime = System.nanoTime();

            // Compute elapsed time
            long duration = endTime - startTime;

            System.out.println("Text length: " + text.length());
            System.out.println("Pattern: " + pattern);
            System.out.println("Matches at indices: " + matches);
            System.out.println("Time taken: " + duration + " ns (" + duration / 1_000_000.0 + " ms)");
            System.out.println("Hash comparisons: " + RabinKarp.getHashComparisons());
            System.out.println("-------------------------");
        }
    }
}
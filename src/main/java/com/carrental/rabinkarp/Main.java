package com.carrental.rabinkarp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TestCase {
    String text;
    String pattern;

    TestCase(String text, String pattern) {
        this.text = text;
        this.pattern = pattern;
    }
}

class OutputCase {
    String pattern;
    List<Integer> matches;

    OutputCase(String pattern, List<Integer> matches) {
        this.pattern = pattern;
        this.matches = matches;
    }
}
// My input/output
public class Main {
    public static void main(String[] args) throws IOException {
        String[][] tests = {
                {"abcdabcabc", "abc"},          // short
                {"aaaaaaaaaaaaaaaaab", "aaab"}, // medium
                {"abababababababababababababababab", "aba"} // long
        };

        // For Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Prepared lists for JSON output
        List<TestCase> shortInputs = new ArrayList<>();
        List<TestCase> mediumInputs = new ArrayList<>();
        List<TestCase> longInputs = new ArrayList<>();

        List<OutputCase> shortOutputs = new ArrayList<>();
        List<OutputCase> mediumOutputs = new ArrayList<>();
        List<OutputCase> longOutputs = new ArrayList<>();

        // Run my original tests and collect input/output
        for (String[] test : tests) {
            String text = test[0];
            String pattern = test[1];

            // Run Rabin-Karp
            long startTime = System.nanoTime();
            List<Integer> matches = RabinKarp.search(text, pattern);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            // Print
            System.out.println("Text length: " + text.length());
            System.out.println("Pattern: " + pattern);
            System.out.println("Matches at indices: " + matches);
            System.out.println("Time taken: " + duration + " ns (" + duration / 1_000_000.0 + " ms)");
            System.out.println("Hash comparisons: " + RabinKarp.getHashComparisons());
            System.out.println("-------------------------");

            // Determine short/medium/long
            if (text.length() <= 12) { // short
                shortInputs.add(new TestCase(text, pattern));
                shortOutputs.add(new OutputCase(pattern, matches));
            } else if (text.length() <= 20) { // medium
                mediumInputs.add(new TestCase(text, pattern));
                mediumOutputs.add(new OutputCase(pattern, matches));
            } else { // long
                longInputs.add(new TestCase(text, pattern));
                longOutputs.add(new OutputCase(pattern, matches));
            }
        }

        // Write input JSONs
        try (FileWriter writer = new FileWriter("data/input/input_short.json")) {
            gson.toJson(shortInputs, writer);
        }
        try (FileWriter writer = new FileWriter("data/input/input_medium.json")) {
            gson.toJson(mediumInputs, writer);
        }
        try (FileWriter writer = new FileWriter("data/input/input_long.json")) {
            gson.toJson(longInputs, writer);
        }

        // Write output JSONs
        try (FileWriter writer = new FileWriter("data/output/output_short.json")) {
            gson.toJson(shortOutputs, writer);
        }
        try (FileWriter writer = new FileWriter("data/output/output_medium.json")) {
            gson.toJson(mediumOutputs, writer);
        }
        try (FileWriter writer = new FileWriter("data/output/output_long.json")) {
            gson.toJson(longOutputs, writer);
        }
    }
}
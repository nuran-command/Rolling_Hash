package com.carrental.rabinkarp;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RabinKarpTest {

    @Test
    void testShortStringBasicMatch() {
        List<Integer> result = RabinKarp.search("abcdabcabc", "abc");
        assertEquals(List.of(0, 4, 7), result, "Should find 'abc' at indices 0, 4, and 7");
    }

    @Test
    void testMediumStringSingleMatch() {
        List<Integer> result = RabinKarp.search("aaaaaaaaaaaaaaaaab", "aaab");
        assertEquals(List.of(14), result, "Should find only one match at index 14");
    }

    @Test
    void testNoMatch() {
        List<Integer> result = RabinKarp.search("abcdefg", "xyz");
        assertTrue(result.isEmpty(), "Should return empty list when no match exists");
    }

    @Test
    void testPatternEqualsText() {
        String text = "pattern";
        List<Integer> result = RabinKarp.search(text, "pattern");
        assertEquals(List.of(0), result, "Full match should return index 0");
    }

    @Test
    void testSingleCharacterRepeated() {
        List<Integer> result = RabinKarp.search("aaaaaa", "aa");
        assertEquals(List.of(0, 1, 2, 3, 4), result, "Should match overlapping occurrences");
    }

    @Test
    void testPatternLongerThanText() {
        List<Integer> result = RabinKarp.search("abc", "abcd");
        assertTrue(result.isEmpty(), "No match when pattern is longer than text");
    }

    @Test
    void testEmptyText() {
        List<Integer> result = RabinKarp.search("", "a");
        assertTrue(result.isEmpty(), "Empty text should return no matches");
    }

    @Test
    void testEmptyPattern() {
        List<Integer> result = RabinKarp.search("abcde", "");
        // Depending on design, empty pattern might match everywhere, or nowhere.
        // Here we treat empty pattern as no valid match.
        assertTrue(result.isEmpty(), "Empty pattern should return no matches");
    }

    @Test
    void testCaseSensitivity() {
        List<Integer> result = RabinKarp.search("AbcABCabc", "abc");
        // current hash ignores case differences, so "A" and "a" mismatch
        assertEquals(List.of(6), result, "Should only match lowercase 'abc' at index 6");
    }

    @Test
    void testLongStringPerformance() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 10000; i++) text.append("abc");
        String pattern = "abcabc";

        long start = System.nanoTime();
        List<Integer> result = RabinKarp.search(text.toString(), pattern);
        long time = System.nanoTime() - start;

        assertFalse(result.isEmpty(), "Should find matches in large input");
        assertTrue(time < 50_000_000, "Should run efficiently (<50 ms)");
    }

    @Test
    void testPatternAtEnd() {
        List<Integer> result = RabinKarp.search("xxxyyyzzzabc", "abc");
        assertEquals(List.of(9), result, "Pattern at end should be found at index 9");
    }

    @Test
    void testPatternAtBeginning() {
        List<Integer> result = RabinKarp.search("abcxxxyyyzzz", "abc");
        assertEquals(List.of(0), result, "Pattern at beginning should be found at index 0");
    }

    @Test
    void testMultipleDistinctMatches() {
        List<Integer> result = RabinKarp.search("abc123abc456abc789", "abc");
        assertEquals(List.of(0, 6, 12), result, "Should match at three distinct positions");
    }

    @Test
    void testOverlappingMatches() {
        List<Integer> result = RabinKarp.search("aaaaa", "aaa");
        assertEquals(List.of(0, 1, 2), result, "Should detect overlapping 'aaa' patterns");
    }

    @Test
    void testRandomStringNoFalsePositives() {
        String text = "zxqwyoplkjhgfdsamnbvc";
        List<Integer> result = RabinKarp.search(text, "abc");
        assertTrue(result.isEmpty(), "Random nonmatching string should yield no matches");
    }
}
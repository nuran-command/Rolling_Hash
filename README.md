#  Rabin–Karp String Matching (Java)

## Algorithm Overview

My code implements **Rabin–Karp string matching** using **polynomial rolling hash**.

### Workflow
1. **Guard Clause:**  
   Handles empty strings, null input, or cases where the pattern is longer than the text.

2. **Preprocessing:**
    - Compute the hash of the pattern using polynomial rolling hash:  
      `hash(P) = Σ_{i=0}^{m-1} (P[i] - 'a' + 1) * P^i mod M`
    - Compute prefix hashes and powers of P for the text.

3. **Sliding Window Search:**
    - For each substring of length `m` in the text, compute the rolling hash using prefix hashes.
    - Compare it with the pattern hash.
    - If hashes match, perform direct substring comparison to avoid collisions.

4. **Counting Operations:**  
   The `hashComparisons` counter tracks how many hash comparisons were performed.

### Key Design Choices
- **Large prime modulus** `M = 1_000_000_009` reduces hash collisions.
- **Base** `P = 31` provides good hash distribution for lowercase English letters.
- Guard clause ensures **robustness** for edge cases (empty text/pattern or pattern longer than text).

##  Description
This project implements the **Rabin–Karp string matching algorithm** using a **Polynomial Rolling Hash**.  
It efficiently finds all occurrences of a pattern within a given text by comparing hash values instead of performing direct string comparisons.

---

##  Project Structure

| File | Description |
|------|--------------|
| `RabinKarp.java` | Core algorithm implementation using rolling hash |
| `Main.java` | Demonstrates algorithm on short, medium, and long test strings |
| `RabinKarpTest.java` | Automated JUnit test suite covering edge and normal cases |
| `report.md` | Report summarizing design, testing, and complexity analysis |
| `pom.xml` | Maven build configuration |

---

## Build & Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.example.rabinkarp.Main"
```
### Prerequisites
- Java 23
- Maven 3.8+

##  Sample Execution Output

| Test Case | Text Length | Pattern | Matches (Indices) | Time (ms) | Hash Comparisons |
|-----------|-------------|---------|------------------|------------|-----------------|
| Short    | 10          | `abc`  | [0, 4, 7]        | 0.73175   | 8               |
| Medium   | 18          | `aaab` | [14]             | 0.01466   | 15              |
| Long     | 32          | `aba`  | [0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28] | 0.03375 | 30 |

## 📊 Observations
- The number of hash comparisons grows **linearly** with text length.
- Execution time remains very low even for medium and long strings, demonstrating **efficiency**.
- The algorithm correctly identifies **overlapping matches** and handles **multiple occurrences**.


##  Complexity Analysis

| Aspect | Analysis |
|--------|---------|
| Time Complexity (average) | O(n + m), where `n` = text length, `m` = pattern length. Hash calculation and rolling are linear. |
| Worst-case Time Complexity | O(n * m) in case of repeated hash collisions requiring substring comparison. Rare with a large prime `M`. |
| Space Complexity | O(n) for storing `prefixHash` and `pow` arrays. Constant space for pattern hash and counter. |

### Explanation
- **Preprocessing hashes:** O(n)
- **Pattern comparison on hash match:** O(k * m), where `k` = number of hash matches
- For typical input with minimal collisions, the algorithm performs effectively in **linear time**.

## ✅ JUnit Test Coverage

| Category | Description | Example Test |
|----------|------------|--------------|
| Basic match | Standard substring search | `testShortStringBasicMatch` |
| Single match | One occurrence in medium text | `testMediumStringSingleMatch` |
| No match | Pattern not present | `testNoMatch` |
| Full text match | Pattern equals text | `testPatternEqualsText` |
| Overlapping matches | Repeated characters | `testSingleCharacterRepeated`, `testOverlappingMatches` |
| Edge cases | Empty text/pattern, pattern longer than text | `testEmptyText`, `testEmptyPattern`, `testPatternLongerThanText` |
| Case sensitivity | Detect lowercase vs uppercase | `testCaseSensitivity` |
| Performance | Long string efficiency | `testLongStringPerformance` |
| Boundary positions | Beginning/end matches | `testPatternAtBeginning`, `testPatternAtEnd` |
| Multiple matches | Multiple non-overlapping occurrences | `testMultipleDistinctMatches` |
| False positive protection | Random string | `testRandomStringNoFalsePositives` |

---

## 5. Deep Insights from Results

### 1. Efficiency
The algorithm demonstrates **linear time performance** in practice. Execution times are extremely low:
- Less than 1 ms for short and medium strings
- ~0.034 ms for longer 32-character inputs

This confirms that the **rolling hash approach** efficiently avoids repeated substring comparisons.

### 2. Accuracy
The implementation reliably detects all intended matches:
- **Overlapping patterns:** Correctly identifies repeated sequences, e.g., `"aaaaa"` with `"aaa"`
- **Multiple occurrences:** Finds all non-overlapping instances of the pattern
- **Boundary matches:** Accurately detects patterns at the start or end of the text

### 3. Robust Edge Case Handling
Guard clauses ensure unusual or problematic inputs are handled gracefully:
- **Empty text or pattern:** Returns no matches without errors
- **Pattern longer than text:** Correctly returns no matches
- **Case sensitivity:** Only exact character matches are considered

### 4. Scalability
Performance testing with large inputs, such as 10,000 repeated `"abc"` sequences, shows the algorithm **scales linearly** with text length. Even very large texts can be processed efficiently, confirming suitability for practical applications.

### 5. Predictable Operation Count
The `hashComparisons` counter grows approximately **linearly** with text length, demonstrating that the algorithm performs a predictable number of hash comparisons regardless of input complexity. This aligns with the **O(n + m)** average time complexity expectation.
##  Observations
- The time taken grows linearly with text size, confirming expected efficiency.
- Hash comparisons ≈ text length, as each substring is checked once.
- Collisions are minimized by choosing a large prime modulus (`1_000_000_009`).
- The algorithm performs efficiently for both short and long inputs.

---

##  Example Hash Parameters

| Parameter | Symbol | Value | Purpose |
|-----------|--------|-------|---------|
| Base | P | 31 | Hash base (multiplier) |
| Modulus | M | 1,000,000,009 | Large prime to prevent overflow |
| Hash Formula | — | `hash = Σ (s[i] - 'a' + 1) * P^i mod M` | Polynomial rolling hash |

---

##  Concluding Remarks

- The **Rabin–Karp algorithm** with polynomial rolling hash proves to be highly **efficient and reliable** for substring search across a wide range of text patterns.
- The use of **prefix hashes**, **modular arithmetic**, and **guard clauses** ensures both **correctness and robustness**, preventing errors in edge cases such as empty strings or patterns longer than the text.
- Comprehensive **JUnit testing** validates all key scenarios, including overlapping matches, multiple occurrences, boundary matches, and large-scale performance, confirming the algorithm’s correctness under diverse conditions.
- The **hash comparison counter** (`hashComparisons`) provides valuable insight into the algorithm’s execution, highlighting **predictable and near-linear scaling** with input size.

**Overall:** This implementation is **correct, efficient, scalable, and thoroughly tested**, demonstrating both the **theoretical efficiency** and **practical reliability** of the Rabin–Karp string matching algorithm.

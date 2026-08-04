# Arrays — Complete Interview Theory Guide

## 1. What is an Array?

An array is a collection of elements of the **same data type**, stored in **contiguous memory locations**, accessed using an **index**.

**Key properties:**
- Fixed size (in most languages like C, Java) — size must be declared upfront (except dynamic arrays like Python lists, ArrayList in Java, vector in C++)
- Elements stored in contiguous memory → allows **O(1) random access** using index
- Index usually starts at **0** (zero-based indexing)
- Homogeneous — all elements same type (in strongly typed languages)

---

## 2. Types of Arrays

| Type | Description |
|---|---|
| **1D Array** | Linear list of elements: `[10, 20, 30]` |
| **2D Array** | Matrix/grid form: `[[1,2],[3,4]]` — rows and columns |
| **Multi-dimensional Array** | 3D or more (less common in interviews) |
| **Static Array** | Fixed size, memory allocated at compile time (C arrays) |
| **Dynamic Array** | Resizable — Python list, Java ArrayList, C++ vector |

---

## 3. Memory & Indexing

- **Address calculation formula** (important theory question):
  ```
  Address of arr[i] = Base Address + (i × size of each element)
  ```
- For 2D arrays, two storage orders exist:
  - **Row-major order** (used by C, C++, Java, Python) — rows stored one after another
  - **Column-major order** (used by Fortran, MATLAB) — columns stored one after another
  - Formula for row-major 2D array `arr[m][n]`:
    ```
    Address of arr[i][j] = Base + (i × n + j) × size
    ```

---

## 4. Time Complexity of Array Operations

| Operation | Time Complexity | Why |
|---|---|---|
| Access (by index) | O(1) | Direct address calculation |
| Search (unsorted) | O(n) | Must check each element |
| Search (sorted, binary search) | O(log n) | Divide and conquer |
| Insertion at end | O(1) amortized (dynamic array) | May need resizing occasionally |
| Insertion at beginning/middle | O(n) | Must shift elements |
| Deletion at end | O(1) | No shifting needed |
| Deletion at beginning/middle | O(n) | Must shift elements |
| Traversal | O(n) | Visit every element once |

**This table is asked very frequently — memorize it cold.**

---

## 5. Array vs Other Data Structures (Common Interview Question)

**Array vs Linked List:**
- Array: contiguous memory, O(1) access, O(n) insertion/deletion (shifting required)
- Linked List: non-contiguous, O(n) access, O(1) insertion/deletion (if position known)

**Array vs ArrayList/Vector (Static vs Dynamic):**
- Static array: fixed size, faster, less overhead
- Dynamic array: resizable automatically (doubles capacity when full), slight overhead due to resizing

**Why dynamic arrays resize by doubling?**
- Amortized O(1) insertion — if you doubled capacity each time it's full, the cost of occasional resizing spreads out over many cheap insertions, averaging to O(1).

---

## 6. Core Concepts You MUST Know

### a) Traversal
- Visiting each element once — the basis of most array algorithms.

### b) Searching
- **Linear Search**: O(n), works on unsorted data
- **Binary Search**: O(log n), requires **sorted array**
  - Know the logic: compare with middle element, discard half each time
  - Know edge cases: empty array, single element, target not found

### c) Sorting (know concepts, not always full implementation)
| Algorithm | Time Complexity (avg) | Space | Stable? |
|---|---|---|---|
| Bubble Sort | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(1) | No |
| Insertion Sort | O(n²) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) avg, O(n²) worst | O(log n) | No |

- **Stability** = equal elements retain their relative order after sorting (interviewers ask this often)
- Be ready to explain **why Quick Sort worst case is O(n²)** (bad pivot choice, e.g., already sorted array with first element as pivot)

### d) Two-Pointer Technique
- Used for problems like: pair sum, reversing array, removing duplicates from sorted array
- Two pointers move toward each other (or in same direction) to avoid nested loops → reduces O(n²) to O(n)

### e) Sliding Window Technique
- Used for subarray problems: max sum subarray of size k, longest substring without repeating characters
- Maintains a "window" that expands/shrinks instead of recomputing from scratch

### f) Kadane's Algorithm (Very frequently asked)
- Used to find **maximum sum subarray**
- Core idea: at each index, decide whether to extend the previous subarray or start fresh
  ```
  current_sum = max(arr[i], current_sum + arr[i])
  max_sum = max(max_sum, current_sum)
  ```
- Time complexity: O(n)

### g) Prefix Sum
- Precompute cumulative sums so range-sum queries become O(1) instead of O(n)
- `prefix[i] = prefix[i-1] + arr[i]`
- Used in subarray sum problems, range queries

### h) Hashing with Arrays
- Using a hashmap/dictionary alongside array traversal to solve in O(n) instead of O(n²)
- Classic use case: **two-sum problem**, finding duplicates, frequency counting

---

## 7. Classic Array Problems Interviewers Love (Know the Approach for Each)

1. **Find duplicate(s) in an array** — hashmap or Floyd's cycle detection (for special constrained version)
2. **Two Sum** — hashmap approach, O(n)
3. **Maximum subarray sum** — Kadane's Algorithm
4. **Move zeroes to end** — two-pointer, in-place
5. **Reverse an array** — two-pointer swap
6. **Rotate an array by k positions** — reversal algorithm (reverse whole, then reverse parts)
7. **Find missing number in 1 to N** — sum formula `n(n+1)/2` or XOR technique
8. **Merge two sorted arrays** — two-pointer merge (like merge step in merge sort)
9. **Find the intersection/union of two arrays**
10. **Check if array is sorted / next permutation**
11. **Majority element** (appears more than n/2 times) — Moore's Voting Algorithm
12. **Trapping rainwater problem** — prefix max / suffix max arrays
13. **Best time to buy/sell stock** — single pass, track minimum so far

---

## 8. Multi-dimensional (2D) Array Concepts

- **Matrix traversal**: row-wise, column-wise, diagonal, spiral order
- **Matrix transpose**: swap `arr[i][j]` with `arr[j][i]`
- **Rotating a matrix 90°**: transpose + reverse rows (clockwise) or reverse columns first (counter-clockwise)
- **Searching in a sorted matrix**: start from top-right or bottom-left corner, eliminate row/column each step — O(m+n)

---

## 9. Edge Cases Interviewers Expect You to Mention

- Empty array
- Single-element array
- Array with all same elements
- Array with negative numbers (especially for max subarray, max/min problems)
- Duplicate elements
- Already sorted / reverse sorted array (for sorting algorithm complexity discussion)

**Always verbally mention these when explaining your approach — it signals thoroughness.**

---

## 10. Common Theory Questions Asked Directly (Not Coding)

- What is an array? How is it different from a linked list?
- Why is array access O(1) but insertion O(n)?
- What is the difference between static and dynamic arrays?
- How does a dynamic array (ArrayList/vector) grow internally?
- What is row-major vs column-major order?
- What is the difference between a stable and unstable sorting algorithm?
- Explain time complexity of binary search and why it needs a sorted array.
- What is the difference between Array and String (in languages where String is a char array)?
- Can an array store different data types? (No, in Java/C/C++ arrays are homogeneous; discuss how Python lists differ since they can technically hold mixed types due to being reference-based)

---

## Quick Revision Checklist Before Interview

- [ ] Time complexity table (memorized)
- [ ] Two-pointer technique
- [ ] Sliding window technique
- [ ] Kadane's Algorithm
- [ ] Prefix sum concept
- [ ] Binary search (and why sorted array is required)
- [ ] Sorting algorithms — complexity + stability
- [ ] At least 10 classic problems solved and explainable out loud
- [ ] 2D array traversal patterns (spiral, transpose, rotate)
- [ ] Comfortable answering "why" questions, not just "how to code it"

---

**Tip for Infosys-style interviews specifically**: they often ask you to **dry-run your code line by line on paper** — so after solving a problem, practice tracing through your own logic with a sample input, writing down variable values at each step, out loud.

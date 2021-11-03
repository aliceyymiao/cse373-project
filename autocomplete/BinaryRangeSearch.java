package autocomplete;

import edu.princeton.cs.algs4.Merge;

import java.util.Arrays;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException();
        }
        for (Term t : terms) {
            if (t == null) {
                throw new IllegalArgumentException();
            }
        }
        this.terms = terms;
        Merge.sort(terms);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        int first = first(terms, new Term(prefix, 0));
        int last = last(terms, new Term(prefix, 0));
        Term[] matches = new Term[last + 1 - first];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = terms[first++];
        }
        Arrays.sort(matches, TermComparators.byReverseWeightOrder());
        return matches;
    }

    /**
     * Source code for inspiration of first and last methods:
     * https://www.geeksforgeeks.org/find-first-and-last-positions-of-an-element-in-a-sorted-array/
     * and https://www.geeksforgeeks.org/binary-search-a-string/
     */
    private int first(Term[] a, Term t) {
        if (a.length == 0) {
            return -1;
        }
        if (a[0] == t) {
            return 0;
        }
        int result = -1;
        int low = 0;
        int high = a.length - 1;
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (t.compareToByPrefixOrder(a[mid], t.query().length()) == 0) {
                result = mid;
                high = mid - 1;
            } else if (t.compareToByPrefixOrder(a[mid], t.query().length()) < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    private int last(Term[] a, Term t) {
        if (a.length == 0) {
            return -1;
        }
        if (a[a.length - 1] == t) {
            return a.length - 1;
        }
        int result = -1;
        int low = 0;
        int high = a.length - 1;
        while (high >= low) {
            int mid = low + (high - low) / 2;
            if (t.compareToByPrefixOrder(a[mid], t.query().length()) == 0) {
                result = mid;
                low = mid + 1;
            } else if (t.compareToByPrefixOrder(a[mid], t.query().length()) < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }
}

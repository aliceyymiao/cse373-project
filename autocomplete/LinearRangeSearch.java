package autocomplete;

import java.util.Arrays;

public class LinearRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     *
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException();
        }
        for (Term t : terms) {
            if (t == null) {
                throw new IllegalArgumentException();
            }
        }
        this.terms = terms;
        Arrays.sort(terms);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     *
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        Term pre = new Term(prefix, 0);
        int count = 0;
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].compareToByPrefixOrder(pre, pre.query().length()) == 0) {
                count++;
            }
        }
        Term[] matches = new Term[count];
        int index = 0;
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].compareToByPrefixOrder(pre, pre.query().length()) == 0) {
                matches[index] = terms[i];
                index++;
            }
        }
        Arrays.sort(matches, TermComparators.byReverseWeightOrder());
        return matches;
    }
}



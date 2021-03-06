package autocomplete;

import java.util.Objects;

public class Term implements Comparable<Term> {
    private String query;
    private long weight;

    /**
     * Initializes a term with the given query string and weight.
     * @throws IllegalArgumentException if query is null or weight is negative
     */
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;
    }

    /**
     * Compares the two terms in lexicographic order by query.
     * @throws NullPointerException if the specified object is null
     */
    public int compareTo(Term that) {
        if (that == null) {
            throw new NullPointerException();
        }
        return this.query.compareTo(that.query);
    }

    /** Compares to another term, in descending order by weight. */
    public int compareToByReverseWeightOrder(Term that) {
        return (int) (that.weight - this.weight);
    }

    /**
     * Compares to another term in lexicographic order, but using only the first r characters
     * of each query. If r is greater than the length of any term's query, compares using the
     * term's full query.
     * @throws IllegalArgumentException if r < 0
     */
    public int compareToByPrefixOrder(Term that, int r) {
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        String thisPrefix;
        String thatPrefix;
        if (this.query.length() > r) {
            thisPrefix = this.query.substring(0, r);
        } else {
            thisPrefix = this.query;
        }
        if (that.query.length() > r) {
            thatPrefix = that.query.substring(0, r);
        } else {
            thatPrefix = that.query;
        }
        return thisPrefix.compareTo(thatPrefix);
    }

    /** Returns this term's query. */
    public String query() {
        return this.query;
    }

    /**
     * Returns the first r characters of this query.
     * If r is greater than the length of the query, returns the entire query.
     * @throws IllegalArgumentException if r < 0
     */
    public String queryPrefix(int r) {
        if (r < 0) {
            throw new IllegalArgumentException();
        }
        if (this.query.length() > r) {
            return this.query.substring(0, r);
        } else {
            return this.query;
        }
    }

    /** Returns this term's weight. */
    public long weight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Term{" +
                "query='" + query + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Term term = (Term) o;
        return weight == term.weight &&
                query.equals(term.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, weight);
    }
}

package autocomplete;

import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryRangeSearchTest {

    private static Autocomplete linearAuto;
    private static Autocomplete binaryAuto;

    private static int N = 0;
    private static Term[] terms = null;

    private static final String INPUT_FILENAME = "data/cities.txt";

    /**
     * Creates LinearRangeSearch and BinaryRangeSearch instances based on the data from cities.txt
     * so that they can easily be used in tests.
     */
    @Before
    public void setUp() {
        if (terms != null) {
            return;
        }

        In in = new In(INPUT_FILENAME);
        N = in.readInt();
        terms = new Term[N];
        for (int i = 0; i < N; i += 1) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query, weight);
        }

        linearAuto = new LinearRangeSearch(terms);
        binaryAuto = new BinaryRangeSearch(terms);
    }

    /**
     * Checks that the terms in the expected array are equivalent to the ones in the actual array.
     */
    private void assertTermsEqual(Term[] expected, Term[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Term e = expected[i];
            Term a = actual[i];
            assertEquals(e.query(), a.query());
            assertEquals(e.weight(), a.weight());
        }
    }

    @Test
    public void testSimpleExample() {
        Term[] moreTerms = new Term[] {
            new Term("hello", 0),
            new Term("world", 0),
            new Term("welcome", 0),
            new Term("to", 0),
            new Term("autocomplete", 0),
            new Term("me", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("autocomplete", 0)};
        assertTermsEqual(expected, brs.allMatches("auto"));
    }

    @Test
    public void testTermsEqual() {
        Term[] moreTerms = new Term[] {
                new Term("Yes", 5),
                new Term("this", 1),
                new Term("is", 2),
                new Term("Yvonne", 10),
                new Term("Yellow", 7),
                new Term("Yummy", 5)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("Yvonne", 10),
                new Term("Yellow", 7),
                new Term("Yes", 5),
                new Term("Yummy", 5)};
        assertTermsEqual(expected, brs.allMatches("Y"));
    }

    @Test
    public void testTermsEqual1() {
        Term[] moreTerms = new Term[] {
                new Term("my", 3),
                new Term("name", 6),
                new Term("meow", 9),
                new Term("mellow", 12),
                new Term("melody", 2),
                new Term("last", 4)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("mellow", 12),
                new Term("meow", 9),
                new Term("melody", 2)};
        assertTermsEqual(expected, brs.allMatches("me"));
    }

    @Test
    public void testTermsEqual2() {
        Term[] moreTerms = new Term[] {
                new Term("what", 8),
                new Term("why", 10),
                new Term("hi", 6),
                new Term("hello", 7),
                new Term("man", 2),
                new Term("people", 5)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("why", 10),
                new Term("what", 8)};
        assertTermsEqual(expected, brs.allMatches("wh"));
    }

    @Test
    public void testRandom() {
        assertTermsEqual(linearAuto.allMatches("Seat"), binaryAuto.allMatches("Seat"));
    }

    @Test
    public void testRandom1() {
        assertTermsEqual(linearAuto.allMatches("Aba"), binaryAuto.allMatches("Aba"));
    }
}

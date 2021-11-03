package deques.palindrome;

import static org.junit.Assert.*;
import org.junit.Test;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('d', 'e'));
        assertTrue(offByOne.equalChars('f', 'g'));
    }

    @Test
    public void testUnequalChars() {
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('b', 'v'));
        assertFalse(offByOne.equalChars('h', 'y'));
    }

    @Test
    public void testEqualChars1() {
        assertTrue(offByOne.equalChars('#', '$'));
    }

    @Test
    public void testUnequalChars1() {
        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('f', 'G'));
        assertFalse(offByOne.equalChars('H', 'i'));
    }

}

package deques.palindrome;

import deques.Deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void emptyStringTest() {
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void singleCharTest() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("z"));
    }

    @Test
    public void capitalizationTest() {
        assertFalse(palindrome.isPalindrome("Aa"));
        assertFalse(palindrome.isPalindrome("Hh"));
    }

    @Test
    public void validPalindromeTest() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void invalidPalindromeTest() {
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
    }

    /* tests for isPalindrome with OffByOne */
    @Test
    public void zeroCharTest() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
    }

    @Test
    public void oneCharTest() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", cc));
    }

    @Test
    public void capitalTest() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("AbA", cc));
    }

    @Test
    public void validOffByOneTest() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("RaedbS", cc));
    }

    @Test
    public void invalidOffByOneTest() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("hoo", cc));
    }

    @Test
    public void specialCharOffByOneTest() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("#$", cc));
    }

}

package deques.palindrome;

import deques.ArrayDeque;
import deques.Deque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            result.addLast(c);
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return isPalindromeHelper(d);
    }

    private boolean isPalindromeHelper(Deque<Character> d) {
        if (d.size() < 2) {
            return true;
        } else if (d.removeLast() != d.removeFirst()) {
            return false;
        } else {
            if (d.removeFirst() == d.removeLast()) {
                return isPalindromeHelper(d);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() < 2) {
            return true;
        } else {
            if (cc.equalChars(d.removeFirst(), d.removeLast())) {
                String s = "";
                while (d.size() > 0) {
                    s += d.removeFirst();
                }
                return isPalindrome(s, cc);
            } else {
                return false;
            }
        }
    }
}

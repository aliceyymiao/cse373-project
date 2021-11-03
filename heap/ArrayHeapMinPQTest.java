package heap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */
    @Test
    public void testChangePriority() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("l", 5.6);
        test1.add("i", 7.8);
        test1.add("s", 3.5);
        test1.add("a", 4.0);
        assertEquals("s", test1.getSmallest());
        test1.changePriority("s", 9.9);
        assertEquals("a", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testChangePriority1() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        assertEquals("e", test1.getSmallest());
        test1.changePriority("e", 9.9);
        assertEquals("l", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testChangePriority2() {
        long start = System.currentTimeMillis();
        NaiveMinPQ<String> test1 = new NaiveMinPQ<>();
        test1.add("l", 5.6);
        test1.add("i", 7.8);
        test1.add("s", 3.5);
        test1.add("a", 4.0);
        assertEquals("s", test1.getSmallest());
        test1.changePriority("s", 9.9);
        assertEquals("a", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }


    @Test
    public void testSmallestAndRemoveS() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test2 = new ArrayHeapMinPQ<>();
        test2.add("y", 3.8);
        test2.add("o", 7.0);
        test2.add("u", 5.7);
        assertEquals("y", test2.getSmallest());
        test2.removeSmallest();
        assertEquals("u", test2.removeSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testSmallestAndRemoveS2() {
        long start = System.currentTimeMillis();
        NaiveMinPQ<String> test2 = new NaiveMinPQ<>();
        test2.add("y", 3.8);
        test2.add("o", 7.0);
        test2.add("u", 5.7);
        assertEquals("y", test2.getSmallest());
        test2.removeSmallest();
        assertEquals("u", test2.removeSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRandom() {
        ExtrinsicMinPQ<Integer> test3 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i++) {
            test3.add(i, Math.random());
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            test3.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
    @Test
    public void testRandom2() {
        ExtrinsicMinPQ<Integer> test3 = new NaiveMinPQ<>();
        for (int i = 0; i < 200000; i++) {
            test3.add(i, Math.random());
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            test3.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("l", 5.6);
        test1.add("i", 7.8);
        test1.add("s", 3.5);
        test1.add("a", 4.0);
        assertEquals("s", test1.getSmallest());
        test1.changePriority("s", 9.9);
        test1.removeSmallest();
        test1.removeSmallest();
        assertEquals("i", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority1() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        test1.changePriority("i", 6.3);
        test1.changePriority("i", 7.3);
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.changePriority("k", 8.8);
        assertEquals("i", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority2() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        assertEquals("e", test1.getSmallest());
        test1.changePriority("o", 0.0);
        test1.removeSmallest();
        test1.removeSmallest();
        assertEquals("l", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority3() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        test1.changePriority("i", 6.6);
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        assertEquals("k", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority4() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        test1.changePriority("m", 5.7);
        test1.changePriority("m", 4.3);
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.changePriority("a", 4.1);
        assertEquals("a", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRemoveAfterChangePriority5() {
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<String> test1 = new ArrayHeapMinPQ<>();
        test1.add("d", 3.5);
        test1.add("i", 4.8);
        test1.add("l", 1.2);
        test1.add("e", 0.2);
        test1.add("m", 7.9);
        test1.add("a", 5.6);
        test1.add("o", 9.0);
        test1.add("k", 6.6);
        test1.changePriority("l", 5.7);
        test1.changePriority("m", 3.2);
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.removeSmallest();
        test1.changePriority("a", 7.8);
        assertEquals("l", test1.getSmallest());
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
}

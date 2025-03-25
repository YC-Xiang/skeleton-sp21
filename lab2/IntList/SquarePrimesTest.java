package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesEmpty() {
        IntList lst = IntList.of();
        boolean changed = IntListExercises.squarePrimes(lst);
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesAllPrimes() {
        IntList lst = IntList.of(2, 3, 5, 7, 11);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 9 -> 25 -> 49 -> 121", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesNoPrimes() {
        IntList lst = IntList.of(4, 6, 8, 9, 10);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 6 -> 8 -> 9 -> 10", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesSinglePrime() {
        IntList lst = IntList.of(13);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("169", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesMultiplePrimes() {
        IntList lst = IntList.of(2, 3, 5, 7, 11);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 9 -> 25 -> 49 -> 121", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesNegativeNumbers() {
        IntList lst = IntList.of(-2, -3, -5, -7, -11);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("-2 -> -3 -> -5 -> -7 -> -11", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesHasZero() {
        IntList lst = IntList.of(0, 1, 2, 3, 4);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0 -> 1 -> 4 -> 9 -> 4", lst.toString());
        assertTrue(changed);
    }
}

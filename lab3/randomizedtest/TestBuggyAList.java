package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> rightAList = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        rightAList.addLast(4);
        rightAList.addLast(5);
        rightAList.addLast(6);

        buggyAList.addLast(4);
        buggyAList.addLast(5);
        buggyAList.addLast(6);

        assertEquals(rightAList.removeLast(), buggyAList.removeLast());
        assertEquals(rightAList.removeLast(), buggyAList.removeLast());
        assertEquals(rightAList.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int LSize = L.size();
                int buggySize= buggyAList.size();
                System.out.println("size: " + LSize);
                assertEquals(LSize, buggySize);
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    int last = L.getLast();
                    int buggyLast = buggyAList.getLast();
                    System.out.println("getLast(" + last + ")");
                    assertEquals(last, buggyLast);
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    int last = L.removeLast();
                    int buggyLast = buggyAList.removeLast();
                    System.out.println("removeLast(" + last + ")");
                    assertEquals(last, buggyLast);
                }
            }
        }
    }

}

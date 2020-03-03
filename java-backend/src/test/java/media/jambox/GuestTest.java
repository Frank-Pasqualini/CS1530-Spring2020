package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.InputMismatchException;
import org.junit.Before;
import org.junit.Test;

public class GuestTest
{
    static Guest testGuest;
    static ArrayList<String> expectedVotes;
    static Event mockEvent;
    static Queue mockQueue;

    final String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
    final String despacito = "6rPO02ozF3bM7NnOV4h6s2";
    final String allStar = "3cfOd4CMv2snFaKAnMdnvK";
    final String pumpedUpKicks = "7w87IxuO7BDcJ3YUqCyMTT";
    final String sandstorm = "24CXuh2WNpgeSYUOvz14jk";

    /**
     * Sets up mock objects for the tests.
     */
    @Before
    public void setUp()
    {
        mockEvent = mock(Event.class);
        mockQueue = mock(Queue.class);

        when(mockEvent.getQueue()).thenReturn(mockQueue);

        expectedVotes = new ArrayList<>();

        testGuest = new Guest("0", mockEvent);
    }

    @Test
    public void testGetId()
    {
        assertEquals("0", testGuest.getId());
    }

    @Test
    public void testVote()
    {

        expectedVotes.add("+" + neverGonnaGiveYouUp);
        assertEquals(expectedVotes, testGuest.changeVote(neverGonnaGiveYouUp, 1));

        expectedVotes.add("-" + despacito);
        assertEquals(expectedVotes, testGuest.changeVote(despacito, -1));

        assertEquals(expectedVotes, testGuest.changeVote(despacito, -1));

        expectedVotes.add("-" + allStar);
        assertEquals(expectedVotes, testGuest.changeVote(allStar, -1));

        expectedVotes.remove("-" + allStar);
        assertEquals(expectedVotes, testGuest.changeVote(allStar, 0));
    }

    @Test
    public void testBadVote()
    {
        when(mockQueue.vote(sandstorm, 1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testGuest.changeVote(sandstorm, 1));

        try
        {
            testGuest.changeVote(despacito, -2);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote(neverGonnaGiveYouUp, 2);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote(allStar, Integer.MAX_VALUE);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote(pumpedUpKicks, Integer.MIN_VALUE);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }
    }
}

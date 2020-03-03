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

        expectedVotes.add("+7GhIk7Il098yCjg4BQjzvb");
        assertEquals(expectedVotes, testGuest.changeVote("7GhIk7Il098yCjg4BQjzvb", 1));

        expectedVotes.add("-6rPO02ozF3bM7NnOV4h6s2");
        assertEquals(expectedVotes, testGuest.changeVote("6rPO02ozF3bM7NnOV4h6s2", -1));

        assertEquals(expectedVotes, testGuest.changeVote("6rPO02ozF3bM7NnOV4h6s2", -1));

        expectedVotes.add("-3cfOd4CMv2snFaKAnMdnvK");
        assertEquals(expectedVotes, testGuest.changeVote("3cfOd4CMv2snFaKAnMdnvK", -1));

        expectedVotes.remove("-3cfOd4CMv2snFaKAnMdnvK");
        assertEquals(expectedVotes, testGuest.changeVote("3cfOd4CMv2snFaKAnMdnvK", 0));
    }

    @Test
    public void testBadVote()
    {
        when(mockQueue.vote("6Sy9BUbgFse0n0LPA5lwy5", 1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testGuest.changeVote("6Sy9BUbgFse0n0LPA5lwy5", 1));

        try
        {
            testGuest.changeVote("6rPO02ozF3bM7NnOV4h6s2", -2);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote("7GhIk7Il098yCjg4BQjzvb", 2);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote("3cfOd4CMv2snFaKAnMdnvK", Integer.MAX_VALUE);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }

        try
        {
            testGuest.changeVote("7w87IxuO7BDcJ3YUqCyMTT", Integer.MIN_VALUE);
        }
        catch (java.util.InputMismatchException e)
        {
            assertNull(e.getMessage());
        }
    }
}

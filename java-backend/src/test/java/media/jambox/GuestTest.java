package media.jambox;

import static org.junit.Assert.assertEquals;
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

    final transient String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
    final transient String despacito = "6rPO02ozF3bM7NnOV4h6s2";
    final transient String allStar = "3cfOd4CMv2snFaKAnMdnvK";
    final transient String sandstorm = "24CXuh2WNpgeSYUOvz14jk";

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
    public void testVoteUp()
    {
        expectedVotes.add("+" + neverGonnaGiveYouUp);
        assertEquals(expectedVotes, testGuest.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testVoteDown()
    {
        expectedVotes.add("-" + despacito);
        assertEquals(expectedVotes, testGuest.changeVote(despacito, -1));
    }

    @Test
    public void testDoubleVoteUp()
    {
        expectedVotes.add("+" + neverGonnaGiveYouUp);
        testGuest.changeVote(neverGonnaGiveYouUp, 1);
        assertEquals(expectedVotes, testGuest.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testDoubleVoteDown()
    {
        expectedVotes.add("-" + despacito);
        testGuest.changeVote(despacito, -1);
        assertEquals(expectedVotes, testGuest.changeVote(despacito, -1));
    }

    @Test
    public void testVoteRemoval()
    {
        testGuest.changeVote(allStar, 1);
        assertEquals(expectedVotes, testGuest.changeVote(allStar, 0));
    }

    @Test
    public void testVoteUpInvalidSong()
    {
        when(mockQueue.vote(sandstorm, 1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testGuest.changeVote(sandstorm, 1));
    }

    @Test
    public void testVoteDownInvalidSong()
    {
        when(mockQueue.vote(sandstorm, -1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testGuest.changeVote(sandstorm, -1));
    }

    @Test
    public void testVoteRemovalInvalidSong()
    {
        when(mockQueue.vote(sandstorm, 0)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testGuest.changeVote(sandstorm, 0));
    }

    @Test(expected = InputMismatchException.class)
    public void testLowVoteValue()
    {
        testGuest.changeVote(despacito, -2);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowestVoteValue()
    {
        testGuest.changeVote(despacito, Integer.MIN_VALUE);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowHighValue()
    {
        testGuest.changeVote(neverGonnaGiveYouUp, 2);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowestHighestValue()
    {
        testGuest.changeVote(neverGonnaGiveYouUp, Integer.MAX_VALUE);
    }

    @Test
    public void testRequestTrack()
    {
        when(mockQueue.append(neverGonnaGiveYouUp)).thenReturn(0);
        assertEquals(0, testGuest.requestTrack(neverGonnaGiveYouUp));
    }

    @Test
    public void testRequestInvalidTrack()
    {
        when(mockQueue.append("a")).thenThrow(InputMismatchException.class);
        assertEquals(-1, testGuest.requestTrack("a"));
    }
}

package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.InputMismatchException;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{
    static User testUser;
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

        testUser = new User("0", mockEvent);
    }

    @Test
    public void testGetId()
    {
        assertEquals("0", testUser.getId());
    }

    @Test
    public void testVoteUp()
    {
        expectedVotes.add("+" + neverGonnaGiveYouUp);
        assertEquals(expectedVotes, testUser.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testVoteDown()
    {
        expectedVotes.add("-" + despacito);
        assertEquals(expectedVotes, testUser.changeVote(despacito, -1));
    }

    @Test
    public void testDoubleVoteUp()
    {
        expectedVotes.add("+" + neverGonnaGiveYouUp);
        testUser.changeVote(neverGonnaGiveYouUp, 1);
        assertEquals(expectedVotes, testUser.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testDoubleVoteDown()
    {
        expectedVotes.add("-" + despacito);
        testUser.changeVote(despacito, -1);
        assertEquals(expectedVotes, testUser.changeVote(despacito, -1));
    }

    @Test
    public void testVoteRemoval()
    {
        testUser.changeVote(allStar, 1);
        assertEquals(expectedVotes, testUser.changeVote(allStar, 0));
    }

    @Test
    public void testVoteUpInvalidSong()
    {
        when(mockQueue.vote(sandstorm, 1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testUser.changeVote(sandstorm, 1));
    }

    @Test
    public void testVoteDownInvalidSong()
    {
        when(mockQueue.vote(sandstorm, -1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testUser.changeVote(sandstorm, -1));
    }

    @Test
    public void testVoteRemovalInvalidSong()
    {
        when(mockQueue.vote(sandstorm, 0)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testUser.changeVote(sandstorm, 0));
    }

    @Test(expected = InputMismatchException.class)
    public void testLowVoteValue()
    {
        testUser.changeVote(despacito, -2);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowestVoteValue()
    {
        testUser.changeVote(despacito, Integer.MIN_VALUE);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowHighValue()
    {
        testUser.changeVote(neverGonnaGiveYouUp, 2);
    }

    @Test(expected = InputMismatchException.class)
    public void testLowestHighestValue()
    {
        testUser.changeVote(neverGonnaGiveYouUp, Integer.MAX_VALUE);
    }

    @Test
    public void testRequestTrack()
    {
        when(mockQueue.append(neverGonnaGiveYouUp)).thenReturn(0);
        assertEquals(0, testUser.requestTrack(neverGonnaGiveYouUp));
    }

    @Test
    public void testRequestInvalidTrack()
    {
        when(mockQueue.append("a")).thenThrow(InputMismatchException.class);
        assertEquals(-1, testUser.requestTrack("a"));
    }

    @Test
    public void testEquals()
    {
        User testUser2 = new User("0", mockEvent);
        assertEquals(testUser, testUser2);
    }

    @Test
    public void testNotEquals()
    {
        User testUser3 = new User("1", mockEvent);
        assertNotEquals(testUser, testUser3);
    }

    @Test
    public void testNotEqualsWrongObject()
    {
        assertNotEquals(testUser, "test");
    }

    @Test
    public void testHash()
    {
        User testUser4 = new User("1", mockEvent);
        assertNotEquals(testUser.hashCode(), testUser4.hashCode());
    }
}

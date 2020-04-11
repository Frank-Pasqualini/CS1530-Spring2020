package media.jambox.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTest
{
    static User testUser;
    static ArrayList<ArrayList<String>> expectedVotes;
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
        mockEvent = Mockito.mock(Event.class);
        Mockito.when(mockEvent.getEventCode()).thenReturn(0);
        mockQueue = Mockito.mock(Queue.class);

        Mockito.when(mockEvent.getQueue()).thenReturn(mockQueue);

        expectedVotes = new ArrayList<>();
        expectedVotes.add(new ArrayList<>());
        expectedVotes.add(new ArrayList<>());

        testUser = new User("0", mockEvent);
    }

    @Test
    public void testGetId()
    {
        assertEquals("0", testUser.getId());
    }

    @Test
    public void testGetUpvoted()
    {
        ArrayList<String> expectedUpvotes = new ArrayList<>();
        expectedUpvotes.add(neverGonnaGiveYouUp);
        testUser.changeVote(neverGonnaGiveYouUp, 1);
        assertEquals(expectedUpvotes, testUser.getUpvoted());
    }

    @Test
    public void testGetDownvoted()
    {
        ArrayList<String> expectedDownvotes = new ArrayList<>();
        expectedDownvotes.add(despacito);
        testUser.changeVote(despacito, -1);
        assertEquals(expectedDownvotes, testUser.getDownvoted());
    }

    @Test
    public void testVoteUp()
    {
        expectedVotes.get(0).add(neverGonnaGiveYouUp);
        assertEquals(expectedVotes, testUser.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testVoteDown()
    {
        expectedVotes.get(1).add(despacito);
        assertEquals(expectedVotes, testUser.changeVote(despacito, -1));
    }

    @Test
    public void testDoubleVoteUp()
    {
        expectedVotes.get(0).add(neverGonnaGiveYouUp);
        testUser.changeVote(neverGonnaGiveYouUp, 1);
        assertEquals(expectedVotes, testUser.changeVote(neverGonnaGiveYouUp, 1));
    }

    @Test
    public void testDoubleVoteDown()
    {
        expectedVotes.get(1).add(despacito);
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
        Mockito.when(mockQueue.vote(sandstorm, 1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testUser.changeVote(sandstorm, 1));
    }

    @Test
    public void testVoteDownInvalidSong()
    {
        Mockito.when(mockQueue.vote(sandstorm, -1)).thenThrow(new InputMismatchException());
        assertEquals(expectedVotes, testUser.changeVote(sandstorm, -1));
    }

    @Test
    public void testVoteRemovalInvalidSong()
    {
        Mockito.when(mockQueue.vote(sandstorm, 0)).thenThrow(new InputMismatchException());
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
        Mockito.when(mockQueue.append(neverGonnaGiveYouUp)).thenReturn(0);
        assertTrue(testUser.requestTrack(neverGonnaGiveYouUp));
    }

    @Test
    public void testRequestInvalidTrack()
    {
        Mockito.when(mockQueue.append("a")).thenThrow(InputMismatchException.class);
        assertFalse(testUser.requestTrack("a"));
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
    public void testNotEqualsEvent()
    {
        Event mockEvent2 = Mockito.mock(Event.class);
        Mockito.when(mockEvent2.getEventCode()).thenReturn(1);
        User testUser3 = new User("0", mockEvent2);
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

    @Test
    public void testDisconnect()
    {
        testUser.disconnect();
        Mockito.verify(mockEvent, Mockito.times(1)).removeUser("0");
    }
}

package media.jambox.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EventTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String playlistId = "65zguqi47ENgOSL285EIdU";
    private final transient int eventCode = 1111;
    private final transient String hostId = "franklesby";
    private transient Event testEvent;
    private transient Playlist mockPlaylist;
    private transient Queue mockQueue;
    private transient Track mockNowPlaying;
    private transient Track mockUpNext;

    /**
     * Run before each test case.
     *
     * @throws InvalidKeyException If the Host does not have spotify premium.
     * @throws IOException An I/O exception occurred while searching for the Track information on Spotify.
     * @throws SpotifyWebApiException An API exception occurred while searching for the Track information on Spotify.
     */
    @Before
    public void setUp()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        mockPlaylist = Mockito.mock(Playlist.class);
        mockQueue = Mockito.mock(Queue.class);
        mockNowPlaying = Mockito.mock(Track.class);
        mockUpNext = Mockito.mock(Track.class);
        Mockito.when(mockQueue.pop()).thenReturn(mockNowPlaying, mockUpNext);

        testEvent = new Event(eventCode, playlistId, accessToken, hostId, mockPlaylist, mockQueue);
    }

    @Test(expected = InvalidKeyException.class)
    public void testNonPremium()
        throws InvalidKeyException
    {
        throw new InvalidKeyException();
        //TODO Implementing would require another refreshing accessToken.
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowCode()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        new Event(-1, playlistId, accessToken, hostId, mockPlaylist, mockQueue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHighCode()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        new Event(10000, playlistId, accessToken, hostId, mockPlaylist, mockQueue);
    }

    @Test
    public void testNonMockEvent()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        new Event(eventCode, playlistId, accessToken, hostId, null, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testEmptyPlaylist()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        Mockito.when(mockQueue.pop()).thenThrow(IndexOutOfBoundsException.class);
        new Event(eventCode, "45McaMvSG3vovfffyxEHz8", accessToken, hostId, mockPlaylist, mockQueue);
    }

    @Test
    public void testAddUser()
    {
        assertTrue(testEvent.addUser("0"));
    }

    @Test
    public void testAddDuplicateUser()
    {
        testEvent.addUser("0");
        assertFalse(testEvent.addUser("0"));
    }

    @Test
    public void testGetUser()
    {
        testEvent.addUser("0");
        assertEquals("0", testEvent.getUser("0").getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetInvalidUser()
    {
        assertEquals("0", testEvent.getUser("0").getId());
    }

    @Test
    public void testRemoveUser()
    {
        testEvent.addUser("0");
        assertEquals("0", testEvent.removeUser("0").getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonexistentUser()
    {
        testEvent.removeUser("0");
    }

    @Test
    public void testGetQueue()
    {
        assertEquals(mockQueue, testEvent.getQueue());
    }

    @Test
    public void testGetNowPlaying()
    {
        Mockito.when(mockNowPlaying.getId()).thenReturn("2WfaOiMkCvy7F5fcp2zZ8L");
        assertEquals("2WfaOiMkCvy7F5fcp2zZ8L", testEvent.getNowPlaying().getId());
    }

    @Test
    public void testGetUpNext()
    {
        Mockito.when(mockUpNext.getId()).thenReturn("3JOVTQ5h8HGFnDdp4VT3MP");
        assertEquals("3JOVTQ5h8HGFnDdp4VT3MP", testEvent.getUpNext().getId());
    }

    @Test
    public void testGetEventCode()
    {
        assertEquals(1111, testEvent.getEventCode());
    }

    @Test(expected = InvalidKeyException.class)
    public void testDeleteEventInvalid()
        throws InvalidKeyException
    {
        testEvent.deleteEvent("wrongvalue");
    }

    @Test(expected = InvalidKeyException.class)
    public void testIllegalCycle()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        testEvent.cycle("illegal");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testBadCycle()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        Mockito.when(mockQueue.pop()).thenThrow(IndexOutOfBoundsException.class);
        testEvent.cycle(accessToken);
    }

    @Test
    public void tesCycle()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        Mockito.when(mockQueue.pop()).thenReturn(Mockito.mock(Track.class));
        testEvent.cycle(accessToken);
    }
}

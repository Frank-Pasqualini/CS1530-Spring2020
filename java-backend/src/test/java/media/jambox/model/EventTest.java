package media.jambox.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.InputMismatchException;
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
     * @throws IOException e
     * @throws SpotifyWebApiException e
     */
    @Before
    public void setUp()
        throws IOException, SpotifyWebApiException
    {
        mockPlaylist = Mockito.mock(Playlist.class);
        mockQueue = Mockito.mock(Queue.class);
        mockNowPlaying = Mockito.mock(Track.class);
        mockUpNext = Mockito.mock(Track.class);
        Mockito.when(mockQueue.pop()).thenReturn(mockNowPlaying, mockUpNext);

        testEvent = new Event(eventCode, playlistId, accessToken, hostId, mockPlaylist, mockQueue);
    }

    @Test
    public void testNonMockEvent()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        new Event(eventCode, playlistId, accessToken, hostId, null, null);
    }

    @Test(expected = InputMismatchException.class)
    public void testEmptyPlaylist()
        throws IOException, SpotifyWebApiException
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

    @Test(expected = InputMismatchException.class)
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

    @Test(expected = InputMismatchException.class)
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
}

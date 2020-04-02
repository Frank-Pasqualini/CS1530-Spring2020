package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.InputMismatchException;
import org.junit.Before;
import org.junit.Test;

public class EventTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String playlistId = "65zguqi47ENgOSL285EIdU";
    private final transient int eventCode = 1111;
    private final transient String hostId = "franklesby";
    private transient Event testEvent;

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
        testEvent = new Event(eventCode, playlistId, accessToken, hostId);
    }

    @Test(expected = InputMismatchException.class)
    public void testEmptyPlaylist()
        throws IOException, SpotifyWebApiException
    {
        new Event(eventCode, "45McaMvSG3vovfffyxEHz8", accessToken, hostId);
    }

    @Test
    public void testAddUser()
    {
        assertEquals("0", testEvent.addUser("0").get(1).getId());
    }

    @Test(expected = InputMismatchException.class)
    public void testAddDuplicateUser()
    {
        testEvent.addUser("0");
        testEvent.addUser("0");
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
    public void testGetHost()
    {
        assertEquals("franklesby", testEvent.getHost().getId());
    }

    @Test
    public void testGetPlaylist()
    {
        assertNotNull(testEvent.getPlaylist());
    }

    @Test
    public void testGetQueue()
    {
        assertTrue(testEvent.getQueue().sortAndDisplay().isEmpty());
    }

    @Test
    public void testGetNowPlaying()
    {
        assertEquals("2WfaOiMkCvy7F5fcp2zZ8L", testEvent.getNowPlaying().getId());
    }

    @Test
    public void testGetUpNext()
    {
        assertEquals("3JOVTQ5h8HGFnDdp4VT3MP", testEvent.getUpNext().getId());
    }

    @Test
    public void testGetEventCode()
    {
        assertEquals(1111, testEvent.getEventCode());
    }
}

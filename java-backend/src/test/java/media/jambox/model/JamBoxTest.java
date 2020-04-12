package media.jambox.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class JamBoxTest
{
    private static Event mockEvent;
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String playlistId = "65zguqi47ENgOSL285EIdU";
    private final transient String hostId = "franklesby";

    @BeforeClass
    public static void setUp()
    {
        assertNotNull(JamBox.getInstance());
        mockEvent = Mockito.mock(Event.class);
    }

    @Test
    public void testMultiple()
    {
        assertNotNull(JamBox.getInstance());
    }

    @Test
    public void testAddEvent()
        throws IOException, SpotifyWebApiException
    {
        JamBox.addEvent(playlistId, accessToken, hostId, 0, mockEvent);
        Mockito.when(mockEvent.getEventCode()).thenReturn(0);
        assertNotNull(JamBox.getEvent(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalEvent()
        throws IOException, SpotifyWebApiException
    {
        JamBox.addEvent(playlistId, accessToken, hostId, -2, mockEvent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalEvent2()
        throws IOException, SpotifyWebApiException
    {
        JamBox.addEvent(playlistId, accessToken, hostId, 10000, mockEvent);
    }

    @Test
    public void testAddDuplicateEvent()
        throws IOException, SpotifyWebApiException
    {
        int eventCode1 = JamBox.addEvent(playlistId, accessToken, hostId, 1, mockEvent);
        Mockito.when(mockEvent.getEventCode()).thenReturn(1);
        int eventCode2 = JamBox.addEvent(playlistId, accessToken, hostId, 1, mockEvent);
        assertNotEquals(eventCode1, eventCode2);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonexistentEvent()
    {
        JamBox.getEvent(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInvalidEvent()
    {
        JamBox.getEvent(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInvalidEvent2()
    {
        JamBox.getEvent(10000);
    }

    @Test
    public void testRemoveEvent()
        throws IOException, SpotifyWebApiException
    {
        JamBox.addEvent(playlistId, accessToken, hostId, 3, mockEvent);
        Mockito.when(mockEvent.getEventCode()).thenReturn(3);
        JamBox.removeEvent(3);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonexistentEvent()
    {
        JamBox.removeEvent(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidEvent()
    {
        JamBox.removeEvent(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidEvent2()
    {
        JamBox.removeEvent(10000);
    }

    @Test
    public void testAddNonMockEvent()
        throws IOException, SpotifyWebApiException
    {
        int eventCode = JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
        assertNotNull(JamBox.getEvent(eventCode));
    }

    @Test
    public void testEventDeleteEvent()
        throws IOException, SpotifyWebApiException
    {
        int eventCode = JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
        JamBox.getEvent(eventCode).deleteEvent(accessToken);
    }
}

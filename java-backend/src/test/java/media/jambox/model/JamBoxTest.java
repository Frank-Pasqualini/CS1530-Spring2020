package media.jambox.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.InputMismatchException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class JamBoxTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String playlistId = "65zguqi47ENgOSL285EIdU";
    private final transient String hostId = "franklesby";
    private static Event mockEvent;

    @BeforeClass
    public static void setUp()
    {
        JamBox.getInstance();
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
        int eventCode = JamBox.addEvent(playlistId, accessToken, hostId, -1, mockEvent);
        Mockito.when(mockEvent.getEventCode()).thenReturn(eventCode);
        assertNotNull(JamBox.getEvent(eventCode));
    }

    @Test
    public void testAddNonMockEvent()
        throws IOException, SpotifyWebApiException
    {
        int eventCode = JamBox.addEvent(playlistId, accessToken, hostId, -1, null);
        assertNotNull(JamBox.getEvent(eventCode));
    }

    @Test
    public void testAddDuplicateEvent()
        throws java.io.IOException, com.wrapper.spotify.exceptions.SpotifyWebApiException
    {
        int eventCode1 = JamBox.addEvent(playlistId, accessToken, hostId, 1, mockEvent);
        Mockito.when(mockEvent.getEventCode()).thenReturn(1);
        int eventCode2 = JamBox.addEvent(playlistId, accessToken, hostId, 1, mockEvent);
        assertNotEquals(eventCode1, eventCode2);
    }

    @Test(expected = InputMismatchException.class)
    public void testGetInvalidEvent()
    {
        JamBox.getEvent(0);
    }
}

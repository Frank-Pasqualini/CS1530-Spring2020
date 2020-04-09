package media.jambox;

import static org.junit.Assert.assertEquals;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class JamBoxTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private transient JamBox testJamBox;

    @Before
    public void setUp()
    {
        testJamBox = JamBox.getInstance();
    }

    @Test
    public void testMultiple()
    {
        testJamBox = JamBox.getInstance();
    }

    @Test
    public void testAddEvent()
        throws IOException, SpotifyWebApiException
    {
        final String playlistId = "65zguqi47ENgOSL285EIdU";
        final int eventCode = 1111;
        final String hostId = "franklesby";
        JamBox.addEvent(eventCode, playlistId, accessToken, hostId);
        assertEquals(1, testJamBox.eventList.size());
    }
}
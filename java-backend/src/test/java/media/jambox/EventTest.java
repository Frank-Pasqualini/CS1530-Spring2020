package media.jambox;

import static org.junit.Assert.assertEquals;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.Test;

public class EventTest {
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String accessTokenScoped = System.getenv("TEST_ACCESS_CODE_SCOPED");
    private final transient String playlistId = "45McaMvSG3vovfffyxEHz8";
    private final transient int eventCode = 1111;
    private final transient String hostId = "Host_01";
    private transient Queue testQueue;

    /**
     * Run before each test case.
     * @throws IOException e
     * @throws SpotifyWebApiException e
     */
    @Before
    public void setUp()
            throws IOException, SpotifyWebApiException
    {
        testEvent = new Event(eventCode, playlistId, accessToken, hostId);
    }

    @Test
    public void testAddGuest()
    {
    }
}

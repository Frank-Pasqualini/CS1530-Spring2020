package media.jambox;

import static org.junit.Assert.assertEquals;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.Test;

public class QueueTest
{

    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String accessTokenScoped = System.getenv("TEST_ACCESS_CODE_SCOPED");
    private final transient String id = "65zguqi47ENgOSL285EIdU";
    private transient Queue testQueue;

    private final transient String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
    private final transient String despacito = "6rPO02ozF3bM7NnOV4h6s2";

    /**
     * Run before each test case.
     * @throws IOException e
     * @throws SpotifyWebApiException e
     */
    @Before
    public void setUp()
            throws IOException, SpotifyWebApiException
    {
        testQueue = new Queue(id, accessToken);
        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, -1);
        testQueue.vote(neverGonnaGiveYouUp, 1);

        testQueue.vote(neverGonnaGiveYouUp, -1);
    }

    @Test
    public void testSortAndDisplay()
    {
        testQueue.sortAndDisplay();
    }

    @Test
    public void testPop()
    {
        testQueue.pop();
    }

    @Test
    public void testAppend()
    {

        testQueue.append(despacito, accessToken); // track not yet in queue

        try
        {
            testQueue.append(despacito, accessToken); // track already in queue
        }
        catch (InputMismatchException e)
        {
            assertEquals("Tried to add Track already in Queue", e.getMessage());
        }
    }

    @Test
    public void testRemoveTrackSucceed()
    {
        testQueue.append(despacito, accessToken); // track not yet in queue
        testQueue.removeTrack(despacito); //remove track in queue
    }

    @Test
    public void testRemoveTrackFailed()
    {
        try
        {
            testQueue.removeTrack(despacito);
        }
        catch (InputMismatchException e)
        {
            assertEquals("Tried to remove Track not in Queue", e.getMessage());
        }

    }

    @Test
    public void testVoteUpSucceed()
    {
        testQueue.append(despacito, accessToken); // track not yet in queue
        testQueue.vote(despacito, 1); //vote +1 for track in queue
    }

    @Test
    public void testVoteDownSucceed()
    {
        testQueue.append(despacito, accessToken); // track not yet in queue
        testQueue.vote(despacito, -1); //vote -1 for track in queue
    }

    @Test
    public void testVoteAmountFailed()
    {
        testQueue.append(despacito,  accessToken); // track not yet in queue
        try
        {
            testQueue.vote(despacito, 2); //vote +2 for track in queue
        }
        catch (InputMismatchException e)
        {
            assertEquals("invalid vote increment/decrement", e.getMessage());
        }
    }

    @Test
    public void testVoteOnInvalidTrack()
    {
        try
        {
            testQueue.vote(despacito, 1); //vote +1 for track not in queue
        }
        catch (InputMismatchException e)
        {
            assertEquals("Cannot vote on Track not in Queue", e.getMessage());
        }
    }
}
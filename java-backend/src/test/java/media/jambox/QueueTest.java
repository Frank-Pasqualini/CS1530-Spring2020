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
    private final transient String id = "45McaMvSG3vovfffyxEHz8";
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
        testQueue = new Queue(id, accessToken);
        testQueue.vote("7GhIk7Il098yCjg4BQjzvb", 1);
        testQueue.vote("7GhIk7Il098yCjg4BQjzvb", 1);
        testQueue.vote("7GhIk7Il098yCjg4BQjzvb", -1);
        testQueue.vote("7GhIk7Il098yCjg4BQjzvb", 1);

        testQueue.vote("6rPO02ozF3bM7NnOV4h6s2", -1);
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

        testQueue.append("6rPO02ozF3bM7NnOV4h6s2", accessToken); // track not yet in queue

        try
        {
            testQueue.append("6rPO02ozF3bM7NnOV4h6s2", accessToken); // track already in queue
        }
        catch (InputMismatchException e)
        {
            assertEquals("Tried to add Track already in Queue", e.getMessage());
        }
    }

    @Test
    public void testRemoveTrackSucceed()
    {
        testQueue.append("6rPO02ozF3bM7NnOV4h6s2", accessToken); // track not yet in queue
        testQueue.removeTrack("6rPO02ozF3bM7NnOV4h6s2"); //remove track in queue
    }

    @Test
    public void testRemoveTrackFailed()
    {
        try
        {
            testQueue.removeTrack("6rPO02ozF3bM7NnOV4h6s2");
        }
        catch (InputMismatchException e)
        {
            assertEquals("Tried to remove Track not in Queue", e.getMessage());
        }

    }

    @Test
    public void testVoteUpSucceed()
    {
        testQueue.append("6rPO02ozF3bM7NnOV4h6s2", accessToken); // track not yet in queue
        testQueue.vote("6rPO02ozF3bM7NnOV4h6s2", 1); //vote +1 for track in queue
    }

    @Test
    public void testVoteDownSucceed()
    {
        testQueue.append("6rPO02ozF3bM7NnOV4h6s2", accessToken); // track not yet in queue
        testQueue.vote("6rPO02ozF3bM7NnOV4h6s2", -1); //vote -1 for track in queue
    }

    @Test
    public void testVoteAmountFailed()
    {
        testQueue.append("6rPO02ozF3bM7NnOV4h6s2",  accessToken); // track not yet in queue
        try
        {
            testQueue.vote("6rPO02ozF3bM7NnOV4h6s2", 2); //vote +2 for track in queue
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
            testQueue.vote("6rPO02ozF3bM7NnOV4h6s2", 1); //vote +1 for track not in queue
        }
        catch (InputMismatchException e)
        {
            assertEquals("Cannot vote on Track not in Queue", e.getMessage());
        }
    }
}
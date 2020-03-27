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
    private final transient String putYourRecordsOn = "2nGFzvICaeEWjIrBrL2RAx";
    private final transient String yellowEyes = "7t304VesCjiRAvpJ6IW8HG";
    private final transient String fuckItILoveYou = "7MtVPRGtZl6rPjMfLoI3Lh";
    private final transient String river = "3mRLHiSHYtC8Hk7bzZdUs1";
    private final transient String lifeAintTheSame = "2fwS74WBkH2cRweraxie9P";
    private final transient String belAmi = "1WJPsblf1uqY9sr1IEWDrV";

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
    }

    @Test
    public void testAppendSuccess()
    {
        testQueue.append(despacito, accessToken); // track not yet in queue
    }

    @Test(expected = InputMismatchException.class)
    public void testAppendCopy()
    {
        testQueue.append(despacito); // track not yet in queue
        testQueue.append(despacito); // track already in queue
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

    @Test(expected = InputMismatchException.class)
    public void testVoteAmountFailed()
    {
        testQueue.append(despacito); // track not yet in queue
        testQueue.vote(despacito, 2); //vote +2 for track in queue
    }

    @Test(expected = InputMismatchException.class)
    public void testVoteOnInvalidTrack()
    {
        testQueue.vote(despacito, 1); //vote +1 for track not in queue
    }

    @Test
    public void testSortAndDisplay()
    {
        testQueue.append(neverGonnaGiveYouUp, accessToken);
        testQueue.append(putYourRecordsOn, accessToken);
        testQueue.append(yellowEyes, accessToken);
        testQueue.append(fuckItILoveYou, accessToken);
        testQueue.append(river, accessToken);
        testQueue.append(lifeAintTheSame, accessToken);
        testQueue.append(belAmi, accessToken);

        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, 1);

        testQueue.vote(putYourRecordsOn, 1);
        testQueue.vote(putYourRecordsOn, 1);

        testQueue.vote(lifeAintTheSame, 1);

        testQueue.vote(fuckItILoveYou, -1);
        testQueue.vote(fuckItILoveYou, -1);

        testQueue.vote(belAmi, -1);
        testQueue.vote(belAmi, -1);
        testQueue.vote(belAmi, -1);

        testQueue.sortAndDisplay();
    }

    @Test
    public void testPop()
    {
        testQueue.pop();
    }

    @Test
    public void testRemoveTrackSucceed()
    {
        testQueue.append(despacito, accessToken); // track not yet in queue
        testQueue.removeTrack(despacito); //remove track in queue
    }

    @Test(expected = InputMismatchException.class)
    public void testRemoveTrackFailed()
    {
        testQueue.removeTrack(despacito);
    }

}
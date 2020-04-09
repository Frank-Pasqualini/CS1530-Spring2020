package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.Test;

public class QueueTest
{
    private transient Queue testQueue;

    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String id = "65zguqi47ENgOSL285EIdU";

    //in initial playlist
    private final transient String takeOnMe = "2WfaOiMkCvy7F5fcp2zZ8L";
    private final transient String madWorld = "3JOVTQ5h8HGFnDdp4VT3MP";

    //songs to add to queue
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
    public void testPop()
    {
        assertEquals(takeOnMe, testQueue.pop().getId());
    }

    @Test
    public void testAppendSuccess()
    {
        int spot = testQueue.append(despacito); // track not yet in queue
        // testQueue.pop();
        // testQueue.pop();
        // assertEquals(despacito, testQueue.pop().getId());
        assertEquals(2, spot);
    }

    @Test
    public void testNotEmptySuccess()
    {
        assertFalse(testQueue.isEmpty());
    }

    @Test
    public void testEmptySuccess()
    {
        testQueue.pop();
        testQueue.pop();
        assertTrue(testQueue.isEmpty());
    }

    @Test
    public void testAppendCopy()
    {
        testQueue.append(takeOnMe); // track already in queue
        assertEquals(takeOnMe, testQueue.pop().getId());
        testQueue.pop(); //remove Mad World
        assertTrue(testQueue.isEmpty()); //check a second takeOnMe not added
    }

    @Test
    public void testRemoveTrackSucceed()
    {
        testQueue.append(despacito); // track not yet in queue
        assertEquals(despacito, testQueue.removeTrack(despacito).getId()); //remove track in queue
    }

    @Test(expected = InputMismatchException.class)
    public void testRemoveTrackFailed()
    {
        testQueue.removeTrack(despacito);
    }

    @Test
    public void testVoteUpSucceed()
    {
        testQueue.vote(takeOnMe, 1); //vote +1 for track in queue
        assertEquals(1, testQueue.removeTrack(takeOnMe).getScore());
    }

    @Test
    public void testVoteDownSucceed()
    {
        testQueue.vote(takeOnMe, -1); //vote -1 for track in queue
        assertEquals(-1, testQueue.removeTrack(takeOnMe).getScore());
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
    public void testSortAndDisplay() throws IOException, SpotifyWebApiException
    {
        testQueue.append(putYourRecordsOn);
        testQueue.append(neverGonnaGiveYouUp);
        testQueue.append(yellowEyes);
        testQueue.append(fuckItILoveYou);
        testQueue.append(lifeAintTheSame);
        testQueue.append(river);
        testQueue.append(belAmi);

        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, 1);
        testQueue.vote(neverGonnaGiveYouUp, 1);

        testQueue.vote(putYourRecordsOn, 1);
        testQueue.vote(putYourRecordsOn, 1);

        testQueue.vote(takeOnMe, 1);

        testQueue.vote(lifeAintTheSame, 1);

        testQueue.vote(fuckItILoveYou, -1);
        testQueue.vote(fuckItILoveYou, -1);

        testQueue.vote(belAmi, -1);
        testQueue.vote(belAmi, -1);
        testQueue.vote(belAmi, -1);

        testQueue.removeTrack(river);

        ArrayList<Track> expectedQueue = new ArrayList();
        Track neverGonnaGiveYouUpTrack = new Track(neverGonnaGiveYouUp, accessToken);
        Track putYourRecordsOnTrack = new Track(putYourRecordsOn, accessToken);
        Track takeOnMeTrack = new Track(takeOnMe, accessToken);
        Track lifeAintTheSameTrack = new Track(lifeAintTheSame, accessToken);
        Track madWorldTrack = new Track(madWorld, accessToken);
        Track yellowEyesTrack = new Track(yellowEyes, accessToken);
        Track fuckItILoveYouTrack = new Track(fuckItILoveYou, accessToken);
        Track belAmiTrack = new Track(belAmi, accessToken);

        expectedQueue.add(neverGonnaGiveYouUpTrack);
        expectedQueue.add(putYourRecordsOnTrack);
        expectedQueue.add(takeOnMeTrack);
        expectedQueue.add(lifeAintTheSameTrack);
        expectedQueue.add(madWorldTrack);
        expectedQueue.add(yellowEyesTrack);
        expectedQueue.add(fuckItILoveYouTrack);
        expectedQueue.add(belAmiTrack);

        assertEquals(expectedQueue, testQueue.sortAndDisplay());
    }


}
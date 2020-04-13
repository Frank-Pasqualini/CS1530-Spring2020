package media.jambox.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class QueueTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private final transient String takeOnMe = "2WfaOiMkCvy7F5fcp2zZ8L";
    private final transient String despacito = "6rPO02ozF3bM7NnOV4h6s2";
    private transient Queue testQueue;

    @Before
    public void setUp()
        throws IOException, SpotifyWebApiException
    {
        final String id = "65zguqi47ENgOSL285EIdU";
        testQueue = new Queue(id, accessToken);
    }

    @Test
    public void testPop()
    {
        assertEquals(takeOnMe, testQueue.pop().getId());
    }

    @Test
    public void testAppendSuccess()
        throws IOException, SpotifyWebApiException
    {
        int spot = testQueue.append(despacito);
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
        throws IOException, SpotifyWebApiException
    {
        testQueue.append(takeOnMe); // track already in queue
        assertEquals(takeOnMe, testQueue.pop().getId());
        testQueue.pop(); //remove Mad World
        assertTrue(testQueue.isEmpty()); //check a second takeOnMe not added
    }

    @Test
    public void testRemoveTrackSucceed()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        testQueue.append(despacito); // track not yet in queue
        assertEquals(despacito, testQueue.removeTrack(despacito, accessToken).getId()); //remove track in queue
    }

    @Test(expected = InvalidKeyException.class)
    public void testRemoveTrackInvalid()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        testQueue.append(despacito); // track not yet in queue
        assertEquals(despacito, testQueue.removeTrack(despacito, "wrongToken").getId()); //remove track in queue
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveTrackFailed()
        throws InvalidKeyException
    {
        testQueue.removeTrack(despacito, accessToken);
    }

    @Test
    public void testVoteUpSucceed()
        throws InvalidKeyException
    {
        testQueue.vote(takeOnMe, 1); //vote +1 for track in queue
        assertEquals(1, testQueue.removeTrack(takeOnMe, accessToken).getScore());
    }

    @Test
    public void testVoteDownSucceed()
        throws InvalidKeyException
    {
        testQueue.vote(takeOnMe, -1); //vote -1 for track in queue
        assertEquals(-1, testQueue.removeTrack(takeOnMe, accessToken).getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoteAmountFailed()
        throws IOException, SpotifyWebApiException
    {
        testQueue.append(despacito); // track not yet in queue
        testQueue.vote(despacito, 2); //vote +2 for track in queue
    }

    @Test(expected = NoSuchElementException.class)
    public void testVoteOnInvalidTrack()
    {
        testQueue.vote(despacito, 1); //vote +1 for track not in queue
    }

    @Test
    public void testSortAndDisplay()
        throws InvalidKeyException, IOException, SpotifyWebApiException
    {
        final String putYourRecordsOn = "2nGFzvICaeEWjIrBrL2RAx";
        testQueue.append(putYourRecordsOn);
        //songs to add to queue
        final String neverGonnaGiveYouUp = "7GhIk7Il098yCjg4BQjzvb";
        testQueue.append(neverGonnaGiveYouUp);
        final String yellowEyes = "7t304VesCjiRAvpJ6IW8HG";
        testQueue.append(yellowEyes);
        final String fuckItILoveYou = "7MtVPRGtZl6rPjMfLoI3Lh";
        testQueue.append(fuckItILoveYou);
        final String lifeAintTheSame = "2fwS74WBkH2cRweraxie9P";
        testQueue.append(lifeAintTheSame);
        final String river = "3mRLHiSHYtC8Hk7bzZdUs1";
        testQueue.append(river);
        final String belAmi = "1WJPsblf1uqY9sr1IEWDrV";
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

        testQueue.removeTrack(river, accessToken);

        ArrayList<Track> expectedQueue = new ArrayList<>();
        Track neverGonnaGiveYouUpTrack = new Track(neverGonnaGiveYouUp, accessToken);
        Track putYourRecordsOnTrack = new Track(putYourRecordsOn, accessToken);
        Track takeOnMeTrack = new Track(takeOnMe, accessToken);
        Track lifeAintTheSameTrack = new Track(lifeAintTheSame, accessToken);
        final String madWorld = "3JOVTQ5h8HGFnDdp4VT3MP";
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

        assertEquals(expectedQueue, testQueue.getTrackList());
    }
}

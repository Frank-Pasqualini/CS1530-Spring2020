package media.jambox.model;

import java.security.InvalidKeyException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HostTest
{
    private final transient String accessToken = System.getenv("TEST_ACCESS_CODE");
    private transient Host testHost;
    private transient Event mockEvent;

    @Before
    public void setUp()
    {
        mockEvent = Mockito.mock(media.jambox.model.Event.class);
        testHost = new Host("franklesby", mockEvent);
    }

    @Test(expected = IllegalCallerException.class)
    public void testDisconnect()
    {
        testHost.disconnect();
    }

    @Test
    public void testEndEvent()
        throws InvalidKeyException
    {
        testHost.endEvent(accessToken);
        Mockito.verify(mockEvent, Mockito.times(1)).deleteEvent(accessToken);
    }

    @Test
    public void testRemoveTrack()
        throws InvalidKeyException
    {
        Queue mockQueue = Mockito.mock(Queue.class);
        Mockito.when(mockEvent.getQueue()).thenReturn(mockQueue);
        testHost.removeTrack("6rPO02ozF3bM7NnOV4h6s2", accessToken);
        Mockito.verify(mockQueue, Mockito.times(1)).removeTrack("6rPO02ozF3bM7NnOV4h6s2", accessToken);
    }
}

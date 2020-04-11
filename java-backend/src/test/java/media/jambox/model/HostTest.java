package media.jambox.model;

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

    @Test
    public void testDisconnect()
    {
        testHost.disconnect();
    }

    @Test
    public void testEndEvent()
    {
        testHost.endEvent(accessToken);
        Mockito.verify(mockEvent, Mockito.times(1)).deleteEvent(accessToken);
    }

    @Test
    public void testRemoveTrack()
    {
        Queue mockQueue = Mockito.mock(Queue.class);
        Mockito.when(mockEvent.getQueue()).thenReturn(mockQueue);
        testHost.removeTrack("6rPO02ozF3bM7NnOV4h6s2", accessToken);
        Mockito.verify(mockQueue, Mockito.times(1)).removeTrack("6rPO02ozF3bM7NnOV4h6s2", accessToken);
    }
}

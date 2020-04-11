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
}

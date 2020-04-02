package media.jambox;

import static org.mockito.Mockito.mock;

import org.junit.Before;

public class HostTest
{
    static Host testHost;

    @Before
    public void setUp()
    {
        Event mockEvent = mock(Event.class);
        testHost = new Host("franklesby", mockEvent);
    }
}

package media.jambox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class HostTest
{
    static Host testHost;

    @Before
    public void setUp()
    {
        Event mockEvent = mock(Event.class);
        testHost = new Host("0", mockEvent);
    }

    @Test
    public void testGetId()
    {
        assertEquals("0", testHost.getId());
    }
}

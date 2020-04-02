package media.jambox;

import org.junit.Before;
import org.junit.Test;

public class JamBoxTest
{
    private transient JamBox testJamBox;

    @Before
    public void setUp()
    {
        testJamBox = JamBox.getInstance();
    }

    @Test
    public void testMultiple()
    {
        testJamBox = JamBox.getInstance();
    }
}
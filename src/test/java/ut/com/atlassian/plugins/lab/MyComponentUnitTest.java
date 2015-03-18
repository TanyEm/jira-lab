package ut.com.atlassian.plugins.lab;

import org.junit.Test;
import com.atlassian.plugins.lab.MyPluginComponent;
import com.atlassian.plugins.lab.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}
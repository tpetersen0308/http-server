package app;

import app.support.Directory;
import app.support.DirectoryIndex;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DirectoryIndexTest {
    @Test
    public void shouldDynamicallyCreateHTMLForDirectoryIndex() {
        String directory = "./src/test/java/stubs/app/public_stub";
        String absDirectory = new File(directory).getAbsolutePath();
        Directory.setPath(absDirectory);
        String expectedHTML = "<h1>Index</h1>" +
                "<h3>public_stub</h3>" +
                    "<ul>" +
                        "<li><a href='/foo.txt'>foo.txt</a></li>" +
                        "<li><a href='/hello_world.html'>hello_world.html</a></li>" +
                        "<li><a href='/more_stuff'>more_stuff</a></li>" +
                        "<li><a href='/other_stuff'>other_stuff</a></li>" +
                    "</ul>";

        DirectoryIndex directoryIndex = new DirectoryIndex(absDirectory);

        assertEquals(expectedHTML, directoryIndex.toHTML());
    }

    @Test
    public void shouldDynamicallyCreateHTMLForSubDirectoryIndex() {
        String directory = "./src/test/java/stubs/app/public_stub";
        String absDirectory = new File(directory).getAbsolutePath();
        Directory.setPath(absDirectory);
        String expectedHTML = "<h1>Index</h1>" +
                "<h3>orange</h3>" +
                    "<ul>" +
                        "<li><a href='/other_stuff/orange/youglad'>youglad</a></li>" +
                    "</ul>";

        DirectoryIndex directoryIndex = new DirectoryIndex(absDirectory + "/other_stuff/orange");

        assertEquals(expectedHTML, directoryIndex.toHTML());
    }
}

package app;

import app.support.DefaultDirectory;
import app.support.DirectoryIndex;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DefaultDirectoryIndexTest {
    @Test
    public void shouldDynamicallyCreateHTMLForDirectoryIndex() {
        String directory = "./src/test/java/stubs/app/public_stub";
        String absDirectory = new File(directory).getAbsolutePath();
        DefaultDirectory.setPath(absDirectory);
        String expectedHTML = "<h1>Index</h1>" +
            "<h3>public_stub</h3>" +
            "<ul>" +
            "<li><a href='/.DS_Store'>.DS_Store</a></li>" +
            "<li><a href='/foo.txt'>foo.txt</a></li>" +
            "<li><a href='/hello_world.html'>hello_world.html</a></li>" +
            "<li><a href='/more_stuff'>more_stuff</a></li>" +
            "<li><a href='/other_stuff'>other_stuff</a></li>" +
            "<li><a href='/so_rich.rtf'>so_rich.rtf</a></li>" +
            "</ul>";

        DirectoryIndex directoryIndex = new DirectoryIndex(absDirectory);

        assertEquals(expectedHTML, directoryIndex.toHTML());
    }

    @Test
    public void shouldDynamicallyCreateHTMLForSubDirectoryIndex() {
        String directory = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(directory);
        String expectedHTML = "<h1>Index</h1>" +
                "<h3>orange</h3>" +
                    "<ul>" +
                        "<li><a href='/other_stuff/orange/youglad'>youglad</a></li>" +
                    "</ul>";

        DirectoryIndex directoryIndex = new DirectoryIndex(DefaultDirectory.PATH + "/other_stuff/orange");

        assertEquals(expectedHTML, directoryIndex.toHTML());
    }
}

package server.directory;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DefaultDirectoryTest {
    @Test
    public void shouldOnlyAllowPathToBeSetOnce() {
        String path1 = "./src/test/java/stubs/app/public_stub";
        String path2 = "./src/test/java/stubs/app/public_stub/more_stuff";

        DefaultDirectory.setPath(path1);
        DefaultDirectory.setPath(path2);
        String expectedPath = new File(path1).getAbsolutePath();

        assertEquals(expectedPath, DefaultDirectory.path());
    }
}

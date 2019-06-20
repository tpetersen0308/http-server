package server.directory;

import java.io.File;

public class DefaultDirectory {
    public static String PATH;

    private DefaultDirectory() {
    }

    public static void setPath(String path) {
        PATH = new File(path).getAbsolutePath();
    }
}

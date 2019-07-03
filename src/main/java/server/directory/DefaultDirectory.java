package server.directory;

import java.io.File;

public class DefaultDirectory {
    public static String PATH;
    private static boolean pathNotSet = true;

    private DefaultDirectory() {
    }

    public static void setPath(String path) {
        if (pathNotSet) {
            PATH = new File(path).getAbsolutePath();
            pathNotSet = false;
        }
    }

    public static String path() {
        return PATH;
    }
}

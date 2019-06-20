package server.directory;

import java.io.File;
import java.util.Arrays;

public class DirectoryIndex {
    private File directory;

    public DirectoryIndex(String directory) {
        this.directory = new File(directory);
    }

    public String toHTML() {
        StringBuilder html = new StringBuilder(indexHeader("Index"));
        html.append(currentFolder());
        html.append(contentsList());

        return html.toString();
    }

    private String indexHeader(String title) {
        return String.format("<h1>%s</h1>", title);
    }

    private String currentFolder() {
        return String.format("<h3>%s</h3>", directory.getName());
    }

    private File[] sortedContents() {
        File[] directoryContents = directory.listFiles();
        Arrays.sort(directoryContents);
        return directoryContents;
    }

    private String contentsList() {
        StringBuilder contentsList = new StringBuilder("<ul>");

        for(File file : sortedContents()) {
            String href = file.getPath().replace(DefaultDirectory.PATH, "");
            String filename = file.getName();
            contentsList.append(String.format("<li><a href='%s'>%s</a></li>", href, filename));
        }

        contentsList.append("</ul>");

        return contentsList.toString();
    }
}

package server.response;

import server.directory.DefaultDirectory;
import server.directory.DirectoryIndex;
import server.request.Request;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.HeaderFields;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Renderers {
    private Renderers() {
    }

    public static Response renderFile(Request request, String path) throws IOException {
        Path filePath;

        filePath = getPath(path);

        byte[] fileContents = Files.readAllBytes(filePath);

        Response response = render(request, fileContents);
        return new Response.Builder(response)
            .withHeader(HeaderFields.CONTENT_TYPE, HeaderHelpers.contentType(new File(path)))
            .build();
    }

    private static Path getPath(String path) {
        if (path.contains(DefaultDirectory.PATH))
            return Paths.get(path);
        return Paths.get(DefaultDirectory.PATH + path);
    }

    public static Response renderDirectory(Request request, String path) {
        String directoryHTML = new DirectoryIndex(path).toHTML();
        Response response = render(request, directoryHTML.getBytes());

        return new Response.Builder(response)
            .withHeader(HeaderFields.CONTENT_TYPE, HeaderHelpers.contentType(new File(".html")))
            .build();
    }

    public static Response render(Request request, byte[] body, Map<String, String> headers) {
        Response.Builder response = new Response.Builder(render(request, body));

        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.withHeader(header.getKey(), header.getValue());
        }

        return response.build();
    }

    public static Response render(Request request, String body) {
        return render(request, body.getBytes());
    }

    public static Response render(Request request, byte[] body) {
        Response response = Types.ok(body.length);

        if (request.method().equals(HTTPMethods.HEAD))
            return response;

        return new Response.Builder(response)
            .withBody(body)
            .build();
    }

    public static Response redirectTo(Request request, String path) {
        String host = request.headers().get(HeaderFields.HOST);
        return Types.movedPermanently(host, path);
    }
}

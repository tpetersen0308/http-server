package app.support;

import app.Routes;
import server.request.Request;
import server.response.Response;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.Status;
import server.response.stringcomponents.WhiteSpace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ResponseHelpers {
    private static Map<String, Map<String, ResponseHandler>> routes = Routes.ROUTES;

    public static Response renderTextFile(Request request, String path) {
        String fileContents = WhiteSpace.EMPTY_BODY;
        Path filePath = Paths.get(path);

        try {
            fileContents = new String(Files.readAllBytes(filePath));
        } catch (IOException err) {
            System.out.println(err);
        }

        Response response = render(request, fileContents);
        return new Response.Builder(response)
            .withContentTypeHeader(new File(path))
            .build();
    }

    public static Response renderDirectory(Request request, String path) {
        String directoryHTML = new DirectoryIndex(path).toHTML();
        Response response = render(request, directoryHTML);

        return new Response.Builder(response)
            .withContentTypeHeader(new File(".html"))
            .build();
    }

    public static Response render(Request request, String body, Map<String, String> headers) {
        Response result = render(request, body);

        return new Response.Builder(result)
            .withCustomHeaders(headers)
            .build();
    }

    public static Response render(Request request, String body) {
        Response response = new Response.Builder()
            .withStatus(Status.OK)
            .withContentLengthHeader(body)
            .withAllowHeader(routes.get(request.path()))
            .build();

        if (request.method().equals(HTTPMethods.HEAD))
            return response;

        return new Response
            .Builder(response)
            .withBody(body)
            .build();
    }

    public static Response redirectTo(Request request, String path) {
        return new Response.Builder()
            .withStatus(Status.MOVED_PERMANENTLY)
            .withLocationHeader(request, path)
            .build();
    }
}

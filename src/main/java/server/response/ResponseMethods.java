package server.response;

import server.directory.DirectoryIndex;
import server.RequestHandler;
import server.request.Request;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ResponseMethods {
    private ResponseMethods() {
    }

    public static Response renderFile(Request request, String path) {
        byte[] fileContents = WhiteSpace.EMPTY_BODY;
        Path filePath = Paths.get(path);

        try {
            fileContents = Files.readAllBytes(filePath);
        } catch (IOException err) {
            System.out.println(err);
        }

        Response response = render(request, fileContents);
        return new Response.Builder(response)
            .withHeader(HeaderFields.CONTENT_TYPE, ResponseHelpers.contentType(new File(path)))
            .build();
    }

    public static Response renderDirectory(Request request, String path) {
        String directoryHTML = new DirectoryIndex(path).toHTML();
        Response response = render(request, directoryHTML.getBytes());

        return new Response.Builder(response)
            .withHeader(HeaderFields.CONTENT_TYPE, ResponseHelpers.contentType(new File(".html")))
            .build();
    }

    public static Response render(Request request, byte[] body, Map<String, String> headers) {
        Response.Builder response = new Response.Builder(render(request, body));

        for(Map.Entry<String, String> header : headers.entrySet()) {
           response.withHeader(header.getKey(), header.getValue());
        }

        return response.build();
    }

    public static Response render(Request request, String body) {
        return render(request, body.getBytes());
    }

    public static Response render(Request request, byte[] body) {
        Response response = ResponseTypes.ok(body.length);

        if (request.method().equals(HTTPMethods.HEAD))
            return response;

        return new Response.Builder(response)
            .withBody(body)
            .build();
    }

    public static Response redirectTo(Request request, String path) {
        String host = request.headers().get(HeaderFields.HOST);
        return ResponseTypes.movedPermanently(host, path);
    }

    public static Response fromRoute(Request request, Map<String, RequestHandler> route) {
        Response response = route.get(request.method()).dispatch(request);
        return new Response.Builder(response)
            .withHeader(HeaderFields.ALLOWED_METHODS, ResponseHelpers.allowedMethods(route))
            .build();
    }
}

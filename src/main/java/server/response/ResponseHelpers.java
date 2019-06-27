package server.response;

import server.RequestHandler;
import server.response.stringcomponents.ContentTypes;
import server.response.stringcomponents.HTTP;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ResponseHelpers {
    private ResponseHelpers() {
    }

    public static String allowedMethods(Map<String, RequestHandler> route) {
        if(route == null)
            return "GET, HEAD";

        Set<String> methodSet = route.keySet();
        String[] allowedMethods = methodSet.toArray(new String[methodSet.size()]);
        return String.join(", ", allowedMethods);
    }

    public static String location(String host, String path) {
        return HTTP.URL_PREFIX + host + path;
    }

    public static String contentType(File file) {
        String extension = fileExtension(file);
        String contentType = ContentTypes.HEADER_VALUES.get(extension);
        if(contentType == null)
            return ContentTypes.HEADER_VALUES.get("");
        return contentType;
    }

    private static String fileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}

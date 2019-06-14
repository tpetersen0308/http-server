package server.response.stringcomponents;

public class Status {
    private Status() {
    }

    public static final String OK = "200 OK";
    public static final String MOVED_PERMANENTLY = "301 Moved Permanently";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
}

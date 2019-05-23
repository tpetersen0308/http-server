package server;

public class StatusCodes {
    private StatusCodes() {}

    public static final String NOT_FOUND = "HTTP/1.1 404 Not Found\r\n\r\n";
    public static final String OK = "HTTP/1.1 200 OK\r\n\r\n";
}

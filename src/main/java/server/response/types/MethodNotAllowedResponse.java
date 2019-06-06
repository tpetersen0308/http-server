package server.response.types;

import server.response.Response;
import server.response.stringcomponents.Status;

import java.util.Map;

public class MethodNotAllowedResponse extends Response {
    public MethodNotAllowedResponse(Map<String, String> headers) {
        super(headers);
        this.status = Status.METHOD_NOT_ALLOWED;
    }
}

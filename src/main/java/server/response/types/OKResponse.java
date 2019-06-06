package server.response.types;

import server.response.Response;
import server.response.stringcomponents.Status;

import java.util.Map;

public class OKResponse extends Response {
    public OKResponse(Map<String, String> headers, String body) {
        super(headers, body);
        this.status = Status.OK;
    }
}

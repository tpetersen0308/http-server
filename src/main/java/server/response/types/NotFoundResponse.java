package server.response.types;

import server.response.Response;
import server.response.stringcomponents.Status;

public class NotFoundResponse extends Response {
    public NotFoundResponse() {
        super();
        this.status = Status.NOT_FOUND;
    }
}

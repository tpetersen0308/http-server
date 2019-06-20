package server.response;

import server.response.stringcomponents.WhiteSpace;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String status;
    private Map<String, String> headers;
    private byte[] body;

    private Response() {
    }

    public String status() {
        return status;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public byte[] body() {
        return body;
    }

    public static class Builder {
        private Response response;
        private String status;
        private Map<String, String> headers;
        private byte[] body;

        public Builder(Response response) {
            this.response = new Response();
            this.status = response.status;
            this.headers = response.headers;
            this.body = response.body;
        }

        public Builder() {
            this.response = new Response();
            this.headers = new HashMap<>();
            this.body = WhiteSpace.EMPTY_BODY;
        }

        public Builder withStatus(String status) {
            this.status = status;

            return this;
        }

        public Builder withHeader(String field, String value) {
            this.headers.put(field, value);

            return this;
        }

        public Builder withBody(byte[] body) {
            this.body = body;

            return this;
        }

        public Response build() {
            response.status = this.status;
            response.headers = this.headers;
            response.body = this.body;

            return response;
        }
    }
}

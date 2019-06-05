package server;

import app.App;
import server.request.Request;
import server.request.RequestParser;
import server.response.Response;
import server.response.ResponseBuilder;
import server.response.HTTPResponseFormatter;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class HTTProtocol implements Runnable {
    private Client client;
    private BufferedReader in;
    private PrintWriter out;
    private App app;

    public HTTProtocol(Client client, App app) {
        this.client = client;
        this.in = client.getInputStreamReader();
        this.out = client.getOutputStreamWriter();
        this.app = app;
    }

    public void run() {
        Request request = getParsedRequest();
        String response = getStringifiedResponse(request);

        out.print(response);
        out.flush();
        client.closeSocket();
    }

    private Request getParsedRequest() {
        RequestParser requestParser = new RequestParser(in);
        return requestParser.parse();
    }

    private String getStringifiedResponse(Request request) {
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();
        HTTPResponseFormatter formatter = new HTTPResponseFormatter(response);
        return formatter.stringifyResponse();
    }
}

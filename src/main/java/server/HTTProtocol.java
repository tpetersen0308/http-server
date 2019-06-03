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
        Request request = RequestParser.parse(in);
        Response response = ResponseBuilder.buildResponse(request, app);
        String parsedResponse = HTTPResponseFormatter.stringify(response);
        out.print(parsedResponse);
        out.flush();
        client.closeSocket();
    }
}

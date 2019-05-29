package server;

import server.request.Request;
import server.request.RequestParser;
import server.response.Response;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class HTTProtocol implements Runnable {
    private Client client;
    private BufferedReader in;
    private PrintWriter out;

    public HTTProtocol(Client client) {
        this.client = client;
        this.in = client.getInputStreamReader();
        this.out = client.getOutputStreamWriter();
    }

    public void run() {
        RequestParser requestParser = new RequestParser(in);
        Request request = requestParser.parse();
        Response response = new Response(request);

        out.print(response.getStatusLine());
        out.flush();
        client.closeSocket();
    }
}

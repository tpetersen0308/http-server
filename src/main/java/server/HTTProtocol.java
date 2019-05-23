package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

import static server.StatusCodes.OK;
import static server.StatusCodes.NOT_FOUND;
import static server.Routes.ROUTES;

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
        RequestParser parser = new RequestParser();
        HashMap parsedRequest = parser.parse(in);
        String requestLine = (String)parsedRequest.get("requestLine");
        String requestPath = requestLine.split(" ")[1].trim();

        if(Arrays.asList(ROUTES).contains(requestPath)){
            out.print(OK);
        } else {
            out.print(NOT_FOUND);
        }
        out.flush();
        client.closeSocket();
    }
}

package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import static server.StatusCodes.*;

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
            RequestParser parser = new RequestParser(in);
            HashMap parsedRequest = parser.parse();
            String requestLine = (String)parsedRequest.get("requestLine");

            if(requestLine.contains("/simple_get")){
                out.print(OK);
            } else {
                out.print(NOT_FOUND);
            }
            out.flush();
            client.closeSocket();
    }
}

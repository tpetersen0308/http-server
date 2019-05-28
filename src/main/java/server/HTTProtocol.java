package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

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
        RequestParser parser = new RequestParser(in);
        Request request = new Request(parser.parse());
        String responseStatus = getResponseStatus(request);

        out.print(responseStatus);
        out.flush();
        client.closeSocket();
    }

    private String getResponseStatus(Request request) {
        if (Arrays.asList(ROUTES).contains(request.getPath())) {
            return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + StatusCodes.OK + ResponseComponents.SP + ReasonPhrases.OK + ResponseComponents.CRLF + ResponseComponents.CRLF;
        } else {
            return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + StatusCodes.NOT_FOUND + ResponseComponents.SP + ReasonPhrases.NOT_FOUND + ResponseComponents.CRLF + ResponseComponents.CRLF;
        }
    }
}

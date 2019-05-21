package server;

import java.io.BufferedReader;
import java.io.IOException;
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
        try {
            while(in.readLine() != null) {
                out.print("HTTP/1.1 404 Not Found\r\n");
                out.flush();
                client.closeSocket();
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}

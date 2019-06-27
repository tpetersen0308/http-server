package server;

import resources.Logger;

import java.io.OutputStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    protected Socket socket;
    protected BufferedReader inputStreamReader;
    protected OutputStream outputStream;
    private Logger logger = new Logger();

    public Client(Socket socket) {
        this.socket = socket;
        try {
            inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = socket.getOutputStream();
        } catch (IOException err) {
            logger.log(err);
        }
    }

    public String read() throws IOException {
        String content = inputStreamReader.readLine();

        return content;
    }

    public String read(int contentLength) throws IOException {
        char[] data = new char[contentLength];
        inputStreamReader.read(data, 0, contentLength);

        return new String(data);
    }

    public void write(byte[] data) {
        try {
            outputStream.write(data);
        } catch (IOException err) {
            logger.log(err);
        }
    }

    public void closeSocket() {
        try {
            outputStream.flush();
            socket.close();
        } catch (IOException err) {
            logger.log(err);
        }
    }
}

package server;

import java.io.OutputStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private Socket socket;
    private BufferedReader inputStreamReader;
    private OutputStream outputStream;

    public Client(Socket socket) {
        this.socket = socket;
        setInputStreamReader(socket);
        setOutputStream(socket);
    }

    public String read() {
        String content = "";
        try {
            content = in().readLine();
        } catch (IOException err) {
            System.out.println(err);
        }

        return content;
    }

    public String read(int contentLength) {
        char[] data = new char[contentLength];
        try {
            in().read(data, 0, contentLength);
        } catch (IOException err) {
            System.out.println(err);
        }

        return new String(data);
    }

    public void write(byte[] data) {
        try {
            out().write(data);
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public OutputStream out() {
        return outputStream;
    }

    public void closeSocket() {
        try {
            out().flush();
            socket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private BufferedReader in() {
        return inputStreamReader;
    }

    private void setInputStreamReader(Socket socket) {
        try {
            inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    private void setOutputStream(Socket socket) {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}

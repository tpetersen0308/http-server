package resources;

public class Logger {
    public void log(Exception err) {
        System.err.println(err.getMessage());
    }
}

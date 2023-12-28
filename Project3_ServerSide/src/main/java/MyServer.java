import java.io.Serializable;
import java.util.function.Consumer;

public class MyServer extends Server {
    public MyServer(int port) {
        super(port);
    }

    @Override
    protected void acceptCallback(String message) {
        ServerStatus.updateServerLog(message);
    }
}
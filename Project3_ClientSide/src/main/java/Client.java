import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    Stage temp;
    private String ipAddress;
    private int port;
    private Socket socketClient;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Consumer<Serializable> callback;

    boolean isConnected = false;

    Client(String ipAddress, int port, Consumer<Serializable> call, Stage primaryStage) {
        temp = primaryStage;
        this.ipAddress = ipAddress;
        this.port = port;
        callback = call;
    }

    public void run() {
        try {
            socketClient = new Socket(ipAddress, port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            isConnected = true;

            // Start the game only if the connection is successful
            Platform.runLater(() -> {
                try {
                    new Game().start(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            while (true) {
                String message = in.readObject().toString();

                if(message.equals("Folded_Results")){
                    Game.GameLog("You folded: Lost all your money ");//make money = 0
                    Game.UpdateWinnings(0);
                }

                if(message.equals("Returned")){
                    Game.GameLog("");
                }

                if(message.equals("Lost_Everything")){
                    Game.UpdateWinnings(0);
                }

                if(message.equals("Won")){
                    int temp = (int) in.readObject();

                    Game.GameLog("You Won Money " + temp);

                    Game.UpdateWinnings(temp);

                }

                if(message.equals("All_Money")){
                    int temp = (int) in.readObject();
                    Game.GameLog("You Won Money (Play Wager) " + temp);

                    Game.UpdateWinnings(temp);
                }
                else{
                    Game.GameLog(message);
                }





                System.out.println(message);
//                Game.GameLog(message);
                callback.accept(message);
            }

        } catch (Exception e) {
            // Handle connection error by displaying a pop-up dialog with the error message
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to connect to server. Please check the IP address and port number and try again.");
                alert.showAndWait();

                try {
                    new StartScreen().start(temp);
                } catch (Exception p) {
                    p.printStackTrace();
                }
            });
        } finally {
            if (isConnected) {
                try {
                    out.writeObject("client_disconnect");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            closeResources();
        }
    }


    // Add a new method to wait for the client to be connected
    public void waitForConnection() {
        while (!isConnected) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String data) {
        if (isConnected) { // Add a check for isConnected before calling writeObject
            try {
                out.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendarray(int [] data) {
        if (isConnected) { // Add a check for isConnected before calling writeObject
            try {
                out.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResources() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socketClient != null) socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
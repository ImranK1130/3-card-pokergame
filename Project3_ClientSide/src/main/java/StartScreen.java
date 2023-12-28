import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.function.Consumer;

public class StartScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene welcomeScene = createWelcomeScene(primaryStage);
        primaryStage.setTitle("Game");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private Scene createWelcomeScene(Stage primaryStage) {
        // Create a VBox layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #336699;"); // Set a custom background color

        // Create a title for the welcome screen
        Text title = new Text("Welcome To 3 Card Poker!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);

        // Create input fields for IP address and port number
        TextField ipAddressInput = new TextField();
        ipAddressInput.setPromptText("IP Address");
        ipAddressInput.setMaxWidth(200); // Set the maximum width of the text field
        TextField portNumberInput = new TextField();
        portNumberInput.setPromptText("Port Number");
        portNumberInput.setMaxWidth(200); // Set the maximum width of the text field

        // Create a connect button
        Button connectButton = new Button("Connect");
        connectButton.setDisable(true); // Set the initial disabled state of the button
        connectButton.setOnAction(e -> {
            String ipAddress = ipAddressInput.getText();
            int portNumber = Integer.parseInt(portNumberInput.getText());

            try {
                // Connect to the server and change the GUI to the game play screen
                connectToServerAndStartGame(primaryStage, ipAddress, portNumber);
            } catch (Exception ex) {
                // Display an error message if the connection fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to connect to server: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // Bind the disabled property of the connect button to the text fields' textProperty() values
        BooleanBinding bothFieldsFilled = ipAddressInput.textProperty().isEmpty().or(portNumberInput.textProperty().isEmpty());
        connectButton.disableProperty().bind(bothFieldsFilled);

        // Add the elements to the VBox layout
        root.getChildren().addAll(title, ipAddressInput, portNumberInput, connectButton);

        // Create and return the welcome scene
        return new Scene(root, 1000, 1000);
    }




//    private void connectToServerAndStartGame(Stage primaryStage, String ipAddress, int portNumber) {
//        // Create a socket with the given IP address and port number
////            Socket socket = new Socket(ipAddress, portNumber);
////
////            // Initialize input and output streams for the socket
////            DataInputStream in = new DataInputStream(socket.getInputStream());
////            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
////
////            // Send a message to the server to confirm the connection
//////            out.writeUTF("Connected to server.");
////
////            // Read a message from the server to confirm the connection
//////            String response = in.readUTF();
//////            System.out.println(response);
//////
////            // Close the input and output streams and the socket
//////            in.close();
//////            out.close();
////            socket.close();
//
//        //------------------------------------------------------------------------
//
//        Client.Client(ipAddress,portNumber);
//        Client.run2();
//
//        // Start the game with the primaryStage object
//        playGame(primaryStage);
//    }


    private void connectToServerAndStartGame(Stage primaryStage, String ipAddress, int portNumber) {

        Consumer<Serializable> messageHandler = message -> System.out.println("Server: " + message);


        Client client = new Client(ipAddress, portNumber, messageHandler,primaryStage);
        client.start();

        Game.startup(client);

        // Send data to the server
        client.send("Hello, server!");

//        playGame(primaryStage,client);
    }

//    private void playGame(Stage primaryStage, Client client) {
//        // Check if the client is connected to the server before starting the game
////        if (!client.isConnected) {
////            // Display an error message to the user and return without starting the game
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Connection Error");
////            alert.setHeaderText(null);
////            alert.setContentText("You must connect to the server before starting the game.");
////            alert.showAndWait();
////            return;
////        }
//
//        // Print a message to the console to indicate that the game is starting
//        System.out.println("Starting game...");
//
//        try {
//            // Create a new instance of the Game class and start the game with the primaryStage object
//            new Game().start(primaryStage);
//        } catch (Exception e) {
//            // If an exception occurs, print the stack trace to the console for debugging purposes
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }

}

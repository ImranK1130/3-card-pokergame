import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Consumer;

public class StartScreenServer extends Application {



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
        Text title = new Text("Welcome To 3 Card Poker! (Server Side)");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);

        Label portNumberLabel = new Label("Enter port number:");
        portNumberLabel.setTextFill(Color.WHITE);


        TextField portNumberInput = new TextField();
        portNumberInput.setPromptText("Port Number");
        portNumberInput.setMaxWidth(200); // Set the maximum width of the text field

        HBox portNumberBox = new HBox(10, portNumberLabel, portNumberInput);
        portNumberBox.setAlignment(Pos.CENTER);

        // Create a connect button
        Button connectButton = new Button("Turn On Server");
        connectButton.setDisable(true); // Set the initial disabled state of the button
        connectButton.setOnAction(e -> {
            int portNumber = Integer.parseInt(portNumberInput.getText());

            try {

                MyServer server = new MyServer(portNumber);

//                server.startServer(portNumber);
                connectButton.disableProperty().unbind(); // Unbind the property
                connectButton.setDisable(true); // Disable the button after the server starts
                // Show a message that the server has started
                Label serverStatusLabel = new Label("Server started on port " + portNumber);
                serverStatusLabel.setTextFill(Color.WHITE);
                root.getChildren().add(serverStatusLabel);

                // Launch the server status GUI


                new ServerStatus(server).start(primaryStage);
            } catch (Exception p) {
                // If an exception occurs, print the stack trace to the console for debugging purposes
                p.printStackTrace();
            }
        });

        // Bind the disabled property of the connect button to the text fields' textProperty() values
        BooleanBinding portNumberFilled = portNumberInput.textProperty().length().lessThan(4);
        connectButton.disableProperty().bind(portNumberFilled);

        // Add the elements to the VBox layout
        root.getChildren().addAll(title, portNumberBox, connectButton);

        // Create and return the welcome scene
        return new Scene(root, 1000, 1000);
    }



    public static void main(String[] args) {
        launch(args);
    }

}


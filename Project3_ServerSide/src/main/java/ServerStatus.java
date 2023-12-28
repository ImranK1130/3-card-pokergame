import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerStatus extends Application {
    private static Server server;

    private static TextArea serverLog;
    private static Text numClientsText;

    public ServerStatus(Server server) {
        this.server = server;
    }



    @Override
    public void start(Stage primaryStage) {
        Scene serverStatusScene = createServerStatusScene(primaryStage);
        primaryStage.setTitle("Server Status");
        primaryStage.setScene(serverStatusScene);
        primaryStage.show();
    }

    private Scene createServerStatusScene(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text serverStatusTitle = new Text("Server Status");
        serverStatusTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        serverLog = new TextArea();
        serverLog.setPrefSize(400, 400);

        numClientsText = new Text();
        numClientsText.setStyle("-fx-font-size: 16;");

        HBox numClientsBox = new HBox(numClientsText);
        numClientsBox.setAlignment(Pos.TOP_RIGHT);
        numClientsText.setText("Number of clients connected: " + server.clients.size());

        Button closeButton = new Button("Close Server");
        closeButton.setOnAction(event -> {
            // Close the server and exit the application
            System.exit(0);
        });

        root.getChildren().addAll(serverStatusTitle, serverLog, closeButton);
        root.getChildren().add(numClientsBox);

        return new Scene(root);
    }

    static void updateServerLog(String message) {
        Platform.runLater(() -> {
            serverLog.appendText(message + "\n");
            numClientsText.setText("Number of clients connected: " + MyServer.clients.size());
        });
    }

    static void updateServerLog2(int count) {
        Platform.runLater(() -> {
            numClientsText.setText("Number of clients connected: " + count);
        });
    }
}

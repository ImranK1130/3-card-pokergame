import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EndScreen extends Application {

    private static int winnings = 100; // Replace with the actual winnings value from your game

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateWinnings(int temp){
        winnings = temp;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ece95c");

        Label titleLabel = new Label(winnings == 0 ? "You lose!" : "You win!");
//        titleLabel.setStyle("-text");
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));


        TextField winningsField = new TextField("Winnings: $" + winnings);
        winningsField.setEditable(false);

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(event -> System.exit(0));
        exitButton.setStyle("-fx-background-color: red");
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button anotherGameButton = new Button("Another Game");
        anotherGameButton.setStyle("-fx-background-color: green");
        anotherGameButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        anotherGameButton.setOnAction(event -> {
            Platform.runLater(() -> {
                try {
                    new Game().start(primaryStage);
                } catch (Exception p) {
                    p.printStackTrace();
                }
            });
            System.out.println("Starting another game...");
        });

        root.getChildren().addAll(titleLabel, winningsField, exitButton, anotherGameButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("End Screen");
        primaryStage.show();
    }
}
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.desktop.SystemEventListener;
import java.util.Arrays;
import java.util.Random;

public class Game extends Application {
    int continueClicked = 0;

    static Client client;

    private static TextArea gameUpdates;

    private static TextArea winningsField;

    static int tempWinnings = 0;


    static  int totalMoney = 0;


    private int[] wagers = new int[3]; // array to store wagers

    private int[] AllForOne = new int[9]; // array to store wagers
    private int[] playerCardIndexes = new int[3]; // array to store player card indexes
    private int[] dealerCardIndexes = new int[3]; // array to store dealer card indexes

    public static void startup(Client tempclient) {

        client = tempclient;
    }


    @Override
    public void start(Stage primaryStage) {
        // Create the main layout with a border pane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
//        root.setStyle("-fx-background-image:  ");
//        Image backgroundI = new Image("pokerbackground.png");
//        BackgroundImage backgroundImage = new BackgroundImage(backgroundI, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//        // Create a background from the background image
//        Background background = new Background(backgroundImage);

        root.setStyle("-fx-background-color: #409340");


        // Set the background to the pane
//        root.setBackground(background);

        // Create the menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setTranslateX(-40);
        menuBar.setTranslateY(10);

        // Create the Options menu
        Menu optionsMenu = new Menu("Options");

        // Create the menu items
        MenuItem freshStartItem = new MenuItem("Fresh Start");
        MenuItem newLookItem = new MenuItem("NewLook");
        MenuItem exitItem = new MenuItem("Exit");


        // Add the menu items to the Options menu
        optionsMenu.getItems().addAll(newLookItem, freshStartItem, exitItem);

        // Add the Options menu to the menu bar
        menuBar.getMenus().add(optionsMenu);

//        root.setTop(menuBar);


        // Create the grid pane for the wagers
        GridPane wagerGrid = new GridPane();
        wagerGrid.setHgap(10);
        wagerGrid.setVgap(10);

        // Add the labels for the wagers
        Text anteLabel = new Text("Ante");
        anteLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//        wagerGrid.add(anteLabel, 1, 1);

        Text pairPlusLabel = new Text("Pair Plus");
        pairPlusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//        wagerGrid.add(pairPlusLabel, 1, 1);

        wagerGrid.setTranslateY(-120);
        wagerGrid.setTranslateX(10);

        pairPlusLabel.setTranslateX(-620);
        pairPlusLabel.setTranslateY(180);

        anteLabel.setTranslateX(-750);
        anteLabel.setTranslateY(180);

        // Add the input fields for the wagers
        TextField anteField = new TextField("0");
        anteField.setDisable(false);
        anteField.setPrefWidth(60);
        anteField.setTranslateY(200);
        anteField.setTranslateX(-205);

        TextField pairPlusField = new TextField("0");
        pairPlusField.setDisable(false);
        pairPlusField.setPrefWidth(60);
        pairPlusField.setTranslateY(200);
        pairPlusField.setTranslateX(-70);


        // Add event handlers for the text fields to update the wagers array
        anteField.setOnAction(e -> {
            wagers[0] = (int) Double.parseDouble(anteField.getText());
            System.out.println("Wagers: " + Arrays.toString(wagers));
        });

        pairPlusField.setOnAction(e -> {
            wagers[1] = (int) Double.parseDouble(pairPlusField.getText());
            System.out.println("Wagers: " + Arrays.toString(wagers));
        });


//        // Add the wager grid to the center of the border pane
//        root.setCenter(wagerGrid);

        // Create the deal button and add it to the bottom of the screen
        Button dealButton = new Button("Deal");
//        root.setTop(dealButton);
        dealButton.setStyle("-fx-background-color: black;");
        dealButton.setTextFill(Color.WHITE);
        dealButton.setTranslateY(240);
        dealButton.setTranslateX(-60);
        dealButton.setPrefSize(50,40);


        // Create Play and Fold Buttons and set the coordinates for the button
        Button playButton = new Button(("Play"));
        playButton.setStyle("-fx-background-color: #0f7505; -fx-font-weight: bold;"); // Set button background color to green
//        playButton.setFont();
        Button foldButton = new Button("Fold");
        foldButton.setStyle("-fx-background-color: #0476fa; -fx-font-weight: bold;"); // Set button background color to blue




        // buttons are disabled by default
        playButton.setDisable(true);
        foldButton.setDisable(true);

//        playButton.setPrefSize(100,30);
        foldButton.setPrefSize(100,30);
        foldButton.setMinSize(100,30);
        playButton.setPrefSize(100,30);
        playButton.setMinSize(100,30);

//        playButton.setAlignment(Pos.BOTTOM_CENTER);
        playButton.setTranslateY(75);
        playButton.setTranslateX(430);
//        foldButton.setAlignment((Pos.BOTTOM_CENTER));
        foldButton.setTranslateY(75);
        foldButton.setTranslateX(430);

        // create hbox to add all bottom of game screen items
        HBox bottomBox = new HBox(20);

        // Create the textField that will display the game updates

//     // Create the textField that will display the game updates
        gameUpdates = new TextArea(); // create the gameUpdates object in the start method
        gameUpdates.setEditable(false);
//        gameUpdates.setPrefSize(450,200);
        gameUpdates.setMaxSize(400,180);
        gameUpdates.setMinSize(400,180);
        gameUpdates.setTranslateX(-250);
        gameUpdates.setTranslateY(0);





        //Array of card names
        String[] cards = {"2_of_clubs.png", "2_of_diamonds.png", "2_of_hearts.png", "2_of_spades.png",
                "3_of_clubs.png", "3_of_diamonds.png", "3_of_hearts.png", "3_of_spades.png", "4_of_clubs.png",
                "4_of_diamonds.png", "4_of_hearts.png", "4_of_spades.png", "5_of_clubs.png", "5_of_diamonds.png",
                "5_of_hearts.png", "5_of_spades.png", "6_of_clubs.png", "6_of_diamonds.png", "6_of_hearts.png",
                "6_of_spades.png", "7_of_clubs.png", "7_of_diamonds.png", "7_of_hearts.png", "7_of_spades.png",
                "8_of_clubs.png", "8_of_diamonds.png", "8_of_hearts.png", "8_of_spades.png", "9_of_clubs.png",
                "9_of_diamonds.png", "9_of_hearts.png", "9_of_spades.png", "10_of_clubs.png", "10_of_diamonds.png",
                "10_of_hearts.png", "10_of_spades.png", "jack_of_clubs2.png", "jack_of_diamonds2.png", "jack_of_hearts2.png", "jack_of_spades2.png",
                "king_of_clubs2.png", "king_of_diamonds2.png", "king_of_hearts2.png", "king_of_spades2.png", "queen_of_clubs2.png",
                "queen_of_diamonds2.png", "queen_of_hearts2.png", "queen_of_spades2.png" , "ace_of_clubs.png", "ace_of_diamonds.png", "ace_of_hearts.png",
                "ace_of_spades2.png"};


        //random number generator to get three random cards for the client cards
        Random random = new Random();
        do {
            playerCardIndexes[0] = random.nextInt(52);
            playerCardIndexes[1] = random.nextInt(52);
            playerCardIndexes[2] = random.nextInt(52);
        } while (playerCardIndexes[0] == playerCardIndexes[1] || playerCardIndexes[0] == playerCardIndexes[2] || playerCardIndexes[1] == playerCardIndexes[2]);

        // Random number generator to get three unique random cards for the dealer side
        do {
            dealerCardIndexes[0] = random.nextInt(52);
            dealerCardIndexes[1] = random.nextInt(52);
            dealerCardIndexes[2] = random.nextInt(52);
        } while (dealerCardIndexes[0] == dealerCardIndexes[1] || dealerCardIndexes[0] == dealerCardIndexes[2] || dealerCardIndexes[1] == dealerCardIndexes[2]);

        // Adding card images to game

        Image cardImage1 = new Image("playingCardBack.png");
        Image cardImage2 = new Image("playingCardBack.png");
        Image cardImage3 = new Image("playingCardBack.png");


        ImageView cardView1 = new ImageView();
        ImageView cardView2 = new ImageView();
        ImageView cardView3 = new ImageView();
        //Card 1
        cardView1.setImage(cardImage1);
        cardView1.setFitWidth(250/2);
        cardView1.setFitHeight(363/2);
        // Card 2
        cardView2.setImage(cardImage2);
        cardView2.setFitWidth(250/2);
        cardView2.setFitHeight(363/2);
        // Card 3
        cardView3.setImage(cardImage3);
        cardView3.setFitWidth(250/2);
        cardView3.setFitHeight(363/2);

        //Setting Coordinates for the 3 cards
        cardView1.setTranslateY(-235);
        cardView1.setTranslateX(-300);

        cardView2.setTranslateY(-235);
        cardView2.setTranslateX(-300);

        cardView3.setTranslateY(-235);
        cardView3.setTranslateX(-300);



        // CARDS FOR DEALER SIDE ----------------------------------------------------------

        Image cardImage1D = new Image("playingCardBack.png");
        Image cardImage2D = new Image("playingCardBack.png");
        Image cardImage3D = new Image("playingCardBack.png");

        ImageView cardView1D = new ImageView();
        ImageView cardView2D = new ImageView();
        ImageView cardView3D = new ImageView();
        //Card 1
        cardView1D.setImage(cardImage1D);
        cardView1D.setFitWidth(250/2);
        cardView1D.setFitHeight(363/2);
        // Card 2
        cardView2D.setImage(cardImage2D);
        cardView2D.setFitWidth(250/2);
        cardView2D.setFitHeight(363/2);
        // Card 3
        cardView3D.setImage(cardImage3D);
        cardView3D.setFitWidth(250/2);
        cardView3D.setFitHeight(363/2);

        //Setting Coordinates for the 3 cards
        cardView1D.setTranslateY(40);
        cardView1D.setTranslateX(200);

        cardView2D.setTranslateY(40);
        cardView2D.setTranslateX(220);

        cardView3D.setTranslateY(40);
        cardView3D.setTranslateX(240);


        HBox topBox = new HBox();

        // winnings label and textBox
        Label winningsLabel = new Label("Winnings:");
        TextArea winningsField = new TextArea("$ " + tempWinnings);

        winningsLabel.setFont(Font.font("Arial",FontWeight.BOLD, 14));
//        winningsField.setPrefSize(5, 5);
        winningsField.setMinSize(100,30);
        winningsField.setMaxSize(100,30);
        //disable TextField so plays cannot change it
        winningsField.setDisable(true);
        // set location for the winningsLabel and winningsField
        winningsLabel.setTranslateY(375);
        winningsLabel.setTranslateX(-600);

        winningsField.setTranslateY(0);
        winningsField.setTranslateX(0);

        // Continue button for when the user folds or plays and wins
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: #afef17; -fx-font-weight: bold;"); // Set button color to yellowish-green

        Button endGameButton = new Button("End Game");
        endGameButton.setStyle("-fx-background-color: #de1818; -fx-font-weight: bold;"); // Set button background color red


        //set the locations for the Continue and End Game buttons
        continueButton.setTranslateX(0);
        continueButton.setTranslateY(0);

        endGameButton.setTranslateX(0);
        endGameButton.setTranslateX(0);

        HBox endContinueBox = new HBox(5);
        endContinueBox.getChildren().addAll(endGameButton,continueButton);


        endGameButton.setOnAction(e ->{
            Platform.runLater(() -> {
                try {
                    EndScreen.updateWinnings(totalMoney);
                    new EndScreen().start(primaryStage);
                } catch (Exception p) {
                    p.printStackTrace();
                }
            });
        });

        endContinueBox.setTranslateX(780);
        endContinueBox.setTranslateY(90);



        bottomBox.getChildren().addAll(playButton,foldButton,gameUpdates,cardView1,cardView2, cardView3);
        topBox.getChildren().addAll(menuBar, dealButton, pairPlusField, anteField, cardView1D, cardView2D, cardView3D, winningsLabel, pairPlusLabel, anteLabel);
//        root.setTop(topBox);
//        root.setRight(winningsField);
//        root.setLeft(endContinueBox);
//        root.setBottom(bottomBox);

//        wagerGrid.getChildren().addAll(bottomBox, topBox, winningsField, endContinueBox);

        wagerGrid.add(bottomBox,1,55);
        wagerGrid.add(topBox, 1,10);
        wagerGrid.add(winningsField,1, 30);
        wagerGrid.add(endContinueBox,1, 20);


        // Add the wager grid to the center of the border pane
        root.setCenter(wagerGrid);


        foldButton.setOnAction(e -> {

            //enable continue button
            continueButton.setDisable(true);

            //disable fold and play button
            foldButton.setDisable(true);
            playButton.setDisable(true);

            // Get the indices of the dealer's cards
            int dealerCard1Index = dealerCardIndexes[0];
            int dealerCard2Index = dealerCardIndexes[1];
            int dealerCard3Index = dealerCardIndexes[2];

            // Load the images corresponding to the dealer's cards
            Image dealerCardImage1 = new Image(cards[dealerCard1Index]);
            Image dealerCardImage2 = new Image(cards[dealerCard2Index]);
            Image dealerCardImage3 = new Image(cards[dealerCard3Index]);

            // Set the dealer's cards images to the CardView objects
            cardView1D.setImage(dealerCardImage1);
            cardView2D.setImage(dealerCardImage2);
            cardView3D.setImage(dealerCardImage3);

//            String message = "Fold_Button_Clicked";
//            client.send(message);
//            client.sendarray(dealerCardIndexes);

            String message2 = "Folding_in_process";
            client.send(message2);

//            Platform.runLater(() -> {
//                try {
//                    new EndScreen().start(primaryStage);
//                } catch (Exception p) {
//                    p.printStackTrace();
//                }
//            });


            // Update the game updates text field to show that the player has folded
//            gameUpdates.appendText("Player has folded! Dealer's cards are revealed.");
        });

//        boolean counter = true;
//
//        // make the deal button disabled unless the user inputs the right values in the anteField and pairPlusField
//        if(5 > (int) Double.parseDouble(anteField.getText()) || (int) Double.parseDouble(anteField.getText()) > 25 || (int) Double.parseDouble(anteField.getText()) ==0 ){
//            counter = false;
//        }
//        if (5 > (int) Double.parseDouble(pairPlusField.getText()) || (int) Double.parseDouble(pairPlusField.getText()) > 25) {
//            counter = false;
//        }
//
//        if(0 == (int) Double.parseDouble(pairPlusField.getText())){
//            counter = true;
//        }

        continueButton.setOnAction(e1 -> {

            continueClicked++;
            Image playingCardBack = new Image("playingCardBack.png");

            // reset all cards to the backs
            cardView1D.setImage(playingCardBack);
            cardView2D.setImage(playingCardBack);
            cardView3D.setImage(playingCardBack);

            cardView1.setImage(playingCardBack);
            cardView2.setImage(playingCardBack);
            cardView3.setImage(playingCardBack);

            Random temp = new Random();
            do {
                playerCardIndexes[0] = temp.nextInt(52);
                playerCardIndexes[1] = temp.nextInt(52);
                playerCardIndexes[2] = temp.nextInt(52);
            } while (playerCardIndexes[0] == playerCardIndexes[1] || playerCardIndexes[0] == playerCardIndexes[2] || playerCardIndexes[1] == playerCardIndexes[2]);


            // Random number generator to get three unique random cards for the dealer side
            do {
                dealerCardIndexes[0] = temp.nextInt(52);
                dealerCardIndexes[1] = temp.nextInt(52);
                dealerCardIndexes[2] = temp.nextInt(52);
            } while (dealerCardIndexes[0] == dealerCardIndexes[1] || dealerCardIndexes[0] == dealerCardIndexes[2] || dealerCardIndexes[1] == dealerCardIndexes[2]);


            // enabling the deal button and wager textBoxes
            dealButton.setDisable(false);
            anteField.setDisable(false);
            pairPlusField.setDisable(false);

            //disable the play and fold buttons
            playButton.setDisable(true);
            foldButton.setDisable(true);


        });


//        boolean finalCounter = counter;
        dealButton.setOnAction(e -> {

            boolean counter = true;
            boolean counter2 = true;

            // make the deal button disabled unless the user inputs the right values in the anteField and pairPlusField
            if(5 > (int) Double.parseDouble(anteField.getText()) || (int) Double.parseDouble(anteField.getText()) > 25 || (int) Double.parseDouble(anteField.getText()) ==0 ){
                counter = false;
                System.out.println("Penis is not long enough");
            }
            if (5 > (int) Double.parseDouble(pairPlusField.getText()) || (int) Double.parseDouble(pairPlusField.getText()) > 25) {
                counter2 = false;
                System.out.println("Penis is still not long enough");
            }

            if(0 == (int) Double.parseDouble(pairPlusField.getText())){
                System.out.println("Penis is long enough");
                counter2 = true;
            }

            boolean finalCounter = counter;

            if(finalCounter == true && counter2 == true){

                playButton.setDisable(false);
                foldButton.setDisable(false);
                continueButton.setDisable(true);

                wagers[0] = (int) Double.parseDouble(anteField.getText());
                if((int) Double.parseDouble(pairPlusField.getText()) != 0){
                    wagers[1] = (int) Double.parseDouble(pairPlusField.getText());
                }

                cardView1.setImage(new Image(cards[playerCardIndexes[0]]));
                cardView2.setImage(new Image(cards[playerCardIndexes[1]]));
                cardView3.setImage(new Image(cards[playerCardIndexes[2]]));


                System.out.println("Ante: " + wagers[0]);

                if((int) Double.parseDouble(pairPlusField.getText()) != 0){
                    System.out.println("Pair Plus: " + wagers[1]);
                }

//                System.out.println("Play: " + wagers[2]);

//                for(int i =0 ; i<3; i++){
//                    System.out.println(dealerCardIndexes[i]);
//                    System.out.println("dealercards");
//                }

                combineArraystoOne(wagers,playerCardIndexes,dealerCardIndexes);//AllForOne

                for(int i =0 ; i<9; i++){
                    System.out.println(AllForOne[i]);
                    System.out.println("AllCards");
                }

//                String message = "Deal_Button_Clicked";
//                client.send(message);
//
//                client.sendarray(wagers);
//
//                String temp = "Sending_player_cards";
//                client.send(message);
//                client.sendarray(playerCardIndexes);
                if(continueClicked == 0){
                    String temp2 = "Sending_All_For_One";
                    client.send(temp2);
                    client.sendarray(AllForOne);
                }
//                String temp2 = "Sending_All_For_One";
//                client.send(temp2);
//                client.sendarray(AllForOne);
                else{
                    String temp2 = "Update_One_for_All";
                    client.send(temp2);
                    client.sendarray(AllForOne);
                }



                System.out.println("Wagers: " + Arrays.toString(wagers));

                // Disable the text fields
                anteField.setDisable(true);
                pairPlusField.setDisable(true);
//             playField.setDisable(true);
                //disable the deal button
                dealButton.setDisable(true);
            }
            else {
                System.out.println("hello");
            }

        });




        playButton.setOnAction(e -> {

            //enable continue button
            continueButton.setDisable(false);

            //disable fold and play button
            foldButton.setDisable(true);
            playButton.setDisable(true);


            // Get the indices of the dealer's cards
            int dealerCard1Index = dealerCardIndexes[0];
            int dealerCard2Index = dealerCardIndexes[1];
            int dealerCard3Index = dealerCardIndexes[2];

            // Load the images corresponding to the dealer's cards
            Image dealerCardImage1 = new Image(cards[dealerCard1Index]);
            Image dealerCardImage2 = new Image(cards[dealerCard2Index]);
            Image dealerCardImage3 = new Image(cards[dealerCard3Index]);

            // Set the dealer's cards images to the CardView objects
            cardView1D.setImage(dealerCardImage1);
            cardView2D.setImage(dealerCardImage2);
            cardView3D.setImage(dealerCardImage3);


            String message = "Continue_Button_clicked";
            client.send(message);

        });


        // Handle the Exit menu item
        exitItem.setOnAction(e -> {
            System.exit(0);
        });

        // Handle the Fresh Start menu item
        freshStartItem.setOnAction(e -> {
            // Reset players' winnings and start a new game
            // (You'll need to implement the logic to reset the game state)
            // For example:
            resetGame(primaryStage);
        });

        // Handle the NewLook menu item
        newLookItem.setOnAction(e -> {
            // Change the look of the GUI (colors, fonts, images, etc.)
            // (You'll need to implement the logic to change the appearance)
            // For example:
            // changeAppearance();
        });

        // Set the scene and show the stage
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Play Screen");
        primaryStage.show();
    }

    private void combineArraystoOne(int[] wagers, int[] playerCardIndexes, int[] dealerCardIndexes) {
        for(int i = 0 ; i<3 ; i++){
            AllForOne[i] = wagers[i];
        }
        int counter = 0;
        for(int i = 3 ; i<6 ; i++){
            AllForOne[i] = playerCardIndexes[counter];
            counter++;
        }
        counter =0;
        for(int i= 6; i<9; i++){
            AllForOne[i] = dealerCardIndexes[counter];
            counter++;
        }


    }

// Implement methods to reset the game state and change the appearance
// (You'll need to add the necessary logic based on your specific requirements)


    private void resetGame(Stage primaryStage) {
        Platform.runLater(() -> {
            try {
                new Game().start(primaryStage);
            } catch (Exception p) {
                p.printStackTrace();
            }
        });
    }

//private void changeAppearance() {
//    // Change GUI appearance here
//}



    static void GameLog(String message) {
        Platform.runLater(() -> {
            gameUpdates.appendText(message + "\n");

        });
    }

    static void UpdateWinnings(int winnings) {
        tempWinnings = winnings;
        if(winnings == 0){
            tempWinnings = 0;
        }
        Platform.runLater(() -> {
            totalMoney = totalMoney + tempWinnings;
            if(tempWinnings ==0){
                totalMoney = 0;
            }
//            winningsField.setText("$" + totalMoney);

        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
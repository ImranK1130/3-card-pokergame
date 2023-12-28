import java.io.Serializable;
import java.util.Arrays;

public class PokerInfo implements Serializable {

    static int[] dealer_cards =  new int[3];

    static int[] player_cards =  new int[3];
    private static int[] wagers = new int[3];

    private static int[] AllForOne = new int[9];


    static String[] cards = {"2_of_clubs.png", "2_of_diamonds.png", "2_of_hearts.png", "2_of_spades.png",
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


    static void transferAllforone(int[] array){
        for(int i = 0; i < array.length; i++) {
            AllForOne[i] = array[i];
            System.out.print(AllForOne[i] + " ALL ");
        }

        System.out.println();
        seperate(AllForOne,wagers,player_cards,dealer_cards);
    }

    private static void seperate(int[] allForOne, int[] wagers, int[] playerCards, int[] dealer_cards) {

        for(int i = 0 ; i<3 ; i++){
            wagers[i] = allForOne[i];
        }
        int counter = 0;
        for(int i = 3 ; i<6 ; i++){
            playerCards[counter] = allForOne[i];
            counter++;
        }
        counter = 0;
        for(int i = 6 ; i<9 ; i++){
            dealer_cards[counter] = allForOne[i];
            counter++;
        }
    }

    static void transfer_dealer_cards(int[] array){
        for(int i = 0; i < array.length; i++) {
            dealer_cards[i] = array[i];
            System.out.print(array[i] + " DC ");
        }

        System.out.println();
    }

    static void transfer_player_cards(int[] array){
        for(int i = 0; i < array.length; i++) {
            player_cards[i] = array[i];
            System.out.print(array[i] + " PC ");
        }

        System.out.println();
    }

    static void transfer_wager(int[] array){
        for(int i = 0; i < array.length; i++) {
            wagers[i] = array[i];
            System.out.print(array[i] + " W ");
        }

        System.out.println();
    }

//-------------------------------------------2 kind-----------------------------------------
    static int checkTwoOfAKind(int[] hand) {
        int[] sortedHand = new int[3];
        for (int i = 0; i < 3; i++) {
            sortedHand[i] = hand[i] % 13;
            if (sortedHand[i] == 0) { // Ace should be considered as the highest card in this case
                sortedHand[i] = 13;
            }
        }
        Arrays.sort(sortedHand);

        for (int i = 0; i < sortedHand.length - 1; i++) {
            if (sortedHand[i] == sortedHand[i + 1]) {
                return (sortedHand[i] % 13) + 1;
            }
        }
        return -1;
    }


    static int calculateMoney(int wager, boolean playerWon) {
        int money = playerWon ? wager : -wager;
        return money;
    }

    static boolean dealerHasQueenHighOrBetter(int[] hand) {
        int queenIndex = 40;
//        for (int card : hand) {
//            if (card >= queenIndex) {
//                return true;
//            }
//        }
        int temp =0;
        for(int i =0 ; i<3; i++){
            temp = dealer_cards[i];
            if(temp >=40){
             return true;
            }

        }
        return false;
    }



    public static void Folded(Server.ClientThread clientThread, int count) {
        int anteWager = wagers[0];

        int money = 0;

        String message = "Player #" + count + " has folded. Ante wager is lost and  pair plus wager(if it was made).\n";
        System.out.println(message);
        clientThread.updateClients(message);
        clientThread.updateSpecificClient("Folded_Results",count);

    }



    public static int evaluateHand(int[] hand) {
        int[] sortedHand = hand.clone();
        Arrays.sort(sortedHand);


        if (isStraightFlush(hand)) {
            return 40;
        } else if (isThreeOfAKind(hand)) {
            return 30;
        } else if (isStraight(hand)) {
            return 6;
        } else if (isFlush(hand)) {
            return 3;
        } else if (isPair(hand)) {
            return 1;
        }
        return 0;
    }

    private static boolean isPair(int[] hand) {
        for (int i = 0; i < hand.length; i++) {
            for (int j = i + 1; j < hand.length; j++) {
                if (hand[i] % 13 == hand[j] % 13) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isFlush(int[] hand) {
        if (hand[0] / 13 == hand[1] / 13 && hand[1] / 13 == hand[2] / 13) {
            return true;
        }
        return false;
    }

    private static boolean isStraight(int[] hand) {
        int[] sortedHand = new int[3];
        for (int i = 0; i < 3; i++) {
            sortedHand[i] = hand[i] % 13;
            if (sortedHand[i] == 0) { // Ace should be considered as the lowest card in this case
                sortedHand[i] = 13;
            }
        }
        Arrays.sort(sortedHand);

        if (sortedHand[0] == sortedHand[1] - 1 && sortedHand[1] == sortedHand[2] - 1) {
            return true;
        }
        return false;
    }

//    private static boolean isThreeOfAKind(int[] hand) {
//        if (hand[0] % 13 == hand[1] % 13 && hand[1] % 13 == hand[2] % 13) {
//            return true;
//        }
//
//        return false;
//    }

    private static boolean isThreeOfAKind(int[] hand) {
        if (hand[0] % 13 == hand[1] % 13 && hand[0] % 13 == hand[2] % 13) {
            return true;
        }
        return false;
    }

    private static boolean isStraightFlush(int[] hand) {

        if (hand[0] % 13 == hand[1] % 13 - 1 && hand[1] % 13 == hand[2] % 13 - 1 &&
                hand[0] / 13 == hand[1] / 13 && hand[1] / 13 == hand[2] / 13) {
            return true;
        }

        return false;

    }


    public static void Continue(Server.ClientThread clientThread, int count) {
        // Test with sample hands
//        int[] playerHand = {1, 14, 27};
//        int[] dealerHand = {2, 15, 28};

        int[] playerHand = player_cards;
        int[] dealerHand = dealer_cards;

        for(int i = 0; i < dealer_cards.length; i++) {
//            dealer_cards[i] = array[i];
            System.out.print(dealer_cards[i] + " DC ");
        }


        int playerStatus = -1;
        int dealerStatus = -1;


        int anteWager = wagers[0];
        int playWager = wagers[0];
        int pairPlusWager = wagers[1];


        int moneyWon;

        boolean dealarQueen = false;

        System.out.println(playWager);
        playerStatus = evaluateHand(playerHand);
        int AllMoneyWon = pairPlusWager * playerStatus;

        System.out.println(playerStatus);
        System.out.println(AllMoneyWon);

        dealarQueen = dealerHasQueenHighOrBetter(dealerHand);


        if(dealarQueen == false) {
            String message = "Player #" + count + " the play wager is returned and the ante bet is pushed to the next hand\n";
            System.out.println(message);
            clientThread.updateSpecificClient("Returned",count);


            clientThread.updateSpecificClient("All_Money",count);
            clientThread.updateSpecificClientWinnings(AllMoneyWon,count);

            clientThread.updateClients(message);

        }
        else{

            playerStatus = evaluateHand(playerHand);
            dealerStatus = evaluateHand(dealerHand);




            if(dealerStatus > playerStatus){
                String message = "Player #" + count + " loses both the ante and play wager";
                System.out.println(message);
                clientThread.updateSpecificClient("Lost_Everything",count);
                clientThread.updateClients(message);
            }
            else{
                moneyWon = (anteWager + playWager)*2;
                String message = "Player #" + count + "wins, they get paid out 1 to 1";

                clientThread.updateClients(message);

                clientThread.updateSpecificClient("Won",count);
                clientThread.updateSpecificClientWinnings(moneyWon,count);

                clientThread.updateSpecificClient("All_Money",count);
                clientThread.updateSpecificClientWinnings(AllMoneyWon,count);

                clientThread.updateClients(message);
            }

        }

    }




    public static void main() {
//        Folded();
    }
}

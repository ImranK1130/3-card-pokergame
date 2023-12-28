import java.io.Serializable;
import java.util.Arrays;

public class PokerInfo implements Serializable {

    static int[] dealer_cards = new int[3];

    static int[] player_cards = new int[3];
    private static int[] wagers = new int[3];

    private static int[] AllForOne = new int[6];


    static String[] cards = {"2_of_clubs.png", "2_of_diamonds.png", "2_of_hearts.png", "2_of_spades.png",
            "3_of_clubs.png", "3_of_diamonds.png", "3_of_hearts.png", "3_of_spades.png", "4_of_clubs.png",
            "4_of_diamonds.png", "4_of_hearts.png", "4_of_spades.png", "5_of_clubs.png", "5_of_diamonds.png",
            "5_of_hearts.png", "5_of_spades.png", "6_of_clubs.png", "6_of_diamonds.png", "6_of_hearts.png",
            "6_of_spades.png", "7_of_clubs.png", "7_of_diamonds.png", "7_of_hearts.png", "7_of_spades.png",
            "8_of_clubs.png", "8_of_diamonds.png", "8_of_hearts.png", "8_of_spades.png", "9_of_clubs.png",
            "9_of_diamonds.png", "9_of_hearts.png", "9_of_spades.png", "10_of_clubs.png", "10_of_diamonds.png",
            "10_of_hearts.png", "10_of_spades.png", "jack_of_clubs2.png", "jack_of_diamonds2.png", "jack_of_hearts2.png", "jack_of_spades2.png",
            "king_of_clubs2.png", "king_of_diamonds2.png", "king_of_hearts2.png", "king_of_spades2.png", "queen_of_clubs2.png",
            "queen_of_diamonds2.png", "queen_of_hearts2.png", "queen_of_spades2.png", "ace_of_clubs.png", "ace_of_diamonds.png", "ace_of_hearts.png",
            "ace_of_spades2.png"};


    static void transferAllforone(int[] array){
        for(int i = 0; i < array.length; i++) {
            AllForOne[i] = array[i];
            System.out.print(array[i] + " ALL ");
        }

        System.out.println();
        seperate(AllForOne,wagers,player_cards);
    }

    private static void seperate(int[] allForOne, int[] wagers, int[] playerCards) {

        for(int i = 0 ; i<3 ; i++){
            wagers[i] = allForOne[i];
        }
        int counter = 0;
        for(int i = 3 ; i<6 ; i++){
            playerCards[counter] = allForOne[i];
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



}
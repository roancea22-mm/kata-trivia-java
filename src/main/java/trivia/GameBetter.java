package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

import static trivia.PlayGame.readYesNo;

public class GameBetter implements IGame {

   public static final String QUESTION = " Question ";
   public static final int DEFAULT_SIZE = 7;

   ArrayList<String> players = new ArrayList<>();
   int[] places = new int[DEFAULT_SIZE];
   int[] purses = new int[DEFAULT_SIZE];
   boolean[] inPenaltyBox = new boolean[DEFAULT_SIZE];

   LinkedList<String> popQuestions = new LinkedList<>();
   LinkedList<String> scienceQuestions = new LinkedList<>();
   LinkedList<String> sportsQuestions = new LinkedList<>();
   LinkedList<String> rockQuestions = new LinkedList<>();
   LinkedList<String> geographyQuestions = new LinkedList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop" + QUESTION + i);
         scienceQuestions.addLast("Science" + QUESTION + i);
         sportsQuestions.addLast("Sports" + QUESTION + i);
         rockQuestions.addLast("Rock" + QUESTION + i);
         geographyQuestions.addLast("Geography" + QUESTION + i);
      }
   }

   public boolean add(String playerName) {
      if (players.contains(playerName)) {
         System.out.println("Player name already added");
         return false;
      } else {
         players.add(playerName);
         int noOfPlayers = players.size();
         places[noOfPlayers] = 0;
         purses[noOfPlayers] = 0;
         inPenaltyBox[noOfPlayers] = false;

         System.out.println(playerName + " was added");
         System.out.println("They are player number " + players.size());
         return true;
      }
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            moveToLocation(roll);
         } else {

            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         moveToLocation(roll);
      }

   }

   private void moveToLocation(int roll) {
      places[currentPlayer] += roll;
      if (places[currentPlayer] >= 12) places[currentPlayer] -= 12;

      System.out.println(players.get(currentPlayer)
              + "'s new location is "
              + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   public void askQuestion() {
      switch (currentCategory()) {
         case "Pop":
            System.out.println(popQuestions.removeFirst());
            break;
         case "Science":
            System.out.println(scienceQuestions.removeFirst());
            break;
         case "Sports":
            System.out.println(sportsQuestions.removeFirst());
            break;
         case "Rock":
            System.out.println(rockQuestions.removeFirst());
            break;
         case "Geography":
            System.out.println(geographyQuestions.removeFirst());
            break;
         default:
            break;
      }
   }

   private String currentCategory() {
      switch (places[currentPlayer]) {
         case 0:
         case 1:
            return "Geography";
         case 8:
         case 4:
            return "Pop";
         case 5:
         case 9:
            return "Science";
         case 2:
         case 6:
         case 10:
            return "Sports";
         default: return "Rock";
      }
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            inPenaltyBox[currentPlayer] = false ;
            return getWinner();
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }
      } else {
         return getWinner();
      }
   }

   private boolean getWinner() {
      System.out.println("Answer was correct!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer)
              + " now has "
              + purses[currentPlayer]
              + " Gold Coins.");

      boolean winner = purses[currentPlayer] != 6;
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;

      return winner;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }
}

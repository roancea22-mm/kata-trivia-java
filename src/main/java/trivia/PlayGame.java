package trivia;

import java.util.Random;
import java.util.Scanner;

// DON'T TOUCH THIS CLASS. ONLY RUN IT TO MANUALLY PLAY THE GAME YOURSELF TO UNDERSTAND THE PROBLEM
public class PlayGame {

   private static final Scanner scanner = new Scanner(System.in);

   public static void main(String[] args) {

      System.out.println("*** Welcome to Trivia Game ***\n");
      System.out.println("Enter number of players: 2-6");
      int playerCount = Integer.parseInt(scanner.nextLine());
      if (playerCount < 2 || playerCount > 6) throw new IllegalArgumentException("No player 2..6");
      System.out.println("Reading names for " + playerCount + " players:");

      IGame aGame = new Game();
      boolean result;
      for (int i = 1; i <= playerCount; i++) {
         do {
            System.out.print("Player " + i + " name: ");
            String playerName = scanner.nextLine();
            result = aGame.add(playerName);
         } while(!result);
      }

      System.out.println("\n\n--Starting game--");


      boolean notAWinner;
      do {
         int roll = readRoll();
         aGame.roll(roll);

         System.out.print(">> Was the answer correct? [y/n] ");
         boolean correct = readYesNo();
         //TODO
         //answer == N -> ask question -> Was the answer correct? [y/n] -> answer == N -> penalty
         //answer == N -> ask question -> Was the answer correct? [y/n] -> answer == Y
         //answer == Y

         if (correct) {
            notAWinner = aGame.wasCorrectlyAnswered();
         } else {
            aGame.askQuestion();
            System.out.print(">> Was the answer correct? [y/n] ");
            correct = readYesNo();
            if (correct) {
               notAWinner = aGame.wasCorrectlyAnswered();
            } else {
               notAWinner = aGame.wrongAnswer();
            }
         }

      } while (notAWinner);
      System.out.println(">> Game won!");
   }

   static boolean readYesNo() {
      String yn = scanner.nextLine().trim().toUpperCase();
      if (!yn.matches("[YN]")) {
         System.err.println("y or n please");
         return readYesNo();
      }
      return yn.equalsIgnoreCase("Y");
   }

   private static int readRoll() {
      System.out.print(">> Throw a die and input roll, or [ENTER] to generate a random roll: ");
      String rollStr = scanner.nextLine().trim();
      if (rollStr.isEmpty()) {
         int roll = new Random().nextInt(6) + 1;
         System.out.println(">> Random roll: " + roll);
         return roll;
      }
      if (!rollStr.matches("\\d+")) {
         System.err.println("Not a number: '" + rollStr + "'");
         return readRoll();
      }
      int roll = Integer.parseInt(rollStr);
      if (roll < 1 || roll > 6) {
         System.err.println("Invalid roll");
         return readRoll();
      }
      return roll;
   }
}

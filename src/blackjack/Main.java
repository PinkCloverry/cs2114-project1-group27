package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This program allows the player to play a game of Blackjack against the
 * dealer. The player can choose to use a standard deck or import their own
 * deck of cards. The program controls the turns, calculates the values of the
 * hands, and determines the winner based on who gets closest to 21 without
 * going over
 *
 * @author Kim Delgadillo
 * @version Sep 21, 2026
 */
public class Main
{
    private static final int MINIMUM_DECK_SIZE = 4;
    private static final int MAXIMUM_DECK_SIZE = 52;


    /**
     * Starts the Blackjack program by creating the scanner and dealer objects,
     * displaying the game instructions, and asking the player which deck they
     * want to use. If the player imports a valid custom deck, it replaces the
     * standard deck before the game begins
     *
     * @param args Command lline arguments
     */
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        Dealer dealer = new Dealer();

        displayWelcome();

        boolean wantsImport = promptForImport(keyboard);

        if (wantsImport)
        {
            Card[] customDeck = promptForDeck(keyboard);

            if (customDeck != null)
            {
                dealer.setDeck(customDeck);
                dealer.shuffleDeck();

                System.out.println(
                    "Your custom deck was imported successfully!");
            }
            else
            {
                System.out.println(
                    "The standard deck will be used instead.");
            }
        }
        else
        {
            System.out.println("The standard deck will be used.");
        }

        playGame(dealer);

        keyboard.close();
    }


    /**
     * Introduces the game by printing a welcome message, explaining that the
     * goal is to get close to 21 without going over, and showing the choices
     * the player can make during their turn
     */
    public static void displayWelcome()
    {
        System.out.println("Welcome to Blackjack!");

        System.out.println(
            "Your goal is to get as close to 21 as possible "
                + "without going over.");

        System.out.println(
            "During your turn, you can choose either to "
                + "\"Hit\" or \"Stay\".");
    }


    /**
     * Asks the player if they want to import their own deck and stores their
     * response as lowercase text. The method uses a loop to continue asking
     * until the player enters yes or no and then returns the matching boolean
     * value
     *
     * @param keyboard Scanner used to read the player's response
     * @return true if the player wants to import a deck or false if they want
     *         to use the standard deck
     */
    public static boolean promptForImport(Scanner keyboard)
    {
        while (true)
        {
            System.out.print(
                "Would you like to import your own deck? (yes/no): ");

            String response = keyboard.nextLine().trim().toLowerCase();

            if (response.equals("yes"))
            {
                return true;
            }
            else if (response.equals("no"))
            {
                return false;
            }
            else
            {
                System.out.println(
                    "Invalid response. Please enter \"yes\" or \"no\".");
            }
        }
    }


    /**
     * Asks the player to enter the name of their custom deck file and sends the
     * file name to the importDeck method. If the file cannot be found or
     * contains invalid information, the exception is caught and null is
     * returned so the standard deck can be used instead
     *
     * @param keyboard Scanner used to read the name of the file
     * @return the imported deck or null if the file cannot be imported
     */
    public static Card[] promptForDeck(Scanner keyboard)
    {
        System.out.print("Enter the name of your deck file: ");
        String fileName = keyboard.nextLine().trim();

        try
        {
            return importDeck(fileName);
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("The file could not be found.");
            return null;
        }
        catch (IllegalArgumentException exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }
    }


    /**
     * Reads the custom deck file one line at a time and changes each line from
     * a String into an integer card value. Each value is checked before a new
     * Card is added to a temporary array. After all the lines are read, the
     * cards are copied into a new array with the exact size needed for the
     * imported deck
     *
     * @param fileName Name of the file containing the custom deck
     * @return an array containing all the valid cards imported from the file
     * @throws FileNotFoundException If a file with the provided name cannot be found
     * @throws IllegalArgumentException If the file contains an invalid card or an invalid deck size
     */
    public static Card[] importDeck(String fileName)
        throws FileNotFoundException
    {
        Scanner fileReader = new Scanner(new File(fileName));

        Card[] temporaryDeck = new Card[MAXIMUM_DECK_SIZE];
        int numberOfCards = 0;

        while (fileReader.hasNextLine())
        {
            String line = fileReader.nextLine().trim();

            if (!line.isEmpty())
            {
                if (numberOfCards == MAXIMUM_DECK_SIZE)
                {
                    fileReader.close();

                    throw new IllegalArgumentException(
                        "The deck cannot contain more than "
                            + MAXIMUM_DECK_SIZE + " cards.");
                }

                try
                {
                    int value = Integer.parseInt(line);

                    if (value < 2 || value > 11)
                    {
                        fileReader.close();

                        throw new IllegalArgumentException(
                            "Every card must have a value from 2 to 11.");
                    }

                    boolean isAce = value == 11;

                    temporaryDeck[numberOfCards] =
                        new Card(value, isAce);

                    numberOfCards++;
                }
                catch (NumberFormatException exception)
                {
                    fileReader.close();

                    throw new IllegalArgumentException(
                        "Every line must contain a number.");
                }
            }
        }

        fileReader.close();

        if (numberOfCards < MINIMUM_DECK_SIZE)
        {
            throw new IllegalArgumentException(
                "The deck must contain at least "
                    + MINIMUM_DECK_SIZE + " cards.");
        }

        Card[] customDeck = new Card[numberOfCards];

        for (int i = 0; i < numberOfCards; i++)
        {
            customDeck[i] = temporaryDeck[i];
        }

        return customDeck;
    }


    /**
     * Controls the order of the Blackjack game by repeatedly calling
     * playerTurn while the player chooses to hit. Once the player stays, the
     * method repeatedly calls dealerTurn until the dealer also chooses to stay
     *
     * @param dealer Dealer used to control the cards and turns during the game
     */
    public static void playGame(Dealer dealer)
    {
        System.out.println();
        System.out.println("The game is starting!");
        
        dealer.initialDeal();
        boolean playerHit = true;

        while (playerHit)
        {
            playerHit = dealer.playerTurn();
        }

        boolean dealerHit = true;

        while (dealerHit)
        {
            dealerHit = dealer.dealerTurn();
        }
        boolean playerWin = dealer.determineWinner();
        String endMessage;
        
        System.out.print("The round is over! ");
        if(playerWin) {
            endMessage = "Player wins!";
        }
        else {
            endMessage = "Dealer wins!";
        }
        System.out.println(endMessage);
    }
}
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Mastermind {
    public static void Mastermind() throws IOException {
        int rounds = 0;
        int wins = 0;
        int winsInARow = 0;
        String playerNumbers = "";

        File file1 = new File("./files/highscore.txt");                                     //Statystyki
        Scanner highscoreScanner1 = new Scanner(file1);
        String currentHighScore = highscoreScanner1.nextLine();

        File file2 = new File("./files/mostgameswon.txt");
        Scanner mostGamesWonScanner1 = new Scanner(file2);
        String currentMostGamesWon = mostGamesWonScanner1.nextLine();

        File file3 = new File("./files/totalgameswon.txt");
        Scanner totalGamesWonScanner1 = new Scanner(file3);
        String currentTotalGamesWon = totalGamesWonScanner1.nextLine();
        int intCurrentTotalGamesWon = Integer.parseInt(currentTotalGamesWon);
        float floatCurrentTotalGamesWon = Float.parseFloat(currentTotalGamesWon);

        File file4 = new File("./files/totalgames.txt");
        Scanner totalGamesScanner1 = new Scanner(file4);
        String currentTotalGames = totalGamesScanner1.nextLine();
        int intCurrentTotalGames = Integer.parseInt(currentTotalGames);
        float floatCurrentTotalGames = Float.parseFloat(currentTotalGames);


        int totalGamesLost = intCurrentTotalGames - intCurrentTotalGamesWon;
        float percentOfGamesWon = floatCurrentTotalGamesWon / floatCurrentTotalGames * 100;

        do {
            Random random = new Random();                                                            // Generujemy kod
            int[] arrayComputerNumbers = new int[4];
            for (int i = 0; i < arrayComputerNumbers.length; i++) {
                arrayComputerNumbers[i] = random.nextInt(1, 7);
            }


            String computerNumbers = "";                                                            // Konwertujemy kod na String
            for (int i = 0; i < arrayComputerNumbers.length; i++) {
                computerNumbers += arrayComputerNumbers[i];
            }


            System.out.println(computerNumbers);                                                   // test (patrzymy jaki kod się wylosował)


            if (rounds == 0) {
                System.out.println("Witamy w grze Mastermind!\nZasady - wpisz /help\nWyjdź z gry - wpisz /exit\nStatystyki - wpisz /stats");
                System.out.println("\nAby rozpocząć, podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
            } else System.out.println("\nPodaj kod:");


            int moves = 0;                                                                        // Ograniczamy ilość prób do 10
            label:
            while (moves < 10) {
                Scanner scanner = new Scanner(System.in);


                try {
                    playerNumbers = scanner.next();
                    switch (playerNumbers) {
                        case "/help":                                                                    // Menu startowe
                            System.out.println("\nGra losuje " + arrayComputerNumbers.length + "-cyfrowy kod składający się z liczb od 1 do 6, które mogą się powtarzać.\n" +
                                    "Gracz ma za zadanie odgadnąć kod, wpisując go w wyznaczonym polu. W każdej rundzie ma na to 10 szans.\n" +
                                    "Po każdej próbie gracz otrzymuje " + arrayComputerNumbers.length + "-cyfrową punktację:\n" +
                                    "Każda 1 oznacza, że jedna z cyfr jest umieszczona na właściwym miejscu.\n" +
                                    "Z kolei każde 0 oznacza, że jedna z cyfr znajduje się w kodzie, lecz na niewłaściwym miejscu.\n" +
                                    "Jeżeli dana cyfra nie znajduje się w kodzie, nie wyświetla się nic.\nAby zakończyć, wpisz /exit.\n");
                            break;
                        case "/stats":
                            System.out.println("Łączna ilość rozegranych rund: " + currentTotalGames + ".");
                            System.out.println("Ilość odgadniętych kodów: " + currentTotalGamesWon + ".");
                            System.out.println("Ilość nieodgadniętych kodów: " + totalGamesLost + ".");
                            System.out.println("Procent odgadniętych kodów: " + percentOfGamesWon + "%");
                            System.out.println("Rekord w ilości odgadniętych kodów w trakcie jednej gry: " + currentMostGamesWon + ".");
                            System.out.println("Rekord w ilości odgadniętych kodów z rzędu: " + currentHighScore + ".");


                            break;
                        case "/exit":
                            break label;
                    }
                                                                                                // Wykluczamy możliwość podania nieprawidłowego kodu
                    if (playerNumbers.length() != arrayComputerNumbers.length || playerNumbers.contains("0") || playerNumbers.contains("7") || playerNumbers.contains("8") || playerNumbers.contains("9")) {
                        System.out.println("Podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
                    continue;
                }


                String[] stringArrayPlayerNumbers = playerNumbers.split("");               // Z liczb gracza tworzymy tablicę
                int[] intArrayPlayerNumbers = new int[stringArrayPlayerNumbers.length];
                for (int i = 0; i < stringArrayPlayerNumbers.length; i++) {
                    intArrayPlayerNumbers[i] = Integer.parseInt(stringArrayPlayerNumbers[i]);
                }


                String points = "";                                                               // 1 w  punktacji
                for (int i = 0; i < arrayComputerNumbers.length; i++) {
                    if (playerNumbers.charAt(i) == computerNumbers.charAt(i)) {
                        points += "1";
                    }
                }


                for (int i = 0; i < intArrayPlayerNumbers.length; i++) {                          // 0 w punktacji
                    for (int j = 0; j < intArrayPlayerNumbers.length; j++) {
                        if (intArrayPlayerNumbers[i] == arrayComputerNumbers[j]) {
                            points += "0";
                            break;
                        }
                    }
                }
                moves++;



                int intTotalGames = Integer.parseInt(currentTotalGames) + rounds;                  // Ilość gier łącznie
                String totalGames = String.valueOf(intTotalGames);

                FileWriter writer4 = new FileWriter("./files/totalgames.txt");
                writer4.write(totalGames);
                writer4.close();

                if (playerNumbers.equals(computerNumbers)) {                                         // Wygrana
                    wins++;
                    winsInARow++;
                    rounds++;
                    System.out.println("Gratulacje! Odgadłeś kod [" + computerNumbers + "] za " + moves + " razem! Wygrane rundy: " + wins + "/" + rounds + ". Wygrane z rzędu: " + winsInARow + ".");

                    // Ilość wygranych łącznie
                    int intTotalGamesWon = Integer.parseInt(currentTotalGamesWon) + wins;
                    String totalGamesWon = String.valueOf(intTotalGamesWon);

                    FileWriter writer3 = new FileWriter("./files/totalgameswon.txt");
                    writer3.write(totalGamesWon);
                    writer3.close();


                                                                                                // Rekordy
                    String mostGamesWon = Integer.toString(wins);
                    if (Integer.parseInt(mostGamesWon) > Integer.parseInt(currentMostGamesWon)) {
                        FileWriter writer = new FileWriter("./files/mostgameswon.txt");
                        writer.write(mostGamesWon);
                        writer.close();
                        System.out.println("POBI�E� SW�J NOWY REKORD W ILOŚCI ODGADNI�TYCH KOD�W W TRAKCIE JEDNEJ GRY!");
                    }

                    String highScore = Integer.toString(winsInARow);
                    if (Integer.parseInt(highScore) > Integer.parseInt(currentHighScore)) {
                        FileWriter writer = new FileWriter("./files/highscore.txt");
                        writer.write(highScore);
                        writer.close();
                        System.out.println("POBI�E� SW�J NOWY REKORD W ILOŚCI ODGADNI�TYCH KOD�W Z RZ�DU!");
                    }
                    System.out.println("Aby zakończyć, wpisz /exit");
                    break;
                }


                if (moves == 10) {                                                                  // Przegrana
                    rounds++;
                    System.out.println("Wyczerpałeś wszystkie próby! Kod to [" + computerNumbers + "]. Wygrane rundy: " + wins + "/" + rounds + ".");
                    if (winsInARow > 1) {
                        System.out.println("Przerwałeś swją serię, która wynosiła " + winsInARow + ".");
                    }
                    winsInARow = 0;
                    break;
                }

                System.out.println("(" + moves + "/10) [" + playerNumbers + "] Punktacja: " + points);

            }


        } while (!playerNumbers.equals("/exit"));
    }

    public static void main(String[] args) throws IOException {
        Mastermind();
    }
}
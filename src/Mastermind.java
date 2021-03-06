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


            System.out.println(computerNumbers);                                                   // test (patrzymy jaki kod si?? wylosowa??)


            if (rounds == 0) {
                System.out.println("Witamy w grze Mastermind!\nZasady - wpisz /help\nWyjd?? z gry - wpisz /exit\nStatystyki - wpisz /stats");
                System.out.println("\nAby rozpocz????, podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
            } else System.out.println("\nPodaj kod:");


            int moves = 0;                                                                        // Ograniczamy ilo???? pr??b do 10
            label:
            while (moves < 10) {
                Scanner scanner = new Scanner(System.in);


                try {
                    playerNumbers = scanner.next();
                    switch (playerNumbers) {
                        case "/help":                                                                    // Menu startowe
                            System.out.println("\nGra losuje " + arrayComputerNumbers.length + "-cyfrowy kod sk??adaj??cy si?? z liczb od 1 do 6, kt??re mog?? si?? powtarza??.\n" +
                                    "Gracz ma za zadanie odgadn???? kod, wpisuj??c go w wyznaczonym polu. W ka??dej rundzie ma na to 10 szans.\n" +
                                    "Po ka??dej pr??bie gracz otrzymuje " + arrayComputerNumbers.length + "-cyfrow?? punktacj??:\n" +
                                    "Ka??da 1 oznacza, ??e jedna z cyfr jest umieszczona na w??a??ciwym miejscu.\n" +
                                    "Z kolei ka??de 0 oznacza, ??e jedna z cyfr znajduje si?? w kodzie, lecz na niew??a??ciwym miejscu.\n" +
                                    "Je??eli dana cyfra nie znajduje si?? w kodzie, nie wy??wietla si?? nic.\nAby zako??czy??, wpisz /exit.\n");
                            break;
                        case "/stats":
                            System.out.println("????czna ilo???? rozegranych rund: " + currentTotalGames + ".");
                            System.out.println("Ilo???? odgadni??tych kod??w: " + currentTotalGamesWon + ".");
                            System.out.println("Ilo???? nieodgadni??tych kod??w: " + totalGamesLost + ".");
                            System.out.println("Procent odgadni??tych kod??w: " + percentOfGamesWon + "%");
                            System.out.println("Rekord w ilo??ci odgadni??tych kod??w w trakcie jednej gry: " + currentMostGamesWon + ".");
                            System.out.println("Rekord w ilo??ci odgadni??tych kod??w z rz??du: " + currentHighScore + ".");


                            break;
                        case "/exit":
                            break label;
                    }
                                                                                                // Wykluczamy mo??liwo???? podania nieprawid??owego kodu
                    if (playerNumbers.length() != arrayComputerNumbers.length || playerNumbers.contains("0") || playerNumbers.contains("7") || playerNumbers.contains("8") || playerNumbers.contains("9")) {
                        System.out.println("Podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Podaj " + arrayComputerNumbers.length + "-cyfrowy kod:");
                    continue;
                }


                String[] stringArrayPlayerNumbers = playerNumbers.split("");               // Z liczb gracza tworzymy tablic??
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



                int intTotalGames = Integer.parseInt(currentTotalGames) + rounds;                  // Ilo???? gier ????cznie
                String totalGames = String.valueOf(intTotalGames);

                FileWriter writer4 = new FileWriter("./files/totalgames.txt");
                writer4.write(totalGames);
                writer4.close();

                if (playerNumbers.equals(computerNumbers)) {                                         // Wygrana
                    wins++;
                    winsInARow++;
                    rounds++;
                    System.out.println("Gratulacje! Odgad??e?? kod [" + computerNumbers + "] za " + moves + " razem! Wygrane rundy: " + wins + "/" + rounds + ". Wygrane z rz??du: " + winsInARow + ".");

                    
                    int intTotalGamesWon = Integer.parseInt(currentTotalGamesWon) + wins;             // Ilo???? wygranych ????cznie
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
                        System.out.println("POBI??E?? SW??J NOWY REKORD W ILO??CI ODGADNI??TYCH KOD??W W TRAKCIE JEDNEJ GRY!");
                    }

                    String highScore = Integer.toString(winsInARow);
                    if (Integer.parseInt(highScore) > Integer.parseInt(currentHighScore)) {
                        FileWriter writer = new FileWriter("./files/highscore.txt");
                        writer.write(highScore);
                        writer.close();
                        System.out.println("POBI??E?? SW??J NOWY REKORD W ILO??CI ODGADNI??TYCH KOD??W Z RZEDU!");
                    }
                    System.out.println("Aby zako??czy??, wpisz /exit");
                    break;
                }


                if (moves == 10) {                                                                  // Przegrana
                    rounds++;
                    System.out.println("Wyczerpa??e?? wszystkie pr??by! Kod to [" + computerNumbers + "]. Wygrane rundy: " + wins + "/" + rounds + ".");
                    if (winsInARow > 1) {
                        System.out.println("Przerwa??e?? swj?? seri??, kt??ra wynosi??a " + winsInARow + ".");
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

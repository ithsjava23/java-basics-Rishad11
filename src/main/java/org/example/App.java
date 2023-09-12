package org.example;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] prices = new int[24];
        char choice;

        do {
            printMenu();
            choice = scanner.next().charAt(0);

            switch (choice) {
                case '1':
                    inputPrices(prices, scanner);
                    break;
                case '2':
                    minMaxAverage(prices);
                    break;
                case '3':
                    sortPrices(prices);
                    break;
                case '4':
                    findBestChargingTime(prices);
                    break;
                case '5':
                    visualizePrices(prices);
                    break;
                case 'e':
                case 'E':
                    System.out.println("Avslutar programmet.");
                    break;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
                    break;
            }
        } while (choice != 'e' && choice != 'E');

        scanner.close();
    }

    public static void printMenu() {
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("5. Visualisering");
        System.out.println("e. Avsluta");
        System.out.print("Välj ett alternativ: ");
    }

    public static void inputPrices(int[] prices, Scanner scanner) {
        for (int hour = 0; hour < 24; hour++) {
            System.out.print("Ange elpris för timme " + hour + ": ");
            prices[hour] = scanner.nextInt();
        }
    }

    public static void minMaxAverage(int[] prices) {
        int minPrice = Arrays.stream(prices).min().getAsInt();
        int maxPrice = Arrays.stream(prices).max().getAsInt();
        int minHour = -1;
        int maxHour = -1;
        int sum = 0;

        for (int hour = 0; hour < 24; hour++) {
            if (prices[hour] == minPrice) {
                minHour = hour;
            }
            if (prices[hour] == maxPrice) {
                maxHour = hour;
            }
            sum += prices[hour];
        }

        double average = (double) sum / 24;

        System.out.println("Lägsta pris: " + minPrice + " öre (kl " + minHour + ")");
        System.out.println("Högsta pris: " + maxPrice + " öre (kl " + maxHour + ")");
        System.out.println("Dygnets medelpris: " + average + " öre");
    }

    public static void sortPrices(int[] prices) {
        int[] sortedPrices = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sortedPrices);

        System.out.println("Sorterade priser (dyrast till billigast):");
        for (int hour = 0; hour < 24; hour++) {
            System.out.printf("%02d-%02d %d öre%n", hour, hour + 1, sortedPrices[hour]);
        }
    }

    public static void findBestChargingTime(int[] prices) {
        int lowestTotalPrice = Integer.MAX_VALUE;
        int bestStartHour = -1;
        double bestAveragePrice = 0;

        for (int startHour = 0; startHour < 21; startHour++) {
            int totalPrice = 0;

            for (int i = startHour; i < startHour + 4; i++) {
                totalPrice += prices[i];
            }

            double averagePrice = (double) totalPrice / 4;

            if (totalPrice < lowestTotalPrice) {
                lowestTotalPrice = totalPrice;
                bestStartHour = startHour;
                bestAveragePrice = averagePrice;
            }
        }

        System.out.println("Bästa laddningstid (4h) börjar kl " + bestStartHour + " och har ett medelpris på " + bestAveragePrice + " öre.");
    }
    public static void visualizePrices(int[] prices) {
        int maxPrice = Arrays.stream(prices).max().getAsInt();
        int scale = maxPrice / 75;

        System.out.println("Visualisering av elpriser (skala 1:" + scale + "):");

        for (int hour = 0; hour < 24; hour++) {
            int price = prices[hour];
            int barLength = price / scale;

            System.out.printf("%02d-%02d ", hour, hour + 1);

            for (int i = 0; i < barLength; i++) {
                System.out.print("#");
            }

            System.out.println(" " + price + " öre");
        }
    }
}

    }
}

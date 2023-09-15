package org.example;
import java.util.Arrays;
import java.util.Scanner;


import java.util.*;

public class App {
    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);

        Scanner scanner = new Scanner(System.in);
        int[] prices = new int[24];
        boolean exit = false;

        while (!exit) {
            System.out.print("""
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """);
            System.out.print("\nVälj ett alternativ: ");
            String choice = scanner.next();

            switch (choice) {
                case "1" -> inputPrices(prices, scanner);
                case "2" -> minMaxAverage(prices);
                case "3" -> sortPrices(prices);
                case "4" -> findBestChargingTime(prices);
                case "e", "E" -> exit = true;
                default -> System.out.println("Ogiltigt val. Försök igen.");
            }
        }

        System.out.println("Programmet avslutas...");
    }

    public static void inputPrices(int[] prices, Scanner scanner) {
        for (int hour = 0; hour < 24; hour++) {
            while (true) {
                System.out.printf("%02d-%02d Ange elpris i hela öre: ", hour, hour + 1);
                try {
                    prices[hour] = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Felaktig inmatning. Ange ett heltal.");
                    scanner.nextLine();
                }
            }
        }
    }

    public static void minMaxAverage(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;
        int minHour = -1;
        int maxHour = -1;
        int sum = 0;

        for (int hour = 0; hour < 24; hour++) {
            int price = prices[hour];
            sum += price;

            if (price < minPrice) {
                minPrice = price;
                minHour = hour;
            }

            if (price > maxPrice) {
                maxPrice = price;
                maxHour = hour;
            }
        }

        float average = (float) sum / 24;

        System.out.printf("Lägsta pris: %02d-%02d, %d öre/kWh\n", minHour, minHour + 1, minPrice);
        System.out.printf("Högsta pris: %02d-%02d, %d öre/kWh\n", maxHour, maxHour + 1, maxPrice);
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);
    }

    public static void sortPrices(int[] prices) {
        Integer[] sortedHours = new Integer[prices.length];
        for (int i = 0; i < prices.length; i++) {
            sortedHours[i] = i;
        }

        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = 0; j < prices.length - i - 1; j++) {
                int hour1 = sortedHours[j];
                int hour2 = sortedHours[j + 1];
                if (prices[hour1] < prices[hour2]) {
                    int temp = sortedHours[j];
                    sortedHours[j] = sortedHours[j + 1];
                    sortedHours[j + 1] = temp;
                }
            }
        }

        for (int i : sortedHours) {
            System.out.printf("%02d-%02d %d öre\n", i, i + 1, prices[i]);
        }
    }

    public static void findBestChargingTime(int[] prices) {
        int lowestTotalPrice = Integer.MAX_VALUE;
        int bestStartHour = -1;

        for (int startHour = 0; startHour < 21; startHour++) {
            int totalPrice = 0;
            for (int i = startHour; i < startHour + 4; i++) {
                totalPrice += prices[i];
            }

            if (totalPrice < lowestTotalPrice) {
                lowestTotalPrice = totalPrice;
                bestStartHour = startHour;
            }
        }

        float averagePrice = (float) lowestTotalPrice / 4;

        System.out.printf("Påbörja laddning klockan %d\n", bestStartHour);
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", averagePrice);
    }
}

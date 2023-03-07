package cinema;

import java.util.Scanner;

public class Cinema {

    public static int calculateIncome(int rows, int seats) {
        int totalNumberOfSeats = rows * seats;
        int totalIncome = 0;

        if (totalNumberOfSeats <= 60) {
            totalIncome = 10 * totalNumberOfSeats;
        } else {
            if (rows % 2 != 0) {
                int firstHalf = rows / 2;
                int secondHalf = rows - firstHalf;
                int firstHalfIncome = (firstHalf * seats) * 10;
                int secondHalfIncome = (secondHalf * seats) * 8;
                totalIncome = firstHalfIncome + secondHalfIncome;
            } else {
                totalIncome = ((totalNumberOfSeats / 2) * 10) + ((totalNumberOfSeats / 2) * 8);
            }
        }
        //System.out.println("Total income:");
        //System.out.println("$" + totalIncome);
        return  totalIncome;
    }

    public static String[][] createCinema(int totalRows, int totalSeats) {
        String[][] plan = new String[totalRows][totalSeats];

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalSeats; j++) {
                plan[i][j] = "S";
            }
        }

        return plan;
    }

    public static void printCinemaPlan(int totalRows, int totalSeats, String[][] plan) {
        System.out.println("Cinema:");

        for (int row = 0; row <= totalRows; row++) {
            if (row == 0) {
                System.out.print(" ");
                System.out.print(" ");
            } else {
                System.out.print(row);
            }
            for (int seat = 1; seat <= totalSeats; seat++) {
                if (row == 0) {
                    System.out.print(seat);
                    System.out.print(" ");
                } else {
                    System.out.print(" ");
                    //System.out.print("S");
                    System.out.print(plan[row -1][seat-1]);
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    public static int calculateSeatPrice(int row, int rows, int seats) {
        if ((seats * rows) < 60) {
            System.out.println("Ticket price: $" + 10);
            return 10;
        } else {
            int firstHalf = rows / 2;
            int secondHalf = firstHalf + 1;
            if (row < secondHalf) {
                System.out.println("Ticket price: $" + 10);
                return 10;
            } else {
                System.out.println("Ticket price: $" + 8);
                return 8;
            }
        }
    }

    public static String reserveSeat(String[][] cinemaPlan, int seatNumber, int rowNumber) {
        String checker = "";
        for (int i = 0; i < cinemaPlan.length; i++) {
            if(rowNumber > cinemaPlan.length) {
                checker = "wi";
            }
            for (int j = 0; j < cinemaPlan[i].length; j++) {
                if(seatNumber > cinemaPlan[i].length) {
                    checker = "wi";
                }
                if (rowNumber - 1 == i && seatNumber - 1 == j) {
                    if (cinemaPlan[i][j].equals("B")) {
                        checker = "false";
                    } else {
                        cinemaPlan[i][j] = "B";
                        checker = "true";
                    }
                }
            }
        }
        return checker;
    }

    public static void buyTicket(String[][] cinema, int rows, int seats, Statistics statistics) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowNumber = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNumber = scanner.nextInt();
        String checker = reserveSeat(cinema,seatNumber,rowNumber);
        if(checker.equals("true")) {
            int seatPrice = calculateSeatPrice(rowNumber,rows,seats);
            double percent = (1.0 / (rows * seats)) * 100;
            statistics.purchaseTicket(seatPrice, percent);
        } else if (checker.equals("wi")) {
            System.out.println("Wrong input!");
            buyTicket(cinema,rows,seats,statistics);
        } else {
            System.out.println("That ticket has already been purchased!");
            buyTicket(cinema,rows,seats,statistics);
        }
    }

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        String[][] cinema = createCinema(rows,seats);
        Statistics statistics = new Statistics(0,0.0,0,calculateIncome(rows,seats));
        String menu = """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;
        int choice;
        do{
            System.out.println(menu);
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> printCinemaPlan(rows, seats, cinema);
                case 2 -> buyTicket(cinema,rows,seats,statistics);
                case 3 -> {
                    System.out.println(statistics.toString());
                    System.out.println();
                }
            }
        }while (choice != 0);



    }

}

class Statistics {
    private int purchasedTickets;
    private double percent;
    private int currentIncome;
    private int totalIncome;

    public Statistics(int purchasedTickets, double percent, int currentIncome, int totalIncome ) {
        this.purchasedTickets = purchasedTickets;
        this.percent = percent;
        this.currentIncome = currentIncome;
        this.totalIncome = totalIncome;
    }

    public void purchaseTicket(int ticketPrice, double percent) {
        this.purchasedTickets += 1;
        this.currentIncome += ticketPrice;
        this.percent += percent;
    }

    public String toString() {
        return String.format("Number of purchased tickets: %d\n" +
                "Percentage: %.2f%%\n" +
                "Current income: $%d\n" +
                "Total income: $%d",this.purchasedTickets, this.percent, this.currentIncome, this.totalIncome);
    }
}
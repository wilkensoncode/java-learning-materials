package cinema;

import java.util.Arrays;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Inputs input = new Inputs();

        int rows = input.rows();
        int seats = input.seats();

        Cinema cinema = new Cinema(rows, seats);

        while (true) {
            int choice = input.menu();

            switch (choice) {
                case 1:
                    cinema.printSeats();
                    break;
                case 2:
                    cinema.buyTicket();
                    break;
                case 3:
                    cinema.statistics();
                    break;
                case 0:
                    return;
                default:
                    cinema.message("Wrong input!");
            }
        }
    }
}


class Cinema {

    int rows;
    int seatsInThatRow;

    int totalSeat;
    int totalIncome;

    int ticketSold;
    double percentage;

    int frontSeat = 10; // price for front seat (half)
    int backSeat = 8;  // price for back seat (remaining)

    int currentIncome;
    char[][] matrix;


    /**
     * initialize instances and create new cinema
     *
     * @param rows
     * @param seats
     */
    public Cinema(int rows, int seats) {
        this.rows = rows;
        this.seatsInThatRow = seats;
        this.totalSeat = rows * seats;
        this.totalIncome = calcTotalIncome();
        this.currentIncome = 0;
        this.ticketSold = 0;
        createCinema();
    }

    public void createCinema() {
        matrix = new char[rows][seatsInThatRow];
        for (char[] rows : matrix)
            Arrays.fill(rows, 'S');
    }

    public int calcTotalIncome() {
        if (totalSeat <= 60) {
            return rows * seatsInThatRow * frontSeat;
        } else {
            int firstHalf = rows / 2; // front seats
            int secondHalf = rows - firstHalf; // back seats
            return firstHalf * seatsInThatRow * frontSeat +
                    secondHalf * seatsInThatRow * backSeat;
        }
    }

    private int calcTicketPrice(int row) {

        if (totalSeat <= 60) {
            return frontSeat;
        } else if (row <= rows / 2) {
            return frontSeat;
        } else {
            return backSeat;
        }
    }

    public void buyTicket() {
        Inputs input = new Inputs();
        while (true) {
            int row = input.row();
            int seat = input.seat();

            if (row > rows || seat > seatsInThatRow) {
                message("Wrong input!");
            } else if (matrix[row - 1][seat - 1] == 'B') {
                message("That ticket has already been purchased!");
            } else {
                matrix[row - 1][seat - 1] = 'B';
                currentIncome += calcTicketPrice(row);
                ticketSold++;
                calcPercent();
                System.out.printf("%nTicket price: $%d%n", calcTicketPrice(row));
                break;

            }
        }

    }

    // show all seats (booked & not booked)
    public void printSeats() {

        System.out.println("\nCinema:");
        System.out.print("  ");

        // number the  seats
        for (int i = 1; i <= seatsInThatRow; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < matrix.length; ++i) {
            // number the rows
            System.out.print(i + 1 + " ");

            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    // calculate the percentage sold
    private double calcPercent() {
        if (ticketSold == 0) {
            return 0.00;
        } else {
            return percentage = (ticketSold * 100D) / totalSeat;
        }
    }

    // shows cinema inventory & income
    public void statistics() {

        System.out.printf("%nNumber of purchased tickets: %d" +
                "%nPercentage: %.2f%% %nCurrent income: $%d%n" +
                "Total income: $%d %n", ticketSold, percentage, currentIncome, totalIncome);

    }

    public void message(String message) {
        System.out.println(message);
    }

}

class Inputs {
    Scanner scanner = new Scanner(System.in);

    public int menu() {
        System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");
        return scanner.nextInt();
    }

    public int rows() {
        System.out.println("Enter the number of rows:");
        return scanner.nextInt();
    }

    public int seats() {
        System.out.println("Enter the number of seats in each row:");
        return scanner.nextInt();
    }

    public int row() {
        System.out.println("Enter a row number:");
        return scanner.nextInt();
    }

    public int seat() {
        System.out.println("Enter a seat number in that row:");
        return scanner.nextInt();
    }
}



    
 

 
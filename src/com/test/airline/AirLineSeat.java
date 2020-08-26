package com.test.airline;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AirLineSeat {

    char[][] LabelledSeats;
    int[][] arr;
    int passengers;
    int[][] visitedArr;
    int maxrow;

    public AirLineSeat(int[][] seatArr, int NoofPassengers) {
        this.arr = seatArr;
        this.passengers = NoofPassengers;
        FillWithLabels();
    }

    /**
     * Function that allocate seats
     *
     * @param seat
     * @param passengercnt
     * @return
     */
    private boolean allocateSeats(char seat, int passengercnt) {
        for (int i = 0; i < LabelledSeats.length; i++) {
            for (int j = 0; j < LabelledSeats[0].length; j++) {
                if (LabelledSeats[i][j] == seat && visitedArr[i][j] == 0) {
                    visitedArr[i][j] = passengercnt + 1;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Function that call the allocation of seat types first it goes for aisle seats , window seats, centre seats
     * @throws Exception
     */

    public void allocate() throws Exception {
        for (int i = 0; i < passengers; i++) {

            if (allocateSeats('A', i) || allocateSeats('W', i) || allocateSeats('C', i)) {
                System.out.println("Seats allocated for "+(i+1));
            } else {
                System.out.println("Seats available only for " + (i));
                break;
            }
        }
        ConstructHtml();
    }


    /**
     * Preparing  a labelled 2d array based on the inputs where A-> Aisle Seat,C->Centre Seat W-> Window Seat,
     */
    private void FillWithLabels() {
        int colSize = 0;
        int rowsize = Integer.MIN_VALUE;


        for (int i = 0; i < arr.length; i++) {
            colSize += arr[i][0];
            rowsize = Math.max(rowsize, arr[i][1]);
        }
        maxrow = rowsize;
        this.LabelledSeats = new char[rowsize][colSize];
        this.visitedArr = new int[rowsize][colSize];
        int indextostart = 0;

        for (int i = 0; i < arr.length; i++) {

            int cols = arr[i][0];
            int rows = arr[i][1];
            FillWithC(indextostart, rows, cols);
            if (i == 0)//Left Most Row
            {
                int count = indextostart;
                while (count < rows) {
                    LabelledSeats[count][0] = 'W';
                    LabelledSeats[count][cols - 1] = 'A';
                    count = count + 1;

                }
                indextostart = cols;

            } else//Middle and lastportions
            {
                int rowIndex = 0;
                int startindex = indextostart;
                int endindex = indextostart + cols - 1;
                while (rowIndex < rows) {
                    LabelledSeats[rowIndex][startindex] = 'A';
                    if (i == arr.length - 1 || i == 0) {
                        LabelledSeats[rowIndex][endindex] = 'W';
                    } else {
                        LabelledSeats[rowIndex][endindex] = 'A';
                    }
                    rowIndex += 1;
                }
                indextostart = endindex + 1;
            }
        }
    }


    /**
     * Construct Html and save the
     * @throws Exception
     */
    private void ConstructHtml() throws Exception {
        int lastCol = 0;
        StringBuilder str = new StringBuilder();
        str.append("<html>");
        str.append("<head>");
        str.append("<style>");
        str.append(".floatedTable { float:left;}");
        str.append(".inlineTable {\n" +
                "            display: inline-block;\n" +
                "        }");
        str.append("</style>");
        str.append("</head>");
        str.append("<body>");

        for (int i = 0; i < arr.length; i++) {
            str.append("<table border=1 class=\"inlineTable\">");
            int cols = arr[i][0] + lastCol;
            int rows = arr[i][1];
            int columnIterator = 0;
            for (int j = 0; j < maxrow; j++) {
                columnIterator = lastCol;
                str.append("<tr>");
                while (columnIterator < cols) {
                    String color = getColor(LabelledSeats[j][columnIterator]);
                    str.append("<td style=\"background-color:" + color + ";\">");
                    str.append(visitedArr[j][columnIterator] > 0 ? visitedArr[j][columnIterator] : "&nbsp &nbsp");
                    str.append("</td>");
                    columnIterator += 1;
                }

                str.append("</tr>");
                //lastCol=columnIncrement;
            }
            lastCol = columnIterator;
            str.append("</table>");
        }
        str.append("</body>");
        str.append("</html>");
        FileWriter fileWriter;
        fileWriter = new FileWriter("LAYOUT.html");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(str.toString());
        printWriter.close();
        System.out.println("The layout is created under"+System.getProperty("user.dir")+"/LAYOUT.html");
    }


    /**
     * Function to fill the in between seats as centre 'C'
     * @param indexToStart
     * @param rows
     * @param cols
     */
    private void FillWithC(int indexToStart, int rows, int cols) {
        int diff = Math.abs(cols - rows);
        int jIndexStart = indexToStart + 1;
        int jIndexStop = indexToStart + cols - 2;
        for (int i = 0; i < rows; i++) {
            for (int j = jIndexStart; j <= jIndexStop; j++) {
                this.LabelledSeats[i][j] = 'C';
            }
        }

    }

    /**
     * A static function to convert arraylist to 2d array
     * @param arr
     * @return
     */
    public static int[][] getArr(ArrayList arr) {
        int[][] toreturn = new int[arr.size()][2];
        for (int i = 0; i < arr.size(); i++) {
            String[] rowColVals = arr.get(i).toString().split((","));
            int[] tempArr = new int[2];
            tempArr[0] = Integer.parseInt(rowColVals[0]);
            tempArr[1] = Integer.parseInt(rowColVals[1]);
            toreturn[i] = tempArr;
        }
        return toreturn;
    }

    /**
     * Function to get the color based on the seat type
     * @param label
     * @return
     */

    public String getColor(char label) {
        String color;
        switch (label) {
            case 'A':
                color = "yellow";
                break;
            case 'W':
                color = "green";
                break;
            case 'C':
                color = "red";
                break;
            default:
                color = "white";
                break;
        }

        return color;
    }


    /**
     * Driver function to start the seat allocation algorithm
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            Scanner ss = new Scanner(System.in);
            System.out.println("Enter the 2D array values one by one like Example: ");
            System.out.println("1,2");
            System.out.println("2,3");
            System.out.println("Press '#' to complete the array");
            String arr = ss.nextLine();
            ArrayList<String> arrayList = new ArrayList<>();
            while (!arr.equalsIgnoreCase("#")) {
                arrayList.add(arr);
                arr = ss.nextLine();
            }
            if (arrayList.size() > 0) {
                System.out.println("Enter the passengers count");
                int passengerCnt = ss.nextInt();
                if(passengerCnt>0) {
                    int[][] TwoDArray = getArr(arrayList);
                    AirLineSeat arrangement = new AirLineSeat(TwoDArray, passengerCnt);
                    arrangement.allocate();
                }
                else
                {
                    System.out.println("Passenger Count must be greater than zero");

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

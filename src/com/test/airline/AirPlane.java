package com.test.airline;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AirPlane {
    private Seat[][] seatArray;
    private int[][] layout;
    private int maxRow;


    public AirPlane(int[][] seatLayout)
    {
        this.layout=seatLayout;
        FillWithLabels();
    }


    /**
     * REturns true if a seat is allocated
     * @param passengerNo
     * @return
     */
    public boolean tryAndReserveSeat(int passengerNo)
    {
        return (allocateSeat("A",passengerNo)||allocateSeat("W",passengerNo)||allocateSeat("C",passengerNo));
    }

    /**
     * Function to allocate seat
     * @param seatType
     * @param passengerNo
     * @return
     */


    private boolean allocateSeat(String seatType,int passengerNo)
    {
        for (int i = 0; i < seatArray.length; i++) {
            for (int j = 0; j < seatArray[0].length; j++) {
                Seat seat=seatArray[i][j];
                if(seat!=null) {
                    if (seatArray[i][j].getSeatLabel().equals(seatType) && !seatArray[i][j].isOccupied()) {
                        seatArray[i][j].setPassengerNo(passengerNo);
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Filling with seats and labels
     */
    private void FillWithLabels()
    {
        int rowsize = Integer.MIN_VALUE;
        int colSize=0;
        for (int i = 0; i < layout.length; i++) {
            colSize += layout[i][0];
            rowsize = Math.max(rowsize, layout[i][1]);
        }
        this.maxRow=rowsize;
        this.seatArray=new Seat[rowsize][colSize];
        int indextostart=0;
        for (int i = 0; i < layout.length; i++) {

            int cols = layout[i][0];
            int rows = layout[i][1];
            FillWithCLabels(indextostart, rows, cols);
            if (i == 0)//Left Most Row
            {
                int count = indextostart;
                while (count < rows) {
                    seatArray[count][0] =new Seat("W");
                    seatArray[count][cols - 1] = new Seat("A");
                    count = count + 1;
                }
                indextostart = cols;

            } else//Middle and lastportions
            {
                int rowIndex = 0;
                int startindex = indextostart;
                int endindex = indextostart + cols - 1;
                while (rowIndex < rows) {
                    seatArray[rowIndex][startindex] = new Seat("A");
                    if (i == layout.length - 1 || i == 0) {
                        seatArray[rowIndex][endindex] = new Seat("W");
                    } else {
                        seatArray[rowIndex][endindex] = new Seat("A");
                    }
                    rowIndex += 1;
                }
                indextostart = endindex + 1;
            }
        }
    }


    private void FillWithCLabels(int indexToStart,int rows, int cols)
    {
        int diff = Math.abs(cols - rows);
        int jIndexStart = indexToStart + 1;
        int jIndexStop = indexToStart + cols - 2;
        for (int i = 0; i < rows; i++) {
            for (int j = jIndexStart; j <= jIndexStop; j++) {
                this.seatArray[i][j] = new Seat("C");
            }
        }

    }

    /**
     * printing the seatarray to a html file
     * @throws IOException
     */
    public void printLayout() throws IOException {
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

        for (int i = 0; i < layout.length; i++) {
            str.append("<table border=1 class=\"inlineTable\">");
            int cols = layout[i][0] + lastCol;
            int rows = layout[i][1];
            int columnIterator = 0;
            for (int j = 0; j < maxRow; j++) {
                columnIterator = lastCol;
                str.append("<tr>");
                while (columnIterator < cols) {
                    Seat seat=seatArray[j][columnIterator];
                    if(seat!=null) {
                        String color = seat.getColor();
                        str.append("<td style=\"background-color:" + color + ";\">");
                        str.append(seat.isOccupied() ? seat.getPassNo() : "&nbsp &nbsp");
                        str.append("</td>");
                    }
                    else
                    {
                        str.append("<td style=\"background-color:white;\">");
                        str.append("&nbsp &nbsp");
                        str.append("</td>");
                    }
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






}





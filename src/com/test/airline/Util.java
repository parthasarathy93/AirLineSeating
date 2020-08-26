package com.test.airline;

import java.util.ArrayList;

public class Util {

    /**
     * A utility function to extract twod aray from the arraylist
     * @param arr
     * @return
     */
    public static int[][] getTwoDArray(ArrayList arr)
    {
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
}

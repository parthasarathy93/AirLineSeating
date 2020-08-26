package com.test.airline;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AirPlaneTest {


    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    /**
     *
     */
    @Test
    public void allocateAll()
    {
        int[][] testArray= {{3,2},{4,3},{2,3},{3,4}};
        AirPlane airPlane=new AirPlane(testArray);
        int totalpassengers=30;
        boolean allreserved=true;
        for(int i=0;i<totalpassengers;i++)
        {
            if(!airPlane.tryAndReserveSeat(i+1)) {
                allreserved = false;
                break;
            }
        }
        Assert.assertTrue(allreserved);
    }


    @Test
    public void reservingMoreThanThreshold ()
    {
        int[][] testArray= {{3,2},{4,3},{2,3},{3,4}};
        AirPlane airPlane=new AirPlane(testArray);
        int totalpassengers=37;
        boolean allreserved=true;
        int expectednotreserved=37;
        int actualnotreserved=0;
        for(int i=0;i<totalpassengers;i++)
        {
            if(!airPlane.tryAndReserveSeat(i+1)) {
                allreserved = false;
                actualnotreserved=(i+1);
                break;
            }
        }
        Assert.assertEquals(expectednotreserved,actualnotreserved);
    }



}
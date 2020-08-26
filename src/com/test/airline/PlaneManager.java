package com.test.airline;

import java.util.ArrayList;
import java.util.Scanner;

public class PlaneManager {

      private int waitingPassengers;
      public AirPlane airPlane;
      public  PlaneManager(ArrayList seatLayout,int waitingListPassengers)
      {
         this.waitingPassengers=waitingListPassengers;
         this.airPlane=new AirPlane(Util.getTwoDArray(seatLayout));
      }

    /**
     *Plan the seat layout and allocate it
     */
    public void planAndAllocate()throws Exception
    {
       for(int i=0;i<waitingPassengers;i++)
       {
          boolean reserved= airPlane.tryAndReserveSeat(i+1);
          if(!reserved)
          {
              System.out.println("Not able to allocate seats for the passenger"+(i+1));
              break;
          }
       }
       System.out.println("Printing Layout");
       airPlane.printLayout();
    }





    public static void main(String[] args)throws Exception
    {
        System.out.println("Welcome aboard! I am the plane Manager, Give me a 2d seat layout and number of people in waitinglist");
        System.out.println("Now give me a seat Layout In this format  like 1,2  line by line, Press # to complete your 2D");
        Scanner ss = new Scanner(System.in);
        String ip = ss.nextLine();
        ArrayList<String> arrayList = new ArrayList<>();
        while (!ip.equalsIgnoreCase("#")) {
            arrayList.add(ip);
            ip = ss.nextLine();
        }
        if (arrayList.size() > 0) {
            System.out.println("Enter the passengers count");
            int passengerCnt = ss.nextInt();
            PlaneManager planeManager=new PlaneManager(arrayList,passengerCnt);
            planeManager.planAndAllocate();
        }


    }

}

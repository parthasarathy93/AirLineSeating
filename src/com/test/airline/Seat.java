package com.test.airline;

public class Seat {
    private String seatLabel;
    private int passNo=0;
    public Seat(String label)
    {
        this.seatLabel=label;
    }

    public String getSeatLabel()
    {
        return this.seatLabel;
    }


    public boolean isOccupied()
    {
        return (passNo>0);
    }

    public void setPassengerNo(int passengerNo)
    {
        this.passNo=passengerNo;
    }

    public int getPassNo()
    {
        return this.passNo;
    }



    public String getColor()
    {
        String color;
        switch (this.seatLabel) {
            case "A":
                color = "yellow";
                break;
            case "W":
                color = "green";
                break;
            case "C":
                color = "red";
                break;
            default:
                color = "white";
                break;
        }

        return color;
    }
}


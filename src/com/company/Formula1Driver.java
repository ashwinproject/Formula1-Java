package com.company;

import java.util.ArrayList;

public class Formula1Driver extends Driver {


    private int fPosCount;
    private int sPosCount;
    private int tCount;
    private int totPoints;
    private int racesCount;

    public Formula1Driver(String fname, String lname, String dcountry, String dteam, int fPosCount, int sPosCount, int tCount, int totPoints, int racesCount) {
        super(fname, lname, dcountry, dteam);
        this.fPosCount = fPosCount;
        this.sPosCount = sPosCount;
        this.tCount = tCount;
        this.totPoints = totPoints;
        this.racesCount = racesCount;
    }

    public int getfPosCount() {
        return fPosCount;
    }

    public void setfPosCount(int fPosCount) {
        this.fPosCount += fPosCount;
    }

    public int getsPosCount() {
        return sPosCount;
    }

    public void setsPosCount(int sPosCount) {
        this.sPosCount += sPosCount;
    }

    public int gettCount() {
        return tCount;
    }

    public void settCount(int tCount) {
        this.tCount += tCount;
    }

    public int getTotPoints() {
        return totPoints;
    }

    public void setTotPoints(int totPoints) {
        this.totPoints += totPoints;
    }

    public int getRacesCount() {
        return racesCount;
    }

    public void setRacesCount(int racesCount) {
        this.racesCount += racesCount;
    }
}
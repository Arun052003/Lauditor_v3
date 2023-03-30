package com.digicoffer.lauditor.TimeSheets.Models;

public class TimeSheetModel {
    String currentWeek;
    String nextWeek;
    String prevWeek;
    boolean isFrozen;

    public String getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(String currentWeek) {
        this.currentWeek = currentWeek;
    }

    public String getNextWeek() {
        return nextWeek;
    }

    public void setNextWeek(String nextWeek) {
        this.nextWeek = nextWeek;
    }

    public String getPrevWeek() {
        return prevWeek;
    }

    public void setPrevWeek(String prevWeek) {
        this.prevWeek = prevWeek;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }
}

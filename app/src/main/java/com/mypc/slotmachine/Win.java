package com.mypc.slotmachine;

public class Win {

    private int counter;

    private static Win instance;

    public static Win getInstance() {
        if(instance == null){
            instance = new Win();
        }
        return instance;
    }

    private Win() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

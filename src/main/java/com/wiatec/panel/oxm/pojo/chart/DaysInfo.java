package com.wiatec.panel.oxm.pojo.chart;

public class DaysInfo {

    private int id;
    private float amount;
    private int volume;
    private int B1;
    private int P1;
    private int P2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getB1() {
        return B1;
    }

    public void setB1(int b1) {
        B1 = b1;
    }

    public int getP1() {
        return P1;
    }

    public void setP1(int p1) {
        P1 = p1;
    }

    public int getP2() {
        return P2;
    }

    public void setP2(int p2) {
        P2 = p2;
    }

    @Override
    public String toString() {
        return "DaysInfo{" +
                "id=" + id +
                ", amount=" + amount +
                ", volume=" + volume +
                ", B1=" + B1 +
                ", P1=" + P1 +
                ", P2=" + P2 +
                '}';
    }
}

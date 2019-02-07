package com.pwir.sprites;


public class BarbarianFireplace {

    private int x;
    private int y;

    public BarbarianFireplace(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}


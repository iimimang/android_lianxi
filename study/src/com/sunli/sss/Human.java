package com.sunli.sss;

class Human
{   

    /**
     * constructor
     */
    public Human(int h)
    {
        this.height = h;
    }

    /**
     * accessor
     */
    public int getHeight()
    {
       return this.height;
    }

    /**
     * mutator
     */
    public void growHeight(int h)
    {
        this.height = this.height + h;
    }

    /**
     * breath
     */
    public void breath()
    {
        System.out.println("hu...hu...");
    }

    private int height; 
}

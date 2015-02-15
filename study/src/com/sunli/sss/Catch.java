package com.sunli.sss;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class Catch
{
    public static void main(String[] args)
    {
        
    	String aa="1422865572";
    	long nowTime=System.currentTimeMillis();
    	long retime =Integer.valueOf(aa).intValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(retime*1000));
		System.out.print(date);
    }
}

class Battery 
{
    /**
     * increase battery
     */
    public void chargeBattery(double p)
    {
        // power <= 1
        if (this.power + p < 1.) {
            this.power = this.power + p;
        }
        else {
            this.power = 1.;
        }
    }
   

    /**
     * consume battery
     */
    public boolean useBattery(double p)
    {
        try {
            test(p);
        }
        catch(Exception e) {
            System.out.println("catch Exception");
            System.out.println(e.getMessage());
            p = 0.0;
        }

        if (this.power >= p) {
            this.power = this.power - p;
            return true;
        }
        else {
            this.power = 0.0;
            return false;
        }
    }

    /**
     * test usage
     */
    private void test(double p) throws Exception // I just throw, don't handle
    {
        if (p < 0) {
            Exception e = new Exception("p must be positive");
            throw e;
        }
    }

    private double power = 0.0; // percentage of battery
}
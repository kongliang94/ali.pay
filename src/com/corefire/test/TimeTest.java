package com.corefire.test;

import java.util.Timer;

public class TimeTest {

	 Timer timer;  
	    public TimeTest(int time,int i){  
	        timer = new Timer();  
	        timer.schedule(new TimerTaskTest(), time * 1000,1000);  
	    }  
	      
	    public static void main(String[] args) {  
	        System.out.println("timer begin....");  
	        
	        for (int i = 0; i < 5; i++) {
	        	
	        	new Thread(new Runnable() {
					//int k=i+1;
					@Override
					public void run() {
						
						new TimeTest(2,10);
					}
				}).start();
			}
	        
	        //new TimeTest(2);
	          
	    }  
}

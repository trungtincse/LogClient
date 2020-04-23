/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.common.util.concurrent.RateLimiter;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.stats.Profiler;
import org.apache.log4j.Logger;

/**
 *
 * @author tindang
 */
public class MainApp {
    private static final Logger _Logger = ZLogger.getLogger(MainApp.class); 
    public static RateLimiter ralimiter = RateLimiter.create(5000);
    public static void main(String[] args) {
        Exception ex=new Exception();
        System.out.println("Hello");
//        while(true){
//            ralimiter.acquire();
//            Profiler.createThreadProfiler("create request", true);
//            _Logger.error(ex.getMessage(),ex);
//            Profiler.closeThreadProfiler();
//            
//        }
    }
}

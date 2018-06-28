/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boryi.bots.news.business.people;

import com.boryi.bots.base.BotConfig;
import com.boryi.bots.base.BotRegex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author songyang
 */
public class Util {
    
    
    
    public static String getDate(Date date) {
        if (date != null) {
            return (new SimpleDateFormat("yyyy年M月d日")).format(date);    
        }
        return null;
    }
    
    public static String getUrl(String url) {
        String domain = "http://qyxy.baic.gov.cn";
        if (url != null && !url.startsWith("http")) {
            if (url.startsWith("/")) {
                return  domain + url;       
            } else {
                return  domain + "/" + url;
            }
        }
        return url;
    }
    
    public static String trimString(String val) {
        if (val != null && !val.trim().isEmpty()) {
            val = val.trim();
        }
        return val;
    }
    
    // 
    public static String trimString(String val, String default_val) {
        if (val != null && !val.trim().isEmpty()) {
            return val.trim();
        } else {
            return default_val;
        }
    }
    
    public static String filterString(String val, String keyword) {
        if (val != null && !val.trim().isEmpty() && keyword != null) {
            val = val.contains(keyword) ? null : val.trim();
        }
        return val;
    }
    
    public static Double trimDouble(String val) {
        Double d_val = null;
        if (val != null && !val.trim().isEmpty()) {
            try {
                d_val = Double.valueOf(val.trim());
            } catch (NumberFormatException ex) {}
        }
        return d_val;
    }
    
    public static Integer trimInteger(String val) {
        Integer i_val = null;
        if (val != null && !val.trim().isEmpty()) {
            try {
                i_val = Integer.valueOf(val.trim());
            } catch (NumberFormatException ex) {}
        }
        return i_val;
    }
    
    public static Date trimDate(String dateStr, String dateFormat){
        Date date = null;
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                date = (new SimpleDateFormat(dateFormat)).parse(dateStr.trim());
            } catch (Exception ex){}
        }
        return date;
    }
    
    public static String MatchSingleValue(String page, String pattern, String group) {
        String reg_value = null;
        String[] par = {group};
        Properties pro = BotRegex.match(BotConfig.getInstance().getPatterns().get(pattern), Arrays.asList(par), page);
        if (pro != null) {
            reg_value = pro.getProperty(group);
        }
        return reg_value;
    }
    
    public static String MatchSingleValue(String page, String pattern) {
        String reg_value = null;
        String[] par = {"value"};
        Properties pro = BotRegex.match(BotConfig.getInstance().getPatterns().get(pattern), Arrays.asList(par), page);
        if (pro != null) {
            reg_value = pro.getProperty("value");
        }
        return reg_value;
    }
    
    /**
     * 
     * @param arr
     * @return
     */
    public static ArrayList initArray(ArrayList arr) {
        return (arr == null) ? new ArrayList() : arr;
    }
    
    public static void DumpCaptcha(byte[] captchaBinData) {
        File file2 = new File("D:\\captcha_temp.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file2);
            fos.write(captchaBinData);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeopleWebThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeopleWebThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

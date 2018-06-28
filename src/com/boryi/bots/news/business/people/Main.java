package com.boryi.bots.news.business.people;

import com.boryi.bots.base.*;
import com.boryi.bots.qa.QaStatistic;
import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class for the bot to crawl website
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 *
 * @author Gang Huang, Ph.D.
 * @version 2.0
 * @since 2018-06-21 10:35:04
 */
public class Main 
{
    public static ConcurrentHashMap<String, Boolean> urls = new ConcurrentHashMap();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Boolean isDebug = true;
        
        /** COPY and MODIFIED FROM com.boryi.bots.base.Main **/
        String currentPath = "";
        try 
        {
            currentPath = URLDecoder.decode(Main.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath(), "UTF-8");
            currentPath = currentPath.substring(0, 
                    currentPath.lastIndexOf(File.separatorChar) + 1);
        } 
        catch (Exception e) 
        {
        }
        
        String[] args_debug = new String[6];
        args_debug[0] = "1";
        args_debug[1] = "11001";
        args_debug[2] = "1";
        args_debug[3] = "1";
        args_debug[4] = "false";
        args_debug[5] = "boryi.bot.news.business.people.conf";

        HashMap<String, Object> values;
        try
        {
            if(isDebug) {
                values = BotBase.getArguments(currentPath, args_debug); // args_debug - args
            } else {
                values = BotBase.getArguments(currentPath, args); // args
            }
        }
        catch (Exception exArgs)
        {
            System.out.println("usage: ");
            System.out.println();
            System.out.println("java -jar botname serverId botId jobId taskId isResume config");
            System.out.println("java -jar botname serverId botId jobId taskId config");
            System.out.println("java -jar botname serverId botId jobId isResume config");
            System.out.println("java -jar botname serverId botId jobId config");
            return;
        }

        long serverId = ((Long)values.get("serverId"));
        long botId = ((Long)values.get("botId"));
        long jobId = ((Long)values.get("jobId"));
        long taskId = ((Long)values.get("taskId"));
        boolean isResume = ((Boolean)values.get("resume"));
        String configFile = (String)values.get("config");
        if(isDebug) {
            configFile = "/root/NetBeansProjects/com.boryi.bots.news.business.people/src/config/boryi.bot.news.business.people.conf";
        }

        try
        {
            BotRunEntity run = new BotRunEntity(serverId, botId, jobId, taskId,
                    System.currentTimeMillis(), isResume);

            BotConfig config = new BotConfig(configFile);

            PeopleDao dao = new PeopleDao();

            BotCheckedItems checked = new BotCheckedItems(false, false);
            
            BotChecklist checklist = new BotChecklist();
            BotWebStatistic webStatistic = new BotWebStatistic();
            
            BotProxyPool proxyPool = new BotProxyPool();

            BotThread botThread = new BotThread();

            QaStatistic qaStatistic = new QaStatistic();

            int total = config.getWebThreads();
            
            if(isDebug) {
                total = 1;
            }
            
            for (int i = 0; i < total; i++) {
                PeopleWebThread webThread = new PeopleWebThread();
            }

            BotBase base = new BotBase();
            
            BotBase.logger.info("Bot started!");
        }
        catch (Exception ex)
        {
            BotBase.logger.fatal("Bot Id: " + botId
                    + " Job Id: " + jobId
                    + " Task Id: " + taskId + " "
                    + BotDateFormat.getDateFormat(BotDateFormatType.MILLISECOND).format(new Date())
                    + " " + ex.toString() + "\r\n" + BotWebThread.getExceptionStack(ex));
        }
    }
}

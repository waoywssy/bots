package com.boryi.bots.news.business.people;

import com.boryi.bots.base.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/**
 * Thread containing fields and methods to parse and save table data from website
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 *
 * @author Gang Huang
 * @version 2.0
 * @since 2018-06-21 10:35:04
 */
public class PeopleWebThread extends BotWebThread
{
    private ArrayList<PagespEntity> pagesps;
    
    private Object placeholder = new Object();
    
    private final static String format_cn_date = "yyyy年M月d日H:m";

    /**
     * Constructor
     */
    public PeopleWebThread() throws InterruptedException
    {}
    
    
    @Override
    public void parseWebData() throws InterruptedException
    {
        if (responseCharset == null)
        {
            try
            {
                webData = new String(webData.getBytes(DEFAULT_RESPONSE_CHARSET), 
                        BotConfig.getInstance().getParams().get("content_charset"));
            }
            catch(UnsupportedEncodingException ex)
            {
                logEvent(BotEventSeverity.WARNING, "Unsupported encoding",
                        ex.toString());
            }
        }

        checklist = new ArrayList<>();
        
        switch (item.getStage())
        {
            case 0:
                parseStage_0();
                break;
            case 1:
                parseStage_1();
                break;
        }

    }


    @Override
    public void parseBinData() throws InterruptedException
    {

    }


    @Override
    public void saveData() throws InterruptedException { }


    /**
     * Get home page, add list url
     */
    private void parseStage_0() throws InterruptedException
    {
        // Search level one urls
        SearchUrls(1, 1);
    }

    private void SearchUrls(int chk_stage, int chk_level) throws InterruptedException {
        ArrayList<BasicClientCookie> chk_cookies;
        int chk_page;
        Long chk_id;
        Long chk_parent_id;
        String chk_key;
        ArrayList<NameValuePair> chk_namevalues;
        String chk_parent_key;
        ArrayList<String> newslist = new ArrayList<>();
        try {
            String[] par_0_get_url = { "value" };
            String url = null;
            ArrayList<Properties> pro_0_get_urls = BotRegex.matches(
                    BotConfig.getInstance().getPatterns().get("0_get_url"), 
                    Arrays.asList(par_0_get_url), webData);
            for (Properties pro_0_get_url : pro_0_get_urls) {
                if (pro_0_get_url != null) {
                    url = Util.trimString(pro_0_get_url.getProperty("value"));
                }
                if (url == null) {
//                    retry(true, BotEventSeverity.ERROR, "Broken news url");
                }
                newslist.add(url);
            }
        }catch (Exception ex){
            this.retry(true, BotEventSeverity.ERROR, "Failed parsing urls: " + ex.toString());
        }
        // deal with the news links
        chk_cookies = null;
        chk_page = 1;
        chk_id = item.getId();
        chk_parent_id = item.getParentId();
        chk_key = null;
        chk_namevalues = new ArrayList<>();
        
        // to keep distinct urls in the current page
        for (String detailUrl : newslist) {
            chk_parent_key = cleanUrl(detailUrl);
            
            if (!testValidUrl(chk_parent_key)){
                continue;
            }
            
            String url;
            try {
                url = item.getUrl().resolve(chk_parent_key).toString();
                
                if (isInvalidSubDomain(url)) {
                    continue;
                }
            } catch (Exception ex) {
                System.out.println(detailUrl);
                continue;
            }
            
            // make sure each url only search once
            if (Main.history_urls.containsKey(detailUrl)) {
                // if the url has been searched during previous runs
                continue;
            }
            
            if (Main.current_run_searched_urls.containsKey(detailUrl)) {
                // if the url hass been searched during this run
                continue;
            } else {
                Main.current_run_searched_urls.put(detailUrl, true);
            }
            
            try {
                checklist.add(new BotChecklistEntity(
                        item.getUrl().resolve(chk_parent_key), // detailUrl
                        chk_namevalues, chk_cookies, getHttpReferer(chk_stage), chk_stage, chk_level,
                        chk_page, chk_id, chk_parent_id, chk_key, chk_parent_key, 0));
            } catch (Exception ex) {
                System.out.println(detailUrl);
                this.retry(true, BotEventSeverity.ERROR, "Illegal character; " 
                        + item.getUrl().resolve(detailUrl) + ex.toString());
            }
        }
        
        System.out.println("Level " + chk_level + ": Matches: " + newslist.size() 
                + "; Distincts: " + Main.current_run_searched_urls.size());
    }

    private String cleanUrl(String detailUrl) {
        // filter urls
        String chk_parent_key = detailUrl.trim();
        if (chk_parent_key.startsWith("&#9;")) {
            chk_parent_key = chk_parent_key.replaceFirst("&#9;", "");
        }
        if (chk_parent_key.startsWith("&#10;")) {
            chk_parent_key = chk_parent_key.replaceFirst("&#10;", "");
        }
        return chk_parent_key;
    }

    private Boolean testValidUrl(String url) {
        if (url.contains("javascript:")) {
            return false;
        }
        if (url.equals("http://www.people.com.cn/")){
            // search no more than twice
            return false;
        }
        if (!url.contains("people.com.cn")) {
            // belongs to other sites
            return false;
        }
        return !url.equals(item.getUrl().toString());
    }

    private static boolean isInvalidSubDomain(String url) {
        return url.contains("sns.people.com.cn") ||
            url.contains("blog.people.com.cn") ||
            url.contains("t.people.com.cn") ||
            url.contains("bbs1.people.com.cn") ||
            url.contains("wza.people.com.cn") ||
            url.contains("history.people.com.cn") ||
            url.contains("tv.people.com.cn") ||
            url.contains("paper.people.com.cn") ||
            url.contains("travel315.people.com.cn") ||
            url.contains("dangshi.people.com.cn") ||
            url.contains("opinion.people.com.cn") ||
            url.contains("theory.people.com.cn") ||
            url.contains("ezheng.people.com.cn") ||
            url.contains("pic.people.com.cn") ||
            url.contains("liuyan.people.com.cn") ||
            url.contains("comments.people.com.cn") ||

            url.contains("yi.people.com.cn") ||
            url.contains("arabic.people.com.cn") ||
            url.contains("jpn_cpc.people.com.cn") || // domain not work
            url.contains("english.cpc.people.com.cn") ||
            url.contains("tibet.cpc.people.com.cn") ||
            url.contains("tibet.people.com.cn") ||
            url.contains("russian.people.com.cn") ||
            url.contains("german.people.com.cn") ||
            url.contains("korean.people.com.cn") ||
            url.contains("j.people.com.cn") ||
            url.contains("kr.people.com.cn") ||
            url.contains("mongol.people.com.cn") ||
            url.contains("uyghur.people.com.cn") ||
            url.contains("kazakh.people.com.cn") ||
            url.contains("spanish.people.com.cn") ||
            url.contains("french.people.com.cn") ||
            url.contains("sawcuengh.people.com.cn") ||
            url.contains("sns.people.com.cn") ||
            url.toUpperCase().contains("/BIG5/");
    }
    
    
    /**
     * Get stage level 1, for details and categories
     * 
     * Maybe detail, maybe sub category
     * Meta里面含有如下关键字，则为新闻详情页面 
     * name="contentid"
     */
    private void parseStage_1() throws InterruptedException
    {
        String chk_url;
        ArrayList<NameValuePair> chk_namevalues;
        ArrayList<BasicClientCookie> chk_cookies;
        int chk_stage;
        int chk_level = item.getLevel() + 1;
        int chk_page;
        Long chk_id;
        Long chk_parent_id;
        String chk_key;
        String chk_parent_key;

        chk_namevalues = null;
        chk_cookies = getCurrentCookies();
//        chk_stage = 1;
        chk_page = 1;
        chk_id = null;
        chk_parent_id = null;
        chk_key = null;
        chk_parent_key = null;
        
        String reg_date;
        String ptitle;
        String pcontent;

        try {
            if (webData!= null && webData.contains("<meta name=\"contentid\"")) {
                // This type of webcontent
                Date pfirstfound = Util.trimDate(
                        Util.MatchSingleValue(webData, "3_get_news_published"), 
                        format_cn_date);
                if (pfirstfound == null) {
                    // 1970年-01月-01日 as default value incase there is none in the web page
                    try {
                        pfirstfound = (new SimpleDateFormat(format_cn_date)).parse("1970年1月1日");
                    } catch(ParseException ex) { }
                }
                
                ptitle = StringEscapeUtils.unescapeHtml3(
                            Util.MatchSingleValue(webData, "3_get_news_title"));
                
                pcontent = getPageContent(ptitle);

                if (ptitle == null) {
    //                retry(true, BotEventSeverity.ERROR, "Table CompanyRandom");
                    System.out.println("No title: " + item.getUrl().toString());
                    return;
                }

                ((PeopleDao) PeopleDao.getInstance()).savePagesp(
                    new PagespEntity(
                        1, item.getUrl().toString(), ptitle, pcontent, pfirstfound)
                );
                
//                System.out.println("Level :" + chk_level + " - Detail page: " + item.getUrl().toString());
//                ((PeopleDao) PeopleDao.getInstance()).saveExcludedUrl(item.getUrl().toString() );
            } else {
                // not detail page
                ((PeopleDao) PeopleDao.getInstance()).saveNextUrl(item.getUrl().toString() );
//                System.out.println("Level :" + chk_level + " - No detail page: " + item.getUrl().toString());
            }
        }
        catch (Exception ex){ 
            //this.retry(true, BotEventSeverity.ERROR, "Company Random; " + ex.toString());
            System.out.println("Url; " + ex.toString());
        }
        
        // to search the next level if not exceed the maximum search level
        if (chk_level < Integer.valueOf(BotConfig.getInstance().getParams().get("max_search_level"))){
            SearchUrls(1, chk_level);
        }
    }

    private String getPageContent(String ptitle) 
            throws IllegalAccessException, NoSuchAlgorithmException, ClassNotFoundException, 
            InstantiationException, UnsupportedEncodingException, SQLException, InterruptedException {
        String pcontent;
        pcontent = Util.MatchSingleValue(webData, "3_get_news_content");
        if (pcontent==null) {
            pcontent = Util.MatchSingleValue(webData, "3_get_news_content_type_1");
        }
        if (pcontent==null) {
            pcontent = Util.MatchSingleValue(webData, "3_get_news_content_type_2");
        }
        if (pcontent==null) {
            // images news
            if (ptitle.contains("图解")) {
                System.out.println("images news: " + item.getUrl().toString());
            } else {
                System.out.println("Unknown: " + item.getUrl().toString());
                ((PeopleDao) PeopleDao.getInstance()).saveUncheckedUrl(item.getUrl().toString());
            }
        } else {
            pcontent = Jsoup.clean(pcontent, Whitelist.none());
            pcontent = StringEscapeUtils.unescapeHtml3(pcontent);
            pcontent = pcontent.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
            pcontent = pcontent.toLowerCase().trim();
        }
        return pcontent;
    }

}

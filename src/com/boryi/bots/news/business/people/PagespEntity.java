package com.boryi.bots.news.business.people;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/**
 * Pagesp entity
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 *
 * @author Gang Huang
 * @version 2.0
 * @since 2018-06-21 10:35:04
 */
public class PagespEntity 
{
    private long pid;
    private final int pstid;
    private String purl;
    private String ptitle;
    private String pabstract = null;
    private String pcontent;
    private String pcontentmd5;
    private Date pfirstfound;
    private Date plastfound;

    
    /**
     * Constructor
     */
    public PagespEntity (int pstid, String purl, String ptitle, String pcontent, Date newstime)
    {
        this.pstid = pstid;
        this.purl = purl;
        this.ptitle = ptitle;
        this.pcontent = pcontent;
        this.pfirstfound = newstime;
    }
    
    public long getPid()
    {
        return pid;
    }

    public int getPstid()
    {
        return pstid;
    }

    public String getPurl()
    {
        return purl;
    }

    public String getPtitle()
    {
        return ptitle;
    }

    public String getPabstract()
    {
        return pabstract;
    }

    public String getPcontent()
    {
        return pcontent;
    }

    public String getPcontentmd5()
    {
        return pcontentmd5;
    }

    public Date getPfirstfound()
    {
        return pfirstfound;
    }

    public Date getPlastfound()
    {
        return plastfound;
    }


}

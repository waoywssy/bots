package com.boryi.bots.news.business.people;

import com.boryi.bots.base.BotBase;
import com.boryi.bots.base.BotConfig;
import com.boryi.bots.base.BotDao;
import com.boryi.bots.base.BotProxyServerEntity;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Bot database access object
 *
 * <p><strong>Copyright 2007 - 2017 Boryi Network Information Inc.</strong></p>
 *
 * @author Gang Huang
 * @version 2.0
 * @since 2018-06-21 10:35:04
 */
public class PeopleDao extends BotDao
{
    public PeopleDao() throws Exception
    {}


    /**
     * Save pagesp information
     *
     * @param pagesp the pagesp instance that retrieved from website
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public synchronized void savePagesp(PagespEntity pagesp)
            throws SQLException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InterruptedException, 
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = getLocalProcedure("save_pagesp");

                cs.setObject(1, pagesp.getPstid());
                cs.setObject(2, pagesp.getPurl());
                cs.setObject(3, pagesp.getPtitle());
                String content = pagesp.getPcontent();
                cs.setObject(4, content);
                
                if (content == null) {
                    cs.setObject(5, null);
                } else {
                    cs.setObject(5, getMd5(pagesp.getPcontent()));
                }
                
                Timestamp timeout = new Timestamp(pagesp.getPfirstfound().getTime());
                cs.setObject(6, timeout);

                cs.execute();
                failed = false;
            }
            catch (SQLException sex)
            {
                retry++;
                if (retry > 2)
                {
                    throw sex;
                }
                prepareLocal();
            }
        }
    }
    
    
    public synchronized void saveCheckedUrl(String url)
            throws SQLException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InterruptedException, 
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = getLocalProcedure("save_url_checked");

                cs.setString(1, url);
                cs.execute();
                failed = false;
            }
            catch (SQLException sex)
            {
                retry++;
                if (retry > 2)
                {
                    throw sex;
                }
                prepareLocal();
            }
        }
    }
    
    public synchronized void saveExcludedUrl(String url)
            throws SQLException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InterruptedException, 
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = getLocalProcedure("save_url_excluded");

                cs.setString(1, url);
                cs.execute();
                failed = false;
            }
            catch (SQLException sex)
            {
                retry++;
                if (retry > 2)
                {
                    throw sex;
                }
                prepareLocal();
            }
        }
    }
    
    public synchronized void saveUncheckedUrl(String url)
            throws SQLException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InterruptedException, 
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = getLocalProcedure("save_url_unchecked");

                cs.setString(1, url);
                cs.execute();
                failed = false;
            }
            catch (SQLException sex)
            {
                retry++;
                if (retry > 2)
                {
                    throw sex;
                }
                prepareLocal();
            }
        }
    }
    
    
    public synchronized void saveNextUrl(String url)
            throws SQLException, ClassNotFoundException, InstantiationException, 
            IllegalAccessException, InterruptedException, 
            NoSuchAlgorithmException, UnsupportedEncodingException
    {
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try
            {
                CallableStatement cs = getLocalProcedure("save_url_next");

                cs.setString(1, url);
                cs.execute();
                failed = false;
            }
            catch (SQLException sex)
            {
                retry++;
                if (retry > 2)
                {
                    throw sex;
                }
                prepareLocal();
            }
        }
    }
    
    
    
    public final ArrayList<String> getUrls()
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException, InterruptedException
    {
        ArrayList<String> urls = new ArrayList<>();
        int retry = 0;
        boolean failed = true;
        while (failed) // database connection failure, then retry
        {
            try (CallableStatement cs = dbProxyConnection.prepareCall(
                    BotConfig.getInstance().getProxyProcedures().get("get_proxies")))
            {
//                cs.setInt(1, BotConfig.getInstance().getProxyPoolRestriction());
                try (ResultSet rs = cs.executeQuery()) {
                    while (rs.next()) {
//                        urls.add(new BotProxyServerEntity(rs.getLong(1), 
//                                rs.getString(4), rs.getString(2), rs.getInt(3),
//                                rs.getString(5), rs.getString(6)));
                    }
                    failed = false;
                }
            } catch (SQLException sex) {
                retry++;
                if (retry > 2) {
                    throw sex;
                } else if (retry == 2) {
                    BotBase.logger.warn("Proxy database getProxies failed - retried "
                        + Integer.toString(retry) + " - " + sex.toString());
                }
            }
        }
        closeProxyConnection();
        return urls.isEmpty() ? null : urls;
    }
}

package com.boryi.bots.news.business.people;

import com.boryi.bots.base.BotDao;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}

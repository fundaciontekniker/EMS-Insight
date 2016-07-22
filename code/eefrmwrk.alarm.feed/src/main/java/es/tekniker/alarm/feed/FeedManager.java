package es.tekniker.alarm.feed;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.database.sql.manage.AlarmManager;
import es.tekniker.eefrmwrk.database.sql.model.Alarm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

public class FeedManager
{
  private static final Log log = LogFactory.getLog(FeedManager.class);
  private Set<String> supportedFeedTypes = new HashSet();
  private static String DEFAULT_FEED_TYPE = "rss_2.0";
  private static String DEFAULT_ALARM_URL = "http://10.1.45.54/LIVINGLAB_UI/alarm.html";
  
  public FeedManager()
    throws Exception
  {
    this.supportedFeedTypes.add("rss_1.0");
    this.supportedFeedTypes.add("rss_2.0");
    try
    {
      new ConfigFile("feedserver.properties");
      DEFAULT_FEED_TYPE = ConfigFile.getStringParam("FeedManager.defaultFeedType");
      DEFAULT_ALARM_URL = ConfigFile.getStringParam("FeedManager.defaultAlarmUrl");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      log.error("searchserver.properties file not found. Setting default values", e);
    }
    log.info("DEFAULT_FEED_TYPE: " + DEFAULT_FEED_TYPE);
  }
  
  public SyndFeed getDocumentFeed(String status, Integer limit, String feedType, String feedUrl)
  {
    log.debug("getDocumentFeed invoked");
    boolean entriesAvailable = false;
    List<Alarm> results = new ArrayList();
    

    SyndFeed feed = new SyndFeedImpl();
    feed.setFeedType(validateFeedType(feedType));
    feed.setTitle("Alarms:");
    feed.setLink(feedUrl);
    try
    {
      results = AlarmManager.findByStatus(status, limit);
      
      feed.setDescription(results.size() + "  obtained");
      if (results.size() > 0) {
        entriesAvailable = true;
      }
    }
    catch (Exception e)
    {
      feed.setDescription("Exception getting alrms: " + e.getLocalizedMessage());
    }
    List<SyndEntryImpl> entries = new ArrayList();
    
    SyndEntryImpl entry = null;
    
    SyndContent description = null;
    
    log.debug("Results obtained: " + results.size());
    CleanerProperties props = new CleanerProperties();
    HtmlCleaner cleaner = new HtmlCleaner(props);
    props.setTranslateSpecialEntities(true);
    props.setTransSpecialEntitiesToNCR(true);
    PrettyXmlSerializer prettyXmLSerializer = new PrettyXmlSerializer(props);
    String prettyFeed = null;
    if (entriesAvailable)
    {
      log.debug("Generating feed entries");
      for (Alarm alarm : results)
      {
        entry = new SyndEntryImpl();
        entry.setTitle(alarm.getAlarmCode());
        entry.setLink(getAlarmPage(feedUrl) + "?" + alarm.getAlarmId());
        entry.setPublishedDate(alarm.getAlarmTimespan());
        


        description = new SyndContentImpl();
        
        description.setType("text/html");
        StringBuffer str = new StringBuffer();
        str.append("<P>Code:<b>");
        str.append(alarm.getAlarmCode());
        str.append("</b></P>");
        str.append("<P>Status:<b> ");
        str.append(alarm.getAlarmState());
        str.append("</b></P>");
        str.append("<P>Description:<b>");
        str.append(alarm.getAlarmDesc());
        str.append("</b></P>");
        str.append("<P>Message:<b>");
        str.append(alarm.getAlarmMessage());
        str.append("</b></P>");
        str.append("<P>Severity:<b>");
        str.append(alarm.getAlarmSeverity());
        str.append("</b></P>");
        str.append("<P>Timestamp:<b>");
        str.append(alarm.getAlarmTimespan());
        str.append("</b></P>");
        str.append("<P>Alarm Page:<b>");
        str.append(getAlarmPage(feedUrl));
        str.append("</b></P>");
        


        String feedDirty = str.toString();
        TagNode tagNode = cleaner.clean(feedDirty);
        try
        {
          prettyFeed = prettyXmLSerializer.getAsString(tagNode);
        }
        catch (IOException e)
        {
          prettyFeed = feedDirty;
          log.error("Failed cleaning feed: " + feedDirty, e);
        }
        description.setValue(prettyFeed);
        entry.setDescription(description);
        entries.add(entry);
      }
    }
    log.debug("Generated " + entries.size() + "feeds");
    feed.setEntries(entries);
    log.debug("returning feeds");
    
    return feed;
  }
  
  private String getAlarmPage(String feedUrl)
  {
    return DEFAULT_ALARM_URL;
  }
  
  private String validateFeedType(String feedType)
  {
    if (this.supportedFeedTypes.contains(feedType)) {
      return feedType;
    }
    return DEFAULT_FEED_TYPE;
  }
}

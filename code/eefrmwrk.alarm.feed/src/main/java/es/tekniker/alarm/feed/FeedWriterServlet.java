package es.tekniker.alarm.feed;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FeedWriterServlet
  extends HttpServlet
{
  private static final Log log = LogFactory.getLog(FeedWriterServlet.class);
  private FeedManager feedManager = null;
  
  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }
  
  protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    log.debug("Processing HTTP request: " + request);
    try
    {
      setFeedManager(new FeedManager());
      PrintWriter out = response.getWriter();
      response.setContentType("text/html");
      
      log.debug("Getting params");
      

      SyndFeed feed = getFeedManager().getDocumentFeed(getStatusParam(request), getLimitParam(request), getFeedType(request), getFeedUrl(request));
      log.debug("Feeds created showing");
      try
      {
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, out);
      }
      catch (FeedException ex)
      {
        log.error("Error showing feeds", ex);
        throw new RuntimeException(ex);
      }
      out.close();
      log.debug("HTTP request processed");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.fatal("Error initiating Repository access", e);
      throw new ServletException(e);
    }
  }
  
  private String getFeedType(HttpServletRequest request)
  {
    return request.getParameter("feedType");
  }
  
  private String getStatusParam(HttpServletRequest request)
  {
    return request.getParameter("status");
  }
  
  private Integer getLimitParam(HttpServletRequest request)
    throws Exception
  {
    Integer i = null;
    try
    {
      String s = request.getParameter("limit");
      if ((s != null) && (!s.isEmpty())) {}
      return Integer.valueOf(Integer.parseInt(s));
    }
    catch (Exception e)
    {
      throw new Exception("limit value is NOT an integer");
    }
  }
  
  private String getFeedUrl(HttpServletRequest request)
  {
    return request.getRequestURL() + "?" + request.getQueryString();
  }
  
  public FeedManager getFeedManager()
  {
    return this.feedManager;
  }
  
  public void setFeedManager(FeedManager feedManager)
  {
    this.feedManager = feedManager;
  }
}

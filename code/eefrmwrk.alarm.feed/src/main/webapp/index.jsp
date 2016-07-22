<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RSS index JSP Page</title>
    </head>
    <body>
        <h1>RSS Servlet</h1>
        <h3>Use /alarmFeed/rss?feedType=?&status=?&limit=?</h3>
        
        <P>[optional param: feedType] Feed Type to return. If omitted, rss_2.0 is used</P>
		<P>FeedTypes suported:</P>
		<ul>rss_1.0</ul>
		<ul>rss_2.0</ul>
		<p></p>
		<P>[optional param: status] Filters the retrieved feeds.</P>
		<P> Available statuses:
		<ul>PENDING</ul>
		<ul>CHECKING</ul>
		<ul>CANCELLED</ul>
		<p></p>
		<P>[optional param: limit] Limits the number of returned feeds. Must be a positive integer</P>
	
	</body>
</html>

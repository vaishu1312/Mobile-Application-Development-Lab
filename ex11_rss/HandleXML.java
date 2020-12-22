package com.example.final_ex6;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

public class HandleXML {
    public volatile boolean parsingComplete = false;
    Context context;
    ArrayList<RssItem> list = new ArrayList<RssItem>();
    private String urlString = null;

    public HandleXML(String url, Context context) {
        this.urlString = url;
        this.context = context;
    }

    public ArrayList<RssItem> getList() {
        return list;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {

        String DESCRIPTION = "description";
        String CHANNEL = "channel";
        String LINK = "link";
        String TITLE = "title";
        String ITEM = "item";

        String text = null;

        int eventType, start = 0;
        boolean done = false;
        RssItem item = null;

        try {
            eventType = myParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        name = myParser.getName();
                        if (name.equalsIgnoreCase(CHANNEL)) {
                            item = new RssItem();
                            start = 1;
                        }
                        if (name.equalsIgnoreCase(ITEM)) {
                            if (start == 1)
                                list.add(item);
                            item = new RssItem();
                            start = -1;
                        } else if (item != null && start == -1) {
                            if (name.equalsIgnoreCase(LINK))
                                item.setLink(myParser.nextText());
                            else if (name.equalsIgnoreCase(DESCRIPTION))
                                item.setDescription(myParser.nextText().trim());
                            else if (name.equalsIgnoreCase(TITLE))
                                item.setTitle(myParser.nextText().trim());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        name = myParser.getName();
                        if (name.equalsIgnoreCase(TITLE)) {
                            item.setTitle(text);
                        } else if (name.equalsIgnoreCase(LINK)) {
                            item.setLink(text);
                        } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                            item.setDescription(text);
                        }

                        if (name.equalsIgnoreCase(ITEM) && item != null)
                            list.add(item);
                        else if (name.equalsIgnoreCase(CHANNEL)) {
                            if (start == 1) {
                                list.add(item);
                            }
                            done = true;
                        }
                        break;
                }
                eventType = myParser.next();
            }
            parsingComplete = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    XmlPullParser myparser = XmlPullParserFactory.newInstance().newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(conn.getInputStream(), null);
                    parseXMLAndStoreIt(myparser);

                    /*XmlPullParser myparser2 =context.getResources().getXml(R.xml.sample);
                    parseXMLAndStoreIt(myparser2);*/        //to obtain xml from file ---file is stored in r=xml directory of res folder.

                } catch (SocketTimeoutException e) {
                    e.printStackTrace();        //due to connectTimeout and readtimeout
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
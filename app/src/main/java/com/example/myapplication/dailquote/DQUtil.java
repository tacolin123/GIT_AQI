package com.example.myapplication.dailquote;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;

public class DQUtil {

    public static String handleDQXmlResponse(String response) {

        String  dailquote = "";

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int eventType = xmlPullParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                if (eventType == XmlPullParser.START_TAG)
                {
                    if(nodeName != null && "meta".equals(nodeName))
                    {
                        dailquote = xmlPullParser.getAttributeValue(null, "content");
                        if (dailquote != null && dailquote.length() > 0) // read data
                            break;
                    }

                }
                /* 190422_t-
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (nodeName) {
                            case "meta":
                               // dailquote = "meta";
                                dailquote = xmlPullParser.getAttributeValue(null, "content");
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }

                if (eventType == XmlPullParser.START_TAG)
                {
                    if(nodeName != null && "meta".equals(nodeName))
                    {
                        dailquote = xmlPullParser.getAttributeValue(null, "content");
                    }
                }


                if (dailquote.length() > 0) // read data
                    break;
 */
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dailquote;
    }

}

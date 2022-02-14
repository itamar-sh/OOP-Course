package Exams.Exam2018A;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// this question is about Regex
public class Q1 {
    // a Q

    //valid name of a tag or a property
    private static final String NAME = "([a-zA-Z]+)";
    //valid value of a property
    private static final String PROPERTY_VAL = "([^\\s\"]+|\"[^\"]+\")";
    //a property-value pair
    private static final String PROPERTY_PAIR = NAME + "=" + PROPERTY_VAL;
    //what a tag without content looks like
    private static final String HTML_TAG = "<" + NAME + "( " + PROPERTY_PAIR + ")*>";
    //what a tag with content looks like
    private static final String HTML_CONTENT = "(" + HTML_TAG + ")(.*)<\\/\\2>";
    //a tag in general
    private static final String GENERAL_HTML = HTML_CONTENT + "|" + HTML_TAG;

    static List<String> getHtmlTags(String text){
        List<String> l = new ArrayList<>();
        Pattern r = Pattern.compile(GENERAL_HTML);
        Matcher m = r.matcher(text);
        while(m.find()) {
            //group 6 in GENERAL_HTML exactly captures the content. It
            //is the "(.*)" from HTML_CONTENT
            String content = m.group(6);
            if (content == null) {
                l.add(text.substring(m.start(),m.end()));
            }
            else {
                String tag = m.group(1);
                l.add(tag.substring(0,tag.length()- 1) +
                        " content=\"" + content + "\">");
            }
        }
        return l;
    }

    // b Q
    static String getLink(String tag) throws MalformedTagException {
        Pattern r = Pattern.compile(HTML_TAG);
        Matcher m = r.matcher(tag);
        if (!m.matches()) {
            throw new MalformedTagException();
        }
        r = Pattern.compile(PROPERTY_PAIR);
        m = r.matcher(tag);
        while(m.find()) {
            //group 1 in PROPERTY_PAIR is the property name
            if (m.group(1).equals("href")) {
                //remove the quotation marks from both sides of the
                //value before returning
                return m.group(2).substring(1,m.group(2).length()-1);
            }
        }
        return null;
    }

}




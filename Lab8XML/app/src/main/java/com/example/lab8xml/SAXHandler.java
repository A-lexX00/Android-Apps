package com.example.lab8xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

import java.util.ArrayList;

public class SAXHandler extends DefaultHandler {
    private boolean validText;
    private String element = "";
    private Item currentItem;
    private ArrayList<Item> items;

    public SAXHandler() {
        validText = false;
        items = new ArrayList<Item>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void startElement(String uri, String localName,
                             String startElement, Attributes attributes)
            throws SAXException {
        validText = true;
        element = startElement;
        if (startElement.equals("item")) // start current item
            currentItem = new Item("", "");
    }

    public void endElement(String uri, String localName,
                           String endElement) throws SAXException {
        validText = false;
        if (endElement.equals("item")) // add current item to items
            items.add(currentItem);
    }

    public void characters(char ch[], int start,
                           int length) throws SAXException {
        if (currentItem != null && element.equals("title") && validText)
            currentItem.setTitle(new String(ch, start, length));
        else if (currentItem != null && element.equals("link") && validText)
            currentItem.setLink(new String(ch, start, length));
    }
}
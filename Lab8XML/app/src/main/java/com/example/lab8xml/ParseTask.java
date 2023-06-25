package com.example.lab8xml;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseTask extends AsyncTask<String, Void, ArrayList<Item>> {
    // MainActivity reference
    private MainActivity activity;

    // When creating a ParseTask reference from
    // MainActivity, we can pass this as the argument
    public ParseTask( MainActivity fromActivity ) {
        activity = fromActivity;
    }

    @Override
    protected ArrayList<Item> doInBackground(String... urls) {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance( );
            SAXParser saxParser = factory.newSAXParser( );
            SAXHandler handler = new SAXHandler( );
            ((SAXParser) saxParser).parse( urls[0], handler );
            return handler.getItems( );
        }catch(Exception e) {
            Log.w( "MainActivity", e.toString( ) );
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Item> returnedItems){
        // activity is a reference to Main Activity

        // displayList is a method of MainActivity
        activity.displayList( returnedItems );
    }
}

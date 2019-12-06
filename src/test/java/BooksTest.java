import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;

import static org.junit.Assert.*;

public class BooksTest
{
    Books testBook = new Books();

    @Test
    public void addQuery() throws Exception
    {
        String query = " "; //type in a query between the ""
        String link = "https://www.googleapis.com/books/v1/volumes?q=";
        link = link + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";

        assertEquals(testBook.addQuery(query), link);
    }

    @Test
    public void checkIfValidURL() throws Exception
    {
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q="); //place any link as the parameter

        assertEquals(testBook.checkIfValidURL(url), false); //test true or false
    }

    @Test
    public void checkIfResultsAvailable() throws Exception
    {
        //enter a link that accepts queries and returns results
        //or enter a search after the 'q' and use '+' to act as spaces for your search
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=*");

        assertEquals(testBook.checkIfResultsAvailable(url), false); //test true or false
    }

    @Test
    public void makeURLConnection() throws Exception
    {
        URL url = new URL("https://www.merriam-webster.com/"); //enter a link
        testBook.makeURLConnection(url);
    }

    @Test
    public void parseData() throws Exception
    {
    }

    @Test
    public void displaySearchResults() throws Exception
    {
    }

    @Test
    public void returnOnlyTitles() throws Exception
    {
    }

    @Test
    public void putInReadingList() throws Exception
    {
    }

    @Test
    public void displayReadingList() throws Exception
    {
    }

}
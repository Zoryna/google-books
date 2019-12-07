import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BooksTest
{
    Books testBook = new Books();

    @Test
    public void addQuery() throws Exception //the method returns the link with the query
    {
        String query = " "; //type in a query between the ""
        String link = "https://www.googleapis.com/books/v1/volumes?q="
                + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";

        assertEquals(testBook.addQuery(query), link);
    }

    @Test
    public void checkIfValidURL() throws Exception //the method checks if the link is a valid URL
    {
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q="); //place any link as the parameter

        assertEquals(testBook.checkIfValidURL(url), false); //test true or false
    }

    @Test
    public void checkIfResultsAvailable() throws Exception //the method checks if the API has data for that query
    {
        //enter a search after the 'q' and use '+' to act as spaces for your search
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=*");

        assertEquals(testBook.checkIfResultsAvailable(url), false); //test true or false
    }

    @Test
    public void makeURLConnection() throws Exception //void method, it makes an HttpURLConnection
    {
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q="); //enter any link
        testBook.makeURLConnection(url);
    }

    @Test
    public void parseData() throws Exception //the method takes data from the API and places it in a JSON object/array
    {
        Scanner readData;
        String inline = ""; //gets the JSON data and makes it a String
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=harry+potter" +
                "&startIndex=0&maxResults=5"); //enter a search after the 'q' and use '+' to act as spaces for your search

        readData = new Scanner(url.openStream()); //reads JSON data
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline); //parse the information from the API

        //for the method parameter to test
        JSONArray storedAPIData = (JSONArray) jObj.get("items"); //array stores data from "items" array

        assertEquals(testBook.parseData(url), storedAPIData);
    }

    @Test
    public void displaySearchResults() throws Exception //void method that shows the books' title, author, publishing company
    {
        Scanner readData;
        String inline = "";
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=hunger+games" +
                "&startIndex=0&maxResults=5"); //enter a search after the 'q' and use '+' to act as spaces for your search

        readData = new Scanner(url.openStream());
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline);

        //for the method parameter to test
        JSONArray dataFromAPI = (JSONArray) jObj.get("items");

        System.out.println("Here are 5 books matching your search:");
        System.out.println("");
        System.out.println("----------------------------------------------");

        for (int i = 0; i < dataFromAPI.size(); i++)
        {
            JSONObject specificJObj = (JSONObject)dataFromAPI.get(i);
            JSONObject volInfo = (JSONObject)specificJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title, author, and publisher
            JSONArray authorArr = (JSONArray)volInfo.get("authors"); //authors has its own array inside volumeInfo

            System.out.println("Title: " + volInfo.get("title"));
            System.out.println("Author: " + authorArr);
            System.out.println("Publisher: " + volInfo.get("publisher"));
            System.out.println("----------------------------------------------");
        }

        testBook.displaySearchResults(dataFromAPI);
    }

    @Test
    public void returnOnlyTitles() throws Exception //method only returns the 5 titles found for the query
    {
        JSONArray titlesList = new JSONArray();

        Scanner readData;
        String inline = "";
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=the+lightning+thief" +
                "&startIndex=0&maxResults=5"); //enter a search after the 'q' and use '+' to act as spaces for your search

        readData = new Scanner(url.openStream());
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline);

        //for the method parameter to test
        JSONArray dataFromAPI = (JSONArray) jObj.get("items");

        for (int i = 0; i < dataFromAPI.size(); i++)
        {
            JSONObject specificJObj = (JSONObject)dataFromAPI.get(i);
            JSONObject volInfo = (JSONObject)specificJObj.get("volumeInfo");
            titlesList.add(volInfo.get("title"));
        }

        assertEquals(testBook.returnOnlyTitles(dataFromAPI), titlesList);
    }

    /*@Test
    public void putInReadingList() throws Exception //method returns a JSONArray containing the book that was added
    {
        //select PutInReadingListTest.java to run the test for this method
    } */

    @Test
    public void displayReadingList() throws Exception //void method that shows what was added to the reading list
    {
        JSONArray testList = new JSONArray();
        testList.add("Harry Potter");
        testList.add("The Hunger Games");
        testList.add("The Lightning Thief");
        testList.add("Bluets");
        testList.add("The Shadow of the Wind");
        testList.add("Romeo and Juliet");

        testBook.displayReadingList(testList);
    }

}
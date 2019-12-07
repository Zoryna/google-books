import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BooksTest //TODO update README
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
        JSONArray storedAPIData = (JSONArray) jObj.get("items");

        testBook.displaySearchResults(storedAPIData);
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
    public void putInReadingList() throws Exception //TODO create test with main
    {
        Scanner keyboard = new Scanner(System.in);
        JSONArray readingList = new JSONArray();
        JSONArray titlesList = new JSONArray();

        titlesList.add("Harry Potter");
        titlesList.add("The Hunger Games");
        titlesList.add("The Lightning Thief");
        titlesList.add("Bluets");
        titlesList.add("The Shadow of the Wind");
        titlesList.add("Romeo and Juliet");


        System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list:");
        for (int i = 0; i < titlesList.size(); i++)
        {
            System.out.println(i + ". " + titlesList.get(i));
        }

        String bookChoice = keyboard.nextLine(); //a String to handle non-integer inputs
        int convertedToInt; //convert so it can later be added by index
        if ((bookChoice.equals("0")) || (bookChoice.equals("1")) || (bookChoice.equals("2")) || (bookChoice.equals("3")) || (bookChoice.equals("4")))
        {
            convertedToInt = Integer.parseInt(bookChoice); //validated input that will be converted so it can correspond with the index
            readingList.add(titlesList.get(convertedToInt)); //takes title from titlesList based on number/index chosen
            System.out.println("This is added to your reading list: " + readingList);
        }
        else
        {
            while (!(bookChoice.equals("0")) && !(bookChoice.equals("1")) && !(bookChoice.equals("2")) && !(bookChoice.equals("3")) && !(bookChoice.equals("4")))
            {
                System.out.println("Please type 0, 1, 2, 3, or 4");
                bookChoice = keyboard.nextLine();
            }
            //exits loop when valid number input, which can then be converted to correspond with the index
            convertedToInt = Integer.parseInt(bookChoice);
            readingList.add(titlesList.get(convertedToInt)); //takes title from titlesList based on number/index chosen
            System.out.println("This is added to your reading list: " + readingList);
        }

        assertEquals(testBook.putInReadingList(titlesList), );
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
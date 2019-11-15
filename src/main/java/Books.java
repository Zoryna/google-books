import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Books
{
    Scanner keyboard = new Scanner(System.in);
    String query;
    String inline;

    //adding a query to the link/also allows for multiple queries
    public String addQuery (String query) throws UnsupportedEncodingException, MalformedURLException
    {
        query = this.query;
        String link = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(query, "UTF-8"); //query is added to url
        link = link +  "&startIndex=0&maxResults=5&key=AIzaSyAI5Pn4IbnRRrHolRJ2SKGO2eHByl7Ua4I";
        return link;
    }

    public void displayResults(String inline)
    {
        try
        {
            inline = this.inline;
            JSONParser parse = new JSONParser();
            JSONObject jObj = (JSONObject)parse.parse(inline); //parse the information from the API
            JSONArray theJArray = (JSONArray)jObj.get("items"); //array stores data from "items" array

            System.out.println("Here are 5 books matching your search:");
            System.out.println("");

            //JSONArray titlesList = new JSONArray();
            for (int i = 0; i < theJArray.size(); i++)
            {
                JSONObject secondJObj = (JSONObject)theJArray.get(i);
                JSONObject volInfo = (JSONObject)secondJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title, author, and publisher
                JSONArray authorArr = (JSONArray)volInfo.get("authors");

                System.out.println("Title: " + volInfo.get("title"));
                //titlesList.add(volInfo.get("title")); //TODO fix unchecked call
                System.out.println("Author: " + authorArr);
                System.out.println("Publisher: " + volInfo.get("publisher"));
                System.out.println("----------------------------------------------");
            }
        }
        catch (ParseException e)
        {
            System.out.println("Parse exception");
        }
    }

    //adding books to the reading list and displays it
    public void addToReading(JSONArray titlesList)
    {
        String response = keyboard.nextLine().toLowerCase();

        if (response.equals("y")) //TODO allow users to make more queries and put more books in their reading list
        {
            System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list");
            for (int i = 0; i < titlesList.size(); i++)
                System.out.println(i + ". " + titlesList.get(i));
        } else
            System.out.println("Happy reading!");

        JSONArray readingList = new JSONArray();
        int bookChoice = keyboard.nextInt();
        readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number chosen
        System.out.println("Here is your reading list: " + readingList);
    }
}
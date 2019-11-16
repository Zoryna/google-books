import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Books
{
    Scanner keyboard = new Scanner(System.in);
    URL url;

    //query is added to url
    public String addQuery(String q) throws UnsupportedEncodingException
    {
        String link = "https://www.googleapis.com/books/v1/volumes?q=";
        link = link + URLEncoder.encode(q, "UTF-8") + "&startIndex=0&maxResults=5";

        System.out.println("Searching..."); //for users to be aware of the status of their search

        return link;
    }

    public void makeConnection (URL url) throws IOException
    {
        this.url = url;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        System.out.println("Connecting..."); //for users to be aware of the status of their search
    }

    public void displayResults(JSONArray theJArray)
    {
        System.out.println("Here are 5 books matching your search:");
        System.out.println("");

        for (int i = 0; i < theJArray.size(); i++)
        {
            JSONObject secondJObj = (JSONObject)theJArray.get(i);
            JSONObject volInfo = (JSONObject)secondJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title, author, and publisher
            JSONArray authorArr = (JSONArray)volInfo.get("authors");

            System.out.println("Title: " + volInfo.get("title"));
            System.out.println("Author: " + authorArr);
            System.out.println("Publisher: " + volInfo.get("publisher"));
            System.out.println("----------------------------------------------");
        }
    }

    public JSONArray returnTitles(JSONArray theJArray)
    {
        JSONArray titlesList = new JSONArray();

        for (int i = 0; i < theJArray.size(); i++)
        {
            JSONObject secondJObj = (JSONObject)theJArray.get(i);
            JSONObject volInfo = (JSONObject)secondJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title, author, and publisher
            titlesList.add(volInfo.get("title"));
        }

        return titlesList;
    }

    //adding books to the reading list and displays what was added
    public JSONArray addToReading(JSONArray titlesList, String response)
    {
        JSONArray readingList = new JSONArray();

        if (response.equals("y"))
        {
            System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list");
            for (int i = 0; i < titlesList.size(); i++)
                System.out.println(i + ". " + titlesList.get(i));

            int bookChoice = keyboard.nextInt();
            readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number chosen
            System.out.println("This is added to your reading list: " + readingList);
        }
        else
        {
            System.out.println("Happy reading!");
        }

        return readingList;
    }

    public void displayReadingList(JSONArray readingList)
    {
        System.out.println("Here is your reading list:");
        for (int i = 0; i < readingList.size(); i++)
        {
           System.out.println(readingList.get(i));
        }
    }
}
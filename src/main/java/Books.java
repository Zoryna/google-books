import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Books
{
    Scanner keyboard = new Scanner(System.in);

    //query is added to url
    public String addQuery() throws UnsupportedEncodingException
    {
        String link = "https://www.googleapis.com/books/v1/volumes?q=";

        System.out.println("Type in a book title that you want to search:");
        String q = keyboard.nextLine().toLowerCase();
        link = link + URLEncoder.encode(q, "UTF-8") + "&startIndex=0&maxResults=5";

        return link;
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

    //adding books to the reading list and displays it
    public void addToReading(JSONArray titlesList)
    {
        JSONArray readingList = new JSONArray();
        String response = keyboard.nextLine().toLowerCase();

        if (response.equals("y"))
        {
            System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list");
            for (int i = 0; i < titlesList.size(); i++)
                System.out.println(i + ". " + titlesList.get(i));

            int bookChoice = keyboard.nextInt(); //TODO fix input
            readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number chosen
            System.out.println("Here is your reading list: " + readingList);
        }
        else
        {
            System.out.println("Happy reading!");
        }
    }
}
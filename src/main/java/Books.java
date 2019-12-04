import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Books //TODO create tests
{
    //query is added to url
    public String addQuery(String query)
    {
        String link = "https://www.googleapis.com/books/v1/volumes?q=";

        try
        {
            link = link + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";
            System.out.println("Searching..."); //for users to be aware of the status of their search
        }
        catch (UnsupportedEncodingException unsopEncodExc)
        {
            unsopEncodExc.printStackTrace();
        }

        return link;
    }

    public boolean checkIfValidQuery(URL url) throws IOException //TODO check if valid query
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int responseCode = con.getResponseCode();

        System.out.println("Checking if query is valid..."); //for users to be aware of the status of their search
        if (HttpURLConnection.HTTP_BAD_REQUEST == responseCode)
        {
            System.out.println("Query is INVALID");
            return false;
        }
        else
        {
            System.out.println("Query is VALID");
            return true;
        }
    }

    public void makeURLConnection (URL url)
    {
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            System.out.println("Connecting..."); //for users to be aware of the status of their search
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public JSONArray parseData(URL url) throws IOException, ParseException
    {
        Scanner readData;
        String inline = ""; //gets the JSON data and makes it a String

        readData = new Scanner(url.openStream()); //reads JSON data
        while (readData.hasNext()) {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline); //parse the information from the API
        JSONArray storedAPIData = (JSONArray) jObj.get("items"); //array stores data from "items" array

        return storedAPIData;
    }

    public void displaySearchResults(JSONArray dataFromAPI)
    {
        System.out.println("Here are 5 books matching your search:");
        System.out.println("");

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
    }

    public JSONArray returnOnlyTitles(JSONArray dataFromAPI)
    {
        JSONArray titlesList = new JSONArray();

        for (int i = 0; i < dataFromAPI.size(); i++)
        {
            JSONObject specificJObj = (JSONObject)dataFromAPI.get(i);
            JSONObject volInfo = (JSONObject)specificJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title
            titlesList.add(volInfo.get("title"));
        }

        return titlesList;
    }

    //adding books to the reading list and displays what was added
    public JSONArray putInReadingList(JSONArray titlesList)
    {
        Scanner keyboard = new Scanner(System.in);
        JSONArray readingList = new JSONArray();

        System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list");
        for (int i = 0; i < titlesList.size(); i++)
        {
            System.out.println(i + ". " + titlesList.get(i));
        }
        int bookChoice = keyboard.nextInt();
        if ((bookChoice == 0) || (bookChoice == 1) || (bookChoice == 2) || (bookChoice == 3) || (bookChoice == 4))
        {
            readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number/index chosen
            System.out.println("This is added to your reading list: " + readingList);
        }

        else
        {
            while (!((bookChoice == 0) || (bookChoice == 1) || (bookChoice == 2) || (bookChoice == 3) || (bookChoice == 4)))
            {
                System.out.println("Please type 0, 1, 2, 3, or 4");
                bookChoice = keyboard.nextInt();
            }

            readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number/index chosen
            System.out.println("This is added to your reading list: " + readingList);
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
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

public class Books
{
    Scanner keyboard = new Scanner(System.in);
    JSONArray readingList = new JSONArray();

    //query is added to url
    public String addQuery(String query) throws UnsupportedEncodingException
    {
        String link = "https://www.googleapis.com/books/v1/volumes?q=";
        link = link + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";

        System.out.println("Processing..."); //for users to be aware of the status of their search

        return link;
    }

    //to check if the added query to the link makes it a valid URL
    public boolean checkIfValidURL(URL url) throws IOException
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int responseCode = con.getResponseCode();

        System.out.println("Checking if query was valid..."); //for users to be aware of the status of their search
        if (HttpURLConnection.HTTP_BAD_REQUEST == responseCode)
        {
            System.out.println("Query was INVALID");
            return false;
        }
        else
        {
            System.out.println("Query was VALID");
            return true;
        }
    }

    //if the query was valid, it checks if there are results
    public boolean checkIfResultsAvailable (URL url) throws IOException, ParseException
    {
        System.out.println("Checking for results..."); //for users to be aware of the status of their search

        Scanner readData;
        String inline = ""; //gets the JSON data and makes it a String

        readData = new Scanner(url.openStream()); //reads JSON data
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline); //parse the information from the API

        if (jObj.get("totalItems").toString().equals("0")) //no search results
        {
            System.out.println("There were no results for your search.");
            return false;
        }
        else
        {
            System.out.println("Results FOUND");
            return true;
        }
    }

    public void makeHTTPURLConnection (URL url) throws IOException
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        System.out.println("Connecting..."); //for users to be aware of the status of their search
    }

    //to read data from the API
    public JSONArray parseData(URL url) throws IOException, ParseException
    {
        Scanner readData;
        String inline = ""; //gets the JSON data and makes it a String

        readData = new Scanner(url.openStream()); //reads JSON data
        while (readData.hasNext())
        {
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
        String response;

        System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
        response = keyboard.nextLine().toLowerCase().trim();

        do //allow chance to enter valid boolean input for adding to reading list
        {
            if (response.equals("y")) //list shows for users to choose from
            {
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
                    System.out.println("This is added to your reading list: " + readingList.get(readingList.size()-1)); //last item from list
                }
                else //checks for valid book number input
                {
                    while (!(bookChoice.equals("0")) && !(bookChoice.equals("1")) && !(bookChoice.equals("2")) && !(bookChoice.equals("3")) && !(bookChoice.equals("4")))
                    {
                        System.out.println("Please type 0, 1, 2, 3, or 4");
                        bookChoice = keyboard.nextLine();
                    }
                    //exits loop when valid number input, which can then be converted to correspond with the index
                    convertedToInt = Integer.parseInt(bookChoice);
                    readingList.add(titlesList.get(convertedToInt)); //takes title from titlesList based on number/index chosen
                    System.out.println("This is added to your reading list: " + readingList.get(readingList.size()-1));
                }

                System.out.println("Do you want to add a different book from the list? Type 'Y' or 'N'");
                response = keyboard.nextLine().toLowerCase().trim();
                while (!(response.equals("y")) && !(response.equals("n"))) //checks for valid boolean input to view list to choose from
                {
                    System.out.println("Invalid input. Please try again.");
                    System.out.println("Do you want to add a different book from the list? Type 'Y' or 'N'");
                    response = keyboard.nextLine().toLowerCase().trim();
                }
            }
            else if (response.equals("n")) //don't want books from search
            {
                System.out.println("Maybe we can find a different book for you!");
            }
            else if (!(response.equals("y")) && !(response.equals("n"))) //checks for valid boolean input for adding to reading list
            {
                System.out.println("Invalid input. Please try again.");
                System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
                response = keyboard.nextLine().toLowerCase().trim();
            }

        } while((response.equals("y")) && !(response.equals("n")));

        return readingList;
    }

    public void displayReadingList()
    {
        if (readingList.isEmpty() == true)
        {
            System.out.println("Your reading list is currently empty!");
        }
        else
        {
            System.out.println("Here is your reading list:");
            for (int i = 0; i < readingList.size(); i++)
            {
                System.out.println(readingList.get(i));
            }
        }
    }
}
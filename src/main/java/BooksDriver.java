import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String args[]) throws IOException, ParseException
  {
    System.out.println("Type in a book title that you want to search:");
    Scanner keyboard = new Scanner(System.in);
    String q = keyboard.nextLine().toLowerCase();
    String link = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(q, "UTF-8"); //query is added to url
    link = link +  "&startIndex=0&maxResults=5&key=AIzaSyAI5Pn4IbnRRrHolRJ2SKGO2eHByl7Ua4I";
    URL url = new URL(link);

    //setting up connection/request
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    String inline = ""; //gets the JSON data and makes it a String
    Scanner sc = new Scanner(url.openStream()); //reads JSON data
    while (sc.hasNext())
    {
      inline += sc.nextLine();
    }
    sc.close();

    JSONParser parse = new JSONParser();
    JSONObject jObj = (JSONObject)parse.parse(inline); //parse the information from the API
    JSONArray theJArray = (JSONArray)jObj.get("items"); //array stores data from "items" array

    System.out.println("Here are 5 books matching your search:");
    System.out.println("");

    JSONArray titlesList = new JSONArray();
    for (int i = 0; i < theJArray.size(); i++)
    {
      JSONObject secondJObj = (JSONObject)theJArray.get(i);
      JSONObject volInfo = (JSONObject)secondJObj.get("volumeInfo"); //volumeInfo inside "items" and contains title, author, and publisher
      JSONArray authorArr = (JSONArray)volInfo.get("authors");

      System.out.println("Title: " + volInfo.get("title"));
      titlesList.add(volInfo.get("title"));
      System.out.println("Author: " + authorArr);
      System.out.println("Publisher: " + volInfo.get("publisher"));
      System.out.println("----------------------------------------------");
    }

    System.out.println("Do you want to save some of these books to your reading list? Type 'Y' or 'N");
    String response = keyboard.nextLine().toLowerCase();

    if (response.equals("y")) //TODO allow users to make more queries and put more books in their reading list
    {
      System.out.println("Which book do you want to save to your reading list? Type 0, 1, 2, 3, or 4 to add the corresponding book to your reading list");
      for (int i = 0; i < titlesList.size(); i++)
        System.out.println(i + ". " + titlesList.get(i));
    }
    else
      System.out.println("Happy reading!");

    JSONArray readingList = new JSONArray();
    int bookChoice = keyboard.nextInt();
    readingList.add(titlesList.get(bookChoice)); //takes title from titlesList based on number chosen
    System.out.println("Here is your reading list: " + readingList);
  }
}

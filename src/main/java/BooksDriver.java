/*
Coded by Janeen Soria

- Type in a query and display a list of 5 books matching that query
- Each item in the list should include the book's author, title, and publishing company

- A user should be able to select a book from the five displayed to save to a "Reading List" (use a Map or Arraylist)
- View a "Reading List" with all the books the user has selected from their queries -- this is a local reading list and not tied to Google Books's account features
*/

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.ArrayList;
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

    for (int i = 0; i < theJArray.size(); i++) //
    {
      JSONObject secondJObj = (JSONObject)theJArray.get(i);
      JSONObject volInfo = (JSONObject)secondJObj.get("volumeInfo"); //volumeInfo contains title, author, and publisher
      JSONArray authorArr = (JSONArray)volInfo.get("authors"); //authors section is an array in "items"

      System.out.println("Title: " + volInfo.get("title"));

      System.out.println("Author: " + authorArr);
      System.out.println("Publisher: " + volInfo.get("publisher"));
      System.out.println("----------------------------------------------");
    }

    /*
    ideas adding book to local reading list:
    create an JSON array where it stores the titles of the books
    key is number of the book, value is the title
    if the user wants to add that book to their reading list (another JSON array)
    type in the number and that title will be added in the other JSON array


     */




  }
}

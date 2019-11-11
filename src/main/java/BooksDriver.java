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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String args[]) throws ParseException
  {
    //setting up the link/query
    System.out.println("Type in a book title that you want to search:");
    Scanner keyboard = new Scanner(System.in);
    String q = keyboard.nextLine().toLowerCase();
    String link = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(q, "UTF-8"); //query is added to url
    link = link +  "&startIndex=0&maxResults=5&key=AIzaSyAI5Pn4IbnRRrHolRJ2SKGO2eHByl7Ua4I";
    URL url = new URL(link); //url now with query

    //setting up connection/request
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    //System.out.println("testing: " + link); //testing
    //System.out.println("Testing con: " + con);

    String inline = ""; //gets the JSON data and makes it a String
    Scanner sc = new Scanner(url.openStream()); //reads JSON data
    while (sc.hasNext()) //while there is more data next
    {
      inline += sc.nextLine(); //keep adding the data
    }
    System.out.println(inline); //prints out all the data
    sc.close();

    JSONParser parse = new JSONParser();
    JSONObject jObj = (JSONObject)parse.parse(inline); //to store String data as JSON objects
    JSONArray theJArray = (JSONArray)jObj.get("items"); //a JSON Array to hold/store them
    for (int i = 0; i < theJArray.size(); i++) //
    {
      JSONObject theJObj = (JSONObject)theJArray.get(i); //get the JSON objects from the JSON array
      //get specific results from the data under the "kind" component
      JSONArray specificArr = (JSONArray)jObj.get("volumeInfo"); //a section in "items"
      System.out.println("Info under volumeInfo" + jObj.get("title"));
      System.out.println("Info under volumeInfo" + jObj.get("authors"));
      System.out.println("Info under volumeInfo" + jObj.get("publisher"));

      for (int j = 0; j < specificArr.size(); j++)
      {
        JSONObject specificJObj = (JSONObject)specificArr.get(j);
        String author = (String)specificJObj.get("author");
        System.out.println(author);
        String title = (String)specificJObj.get("title");
        System.out.println(title);
        String publisher = (String)specificJObj.get("publisher");
        System.out.println(publisher);






      }
    }

  }
}

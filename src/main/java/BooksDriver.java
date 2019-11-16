import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BooksDriver //TODO how to compile and run through terminal commands, only able to run through run icon
{
  public static void main(String args[]) throws IOException, ParseException
  {
    Scanner keyboard = new Scanner(System.in);
    Books aBook = new Books();

    System.out.println("Type in a book title that you want to search:");
    String q = keyboard.nextLine().toLowerCase();
    URL url = new URL(aBook.addQuery(q));

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
    JSONArray theJArray = (JSONArray)jObj.get("items"); //array stores data from "items" array (main array)
    aBook.displayResults(theJArray);

    System.out.println("Do you want to save some of these books to your reading list? Type 'Y' or 'N");
    aBook.addToReading(aBook.returnTitles(theJArray));
  }
}
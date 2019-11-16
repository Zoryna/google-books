import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String args[]) throws IOException, ParseException
  {
    Scanner keyboard = new Scanner(System.in);
    Scanner sc;
    String searching;
    URL url;
    String q, response;
    JSONArray readingList = new JSONArray();
    Books aBook = new Books();

    do
    {
      System.out.println("Type in a book title that you want to search:");
      q = keyboard.nextLine().toLowerCase();
      url = new URL(aBook.addQuery(q));
      aBook.makeConnection(url);

      String inline = ""; //gets the JSON data and makes it a String
      sc = new Scanner(url.openStream()); //reads JSON data
      while (sc.hasNext())
      {
        inline += sc.nextLine();
      }
      sc.close();

      JSONParser parse = new JSONParser();
      JSONObject jObj = (JSONObject)parse.parse(inline); //parse the information from the API
      JSONArray theJArray = (JSONArray)jObj.get("items"); //array stores data from "items" array
      aBook.displayResults(theJArray);

      System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
      response = keyboard.nextLine().toLowerCase();
      readingList.add(aBook.addToReading(aBook.returnTitles(theJArray), response));

      System.out.println("Do you want to search for another book? Type 'Y' or 'N'");
      searching = keyboard.nextLine().toLowerCase();
    }
    while (searching.equals("y"));


    aBook.displayReadingList(readingList);
  }
}
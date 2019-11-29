import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String args[]) throws IOException
  {
    Scanner keyboard = new Scanner(System.in);
    Scanner readData;
    String keepSearching;
    URL url;
    String query, response;
    JSONArray readingList = new JSONArray();
    Books aBook = new Books();

    do
    {
      System.out.println("Type in a book title that you want to search:");
      query = keyboard.nextLine().toLowerCase();
      url = new URL(aBook.addQuery(query));
      aBook.makeURLConnection(url);

      /*String inline = ""; //gets the JSON data and makes it a String //TODO separate this
      readData = new Scanner(url.openStream()); //reads JSON data
      while (readData.hasNext())
      {
        inline += readData.nextLine();
      }
      readData.close(); */

      /*JSONParser parse = new JSONParser(); //TODO separate this
      JSONObject jObj = (JSONObject)parse.parse(inline); //parse the information from the API
      JSONArray theJArray = (JSONArray)jObj.get("items"); //array stores data from "items" array
      aBook.displaySearchResults(theJArray); */

      System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
      response = keyboard.nextLine().toLowerCase();
      if (response.equals("y"))
      {
        readingList.add(aBook.addToReadingList(aBook.returnOnlyTitles(theJArray), response));
      }
      else
      {
        System.out.println("See you next time!");
      }


      System.out.println("Do you want to search for another book? Type 'Y' or 'N'");
      keepSearching = keyboard.nextLine().toLowerCase(); //determines if stay in loop
    }
    while (keepSearching.equals("y"));


    aBook.displayReadingList(readingList);
  }
}
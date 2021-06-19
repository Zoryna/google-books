import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String[] args) throws IOException, ParseException
  {
    Scanner keyboard = new Scanner(System.in);
    String keepSearching;
    URL url;
    Books aBook = new Books();
    JSONArray justTheBookTitles = new JSONArray();

    try //if there is internet connection
    {
      do //allows to continue searching if users want to
      {
        url = new URL(aBook.addQuery());

        if (aBook.checkIfValidURL(url) == true && aBook.checkIfResultsAvailable(url) == true) //URL must be valid and then have available results
        {
          aBook.makeHTTPURLConnection(url);

          //made returned JSONArray methods its own array so it's easier to read and put into parameters
          JSONArray storedAPIData = aBook.parseData(url);
          aBook.displaySearchResults(storedAPIData);
          justTheBookTitles = aBook.returnOnlyTitles(storedAPIData);

          aBook.putInReadingList(justTheBookTitles); //go through search result and choose to put in reading list
        }
        else
          System.out.println("Please try a different search.");

        do //allow to enter a valid input for continuing to search/want to search another book
        {
          System.out.println("Do you want to search for another book? Type 'Y' or 'N'");
          keepSearching = keyboard.nextLine().toLowerCase().trim();
        }
        while (!(keepSearching.equals("y")) && !(keepSearching.equals("n")));

        if (keepSearching.equals("n"))
          System.out.println("See you next time!");
      }
      while (keepSearching.equals("y"));

      aBook.displayReadingList();
    }
    catch (UnknownHostException unkHostExc)
    {
      System.out.println("Not connected to internet. Please connect to the internet and try again.");
    }
  }
}
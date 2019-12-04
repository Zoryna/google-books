import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class BooksDriver
{
  public static void main(String args[]) throws IOException, ParseException
  {
    Scanner keyboard = new Scanner(System.in);
    String keepSearching;
    URL url;
    String query, response;
    JSONArray readingList = new JSONArray();
    Books aBook = new Books();

    try //to handle cases when user has no internet
    {
      do
      {
        System.out.println("Type in a book title that you want to search:");
        query = keyboard.nextLine().toLowerCase();
        url = new URL(aBook.addQuery(query));

        if (aBook.checkIfValidQuery(url) == true)
        {
          aBook.makeURLConnection(url);

          //made returned JSONArray methods its own array so it's easier to read and put into parameters
          JSONArray storedAPIData = aBook.parseData(url);
          aBook.displaySearchResults(storedAPIData);
          JSONArray justTheBookTitles = aBook.returnOnlyTitles(storedAPIData);

          System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
          response = keyboard.nextLine().toLowerCase();
          if (response.equals("y"))
          {
            readingList.add(aBook.putInReadingList(justTheBookTitles)); //choose from selection of titles, then chosen title is added to readingList
          }
          else
          {
            System.out.println("Happy browsing!");
          }
        }

        System.out.println("Do you want to search for another book? Type 'Y' or 'N'"); //allows to keep searching or enter a valid query
        keepSearching = keyboard.nextLine().toLowerCase();
      }
      while (keepSearching.equals("y"));

      aBook.displayReadingList(readingList);
    }
    catch (UnknownHostException unkHostExc)
    {
      System.out.println("Not connected to internet. Please connect to the internet and try again.");
    }
  }
}
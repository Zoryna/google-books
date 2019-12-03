import java.io.IOException;
import java.net.URL;
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

    do
    {
      System.out.println("Type in a book title that you want to search:");
      query = keyboard.nextLine().toLowerCase();
      url = new URL(aBook.addQuery(query));
      aBook.makeURLConnection(url);

      JSONArray storedAPIData = aBook.parseData(url); //make it its own array so it's easier to read and put into parameters
      aBook.displaySearchResults(storedAPIData);

      System.out.println("Do you want to save one of these books to your reading list? Type 'Y' or 'N'");
      response = keyboard.nextLine().toLowerCase();
      if (response.equals("y"))
      {
        readingList.add(aBook.addToReadingList(aBook.returnOnlyTitles(storedAPIData), response)); //TODO make more readable
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
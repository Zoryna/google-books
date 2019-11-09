/*
/*
- Type in a query and display a list of 5 books matching that query. (HTTP GET)
- Each item in the list should include the book's author, title, and publishing company. (HTTP GET)

- A user should be able to select a book from the five displayed to save to a "Reading List" (use a Map or Arraylist)
- View a "Reading List" with all the books the user has selected from their queries -- this is a local reading list and not tied to Google Books's account features.
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BooksDriver
{
  public static void main(String args[]) throws IOException {

    URL url = new URL("https://api.github.com/users/google"); //testing
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(
        con.getInputStream()));
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      System.out.println(inputLine);
    }

    in.close();
  }
}

/*
Coded by Janeen Soria

- Type in a query and display a list of 5 books matching that query. (HTTP GET)
- Each item in the list should include the book's author, title, and publishing company. (HTTP GET)

- A user should be able to select a book from the five displayed to save to a "Reading List" (use a Map or Arraylist)
- View a "Reading List" with all the books the user has selected from their queries -- this is a local reading list and not tied to Google Books's account features.
*/

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BooksDriver
{
  public static void main(String args[]) throws IOException {

    //AIzaSyAI5Pn4IbnRRrHolRJ2SKGO2eHByl7Ua4I (API key)

    Scanner keyboard = new Scanner (System.in);
    String q = keyboard.nextLine().toLowerCase(); //user enters query
    String link = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(q, "UTF-8"); //query is added to url
    URL url = new URL(link); //url now with query

    HttpURLConnection con = (HttpURLConnection) url.openConnection(); //the actual connection
    con.setRequestMethod("GET"); //the type of request-getting information from the API

    //BufferedReader and InputStreamReader that allows characters to be readable
    //parameter is the connection getting the data

    BufferedReader bufferR = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    while ((inputLine = bufferR.readLine()) != null) //while inputLine that equals the buffer reading the text IS NOT null
    {
      System.out.println(inputLine); //print the data
    }

    bufferR.close(); //closes the stream that is releasing the resources











  }
}

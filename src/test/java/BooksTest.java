import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class BooksTest extends TestCase {

    URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=harry+potter&startIndex=0&maxResults=5"); //test url

    Books testBook = new Books();
    JSONArray testArray = testBook.parseData(url);

    JSONArray testTitlesList = new JSONArray();

    public BooksTest() throws IOException, ParseException {
    }

    public void testAddQuery() throws UnsupportedEncodingException
    {
        String query = "harry potter"; //test input

        String link = "https://www.googleapis.com/books/v1/volumes?q=";
        link = link + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";

        String testLink = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(query, "UTF-8") + "&startIndex=0&maxResults=5";

        assertEquals(link, testLink);
    }

    public void testCheckIfValidURL() throws IOException
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int responseCode = con.getResponseCode();

        //if equal to each other then invalid query
        assertNotSame(responseCode, HttpURLConnection.HTTP_BAD_REQUEST);
    }

    public void testCheckIfResultsAvailable() throws IOException, ParseException
    {
        Scanner readData;
        String inline = "";

        readData = new Scanner(url.openStream());
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline);

        //if equal then no results
        assertNotSame(jObj.get("totalItems").toString(), "0");
    }

    public void testMakeHTTPURLConnection() throws IOException
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        assertEquals(con.getRequestMethod(), "GET");
    }

    public void testParseData() throws IOException, ParseException
    {
        Scanner readData;
        String inline = "";

        readData = new Scanner(url.openStream());
        while (readData.hasNext())
        {
            inline += readData.nextLine();
        }
        readData.close();

        JSONParser parse = new JSONParser();
        JSONObject jObj = (JSONObject) parse.parse(inline);
        JSONArray storedAPIData = (JSONArray) jObj.get("items");

        assertEquals(storedAPIData, (JSONArray) jObj.get("items"));
    }

    public void testDisplaySearchResults()
    {
        for (int i = 0; i < testArray.size(); i++)
        {
            JSONObject specificJObj = (JSONObject)testArray.get(i);
            JSONObject volInfo = (JSONObject)specificJObj.get("volumeInfo");
            JSONArray authorArr = (JSONArray)volInfo.get("authors");

            assertEquals(volInfo, (JSONObject)specificJObj.get("volumeInfo"));
            assertEquals(authorArr, volInfo.get("authors"));
        }
    }

    public void testReturnOnlyTitles()
    {
        for (int i = 0; i < testArray.size(); i++)
        {
            JSONObject specificJObj = (JSONObject)testArray.get(i);
            JSONObject volInfo = (JSONObject)specificJObj.get("volumeInfo");
            testTitlesList.add(volInfo.get("title"));

            assertEquals(testTitlesList.get(i), volInfo.get("title"));
        }
    }

    public void testPutInReadingList()
    {
        testTitlesList.add("A title");

        JSONArray testReadingList = new JSONArray();
        testReadingList.add(testTitlesList.get(0));

        assertEquals(testReadingList.get(0), testTitlesList.get(0));
    }

    public void testDisplayReadingList()
    {
        testArray.add("Input 1");
        testArray.add("Input 2");
        testArray.add("Input 3");

        for (int i = 0; i < testArray.size(); i++)
        {
            assertEquals(testArray.get(i), testArray.get(i));
        }
    }
}
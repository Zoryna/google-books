import org.json.simple.JSONArray;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class PutInReadingListTest //method returns a JSONArray containing the book that was added
{
    @Test
    public static void main(String[] args) throws Exception
    {
        JSONArray titlesList = new JSONArray();
        Books testBook = new Books();

        titlesList.add("Harry Potter");
        titlesList.add("The Hunger Games");
        titlesList.add("The Lightning Thief");
        titlesList.add("Bluets");
        titlesList.add("The Shadow of the Wind");
        titlesList.add("Romeo and Juliet");

        testBook.putInReadingList(titlesList);
    }
}
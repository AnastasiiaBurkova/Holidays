package source;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonFileDataParser
{

    private static String timeFormat = "yyyy-MM-dd";

    /**
     * Reads json from the file, iterates through the json list and
     * returns an array list of dates.
     *
     * @param filePath path to country dir.
     * @param value    value in the json file.
     * @return list of dates.
     */
    public List<LocalDate> parseFileData( String filePath, String value )
    {
        JSONParser parser = new JSONParser();
        List<LocalDate> dates = new ArrayList<>();
        try
        {
            Object obj = parser.parse( new FileReader( filePath ) );
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray list = (JSONArray)jsonObject.get( value );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( timeFormat );
            Iterator<String> iterator = list.iterator();

            while( iterator.hasNext() )
            {
                dates.add( LocalDate.parse( iterator.next(), formatter ) );
            }
            return dates;
        }
        catch( Exception e )
        {
            throw new IllegalArgumentException( e.getMessage() );
        }
    }
}

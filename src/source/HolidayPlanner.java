package source;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HolidayPlanner
{

    /**
     * Path to the countries directory.
     */
    public static String selectedCountry = "/Users/aburkova/Code/HolidayPlanner/src/resources/";

    /**
     * Json value of the holiday array.
     */
    public static String countryHolidays = "countryHolidays";
    /**
     * Time format.
     */
    public static String timeFormat = "yyyy-MM-dd";

    /**
     * Fetches a start date, an end date and a country, estimates the number of holidays and
     * the number of work days and returns the final number of days the person should take
     * to be on holidays during the given time period.
     *
     * @param startDate start date of the time span.
     * @param endDate   end date of the time span.
     * @param country   selected country.
     * @return a number of holiday days the person should take.
     */
    public int getNumOfDays( String startDate, String endDate, String country )
    {

        if( startDate == null || endDate == null || country == null )
        {
            throw new IllegalArgumentException( "The required parameters for the start process are missing! " +
              "Start date: " + startDate + ". End date: " + endDate + ". Country: " + country );
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( timeFormat );
        LocalDate firstDate;
        LocalDate secondDate;

        try
        {
            firstDate = LocalDate.parse( startDate, formatter );
            secondDate = LocalDate.parse( endDate, formatter );
        }
        catch( Exception e )
        {
            throw e;
        }

        if( firstDate.isAfter( secondDate ) )
        {
            throw new IllegalArgumentException( "The first date cannot be after the second date" );
        }
        else if( ( firstDate.isBefore( LocalDate.parse( "2020-04-01" ) ) ||
          ( secondDate.isAfter( LocalDate.parse( "2021-03-31" ) ) ) ) )
        {
            throw new IllegalArgumentException( "The given time period is invalid." );
        }
        else if( daysInTimeSpan( firstDate, secondDate ).size() > 50 )
        {
            throw new IllegalArgumentException( "The time span is too big." );
        }

        List<LocalDate> allDays = daysInTimeSpan( firstDate, secondDate );
        List<LocalDate> holidays = parseFileData( selectedCountry + country + ".json", countryHolidays );

        List<LocalDate> common = new ArrayList<>( allDays );
        common.retainAll( holidays );

        List<Integer> workDays = new ArrayList<>();
        for( int i = 0; i < allDays.size(); i++ )
        {
            Calendar date = Calendar.getInstance();
            date.setTime( Date.from( allDays.get( i ).atStartOfDay( ZoneId.systemDefault() ).toInstant() ) );
            int dayOfWeek = date.get( Calendar.DAY_OF_WEEK );

            if( dayOfWeek != 1 )
            {
                workDays.add( dayOfWeek );
            }
        }

        int numOfWorkDays = workDays.size();
        int numOfHolidays = common.size();

        return numOfWorkDays - numOfHolidays;

    }

    /**
     * Looks for the number of dates in the time span and
     * returns a list of all dates.
     *
     * @param startDate start date of the time span.
     * @param endDate   end date of the time span.
     * @return a list of dates in the time span.
     */
    private static List<LocalDate> daysInTimeSpan( LocalDate startDate, LocalDate endDate )
    {
        long numOfDays = ChronoUnit.DAYS.between( startDate, endDate ) + 1;

        return IntStream.iterate( 0, i -> i + 1 )
          .limit( numOfDays )
          .mapToObj( i -> startDate.plusDays( i ) )
          .collect( Collectors.toList() );
    }

    /**
     * Reads json from the file, iterates through the json list and
     * returns an array list of holidays' dates.
     *
     * @param filePath path to country dir.
     * @param value    value in the json file.
     * @return list of country's holidays' dates.
     */
    private static List<LocalDate> parseFileData( String filePath, String value )
    {
        JSONParser parser = new JSONParser();
        List<LocalDate> holidays = new ArrayList<>();
        try
        {
            Object obj = parser.parse( new FileReader( filePath ) );
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray list = (JSONArray)jsonObject.get( value );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( timeFormat );
            Iterator<String> iterator = list.iterator();

            while( iterator.hasNext() )
            {
                holidays.add( LocalDate.parse( iterator.next(), formatter ) );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        return holidays;
    }

}
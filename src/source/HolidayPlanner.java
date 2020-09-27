package source;

import java.io.File;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HolidayPlanner
{

    private static String pathToJsonFiles = "src/resources/";

    private static String jsonValOfHolidayArray = "countryHolidays";

    private static int maxNumOfDaysInTheTimespan = 50;

    private static int sunday = 1;

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
    public int getNumOfDays( LocalDate startDate, LocalDate endDate, String country )
    {

        if( startDate == null || endDate == null || country == null )
        {
            throw new IllegalArgumentException( "The required parameters for the start process are missing! " +
              "Start date: " + startDate + ". End date: " + endDate + ". Country: " + country );
        }

        int currentYear = Calendar.getInstance().get( Calendar.YEAR );
        int currentMonth = Calendar.getInstance().get( Calendar.MONTH );

        if( currentMonth < Calendar.APRIL )
        {
            currentYear = currentYear - 1;
        }

        LocalDate startOfBusinessYear = MonthDay.of( 4, 1 ).atYear( currentYear );
        LocalDate endOfBusinessYear = MonthDay.of( 3, 31 ).atYear( currentYear + 1 );

        if( startDate.isAfter( endDate ) )
        {
            throw new IllegalArgumentException( "The first date cannot be after the second date" );
        }
        else if( startDate.isBefore( startOfBusinessYear ) || endDate.isAfter( endOfBusinessYear ) )
        {
            throw new IllegalArgumentException( "The given time period is invalid (" + startDate + ", " + endDate + ")." );
        }
        else if( daysInTimeSpan( startDate, endDate ).size() > maxNumOfDaysInTheTimespan )
        {
            throw new IllegalArgumentException( "The time span between " + startDate + " and " + endDate + " is too big."
              + " Shouldn't exceed " + maxNumOfDaysInTheTimespan + " days." );
        }

        File jsonCountryFile = new File( pathToJsonFiles + country + ".json" );

        JsonFileDataParser parser = new JsonFileDataParser();
        List<LocalDate> holidays = parser.parseFileData( jsonCountryFile.getAbsolutePath(), jsonValOfHolidayArray );
        List<LocalDate> allDays = daysInTimeSpan( startDate, endDate );

        List<LocalDate> listOfHolidays = new ArrayList<>( allDays );
        listOfHolidays.retainAll( holidays );

        List<Integer> workDays = new ArrayList<>();
        for( int i = 0; i < allDays.size(); i++ )
        {
            LocalDate oneDayFromList = allDays.get( i );
            Date localDateToDate = Date.from( oneDayFromList.atStartOfDay( ZoneId.systemDefault() ).toInstant() );

            Calendar date = Calendar.getInstance();
            date.setTime( localDateToDate );

            int dayOfWeek = date.get( Calendar.DAY_OF_WEEK );

            if( dayOfWeek != sunday )
            {
                workDays.add( dayOfWeek );
            }
        }

        int numOfWorkDays = workDays.size();
        int numOfHolidays = listOfHolidays.size();

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

}
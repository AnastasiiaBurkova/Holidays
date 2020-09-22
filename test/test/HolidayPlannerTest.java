package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import source.HolidayPlanner;


class HolidayPlannerTest
{

    @Test
    public void testDates()
    {

        HolidayPlanner holiday = new HolidayPlanner();
        int resultWithSaturday = holiday.getNumOfDays( "2020-10-05", "2020-10-10", "FI" );
        int resultWithSunday = holiday.getNumOfDays( "2020-10-05", "2020-10-11", "FI" );
        int resultWithHolidays = holiday.getNumOfDays( "2021-01-01", "2021-01-11", "FI" );
        assertEquals( resultWithSaturday, 6 );
        assertEquals( resultWithSunday, 6 );
        assertEquals( resultWithHolidays, 7 );

    }

    @Test
    public void testStartDateIsNull()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( null, "2020-08-05", "FI" );
            }
        } );
    }

    @Test
    public void testEndDateIsNull()
    {

        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-05-05", null, "FI" );
            }
        } );
    }

    @Test
    public void testCountryIsNull()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-05-05", "2020-05-07", null );
            }
        } );
    }

    @Test
    public void testStartDateFormat()
    {
        assertThrows( Exception.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "20", "2020-08-03", "FI" );
            }
        } );
    }

    @Test
    public void testEndDateFormat()
    {
        assertThrows( Exception.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-08-03", "/abc", "FI" );
            }
        } );
    }

    @Test
    public void testStartDateLimit()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-03-31", "2020-04-02", "FI" );
            }
        } );
    }

    @Test
    public void testEndDateLimit()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2021-03-05", "2021-04-01", "FI" );
            }
        } );
    }

    @Test
    public void testCompareDates()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-08-05", "2020-08-03", "FI" );
            }
        } );
    }

    @Test
    public void testTimeSpanLength()
    {

        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( "2020-05-05", "2020-08-05", "FI" );
            }
        } );
    }


}

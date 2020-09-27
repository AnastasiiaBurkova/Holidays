package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import source.HolidayPlanner;


class HolidayPlannerExceptionTest
{

    @Test
    public void check_IfStartDateIsNull_ExceptionThrown()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( null, LocalDate.of( 2020, 8, 5 ), "FI" );
            }
        } );
    }

    @Test
    public void check_IfEndDateIsNull_ExceptionThrown()
    {

        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2020, 5, 5 ), null, "FI" );
            }
        } );
    }

    @Test
    public void check_IfCountryIsNull_ExceptionThrown()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2020, 5, 5 ), LocalDate.of( 2020, 5, 7 ), null );
            }
        } );
    }

    @Test
    public void check_IfStartDateIsBefore1stOfApril_ExceptionThrown()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2020, 3, 31 ), LocalDate.of( 2020, 4, 2 ), "FI" );
            }
        } );
    }

    @Test
    public void check_IfEndDateIsAfter31stOfMarch_ExceptionThrown()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2021, 3, 5 ), LocalDate.of( 2021, 4, 1 ), "FI" );
            }
        } );
    }

    @Test
    public void check_IfStartDateIsBeforeEndDate_ExceptionThrown()
    {
        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2020, 8, 5 ), LocalDate.of( 2020, 8, 3 ), "FI" );
            }
        } );
    }

    @Test
    public void check_IfTimeSpanIsBiggerThan50Days_ExceptionThrown()
    {

        assertThrows( IllegalArgumentException.class, new Executable()
        {

            @Override
            public void execute()
              throws Throwable
            {
                HolidayPlanner holiday = new HolidayPlanner();
                holiday.getNumOfDays( LocalDate.of( 2020, 5, 5 ), LocalDate.of( 2020, 8, 5 ), "FI" );
            }
        } );
    }


}

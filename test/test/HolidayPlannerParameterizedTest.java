package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import source.HolidayPlanner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;


@RunWith( Parameterized.class )
public class HolidayPlannerParameterizedTest
{
    private LocalDate startDate;
    private LocalDate endDate;
    private String country;
    private int expectedNumOfHolidays;
    private HolidayPlanner planner;

    public HolidayPlannerParameterizedTest( LocalDate startDate, LocalDate endDate,
                                            String country, int expectedNumOfHolidays )
    {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.country = country;
        this.expectedNumOfHolidays = expectedNumOfHolidays;

    }

    @Before
    public void initialize()
    {
        planner = new HolidayPlanner();
    }

    @Parameterized.Parameters
    public static Collection input_StartDateEndDateCountryExpectedNumOfHolidays()
    {
        return Arrays.asList( new Object[][]{
          {LocalDate.of( 2020, 10, 5 ), LocalDate.of( 2020, 10, 10 ),
            "FI", 6},
          {LocalDate.of( 2020, 10, 5 ), LocalDate.of( 2020, 10, 11 ),
            "FI", 6},
          {LocalDate.of( 2021, 1, 1 ), LocalDate.of( 2021, 1, 11 ),
            "FI", 7}
        } );

    }

    @Test
    public void check_IfExpectedNumOfHolidays_IsCorrect()
    {
        assertEquals( expectedNumOfHolidays, planner.getNumOfDays( startDate, endDate, country ) );
    }

}

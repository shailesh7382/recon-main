package scb.recontool.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateUtil {
	// thread safe
	private static final DateTimeFormatter formatter ;
	static{
		formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu", Locale.UK)
        .withResolverStyle(ResolverStyle.STRICT);
	}
	
	
	/**
	 * @param source
	 * @return
	 * @throws DateTimeParseException
	 */
	public static LocalDate convertDate(String source) throws DateTimeParseException {
			return LocalDate.parse(source, formatter);
	}

	/**
	 * Posting Date absolute difference is no more than 1 working day (excluding weekends). For
	 *	example, difference between date falling on Friday and the one falling on the following Saturday,
	 *	Sunday or Monday is 1 day
	 * @param firstDateString
	 * @param secondDateString
	 * @return
	 */
	public static boolean isWithinADay(String firstDateString, String secondDateString) {
		LocalDate firstDate = LocalDate.parse(firstDateString, formatter);
        LocalDate secondDate = LocalDate.parse(secondDateString, formatter);
        
        if(ChronoUnit.DAYS.between(firstDate, secondDate) > 4){
        	return false;
        }
        else if(ChronoUnit.DAYS.between(firstDate, secondDate) == 0)
        {
        	return true;
        }
        DayOfWeek first = firstDate.getDayOfWeek();
        DayOfWeek second = secondDate.getDayOfWeek();
        if(firstDate.isAfter(secondDate)){
        	if(first == DayOfWeek.MONDAY){
        		return second == DayOfWeek.SUNDAY 
        		|| second == DayOfWeek.SATURDAY
        		|| second == DayOfWeek.FRIDAY;
        	}
        	else if(first == DayOfWeek.SUNDAY){
        		return second == DayOfWeek.SATURDAY
                		|| second == DayOfWeek.FRIDAY;
        	}
        }
        else {
        	return isWithinADay(secondDateString, firstDateString); // reverse
        }
        
        if(ChronoUnit.DAYS.between(secondDate, firstDate) > 1){
        	return false;
        }
        return true;
    }
	
}

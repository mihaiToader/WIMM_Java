package Utils;

import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import Validator.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Mihai on 07.01.2017.
 */
public class DateUtil {

    private static LocalTime getLocalTime(String time) throws WrongInput {
        String hour;
        String minute;
        String second;

        String []hourMinuteSecond = time.split(":");
        if (3 != hourMinuteSecond.length)
        {
            throw  new WrongInput("Invalid time format");
        }
        else {
            hour = hourMinuteSecond[0];
            minute = hourMinuteSecond[1];
            second = hourMinuteSecond[2].split("\\.")[0];
            String errors = Validator.validateTime(hour,minute,second);
            if (!errors.equals("")) throw new WrongInput(errors);
            return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
        }

    }

    private static LocalDate getLocalDate(String date) throws WrongInput {

        String []yearsMonthDays = date.split("-");

        String year;
        String month;
        String day;

        if (3 != yearsMonthDays.length)
        {
            throw  new WrongInput("Invalid date format");
        }
        else
        {
            year = yearsMonthDays[0];
            month = yearsMonthDays[1];
            day = yearsMonthDays[2];
            String errors = Validator.validateDate(year,month,day);
            if (!errors.equals("")) throw new WrongInput(errors);
            return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }
    }


    public static LocalDateTime fromStringToLocalDateTime(String string) throws WrongInput {
        String dateAndTime[] = string.split(" ");
        if (2 == dateAndTime.length) {
            String date = dateAndTime[0];
            String time = dateAndTime[1];
            return LocalDateTime.of(getLocalDate(date), getLocalTime(time));
        }
        throw new WrongInput("Invalid date time");
    }
}

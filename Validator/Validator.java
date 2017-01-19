package Validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Validator {

    @SuppressWarnings("all")
    public static String validateInt(String id, String message){
        try{
            Integer.parseInt(id);
            return "";
        }catch(NumberFormatException e){
            return message+"\n";
        }
    }

    @SuppressWarnings("all")
    public static String validateAmount(String amount, String message){
        try{
            Double.parseDouble(amount);
            return "";
        }catch(NumberFormatException e){
            return message + "\n";
        }
    }

    public static String validateDate(String year, String month, String day){
        String errors = Validator.validateInt(year, "Year has to be int!") +
                Validator.validateInt(month, "Month has to be int!") +
                Validator.validateInt(day, "Day has to be int");
        if (errors.equals(""))
        {
            errors += validateDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }
        return errors;
    }

    public static String validateTime(String hour, String minute, String seconds){
        String errors = Validator.validateInt(hour, "Hour has to be int!") +
                Validator.validateInt(minute, "Minute has to be int!") +
                Validator.validateInt(seconds, "Seconds have to be int");
        if (errors.equals(""))
        {
            errors += validateTime(Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(seconds));
        }
        return errors;
    }


    @SuppressWarnings("all")
    public static String validateDate(Integer year, Integer month, Integer day){
        try{
            LocalDate.of(year,month,day);
            return "";
        }catch(DateTimeException e){
            return e.getMessage()+"\n";
        }
    }

    @SuppressWarnings("all")
    public static String validateTime(Integer hour, Integer minute, Integer second){
        try{
            LocalTime.of(hour,minute,second);
            return "";
        }catch(DateTimeException e){
            return e.getMessage()+"\n";
        }
    }

}

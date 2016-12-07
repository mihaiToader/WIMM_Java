package Validator;

import java.time.DateTimeException;
import java.time.LocalDate;

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
            LocalDate.of(hour,minute,second);
            return "";
        }catch(DateTimeException e){
            return e.getMessage()+"\n";
        }
    }

}

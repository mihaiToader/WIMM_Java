package Validator;

import Exceptions.WrongInput;

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
            Double a = Double.parseDouble(amount);
            if (amount.split("\\.").length == 2){
                if (amount.split("\\.")[1].length()>2){
                    return "Only 2 decimals allowed!";
                }
            }
            if (a < 0){
                return "Amount cann't be negative!";
            }
            return "";
        }catch(NumberFormatException e){
            return message + "\n";
        }
    }

    public static Double validateAmount(String amount) throws WrongInput {
        if (validateAmount(amount, "message").equals("")){
            return Double.parseDouble(amount);
        }
        throw new WrongInput(validateAmount(amount, "Invalid Amount"));
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

    public static String validateTime(String time){
        String timeElements[] = time.split(":");
        if (timeElements.length == 3){
            if (timeElements[2].split(".").length == 0)
                return validateTime(timeElements[0],timeElements[1],timeElements[2]);
            else
                return validateTime(timeElements[0],timeElements[1],timeElements[2].split(".")[0]);
        }else if (timeElements.length == 2){
            return validateTime(timeElements[0],timeElements[1],"0");
        }
        return "Invalid format time!";
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

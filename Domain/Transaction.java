package Domain;

import Exceptions.WrongInputTranzaction;
import Validator.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction implements HasId
{
    private Integer id;
    private Integer idMoneyPlace;
    private Double amount;
    private String type;
    private String description;
    private LocalDateTime date;

    public Transaction()
    {
       this(null,null,null,null,null,null);
    }

    private Transaction(Integer id,
                        Integer idMoneyPlace,
                        Double amount,
                        String type,
                        String description,
                        LocalDateTime date)
    {
        this.id = id;
        this.idMoneyPlace = idMoneyPlace;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = date;
    }

    private Transaction(Integer id,
                       Integer idMoneyPlace,
                       Double amount,
                       String type,
                       String description,
                       Integer year,
                       Integer month,
                       Integer day,
                       Integer hour,
                       Integer minute,
                       Integer second)
    {
       this(id,idMoneyPlace,amount,type,description, LocalDateTime.of(LocalDate.of(year,month,day), LocalTime.of(hour,minute,second)));
    }

    private String validateType(String type){
        if (type.equals("input") || type.equals("output")){
            return "";
        }
        return "Type has to be input or output";
    }


    public Transaction getTranzaction(String idString,
                                      String idMoneyPlaceString,
                                      String amountString,
                                      String type,
                                      String description,
                                      String yearString,
                                      String monthString,
                                      String dayString,
                                      String hourString,
                                      String minuteString,
                                      String secondString)
                               throws WrongInputTranzaction{
        String errors = "";
        errors += Validator.validateInt(idString,"Id has to be a number!") +
                Validator.validateAmount(amountString, "Amount has to be a real number!") +
                Validator.validateInt(idMoneyPlaceString, "Id money place has to be a number!") +
                validateType(type) +
                Validator.validateInt(yearString, "Year has to be a number!") +
                Validator.validateInt(monthString, "Month has to be a number!") +
                Validator.validateInt(dayString, "Day has to be a number!") +
                Validator.validateInt(hourString, "Hour has to be a number!") +
                Validator.validateInt(minuteString, "Second has to be a number!") +
                Validator.validateInt(secondString, "Minute has to be a number!");

        if (errors.equals("")){
            Integer year = Integer.parseInt(yearString);
            Integer month = Integer.parseInt(monthString);
            Integer day = Integer.parseInt(dayString);
            Integer hour = Integer.parseInt(hourString);
            Integer minute = Integer.parseInt(minuteString);
            Integer second = Integer.parseInt(secondString);
            errors += Validator.validateDate(year,month,day) +
                    Validator.validateTime(hour,minute,second);
            if (errors.equals("")){
                return new Transaction( Integer.parseInt(idString),
                                        Integer.parseInt(idMoneyPlaceString),
                                        Double.parseDouble(amountString),
                                        type,
                                        description,
                                        year,month,day,hour,minute,second);
            }
        }
        throw new WrongInputTranzaction(errors);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMoneyPlace() {
        return idMoneyPlace;
    }

    public void setIdMoneyPlace(Integer idMoneyPlace) {
        this.idMoneyPlace = idMoneyPlace;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}

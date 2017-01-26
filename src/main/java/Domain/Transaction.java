package Domain;

import Exceptions.WrongInputTransaction;
import Validator.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements SerializableWithId
{
    private Integer id;
    private Integer idMoneyPlace;
    private Double amount;
    private String type;
    private String description;


    private String name;
    private LocalDateTime date;

    public Transaction()
    {
       this(-1,-1,"",0.0,"","",LocalDateTime.of(0,0,0,0,0,0));
    }

    public Transaction(Integer id,
                       Integer idMoneyPlace,
                       String name,
                       Double amount,
                       String type,
                       String description,
                       LocalDate date,
                       LocalTime time)
    {
        this.id = id;
        this.idMoneyPlace = idMoneyPlace;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.date = LocalDateTime.of(date,time);
        this.name = name;
    }

    public Transaction(Integer id,
                        Integer idMoneyPlace,
                        String name,
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
        this.name = name;
    }

    private Transaction(Integer id,
                       Integer idMoneyPlace,
                       String name,
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
       this(id,idMoneyPlace,name,amount,type,description, LocalDateTime.of(LocalDate.of(year,month,day), LocalTime.of(hour,minute,second)));
    }

    private static String validateType(String type){
        if (type.equals("income") || type.equals("outcome")){
            return "";
        }
        return "Type has to be input or output";
    }

    private static String validateName(String name){
        if (name.length()> 30){
            return "Name can't be longer than 30";
        }
        return "";
    }

    /*time has to be HH:MM:SS.NS or HH:MM:SS or HH:MM */
    private static LocalTime getLocalTimeFromString(String time){
        String timeElements[] = time.split(":");
        if (timeElements.length == 3){
            if (timeElements[2].split(".").length == 0)
                return LocalTime.of(Integer.parseInt(timeElements[0]),Integer.parseInt(timeElements[1]),Integer.parseInt(timeElements[2]));
            else if (timeElements[2].split(".").length == 2)
                return LocalTime.of(Integer.parseInt(timeElements[0]),Integer.parseInt(timeElements[1]),Integer.parseInt(timeElements[2].split(".")[0]));
        }else if (timeElements.length == 2){
            return LocalTime.of(Integer.parseInt(timeElements[0]),Integer.parseInt(timeElements[1]));
        }
        return null;
    }

    public static Transaction getTransaction(Integer id,
                                             Integer idMoneyPlace,
                                             String amountString,
                                             String name,
                                             String type,
                                             String description,
                                             LocalDate date,
                                             String time) throws WrongInputTransaction {
        String errors = "";
        errors += Validator.validateAmount(amountString, "Amount has to be a real number!") +
                validateType(type) +
                Validator.validateTime(time)+
                validateName(name);

        if (errors.equals("")){
            return new Transaction( id,
                    idMoneyPlace,
                    name,
                    Double.parseDouble(amountString),
                    type,
                    description,
                    date,
                    getLocalTimeFromString(time)
                    );

        }
        throw new WrongInputTransaction(errors);
    }


    public static Transaction getTransaction(String idString,
                                      String idMoneyPlaceString,
                                      String amountString,
                                      String name,
                                      String type,
                                      String description,
                                      String yearString,
                                      String monthString,
                                      String dayString,
                                      String hourString,
                                      String minuteString,
                                      String secondString)
                               throws WrongInputTransaction {
        String errors = "";
        errors += Validator.validateInt(idString,"Id has to be a number!") +
                Validator.validateAmount(amountString, "Amount has to be a real number!") +
                Validator.validateInt(idMoneyPlaceString, "Id money place has to be a number!") +
                validateType(type) +
                Validator.validateInt(yearString, "Year has to be a number!") +
                Validator.validateInt(monthString, "Month has to be a number!") +
                Validator.validateInt(dayString, "Day has to be a number!") +
                Validator.validateInt(hourString, "Hour has to be a number!") +
                Validator.validateInt(minuteString, "Minute has to be a number!") +
                Validator.validateInt(secondString, "Second has to be a number!") +
                validateName(name);

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
                                        name,
                                        Double.parseDouble(amountString),
                                        type,
                                        description,
                                        year,month,day,hour,minute,second);
            }
        }
        throw new WrongInputTransaction(errors);
    }

    public static Transaction getTransaction(String idString,
                                      String idMoneyPlaceString,
                                      String name,
                                      String amountString,
                                      String type,
                                      String description,
                                      LocalDateTime date)
            throws WrongInputTransaction {
        String errors = "";
        errors += Validator.validateInt(idString,"Id has to be a number!") +
                Validator.validateAmount(amountString, "Amount has to be a real number!") +
                Validator.validateInt(idMoneyPlaceString, "Id money place has to be a number!") +
                validateType(type) +
                validateName(name);


        if (errors.equals("")){
                return new Transaction( Integer.parseInt(idString),
                        Integer.parseInt(idMoneyPlaceString),
                        name,
                        Double.parseDouble(amountString),
                        type,
                        description,
                        date);
            }
        throw new WrongInputTransaction(errors);
    }

    public String getOppositeType(){
        if (type.equals("income")){
            return "outcome";
        }
        return "income";
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

    public LocalDate getDate() {
        return date.toLocalDate();
    }

    public LocalTime getTime(){
        return date.toLocalTime();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDateAsString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", idMoneyPlace=" + idMoneyPlace +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}

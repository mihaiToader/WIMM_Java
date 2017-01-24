package Domain;

import Exceptions.WrongInputTransaction;
import Validator.Validator;

public class MoneyPlace implements SerializableWithId{
    private Integer id;
    private String name;
    private Double amount;
    private String description;

    public MoneyPlace(Integer id, String name, Double amount, String description) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    public MoneyPlace(String name, Double amount) {
        id = -1;
        description = "";
        this.name = name;
        this.amount = amount;
    }

    public MoneyPlace(String name, Double amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        id = -1;
    }

    public MoneyPlace() {
        this(null,null,null,null);
    }

    public static MoneyPlace getMoneyPlace(String id,
                                    String name,
                                    String amount,
                                    String description)
                                    throws WrongInputTransaction {
        String errors="";
        errors += Validator.validateInt(id, "Id has to be a number!") +
                Validator.validateAmount(amount, "Amount has to be a real number!") +
                validateName(name);
        if (errors.equals("")){
            return new MoneyPlace(Integer.parseInt(id), name, Double.parseDouble(amount), description);
        }
        throw new WrongInputTransaction(errors);
    }

    public static String validateName(String name){
        if (name.equals("")){
            return "Name can not be empty!";
        }
        return "";
    }

    public static MoneyPlace getMoneyPlace(int id,
                                           String name,
                                           String amount,
                                           String description)
            throws WrongInputTransaction {
        String errors="";
        errors += Validator.validateAmount(amount, "Amount has to be a real number!") +
                validateName(name);
        if (errors.equals("")){
            return new MoneyPlace(id, name, Double.parseDouble(amount), description);
        }
        throw new WrongInputTransaction(errors);
    }

    public static MoneyPlace getMoneyPlaceWithInvalidId(String name,
                                           String amount,
                                           String description)
            throws WrongInputTransaction {
        String errors="";
        errors += Validator.validateAmount(amount, "Amount has to be a real number!") +
                validateName(name);
        if (errors.equals("")){
            return new MoneyPlace(-1, name, Double.parseDouble(amount), description);
        }
        throw new WrongInputTransaction(errors);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}

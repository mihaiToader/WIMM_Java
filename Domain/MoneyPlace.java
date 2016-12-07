package Domain;

import Exceptions.WrongInputTranzaction;
import Validator.Validator;

public class MoneyPlace implements SerializableWithId{
    private Integer id;
    private String name;
    private Double amount;
    private String description;

    private MoneyPlace(Integer id, String name, Double amount, String description) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    public MoneyPlace() {
        this(null,null,null,null);
    }

    public MoneyPlace getMoneyPlace(String id,
                                    String name,
                                    String amount,
                                    String description)
                                    throws WrongInputTranzaction{
        String errors="";
        errors += Validator.validateInt(id, "Id has to be a number!") +
                Validator.validateAmount(amount, "Amount has to be a real number");
        if (errors.equals("")){
            return new MoneyPlace(Integer.parseInt(id), name, Double.parseDouble(amount), description);
        }
        throw new WrongInputTranzaction(errors);
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
        return "MoneyPlace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

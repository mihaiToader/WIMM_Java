package Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tranzaction
{
    private Integer id;
    private Integer idMoneyPlace;
    private Double amount;
    private String type;
    private String description;
    private LocalDateTime date;

    public Tranzaction()
    {
       this(null,null,null,null,null,null);
    }

    public Tranzaction(Integer id,
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

    public Tranzaction(Integer id,
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



}

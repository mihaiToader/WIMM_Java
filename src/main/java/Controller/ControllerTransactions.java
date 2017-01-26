package Controller;

import Domain.MoneyPlace;
import Domain.Transaction;
import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import Observer.Observable;
import Observer.Observer;
import Repository.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerTransactions implements Observable{

    private Repository<Transaction> repository;
    private ArrayList<Observer> observers;
    private Boolean dataModified;

    public ControllerTransactions(Repository<Transaction> repository) {
        observers = new ArrayList<Observer>();
        this.repository = repository;
        dataModified = false;
    }


    public ArrayList<Transaction> getAll() {return repository.getAll();}

    public Transaction add(Integer idMoneyPlace, String amount, String name, String type, String description, LocalDate date, String time) throws WrongInput, WrongInputTransaction {
        Transaction t = Transaction.getTransaction(repository.getNextId(),idMoneyPlace,amount,name,type,description,date,time);
        repository.add(t);
        return t;
    }

    public Transaction add(Integer idMoneyPlace, Double amount, String name, String type, String description, LocalDate date, LocalTime time){
        Transaction t = new Transaction(repository.getNextId(), idMoneyPlace, name, amount, type,description,date,time);
        repository.add(t);
        return t;
    }

    public void add(Transaction t){
        repository.add(t);
    }

    public void saveData(){
        repository.saveData();
        dataModified = false;
    }

    public ArrayList<Transaction> getTransactionsBetweenTwoDates(LocalDate first, LocalDate second){
        ArrayList<Transaction> result = new ArrayList<>();
        getAll().stream()
                .filter(x->((x.getDate().isAfter(first) && (x.getDate().isBefore(second))) || x.getDate().equals(first) || x.getDate().equals(second)))
                .forEach(x -> result.add(x));
        return result;
    }

    public void deleteTransaction(Transaction t){
        repository.delete(t);
    }

    public Boolean getDataModified(){return dataModified;}

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        dataModified = true;
        observers.forEach(x->x.update(this));
    }

    @Override
    public void removeObserver(Observer o) {

    }
}

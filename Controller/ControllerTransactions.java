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

    public ControllerTransactions(Repository<Transaction> repository) {
        observers = new ArrayList<Observer>();
        this.repository = repository;
    }


    public List<Transaction> getAll() {return repository.getAll();}

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

    public void deleteTransaction(Transaction t){
        repository.delete(t);
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(x->x.update(this));
    }

    @Override
    public void removeObserver(Observer o) {

    }
}

package Controller;

import Domain.MoneyPlace;
import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import Observer.Observable;
import Observer.Observer;
import Repository.Repository;

import java.util.*;

public class ControllerMoneyPlaces implements Observable {
    Repository<MoneyPlace> repository;

    public ControllerMoneyPlaces(Repository<MoneyPlace> repository) {
        this.repository = repository;
    }

    public List<MoneyPlace> getAll() {
        return repository.getAll();
    }

    private List<Observer> observers = new ArrayList<Observer>();

    public void add(String name, String amount, String description) throws WrongInputTransaction {
        repository.add(MoneyPlace.getMoneyPlace(repository.getNextId(),name,amount,description));
        notifyObservers();
    }

    public void delete(Integer id){
        MoneyPlace mP = getMoneyPlace(id);
        if (null != mP){
            repository.delete(mP);
            notifyObservers();
        }
    }

    public MoneyPlace getMoneyPlace(Integer id)
    {
        for (MoneyPlace mP: getAll()){
            if (mP.getId().equals(id)){
                return mP;
            }
        }
        return null;
    }


    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o->o.update(this));
    }

    @Override
    public void removeObserver(Observer o) {
        //To DO
    }
}

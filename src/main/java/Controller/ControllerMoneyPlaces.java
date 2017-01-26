package Controller;

import Domain.MoneyPlace;
import Exceptions.WrongInputTransaction;
import Observer.Observable;
import Observer.Observer;
import Repository.Repository;

import java.util.*;

public class ControllerMoneyPlaces implements Observable {
    private Repository<MoneyPlace> repository;
    private Boolean dataModified;

    public ControllerMoneyPlaces(Repository<MoneyPlace> repository) {
        this.repository = repository;
        dataModified = false;
    }

    public List<MoneyPlace> getAll() {
        return repository.getAll();
    }

    private List<Observer> observers = new ArrayList<Observer>();

    public MoneyPlace add(String name, String amount, String description) throws WrongInputTransaction {
        MoneyPlace mP = MoneyPlace.getMoneyPlace(repository.getNextId(),name,amount,description);
        repository.add(mP);
        return mP;
    }

    public MoneyPlace add(String name, Double amount, String description){
        MoneyPlace mP = new MoneyPlace(repository.getNextId(), name,amount,description);
        repository.add(mP);
        notifyObservers();
        return mP;
    }

    public void updateAmount(Integer id, Double amount){
        MoneyPlace mP = getMoneyPlace(id);
        mP.setAmount(amount);
        repository.update(mP);
    }

    public void update(MoneyPlace mP){
        repository.update(mP);
    }

    public void delete(Integer id){
        MoneyPlace mP = getMoneyPlace(id);
        if (null != mP){
            repository.delete(mP);
            notifyObservers();
        }
    }

    public void delete(MoneyPlace mP){
        repository.delete(mP);
    }

    public Boolean doesMoneyPlaceExists(Integer id){
        for (MoneyPlace mP: getAll()){
            if (mP.getId().equals(id)){
                return true;
            }
        }
        return false;
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

    public String getMoneyPlaceName(Integer id){
        MoneyPlace mP = getMoneyPlace(id);
        if (null != mP){
            return mP.getName();
        }
        return "";
    }

    public Boolean thisMoneyPlaceNameExists(String name){
        for (MoneyPlace mP: getAll()){
            if (mP.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void saveData(){
        repository.saveData();
        dataModified = false;
    }

    public Boolean getDataModified() {
        return dataModified;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {

        dataModified = true;
        observers.forEach(o->o.update(this));
    }

    @Override
    public void removeObserver(Observer o) {
        //To DO
    }
}

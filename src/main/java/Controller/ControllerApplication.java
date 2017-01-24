package Controller;


import Domain.MoneyPlace;
import Domain.Transaction;
import Exceptions.WrongInput;
import Exceptions.WrongInputTransaction;
import Observer.Observer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerApplication {
    private ControllerMoneyPlaces controllerMoneyPlaces;
    private ControllerTransactions controllerTransactions;
    private Double total;
    private Boolean dataModified;

    public ControllerApplication(ControllerMoneyPlaces controllerMoneyPlaces, ControllerTransactions controllerTransactions) {
        this.controllerMoneyPlaces = controllerMoneyPlaces;
        this.controllerTransactions = controllerTransactions;
        calculateTotalFromMoneyPlaces();
        dataModified = false;
    }

    public Transaction addTransaction(Integer idMoneyPlace, String amount, String name, String type, String description, LocalDate date, String time) throws WrongInput, WrongInputTransaction {
        if (controllerMoneyPlaces.getMoneyPlace(idMoneyPlace) == null){
            throw new WrongInputTransaction("That money place doesn't exist!");
        }
        Transaction t = controllerTransactions.add(idMoneyPlace, amount, name, type, description, date, time);
        transactionReloadTotalAndMoneyPlace(t);
        return t;
    }

    public void addTransaction(Transaction t){
        controllerTransactions.add(t);
        transactionReloadTotalAndMoneyPlace(t);
    }
    /**
     * Doesn't modify his money place amount
     *
     */
    public void addTransaction(Integer idMoneyPlace, Double amount, String name, String type, String description, LocalDate date, LocalTime time){
        Transaction t = controllerTransactions.add(idMoneyPlace, amount, name, type, description, date, time);
        addToTotal(t.getType(),t.getAmount());
        notifyAllObservers();
    }

    private void transactionReloadTotalAndMoneyPlace(Transaction t){
        addToTotal(t.getType(),t.getAmount());
        updateMoneyPlaceAmount(t.getIdMoneyPlace(),t.getType(),t.getAmount());
        notifyAllObservers();
    }

    private void updateMoneyPlaceAmount(Integer id, String type, Double amount){
        MoneyPlace mP = controllerMoneyPlaces.getMoneyPlace(id);
        if (type.equals("income")){
            controllerMoneyPlaces.updateAmount(id, mP.getAmount() + amount);
        }else{
            controllerMoneyPlaces.updateAmount(id, mP.getAmount() - amount);
        }
    }

    public void deleteTransaction(Transaction t){
        addToTotal(t.getOppositeType(),t.getAmount());
        updateMoneyPlaceAmount(t.getIdMoneyPlace(),t.getOppositeType(),t.getAmount());
        controllerTransactions.deleteTransaction(t);
        notifyAllObservers();
    }

    public void deleteMoneyPlace(MoneyPlace mP){
        ArrayList<Transaction> toDelete = new ArrayList<>();
        getAllTransactions().forEach(x->{
            if (x.getIdMoneyPlace().equals(mP.getId())){
                toDelete.add(x);
            }
        });
        toDelete.forEach(x->controllerTransactions.deleteTransaction(x));
        total -= mP.getAmount();
        controllerMoneyPlaces.delete(mP);
        notifyAllObservers();
    }

    public void updateTransaction(Transaction oldT, Transaction newT){
        deleteTransaction(oldT);
        addTransaction(newT);
    }

    public void notifyAllObservers(){
        controllerMoneyPlaces.notifyObservers();
        controllerTransactions.notifyObservers();
    }

    private void addToTotal(String type, Double amount){
        if (type.equals("income")){
            total += amount;
        }else{
            total -= amount;
        }

    }

    private void calculateTotalFromTransactions(){
        total = 0.0;
        for (Transaction t:getAllTransactions()){
            addToTotal(t.getType(),t.getAmount());
        }
    }

    private void calculateTotalFromMoneyPlaces(){
        total = 0.0;
        for (MoneyPlace mp:getAllMoneyPlaces()){
            total += mp.getAmount();
        }
    }

    public void saveData() {
        controllerMoneyPlaces.saveData();
        controllerTransactions.saveData();
    }

    public List<Transaction> getAllTransactions(){
        return controllerTransactions.getAll();
    }

    public List<MoneyPlace> getAllMoneyPlaces(){
        return controllerMoneyPlaces.getAll();
    }

    public ControllerMoneyPlaces getControllerMoneyPlaces() {
        return controllerMoneyPlaces;
    }

    public ControllerTransactions getControllerTransactions() {
        return controllerTransactions;
    }

    public void addObserverTransactionController(Observer o){
        controllerTransactions.addObserver(o);
    }

    public void addObserverMoneyPlace(Observer o){
        controllerMoneyPlaces.addObserver(o);
    }

    public Double getTotal() {
        return total;
    }

    public Boolean getDataModified() {
        return dataModified;
    }

}

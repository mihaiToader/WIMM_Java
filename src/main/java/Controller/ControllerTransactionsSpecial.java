package Controller;

import Domain.MoneyPlace;
import Domain.Transaction;
import Repository.RepositoryTwoRepositoriesSameFile;

public class ControllerTransactionsSpecial extends ControllerTransactions {
    private RepositoryTwoRepositoriesSameFile<Transaction, MoneyPlace> repository;

    public ControllerTransactionsSpecial(RepositoryTwoRepositoriesSameFile<Transaction, MoneyPlace> repository) {
        super(repository.getRepositoryT());
        this.repository = repository;
    }

    public void saveData(){
        repository.saveData();
        dataModified = false;
    }
}

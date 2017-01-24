package Utils;

import Domain.Transaction;
import Repository.Repository;

public class ExportTransactionsToPdf {
    private Repository<Transaction> repository;

    public ExportTransactionsToPdf(Repository<Transaction> repository) {
        this.repository = repository;
    }

}

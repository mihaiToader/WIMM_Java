package Repository;

import Domain.HasId;

import java.util.ArrayList;

public interface RepositoryCRUD<T extends HasId> {
    void add(T obj);
    void delete(T obj);
    ArrayList<T> getAll();
    void loadData();
    void saveData();
}

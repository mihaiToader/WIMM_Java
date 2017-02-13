package Repository;

import Domain.HasId;

import java.util.ArrayList;

public interface RepositoryCRUD<T extends HasId> {
    void add(T obj);
    void delete(T obj);
    void update(T obj);
    ArrayList<T> getAll();
    Boolean exists(T obj);
    void loadData();
    void saveData();
}

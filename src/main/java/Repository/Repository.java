package Repository;

import Domain.HasId;

import java.util.ArrayList;

public class Repository<T extends HasId> implements RepositoryCRUD<T> {

    ArrayList<T> all;

    Repository() {
        all = new ArrayList<>();
    }

    @Override
    public void add(T obj) {
        all.add(obj);
    }

    @Override
    public void delete(T obj) {
        all.remove(obj);
    }

    @Override
    public void update(T obj) {
        this.all.replaceAll(e -> {
            if (e.getId().equals(obj.getId())) {
                return obj;
            }
            return e;
        });
    }

    @Override
    public ArrayList<T> getAll() {
        return all;
    }

    private Integer getLastId() {
        Integer lastId = Integer.MIN_VALUE;
        for (HasId e : this.all) {
            lastId = lastId < e.getId() ? e.getId() : lastId;
        }

        lastId = lastId == Integer.MIN_VALUE ? null : lastId;
        return lastId;
    }

    public Integer getNextId() {
        return this.getLastId() == null ? 0 : this.getLastId() + 1;
    }

    public Boolean exists(T obj){
        return all.contains(obj);
    }

    public Boolean existsId(Integer id){
        for (T obj: all){
            if (obj.getId().equals(id)){
                return true;
            }
        }
        return false;
    }


    public void setAll(ArrayList<T> list) {
        all = list;
    }

    @Override
    public void loadData() {
        //nothing
    }

    @Override
    public void saveData() {
        //nothing
    }
}

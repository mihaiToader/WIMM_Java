package Repository;

import Domain.SerializableWithId;

import java.io.*;
import java.util.ArrayList;

public class RepositoryTwoRepositoriesSameFile<T extends SerializableWithId, D extends SerializableWithId> {
    private Repository<T> repositoryT;
    private Repository<D> repositoryD;
    private String fileName;

    public RepositoryTwoRepositoriesSameFile(String fileName) {
        this.fileName = fileName;
        repositoryT = new Repository<T>();
        repositoryD = new Repository<D>();
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData(){
        ArrayList<T> tObjects = new ArrayList<T>();
        ArrayList<D> dObjects = new ArrayList<D>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            if (br.readLine() != null) {
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream input = new ObjectInputStream(fis);
                dObjects = (ArrayList<D>) input.readObject();
                tObjects = (ArrayList<T>) input.readObject();
                input.close();
            }
            repositoryD.setAll(dObjects);
            repositoryT.setAll(tObjects);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveData()
    {
        try
        {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
            output.writeObject(repositoryD.getAll());
            output.writeObject(repositoryT.getAll());
            output.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public Repository<T> getRepositoryT() {
        return repositoryT;
    }

    public Repository<D> getRepositoryD() {
        return repositoryD;
    }
}

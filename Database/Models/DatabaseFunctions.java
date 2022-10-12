package Database.Middleware;

import java.util.HashMap;

public interface DatabaseFunctions<T> {

    public T get(String ID);

    public HashMap<String, T> getAll();

    public void add(T t);

    public void remove(T t);

    public void udpate(T t);

}

package Database;

public interface DatabaseFunctions<T> {

    public T get(String ID);

    public void add(T t);

    public void remove(T t);

    public void udpate(T t);

}

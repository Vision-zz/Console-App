package Database;

public interface DatabaseFunctions<T> {

    T get(String ID);

    void add(T t) throws Exception;

    void remove(T t);

    void udpate(T t) throws Exception;

}

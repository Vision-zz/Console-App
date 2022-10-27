package com.pitstop.Database.Models;

import java.util.HashMap;

public interface DatabaseFunctions<T> {

    public T get(String ID);

    public int getCurrentID();

    public void updateCurrentID(int ID);

    public HashMap<String, T> getAll();

    public void add(T t);

    public void remove(T t);

    public void udpate(T t);

}

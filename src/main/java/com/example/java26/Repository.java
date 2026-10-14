package com.example.java26;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {

    private List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> findAll() {
        return items;
    }

    public List<T> findWhere(Predicate<T> condition) {
        List<T> result = new ArrayList<>();

        for (T item : items) {
            if (condition.test(item)) {
                result.add(item);
            }

        }

        return result;
    }

}

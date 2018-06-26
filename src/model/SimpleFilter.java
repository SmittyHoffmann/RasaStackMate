package model;

import java.util.function.Predicate;

public class SimpleFilter<T> implements Predicate<T>{

    T filter;

    @Override
    public boolean test(T var) {
        if(filter.equals(var)){
            return true;
        }
        return false;
    }

    public void setFilter(T filter){
        this.filter = filter;
    }
}

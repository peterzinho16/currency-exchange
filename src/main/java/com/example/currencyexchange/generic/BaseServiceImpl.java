package com.example.currencyexchange.generic;

public abstract class BaseServiceImpl<T> {

    protected T repository;

    public BaseServiceImpl(T repository) {
        this.repository = repository;
    }
}

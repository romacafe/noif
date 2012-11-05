package com.mc.noif;

class Constant<T> implements Function<T> {
    final T val;
    public Constant(T val) {
        this.val = val;
    }
    public T execute() {
        return val;
    }
}
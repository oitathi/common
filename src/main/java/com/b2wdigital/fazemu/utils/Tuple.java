package com.b2wdigital.fazemu.utils;

public class Tuple<T, M> {

    private final T first;
    private final M second;

    private Tuple(T first, M second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public M getSecond() {
        return second;
    }

    public static <T, M> Tuple<T, M> of(T first, M second) {
        return new Tuple<>(first, second);
    }
}

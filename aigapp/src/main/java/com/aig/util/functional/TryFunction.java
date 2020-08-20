package com.aig.util.functional;

@FunctionalInterface
public interface TryFunction<T, R> {
    R apply(T t) throws Throwable;
}
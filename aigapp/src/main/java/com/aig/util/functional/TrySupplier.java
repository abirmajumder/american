package com.aig.util.functional;

@FunctionalInterface
public interface TrySupplier<T> {
    T get() throws Throwable;
}
package com.forgemagic.api;

import java.util.Objects;
import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Success, Result.Failure {
    boolean isSuccess();
    T value();
    E error();

    default <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return isSuccess() ? success(mapper.apply(value())) : failure(error());
    }

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(Objects.requireNonNull(value, "value"));
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(Objects.requireNonNull(error, "error"));
    }

    record Success<T, E>(T value) implements Result<T, E> {
        public boolean isSuccess() { return true; }
        public E error() { throw new IllegalStateException("success has no error"); }
    }

    record Failure<T, E>(E error) implements Result<T, E> {
        public boolean isSuccess() { return false; }
        public T value() { throw new IllegalStateException("failure has no value"); }
    }
}

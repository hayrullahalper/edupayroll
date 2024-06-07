package com.incubator.edupayroll.util.response;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Response<D, M, E> {

    private D data = null;
    private M meta = null;
    private final List<Error<E>> errors = new ArrayList<>();

    protected Response() {
    }

    public static <D> DataBuilder<D> data(D data) {
        return new DataBuilder<>(data);
    }

    public static <E> ErrorBuilder<E> error(E code, String message) {
        return new ErrorBuilder<>(code, message);
    }

    public static <E> ErrorBuilder<E> error(E code, List<String> messages) {
        return new ErrorBuilder<>(code, messages);
    }

    private Response<D, M, E> withData(D data) {
        this.data = data;
        return this;
    }

    private Response<D, M, E> withMeta(M meta) {
        this.meta = meta;
        return this;
    }

    private Response<D, M, E> withError(List<Error<E>> errors) {
        this.errors.addAll(errors);
        return this;
    }

    @Value
    private static class Error<E> {
        E code;
        String message;
    }

    public static class DataBuilder<D> {
        private final D data;

        DataBuilder(D data) {
            this.data = data;
        }

        public <M> MetaBuilder<D, M> meta(M meta) {
            return new MetaBuilder<>(data, meta);
        }

        public <M, E> Response<D, M, E> build() {
            return new Response<D, M, E>().withData(data);
        }
    }

    public static class MetaBuilder<D, M> {
        private final D data;
        private final M meta;

        MetaBuilder(D data, M meta) {
            this.data = data;
            this.meta = meta;
        }

        public <E> Response<D, M, E> build() {
            return new Response<D, M, E>().withData(data).withMeta(meta);
        }
    }

    public static class ErrorBuilder<E> {

        private final List<Error<E>> errors = new ArrayList<>();

        ErrorBuilder(E code, String message) {
            this.errors.add(new Error<>(code, message));
        }

        ErrorBuilder(E code, List<String> messages) {
            messages.forEach(message -> this.errors.add(new Error<>(code, message)));
        }

        public ErrorBuilder<E> error(E code, String message) {
            this.errors.add(new Error<>(code, message));
            return this;
        }

        public ErrorBuilder<E> error(E code, List<String> messages) {
            messages.forEach(message -> this.errors.add(new Error<>(code, message)));
            return this;
        }

        public <D, M> Response<D, M, E> build() {
            return new Response<D, M, E>().withError(errors);
        }
    }
}
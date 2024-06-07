package com.incubator.edupayroll.util.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Response<D, M, E> {
    private D data = null;
    private M meta = null;
    private final List<E> errors = new ArrayList<>();

    public static <D, M, E> Response<D, M, E> data(D data) {
        return new Response<D, M, E>().withData(data);
    }

    public static <D, M, E> Response<D, M, E> meta(M meta) {
        return new Response<D, M, E>().withMeta(meta);
    }

    public static <D, M, E> Response<D, M, E> error(E error) {
        return new Response<D, M, E>().withError(error);
    }

    public static <D, M, E> Response<D, M, E> errors(List<E> errors) {
        return new Response<D, M, E>().withErrors(errors);
    }

    public Response<D, M, E> withData(D data) {
        this.data = data;
        return this;
    }

    public Response<D, M, E> withMeta(M meta) {
        this.meta = meta;
        return this;
    }

    public Response<D, M, E> withError(E error) {
        this.errors.add(error);
        return this;
    }

    public Response<D, M, E> withErrors(List<E> errors) {
        this.errors.addAll(errors);
        return this;
    }
}

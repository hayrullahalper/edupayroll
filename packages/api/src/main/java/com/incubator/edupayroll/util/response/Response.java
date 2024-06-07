package com.incubator.edupayroll.util.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Response<D, M, E> {
    private D data = null;
    private M meta = null;
    private final List<ResponseError<E>> errors = new ArrayList<>();

    public static <D, M, E> Response<D, M, E> data(D data) {
        return new Response<D, M, E>().withData(data);
    }

    public static <D, M, E> Response<D, M, E> meta(M meta) {
        return new Response<D, M, E>().withMeta(meta);
    }

    public static <D, M, E> Response<D, M, E> error(E code, String message) {
        return new Response<D, M, E>().withError(code, message);
    }

    public Response<D, M, E> withData(D data) {
        this.data = data;
        return this;
    }

    public Response<D, M, E> withMeta(M meta) {
        this.meta = meta;
        return this;
    }

    public Response<D, M, E> withError(E code, String message) {
        this.errors.add(new ResponseError<>(code, message));
        return this;
    }
}

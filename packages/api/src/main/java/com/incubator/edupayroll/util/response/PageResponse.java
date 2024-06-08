package com.incubator.edupayroll.util.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageResponse<D, E> {
    private List<D> data = null;
    private PageMeta meta = null;

    @NotNull
    private final List<ResponseError<E>> errors = new ArrayList<>();

    PageResponse() {
    }

    public static <D> DataBuilder<D> data(List<D> data) {
        return new DataBuilder<>(data);
    }

    public static <E> ErrorBuilder<E> error(E code, String message) {
        return new ErrorBuilder<>(code, message);
    }

    public static <E> ErrorBuilder<E> error(E code, List<String> messages) {
        return new ErrorBuilder<>(code, messages);
    }

    private PageResponse<D, E> withData(List<D> data) {
        this.data = data;
        return this;
    }

    private PageResponse<D, E> withMeta(PageMeta meta) {
        this.meta = meta;
        return this;
    }

    private PageResponse<D, E> withError(List<ResponseError<E>> errors) {
        this.errors.addAll(errors);
        return this;
    }

    public static class DataBuilder<D> {
        private final List<D> data;

        DataBuilder(List<D> data) {
            this.data = data;
        }

        public MetaBuilder<D> meta(int page, int size, int total) {
            return new MetaBuilder<>(data, page, size, total);
        }

        public <E> PageResponse<D, E> build() {
            return new PageResponse<D, E>().withData(data);
        }
    }

    public static class MetaBuilder<D> {
        private final PageMeta meta;
        private final List<D> data;

        MetaBuilder(List<D> data, int page, int size, int total) {
            this.data = data;
            this.meta = new PageMeta(page, size, total);
        }

        public <E> PageResponse<D, E> build() {
            return new PageResponse<D, E>().withData(data).withMeta(meta);
        }
    }

    public static class ErrorBuilder<E> {

        private final List<ResponseError<E>> errors = new ArrayList<>();

        ErrorBuilder(E code, String message) {
            this.errors.add(new ResponseError<>(code, message));
        }

        ErrorBuilder(E code, List<String> messages) {
            messages.forEach(message -> this.errors.add(new ResponseError<>(code, message)));
        }

        public ErrorBuilder<E> error(E code, String message) {
            this.errors.add(new ResponseError<>(code, message));
            return this;
        }

        public ErrorBuilder<E> error(E code, List<String> messages) {
            messages.forEach(message -> this.errors.add(new ResponseError<>(code, message)));
            return this;
        }

        public <D> PageResponse<D, E> build() {
            return new PageResponse<D, E>().withError(errors);
        }
    }
}
package com.incubator.edupayroll.util.response;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Response<D, E> {
  private D data = null;

  @NotNull private final List<ResponseError<E>> errors = new ArrayList<>();

  protected Response() {}

  public static <D> DataBuilder<D> data(D data) {
    return new DataBuilder<>(data);
  }

  public static <E> ErrorBuilder<E> error(E code, String message) {
    return new ErrorBuilder<>(code, message);
  }

  public static <E> ErrorBuilder<E> error(E code, List<String> messages) {
    return new ErrorBuilder<>(code, messages);
  }

  private Response<D, E> withData(D data) {
    this.data = data;
    return this;
  }

  private Response<D, E> withError(List<ResponseError<E>> errors) {
    this.errors.addAll(errors);
    return this;
  }

  public static class DataBuilder<D> {
    private final D data;

    DataBuilder(D data) {
      this.data = data;
    }

    public <E> Response<D, E> build() {
      return new Response<D, E>().withData(data);
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

    public <D, M> Response<D, E> build() {
      return new Response<D, E>().withError(errors);
    }
  }
}

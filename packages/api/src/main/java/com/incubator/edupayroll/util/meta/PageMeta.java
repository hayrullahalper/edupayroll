package com.incubator.edupayroll.util.meta;

import lombok.Value;

@Value
public class PageMeta {
    int page;
    int size;
    int total;

    public static PageMeta of(int page, int size, int total) {
        return new PageMeta(page, size, total);
    }
}

package com.app.builderplante.core.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestBuilder {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    public static Pageable build(Integer page, Integer size) {
        int p = (page != null && page >= 0) ? page : DEFAULT_PAGE;
        int s = (size != null && size > 0) ? Math.min(size, MAX_SIZE) : DEFAULT_SIZE;
        return PageRequest.of(p, s);
    }

    public static Pageable build(Integer page, Integer size, String sortBy, String direction) {
        int p = (page != null && page >= 0) ? page : DEFAULT_PAGE;
        int s = (size != null && size > 0) ? Math.min(size, MAX_SIZE) : DEFAULT_SIZE;
        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortBy != null ? sortBy : "id"
        );
        return PageRequest.of(p, s, sort);
    }
}

package com.quocnguyen.studentmanagement.entities;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageMetadata<T> {
    int totalPages, size, number, page;
    long totalElements;

    public  PageMetadata(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.number = page.getNumberOfElements();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber() + 1;
    }

}

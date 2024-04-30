package com.quocnguyen.studentmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class CollectionResponse<T> {
    private Iterable<T> data;
    private PageMetadata<T> page;

    public CollectionResponse(Page<T> page) {
        this.data = page.getContent();
        this.page = new PageMetadata<T>(page);
    }
}

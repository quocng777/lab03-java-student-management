package com.quocnguyen.studentmanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollectionResponse<T> extends DataResponse<Iterable<T>> {
    private PageMetadata<T> page;

    public CollectionResponse(Page<T> page) {
        super(page.getContent());
        this.page = new PageMetadata<T>(page);
    }
}

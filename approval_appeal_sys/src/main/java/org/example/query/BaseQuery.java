package org.example.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class BaseQuery {

    private Integer current;
    private Integer size;

    public Integer getCurrent() {
        if(current == null){
            current=1;
        }
        return current;
    }

    public Integer getSize() {
        if(size == null){
            size=5;
        }
        return size;
    }
}

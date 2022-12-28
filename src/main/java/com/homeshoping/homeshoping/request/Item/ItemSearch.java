package com.homeshoping.homeshoping.request.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearch {

    private static final int MAX_SIZE = 2000;

    private Integer page = 1;
    private  Integer size = 20;

    public int getOffset(){
        return (Math.max(page,1)-1)* Math.min(size,MAX_SIZE);
    }

    public int getLimit(){ return Math.min(size , MAX_SIZE);}
}

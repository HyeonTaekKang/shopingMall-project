package com.homeshoping.homeshoping.response.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemListResponse<T> {
    private T data;
}

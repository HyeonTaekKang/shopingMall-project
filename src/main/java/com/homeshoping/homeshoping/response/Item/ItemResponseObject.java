package com.homeshoping.homeshoping.response.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemResponseObject<T> {
    private T data;
}

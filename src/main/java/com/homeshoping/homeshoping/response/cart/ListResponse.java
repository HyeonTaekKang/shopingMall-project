package com.homeshoping.homeshoping.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListResponse<T>{

    private T data;
}

package com.ljh.back.dto;

import lombok.Getter;

@Getter
public class OrderDto {

    private String name;
    private String address;
    private String payment;
    private String memberId;
    private String cardNumber;
    private String items;
}

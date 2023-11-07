package com.ljh.back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDto {


    private int id;
    private int adminId;
    private int price;
    private String name;
    private int itemId;
    private int memberId;
}

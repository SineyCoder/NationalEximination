package com.example.homework.entity;

import lombok.Data;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@Data
public class User {

    private Integer cId;
    private String cAccount;
    private String cPassword;
    private Integer cGrant;

}

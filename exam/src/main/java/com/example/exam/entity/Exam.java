package com.example.exam.entity;

import lombok.Data;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@Data
public class Exam {

    private Integer cId;
    private String cTitle;
    private String cType;
    private String cContent;
    private String cAnswer;
    private String cAnalyse;
    private String cPicture;
    private Integer cExamType;
    private Integer cExamProject;

}

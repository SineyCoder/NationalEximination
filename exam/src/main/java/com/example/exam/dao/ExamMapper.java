package com.example.exam.dao;


import com.example.exam.entity.Exam;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@Mapper
public interface ExamMapper {

    @Insert("INSERT INTO t_question(`c_title`, `c_type`, `c_content`, `c_answer`, `c_analyse`, `c_picture`, `c_exam_type`, `c_exam_project`) VALUES(" +
            "#{cTitle}, #{cType}, #{cContent}, #{cAnswer}, #{cAnalyse}, #{cPicture}, #{cExamType}, #{cExamProject})")
    @Options(useGeneratedKeys = true, keyProperty = "cId", keyColumn = "c_id")
    void insertExam(Exam exam);

    @Select("SELECT * FROM t_question")
    List<Exam> selectAll();

    @Select("SELECT * FROM (SELECT * FROM `t_question` where MATCH(`c_title`) AGAINST(#{content} IN NATURAL LANGUAGE MODE) LIMIT 0, 5) a " +
            "WHERE a.c_title LIKE '%${content}%'")
    List<Exam> selectTitleContain(@Param("content") String content);

}

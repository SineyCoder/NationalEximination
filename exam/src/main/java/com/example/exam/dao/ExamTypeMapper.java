package com.example.exam.dao;

import com.example.exam.entity.ExamType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author siney
 * @createTime 2020-10-28
 **/
@Mapper
public interface ExamTypeMapper {

    @Select("SELECT * FROM `t_exam_type`")
    List<ExamType> selectAll();

    @Select("SELECT b.c_id, b.c_project c_type FROM (SELECT * FROM `c_exam_type_subproject` WHERE c_type_id = #{id}) a " +
            "LEFT JOIN c_exam_subproject b ON b.c_id = a.c_project_id")
    List<ExamType> selectExamProjectByExamType(@Param("id") Integer id);

}

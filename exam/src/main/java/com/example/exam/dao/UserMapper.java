package com.example.exam.dao;

import com.example.exam.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM `t_user`")
    List<User> selectAllUser();

    @Select("SELECT COUNT(*) FROM `t_user` WHERE c_account = #{cAccount} AND c_password = #{cPassword} AND c_grant = #{cGrant}")
    Long selectUserByUPG(User user);

}

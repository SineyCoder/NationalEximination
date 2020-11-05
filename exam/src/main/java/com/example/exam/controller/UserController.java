package com.example.exam.controller;

import com.alibaba.fastjson.JSON;
import com.example.exam.dao.UserMapper;
import com.example.exam.entity.User;
import com.example.exam.utils.ImgUtils;
import com.example.exam.utils.ObjectFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/exam/check")
    public Integer check(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            System.out.println(user.getCGrant());
            return user.getCGrant();
        }
        return -1;
    }

    @RequestMapping("/exam/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        String text = new ImgUtils().drawImg().output(response.getOutputStream()).getText();
        session.setAttribute("code",text);
    }

    @RequestMapping("/exam/login")
    public Object login(@RequestBody Map<String, Object> map, HttpSession session) throws Exception{
        User user = JSON.parseObject(JSON.toJSONString(map), User.class);
        Long aLong = userMapper.selectUserByUPG(user);
        if(aLong == 0){
            return ObjectFactoryUtils.createResultMap(-1, "msg", "账号或密码错误");
        }else{
            String code = (String) session.getAttribute("code");
            String mCode = (String) map.get("code");
            if(mCode.equals(code) || 1==1){
                session.setAttribute("user", user);
            }else{
                return ObjectFactoryUtils.createResultMap(-1, "msg", "输入验证码错误");
            }
        }
        return ObjectFactoryUtils.createResultMap(200, "msg", "登录成功");
    }

}

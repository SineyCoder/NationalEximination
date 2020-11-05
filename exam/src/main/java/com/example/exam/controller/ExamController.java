package com.example.exam.controller;

import com.alibaba.fastjson.JSON;
import com.example.exam.config.SystemConfig;
import com.example.exam.dao.ExamMapper;
import com.example.exam.dao.ExamTypeMapper;
import com.example.exam.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author siney
 * @createTime 2020-10-21
 **/
@RestController
public class ExamController {

    @Autowired
    private SystemConfig config;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamTypeMapper examTypeMapper;

    @RequestMapping("/exam/test")
    public void test(HttpSession session){
        System.out.println(session.getId() + " 打开");
    }

    @RequestMapping(value = "/exam/getAllExamType", method = RequestMethod.GET)
    public Object getAllExamType(){
        return examTypeMapper.selectAll();
    }

    @RequestMapping(value = "/exam/getExamProject/{id}", method = RequestMethod.GET)
    public Object getExamProjectByExamType(@PathVariable("id")Integer id){
        return examTypeMapper.selectExamProjectByExamType(id);
    }

    @RequestMapping("/exam/add")
    public String add(@RequestBody Map<Object, Object> param)throws Exception{
        Exam exam = JSON.parseObject(JSON.toJSONString(param), Exam.class);
        examMapper.insertExam(exam);
        List<String> pictures = JSON.parseArray(exam.getCPicture(), String.class);
        if(!pictures.isEmpty()){
            String prefix = exam.getCId() + "_";
            for(String picture : pictures){
                Path oldPath = new File(config.tmpPicturePath + picture).toPath();
                Path newPath = new File(config.picturePath + prefix + picture).toPath();
                Files.move(oldPath, newPath);
            }
        }
        return "success";
    }

    @RequestMapping(value = "/exam/uploadPicture", method = RequestMethod.POST)
    public String uploadPicture(@RequestParam("file")MultipartFile file)throws Exception{
        Files.createDirectories(new File(config.tmpPicturePath).toPath());
        Files.createDirectories(new File(config.picturePath).toPath());
        int index = file.getOriginalFilename().lastIndexOf(".");
        String filename = UUID.randomUUID().toString().replace("-", "").toLowerCase() + file.getOriginalFilename().substring(index);
        File f = new File(config.tmpPicturePath, filename);
        file.transferTo(f);
        return filename;
    }

    @RequestMapping(value = "/exam/getAll", method = RequestMethod.GET)
    public Object getAllExam(){
        return examMapper.selectAll();
    }

    @RequestMapping(value = "/exam/searchTitle", method = RequestMethod.POST)
    public Object getContainTitle(@RequestBody HashMap<String, String> map) throws UnsupportedEncodingException {
        return examMapper.selectTitleContain(map.get("content"));
    }

}

package com.example.homework.http.interf;


import com.example.homework.entity.Exam;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * author: siney
 * Date: 2020/10/23
 * description:
 */
public interface ExamApi {

    @GET("getAll")
    Observable<List<Exam>> getAll();

}

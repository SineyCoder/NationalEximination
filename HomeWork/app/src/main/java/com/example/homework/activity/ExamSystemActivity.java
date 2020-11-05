package com.example.homework.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.homework.R;
import com.example.homework.base.BaseActivity;
import com.example.homework.entity.Exam;
import com.example.homework.http.ApiFactory;
import com.example.homework.http.ObservableBinder;
import com.example.homework.http.interf.ExamApi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamSystemActivity extends BaseActivity {

    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.check)
    LinearLayout check;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.analyse)
    TextView analyse;
    private List<Exam> examList;

    private int currentIndex;
    private Set<Integer> answer = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_system);
        ButterKnife.bind(this);

        new ObservableBinder<List<Exam>>(this)
                .callback((data) -> {
                    examList = data;
                    show(currentIndex);
                    Log.e("NEWHTTP", data.toString());
                }).bind(ApiFactory.getApi(ExamApi.class).getAll());

    }

    private void show(int index) {
        Exam exam = examList.get(index);
        String prefix = (currentIndex + 1) + "/" + examList.size() + "  ";
        String title = prefix + "(" + exam.getCType() + ") " + exam.getCTitle();
        mTitle.setText(title);
        showContent(exam);
    }

    private void showContent(Exam exam) {
        List<String> strings = JSON.parseArray(exam.getCContent(), String.class);
        check.setVisibility(View.GONE);
        radio.setVisibility(View.GONE);
        answer.clear();
        analyse.setText("");
        if ("不定项选择题".equals(exam.getCType())) {
            check.setVisibility(View.VISIBLE);
            check.removeAllViews();
            for (int i = 0; i < strings.size(); i++) {
                final String str = strings.get(i);
                CheckBox checkBox = new CheckBox(this);
                String text = (char)(i + 'A') + "." + str;
                checkBox.setText(text);
                checkBox.setId(i);
                checkBox.setChecked(false);
                check.addView(checkBox, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        answer.add(buttonView.getId());
                    } else {
                        answer.remove(buttonView.getId());
                    }
                });
            }
        } else {
            radio.setVisibility(View.VISIBLE);
            radio.removeAllViews();
            radio.clearCheck();
            for (int i = 0; i < strings.size(); i++) {
                final String str = strings.get(i);
                RadioButton radioButton = new RadioButton(this);
                String text = (char)(i + 'A') + "." + str;
                radioButton.setText(text);
                radioButton.setId(i);
                radio.addView(radioButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        answer.clear();
                        answer.add(buttonView.getId());
                    }
                });
            }
        }
    }

    private void checkAnswer() {
        if(answer.size() > 0){
            Exam exam = examList.get(currentIndex);
            showCorrectAnswer(exam);
        }
        /*Log.e("NEWHTT", currentIndex+"");
        har[] answers = exam.getCAnswer().toCharArray();
        if (answer.size() == answers.length) {
            for (char ans : answers) {
                Integer a = ans - '0';
                if (!answer.contains(a)) {
                    //说明答案不对
                    showCorrectAnswer(exam);
                    return;
                }
            }
        } else {
            //说明答案不对
            showCorrectAnswer(exam);
        }*/

    }

    private void showCorrectAnswer(Exam exam) {
        char[] answers = exam.getCAnswer().toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append("【答案】");
        for (char ans : answers) {
            sb.append((char) (ans - '0' + 'A'));
        }
        sb.append("。").append(exam.getCAnalyse());
        analyse.setText(sb.toString());
    }

    @OnClick({R.id.confirm, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                checkAnswer();
                break;
            case R.id.next:
                if(currentIndex + 1 < examList.size() && answer.size() > 0)
                    show(++currentIndex);
                break;
        }
    }


}

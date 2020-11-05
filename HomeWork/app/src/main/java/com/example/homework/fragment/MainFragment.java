package com.example.homework.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.homework.MainActivity;
import com.example.homework.R;
import com.example.homework.activity.ExamSystemActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//刷题部分
public class MainFragment extends Fragment {

    @BindView(R.id.exam)
    RelativeLayout exam;
    @BindView(R.id.wrong)
    RelativeLayout wrong;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick({R.id.exam, R.id.wrong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exam:
                Intent intent = new Intent(getActivity(), ExamSystemActivity.class);
                startActivity(intent);
                break;
            case R.id.wrong:
                break;
        }
    }
}

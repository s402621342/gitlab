package com.example.song4.gitlab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.song4.gitlab.VO.QuestionVO;
import com.example.song4.gitlab.VO.TestcaseVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song4 on 2017/6/26.
 */

public class TestcasesDialogFragment extends DialogFragment {
    private List<TestcaseVO> voList;
    public TestcasesDialogFragment(){

    }

    public void setVoList(List<TestcaseVO> voList){
        this.voList=voList;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_question_dialog,null);
        ListView listView=(ListView) view.findViewById(R.id.question_list);
        if(this.voList!=null){
            List<Map<String, String>> names = new ArrayList<>();
            Map<String, String> map;
            for (int i = 0; i < voList.size(); i++) {
                map = new HashMap<>();
                map.put("name", voList.get(i).getName());
                map.put("passed", voList.get(i).getPassed());
                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.testcase_list,
                    new String[]{"name","passed"},
                    new int[]{R.id.testcaseName,R.id.testcasePassed,});
            listView.setAdapter(simpleAdapter);
        }
        builder.setView(view);
        return builder.create();
    }
}

package com.example.song4.gitlab;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.song4.gitlab.VO.QuestionVO;
import com.example.song4.gitlab.VO.SimpleStudentVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreDialogFragment extends DialogFragment {
    private List<SimpleStudentVO> voList;

    public ScoreDialogFragment(){

    }

    public void setVoList(List<SimpleStudentVO> voList){
        this.voList=voList;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_score_dialog,null);
        ListView listView=(ListView) view.findViewById(R.id.score_list);
        TextView view1=(TextView) view.findViewById(R.id.student_total_number);
        view1.setText(voList.size()+"");
        if(this.voList!=null){
            List<Map<String, String>> names = new ArrayList<>();
            Map<String, String> map;
            for (int i = 0; i < voList.size(); i++) {
                map = new HashMap<>();
                map.put("studentId", voList.get(i).getStudentId());
                map.put("studentName", voList.get(i).getStudentName());
                map.put("studentNumber", voList.get(i).getStudentNumber());
                map.put("score", voList.get(i).getScore());
                map.put("scored", voList.get(i).getScored());

                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.student_score_list,
                    new String[]{"studentId","studentName","studentNumber","score","scored"},
                    new int[]{R.id.studentId,R.id.studentName,R.id.studentNumber,R.id.studentScore,R.id.studentScored});
            listView.setAdapter(simpleAdapter);
        }
        builder.setView(view);

        return builder.create();
    }


}

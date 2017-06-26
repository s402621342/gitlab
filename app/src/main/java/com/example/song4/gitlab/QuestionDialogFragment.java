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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.song4.gitlab.VO.QuestionVO;
import com.example.song4.gitlab.VO.StudentVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDialogFragment extends DialogFragment {
    private List<QuestionVO> voList;
    public QuestionDialogFragment(){

    }

    public void setVoList(List<QuestionVO> voList){
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
                map.put("id", voList.get(i).getId());
                map.put("title", voList.get(i).getTitle());
                map.put("description", voList.get(i).getDescription());
                map.put("difficulty", voList.get(i).getDifficulty());
                map.put("gitUrl", voList.get(i).getGitUrl());
                map.put("type", voList.get(i).getType());
                map.put("creator_id", voList.get(i).getCreator_id());
                map.put("creator_username", voList.get(i).getCreator_username());
                map.put("creator_name", voList.get(i).getCreator_name());
                map.put("creator_type", voList.get(i).getCreator_type());
                map.put("creator_avatar", voList.get(i).getCreator_avatar());
                map.put("creator_gender", voList.get(i).getCreator_gender());
                map.put("creator_email", voList.get(i).getCreator_email());
                map.put("creator_schoolId", voList.get(i).getCreator_schooId());
                map.put("duration", voList.get(i).getDuration());
                map.put("link", voList.get(i).getLink());
                map.put("knowledgeVos", voList.get(i).getKnowledgeVos());
                map.put("readme",voList.get(i).getReadme());
                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.question_list,
                    new String[]{"id","title","description","difficulty","gitUrl","type","creator_id","creator_username","creator_name",
                    "creator_type","creator_avatar","creator_gender","creator_email","creator_schoolId","duration","link","knowledgeVos","readme"},
                    new int[]{R.id.questionId,R.id.questionTitle,R.id.questionDescription,R.id.questionDifficulty,R.id.questionGitUrl,R.id.questionType,
                    R.id.questionCreatorId,R.id.questionCreatorUsername,R.id.questionCreatorName,R.id.questionCreatorType,R.id.questionCreatorAvatar,
                    R.id.questionCreatorGender,R.id.questionCreatorEmail,R.id.questionCreatorSchoolId,R.id.questionDuration,R.id.questionLink,
                            R.id.questionKnowledgeVos,R.id.questionReadme});
            listView.setAdapter(simpleAdapter);
        }
        builder.setView(view);
        return builder.create();
    }


}

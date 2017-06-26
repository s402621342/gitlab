package com.example.song4.gitlab;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.song4.gitlab.VO.AssignmentVO;
import com.example.song4.gitlab.VO.ExamVO;
import com.example.song4.gitlab.VO.QuestionVO;
import com.example.song4.gitlab.VO.SimpleStudentVO;
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
public class AssignmentScoreFragment extends ListFragment {
    private byte[] tokan;
    private Handler handler;
    private List<AssignmentVO> assignmentVOList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        assignmentVOList=new ArrayList<>();

    }

    @Override
    public void onStart(){
        super.onStart();
        Spinner spinner = (Spinner) (getView().findViewById(R.id.assignment_spinner));
        List<String> assignments=new ArrayList<>();
        assignments.add("38");
        assignments.add("93");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, assignments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String assignmentId = (String) parent.getItemAtPosition(position);
                assignmentVOList.clear();
                ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                getScore(assignmentId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getScore(final String assignmentId){
        Intent intent=getActivity().getIntent();

        if(intent!=null) {
            tokan = intent.getByteArrayExtra("tokan");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://115.29.184.56:8090/api/assignment/"+assignmentId+"/score");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Authorization", "Basic " + new String(tokan));
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoInput(true);
                        int response = connection.getResponseCode();            //获得服务器的响应码
                        if (response == HttpURLConnection.HTTP_OK) {
                            InputStream in = connection.getInputStream();
                            BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                            StringBuilder responseString = new StringBuilder();
                            String line = line = bufr.readLine();
                            if (line != null) {
                                do {
                                    responseString.append(line);
                                } while ((line = bufr.readLine()) != null);

                                if (connection != null) {
                                    connection.disconnect();
                                }
                                JSONObject object=new JSONObject(responseString.toString());
                                JSONArray jsonArray = object.getJSONArray("questions");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject question=jsonArray.getJSONObject(i);
                                    JSONArray students=question.getJSONArray("students");
                                    List<SimpleStudentVO> studentVOs=new ArrayList<SimpleStudentVO>();
                                    for(int j=0;j<students.length();j++){
                                        JSONObject student=students.getJSONObject(j);
                                        studentVOs.add(new SimpleStudentVO(student.getString("studentId"),student.getString("studentName"),
                                                student.getString("studentNumber"),student.getString("score"),student.getString("scored")));
                                    }
                                    JSONObject questionInfo=question.getJSONObject("questionInfo");
                                    assignmentVOList.add(new AssignmentVO(questionInfo.getString("id"),questionInfo.getString("title"),
                                            questionInfo.getString("description"),questionInfo.getString("type"),studentVOs));
                                }
                                handler.post(updateQuestionList);
                            } else {
                                System.out.println("fail");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                }
            }).start();
        }
    }

    Runnable   updateQuestionList=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            List<Map<String, String>> names = new ArrayList<>();
            Map<String, String> map;
            for (int i = 0; i < assignmentVOList.size(); i++) {
                map = new HashMap<>();
                map.put("id", assignmentVOList.get(i).getId());
                map.put("title", assignmentVOList.get(i).getTitle());
                map.put("description", assignmentVOList.get(i).getDescription());
                map.put("type", assignmentVOList.get(i).getType());
                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.assginment_question_list,
                    new String[]{"id","title","description","type"},
                    new int[]{R.id.assignmentId, R.id.assignmentType,R.id.assignmentDescription,R.id.assignmentType});
            ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            setListAdapter(simpleAdapter);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<SimpleStudentVO> vos=assignmentVOList.get(position).getStudentVOList();
                    ScoreDialogFragment scoreDialogFragment=new ScoreDialogFragment();
                    scoreDialogFragment.setVoList(vos);
                    scoreDialogFragment.show(getFragmentManager(),"scoreDialog");
                }
            });

        }

    };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_score, container, false);
    }

}

package com.example.song4.gitlab;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.song4.gitlab.VO.ExamVO;
import com.example.song4.gitlab.VO.QuestionVO;

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
public class HomeworkListFragment extends ListFragment {
    protected byte[] tokan;
    protected Handler handler;
    protected List<ExamVO> examVOList;
    protected String studentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        examVOList=new ArrayList<>();
    }
    @Override
    public void onStart(){
        super.onStart();
        //班级名称选择器
        Intent intent=getActivity().getIntent();
        examVOList.clear();
        if(intent!=null) {
            tokan = intent.getByteArrayExtra("tokan");
            studentId=intent.getStringExtra("studentId");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://115.29.184.56:8090/api/course/2/homework");
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
                                JSONArray jsonArray = new JSONArray(responseString.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String assignmentid=object.getString("id");
                                    JSONArray questionArray=object.getJSONArray("questions");
                                    List<QuestionVO> questionVOs=new ArrayList<>();
                                    for(int j=0;j<questionArray.length();j++){
                                        JSONObject question=questionArray.getJSONObject(j);
                                        JSONObject creator=question.getJSONObject("creator");
                                        String id=question.getString("id");
                                        //获取readme
                                        String readme="";
                                        if(studentId!=null) {
                                            url = new URL("http://115.29.184.56:8090/api/assignment/" + assignmentid + "/student/" + studentId + "/question/" + id);
                                            connection = (HttpURLConnection) url.openConnection();
                                            connection.setRequestMethod("GET");
                                            connection.setRequestProperty("Authorization", "Basic " + new String(tokan));
                                            connection.setRequestProperty("Content-Type", "application/json");
                                            response = connection.getResponseCode();            //获得服务器的响应码
                                            if (response == HttpURLConnection.HTTP_OK) {
                                                in = connection.getInputStream();
                                                bufr = new BufferedReader(new InputStreamReader(in));
                                                responseString = new StringBuilder();
                                                line = bufr.readLine();
                                                if (line != null) {
                                                    do {
                                                        responseString.append(line);
                                                    } while ((line = bufr.readLine()) != null);

                                                    if (connection != null) {
                                                        connection.disconnect();
                                                    }
                                                }
                                                try {
                                                    JSONObject contentObject = new JSONObject(responseString.toString());
                                                    readme = contentObject.getString("content");
                                                } catch (Exception e) {
                                                    //可能没有readme
                                                }
                                            }
                                        }
                                        questionVOs.add(new QuestionVO(question.getString("id"),question.getString("title"),question.getString("description"),
                                                question.getString("difficulty"),question.getString("gitUrl"),question.getString("type"),
                                                creator.getString("id"),creator.getString("username"),creator.getString("name"),creator.getString("type"),
                                                creator.getString("avatar"),creator.getString("gender"),creator.getString("email"),creator.getString("schoolId"),
                                                question.getString("duration"),question.getString("link"),question.getString("knowledgeVos"),readme));

                                        //获取readme


                                    }
                                    examVOList.add(new ExamVO(object.getString("id"),object.getString("title"),object.getString("description"),
                                            object.getString("startAt"),object.getString("endAt"),questionVOs,object.getString("course"),
                                            object.getString("status"),object.getString("currentTime")));

                                }

                                handler.post(setExamList);

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
    Runnable setExamList=new Runnable() {
        @Override
        public void run() {
            List<Map<String, String>> names = new ArrayList<>();
            Map<String, String> map;
            for (int i = 0; i < examVOList.size(); i++) {
                map = new HashMap<>();
                map.put("id", examVOList.get(i).getId());
                map.put("title", examVOList.get(i).getTitle());
                map.put("description", examVOList.get(i).getDescription());
                map.put("startAt", examVOList.get(i).getStartAt());
                map.put("endAt", examVOList.get(i).getEndAt());
                map.put("course", examVOList.get(i).getCourse());
                map.put("status", examVOList.get(i).getStatus());
                map.put("currentTime", examVOList.get(i).getCurrentTime());
                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.exam_list,
                    new String[]{"id","title","description","startAt","endAt","course","status","currentTime"},
                    new int[]{R.id.examId,R.id.examTitle,R.id.examDescription,R.id.examStartAt,R.id.examEndAt,R.id.examCourse,R.id.examStatus, R.id.examCurrentTime});
            ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            setListAdapter(simpleAdapter);
            setListViewListener();

        }
    };
    public  void setListViewListener(){
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //产生问题列表
                List<QuestionVO> questionVO=examVOList.get(position).getQuestions();
                QuestionDialogFragment fragment=new QuestionDialogFragment();
                fragment.setVoList(questionVO);
                fragment.show(getFragmentManager(),"questions");
            }
        });

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homework_list, container, false);
    }

}

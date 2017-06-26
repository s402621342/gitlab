package com.example.song4.gitlab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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


public class StudentListFragment extends ListFragment{
    private byte[] tokan;
    private Map<String,String> classmap;
    private List<StudentVO> studentVOList;
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classmap=new HashMap<>();
        studentVOList=new ArrayList<>();
        handler=new Handler();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        //班级名称选择器
        Intent intent=getActivity().getIntent();
        if(intent!=null) {
            tokan = intent.getByteArrayExtra("tokan");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://115.29.184.56:8090/api/group");
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
                                    classmap.put(object.getString("name"), object.getString("id"));
                                }
                                handler.post(setSpinner);
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

    Runnable   setSpinner=new  Runnable(){
        @Override
        public void run() {
            Spinner spinner = (Spinner) (getView().findViewById(R.id.class_spinner));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(classmap.keySet()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String name = (String) parent.getItemAtPosition(position);
                    studentVOList.clear();
                    ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    getStudentList(classmap.get(name));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    };

    private void getStudentList(String groupId){
        final String groupID=groupId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://115.29.184.56:8090/api/group/"+groupID+"/students");
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Authorization","Basic "+new String(tokan));
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoInput(true);
                    int response = connection.getResponseCode();            //获得服务器的响应码
                    if(response == HttpURLConnection.HTTP_OK) {
                        InputStream in = connection.getInputStream();
                        BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                        StringBuilder responseString = new StringBuilder();
                        String line = line = bufr.readLine();
                        if (line!=null) {
                            do {
                                responseString.append(line);
                            } while ((line = bufr.readLine()) != null);

                            if (connection != null) {
                                connection.disconnect();
                            }
                            JSONArray jsonArray=new JSONArray(responseString.toString());
                            studentVOList.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                studentVOList.add(new StudentVO(object.getString("id"),object.getString("username"),object.getString("name"),object.getString("type"),
                                        object.getString("avatar"),object.getString("gender"),object.getString("email"),object.getString("schoolId"),
                                        object.getString("gitId"),object.getString("number"), object.getString("groupId"),object.getString("gitUsername")));
                            }
                            handler.post(updateStudentList);

                        }else {
                            System.out.println("fail");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace(System.out);
                }
            }
        }).start();
    }
    Runnable   updateStudentList=new  Runnable(){
        @Override
        public void run() {
            //更新界面
                List<Map<String, String>> names = new ArrayList<>();
                Map<String, String> map;
                for (int i = 0; i < studentVOList.size(); i++) {
                    map = new HashMap<>();
                    map.put("number", studentVOList.get(i).getNumber());
                    map.put("name", studentVOList.get(i).getName());
                    names.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.student_list, new String[]{"number","name"}, new int[]{R.id.studentNumber, R.id.studentName});
                ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);

                setListAdapter(simpleAdapter);
                final TextView textView=(TextView) getView().findViewById(R.id.student_total);
                textView.setText(names.size()+"");

                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StudentVO vo=studentVOList.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View view1=inflater.inflate(R.layout.studentinfo_dialog, null);
                        ((TextView)view1.findViewById(R.id.student_id)).setText(vo.getId());
                        ((TextView)view1.findViewById(R.id.student_name)).setText(vo.getName());
                        ((TextView)view1.findViewById(R.id.student_number)).setText(vo.getNumber());
                        ((TextView)view1.findViewById(R.id.student_type)).setText(vo.getType());
                        ((TextView)view1.findViewById(R.id.student_avatar)).setText(vo.getAvatar());
                        ((TextView)view1.findViewById(R.id.student_gender)).setText(vo.getGender());
                        ((TextView)view1.findViewById(R.id.student_email)).setText(vo.getEmail());
                        ((TextView)view1.findViewById(R.id.student_schoolId)).setText(vo.getSchoolId());
                        ((TextView)view1.findViewById(R.id.student_gitId)).setText(vo.getGitId());
                        ((TextView)view1.findViewById(R.id.student_groupId)).setText(vo.getGroupId());
                        ((TextView)view1.findViewById(R.id.student_gitUsername)).setText(vo.getGitUsername());
                        builder.setView(view1);
                        AlertDialog dialog=builder.create();
                        dialog.show();

                    }
                });

        }

    };


}
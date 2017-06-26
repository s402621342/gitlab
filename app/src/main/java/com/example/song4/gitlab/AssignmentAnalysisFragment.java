package com.example.song4.gitlab;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.song4.gitlab.VO.AnalysisVO;
import com.example.song4.gitlab.VO.TestcaseVO;

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
public class AssignmentAnalysisFragment extends ListFragment {


    public AssignmentAnalysisFragment() {
        // Required empty public constructor
    }
    private byte[] tokan;
    private Handler handler;
    private List<AnalysisVO> analysisVOs;
    private String studentId;
    private String assignmentId;

    public void setAssigmentId(String assignmentId){
        this.assignmentId=assignmentId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        analysisVOs=new ArrayList<>();

    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent=getActivity().getIntent();

        if(intent!=null) {
            tokan = intent.getByteArrayExtra("tokan");
            studentId=intent.getStringExtra("studentId");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://115.29.184.56:8090/api/assignment/"+assignmentId+"/student/"+studentId+"/analysis");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Authorization", "Basic " + new String(tokan));
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoInput(true);
                        int response = connection.getResponseCode();            //获得服务器的响应码
                        System.out.println(response);
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
                                JSONArray array=object.getJSONArray("questionResults");
                                if(array.length()==0){
                                    //设置标题为暂无分析结果
                                    handler.post(setText);
                                }else{
                                    for(int i=0;i<array.length();i++){
                                        JSONObject result=array.getJSONObject(i);
                                        JSONObject scoreResult=result.getJSONObject("scoreResult");
                                        JSONObject testResult=result.getJSONObject("testResult");
                                        JSONObject metricData=result.getJSONObject("metricData");
                                        List<TestcaseVO> testcaseVOs=new ArrayList<TestcaseVO>();
                                        try {       //可能没有testCase
                                            JSONArray testcases=testResult.getJSONArray("testcases");
                                            for (int j = 0; j < testcases.length(); j++) {
                                                testcaseVOs.add(new TestcaseVO(testcases.getJSONObject(j).getString("name"),
                                                        testcases.getJSONObject(j).getString("passed")));
                                            }
                                        }catch (Exception e){

                                        }
                                            analysisVOs.add(new AnalysisVO(result.getString("questionId"),result.getString("questionTitle"),
                                                    scoreResult.getString("git_url"),scoreResult.getString("score"),scoreResult.getString("scored"),
                                                    testResult.getString("git_url"),testResult.getString("compile_succeeded"),
                                                    testResult.getString("tested"),testcaseVOs,metricData.getString("git_url"),
                                                    metricData.getString("measured"),metricData.getString("total_line_count"),
                                                    metricData.getString("comment_line_count"),metricData.getString("field_count"),
                                                    metricData.getString("method_count"),metricData.getString("max_coc")));

                                        }
                                    handler.post(setAnalysis);
                                }
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

    Runnable setText=new Runnable() {
        @Override
        public void run(){
            ((TextView)getView().findViewById(R.id.analysis_title)).setText(R.string.no_analyes);
            ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }


    };

    Runnable setAnalysis=new Runnable() {
        @Override
        public void run(){
            List<Map<String, String>> names = new ArrayList<>();
            Map<String, String> map;
            for (int i = 0; i < analysisVOs.size(); i++) {
                map = new HashMap<>();
                map.put("questionId", analysisVOs.get(i).getQuestionId());
                map.put("questionTitle", analysisVOs.get(i).getQuestionTitle());
                map.put("score_git_url", analysisVOs.get(i).getScore_git_url());
                map.put("score_score", analysisVOs.get(i).getScore_score());
                map.put("score_scored", analysisVOs.get(i).getScore_scored());
                map.put("test_git_url", analysisVOs.get(i).getTest_git_url());
                map.put("test_compile_succeeded", analysisVOs.get(i).getTest_compile_succeeded());
                map.put("test_tested", analysisVOs.get(i).getTest_tested());
                map.put("metric_git_url", analysisVOs.get(i).getMetric_git_url());
                map.put("metric_measured", analysisVOs.get(i).getMetric_measured());
                map.put("metric_total_line_count", analysisVOs.get(i).getMetric_total_line_count());
                map.put("metric_comment_line_count", analysisVOs.get(i).getMetric_comment_line_count());
                map.put("metric_field_count", analysisVOs.get(i).getMetric_field_count());
                map.put("metric_method_count", analysisVOs.get(i).getMetric_method_count());
                map.put("metric_max_coc", analysisVOs.get(i).getMetric_max_coc());
                names.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), names, R.layout.analysis_list,
                    new String[]{"questionId","questionTitle","score_git_url","score_score","score_scored","test_git_url","test_compile_succeeded",
                            "test_tested","metric_git_url","metric_measured","metric_total_line_count","metric_comment_line_count",
                            "metric_field_count","metric_method_count","metric_max_coc"},
                    new int[]{R.id.analysisId,R.id.analysisTitle,R.id.analysisScoreGiturl,R.id.analysisScoreScore,R.id.analysisScoreScored,
                            R.id.analysisTestGiturl,R.id.analysisTestCompile, R.id.analysisTestTested,R.id.analysisMetricGiturl,
                            R.id.analysisMetricMeasured,R.id.analysisMetricTotalLine,R.id.analysisMetricCommmentLine,R.id.analysisMetricField,
                            R.id.analysisMetricMethod,R.id.analysisMetricMax});
            ProgressBar progressBar=(ProgressBar)getView().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            setListAdapter(simpleAdapter);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<TestcaseVO> testcaseVOs=analysisVOs.get(position).getTestcaseVOList();
                    if(testcaseVOs.size()==0){
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setMessage(getString(R.string.noTestcases));
                        builder.show();
                    }else {
                        TestcasesDialogFragment fragment = new TestcasesDialogFragment();
                        fragment.setVoList(testcaseVOs);
                        fragment.show(getFragmentManager(), "analysis");
                    }

                }
            });
        }


    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_analysis, container, false);
    }

}

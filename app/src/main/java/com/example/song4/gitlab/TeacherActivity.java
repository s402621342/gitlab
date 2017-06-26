package com.example.song4.gitlab;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeacherActivity extends AppCompatActivity {

    private StudentListFragment studentListFragment;
    private ExamListFragment examListFragment;
    private HomeworkListFragment homeworkListFragment;
    private ExerciseListFragment exerciseListFragment;
    private AssignmentScoreFragment assignmentScoreFragment;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_drawer);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);










    ListView listView=(ListView)findViewById(R.id.left_drawer);
        String[] mMenuTitles=getResources().getStringArray(R.array.teacher_drawer_list);
        try {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drawer_list,mMenuTitles);
            listView.setAdapter(arrayAdapter);
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        //默认打开作业列表
        listView.setItemChecked(1,true);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(homeworkListFragment==null){
            homeworkListFragment=new HomeworkListFragment();
        }
        transaction.replace(R.id.frame,homeworkListFragment);
        transaction.commit();

        //




    }


    private void selectItem(int position){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        switch (position){
            case 0:
                if(studentListFragment==null){
                    studentListFragment=new StudentListFragment();
                }
                transaction.replace(R.id.frame,studentListFragment);
                transaction.commit();
                break;
            case 1:
                if(homeworkListFragment==null){
                    homeworkListFragment=new HomeworkListFragment();
                }
                transaction.replace(R.id.frame,homeworkListFragment);
                transaction.commit();
                break;
            case 2:
                if(exerciseListFragment==null){
                    exerciseListFragment=new ExerciseListFragment();
                }
                transaction.replace(R.id.frame,exerciseListFragment);
                transaction.commit();
                break;
            case 3:
                if(examListFragment==null){
                    examListFragment=new ExamListFragment();
                }
                transaction.replace(R.id.frame,examListFragment);
                transaction.commit();
                break;
            case 4:
                if(assignmentScoreFragment==null){
                    assignmentScoreFragment=new AssignmentScoreFragment();
                }
                transaction.replace(R.id.frame,assignmentScoreFragment);
                transaction.commit();
                break;
        }
    }
}

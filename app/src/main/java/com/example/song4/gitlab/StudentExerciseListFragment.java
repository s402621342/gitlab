package com.example.song4.gitlab;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.song4.gitlab.VO.QuestionVO;

import java.util.List;

/**
 * Created by song4 on 2017/6/25.
 */

public class StudentExerciseListFragment extends ExerciseListFragment {
    private android.view.ActionMode mActionMode;
    private int clickedPostion=-1;
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.student_action_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.analyse:
                    mode.finish();
                    String assignmentId=examVOList.get(clickedPostion).getId();
                    ((StudentActivity)getActivity()).analysis(assignmentId);
                    break;
                case R.id.look:
                    mode.finish();
                    List<QuestionVO> questionVO=examVOList.get(clickedPostion).getQuestions();
                    QuestionDialogFragment fragment=new QuestionDialogFragment();
                    fragment.setVoList(questionVO);
                    fragment.show(getFragmentManager(),"questions");
                    break;
            }
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
    @Override
    public  void setListViewListener(){
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return;
                }
                clickedPostion=position;
                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                return;


            }
        });
    }
}

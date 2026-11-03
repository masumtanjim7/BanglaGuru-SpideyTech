package com.banglaguru.app.ui.mcq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.banglaguru.app.R;

public class McqSetupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcq_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup rgTopic = view.findViewById(R.id.rg_topic);
        RadioGroup rgMode = view.findViewById(R.id.rg_mode);
        View btnStart = view.findViewById(R.id.btn_start_practice);

        btnStart.setOnClickListener(v -> {
            String topic;
            int topicId = rgTopic.getCheckedRadioButtonId();
            if (topicId == R.id.rb_topic_grammar) topic = "grammar";
            else if (topicId == R.id.rb_topic_literature) topic = "literature";
            else topic = "random";

            int count;
            int modeId = rgMode.getCheckedRadioButtonId();
            if (modeId == R.id.rb_mode_easy) count = 10;
            else if (modeId == R.id.rb_mode_medium) count = 25;
            else if (modeId == R.id.rb_mode_hard) count = 50;
            else {
                count = 20;
                topic = "random";
            }

            Bundle args = new Bundle();
            args.putString("sessionType", "mcq");
            args.putString("topic", topic);
            args.putInt("count", count);
            Navigation.findNavController(view).navigate(R.id.action_mcqSetup_to_mcqQuestion, args);
        });
    }
}

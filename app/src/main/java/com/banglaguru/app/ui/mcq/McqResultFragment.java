package com.banglaguru.app.ui.mcq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.banglaguru.app.R;
import com.banglaguru.app.data.model.Mcq;

import java.util.List;

public class McqResultFragment extends Fragment {

    private McqViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mcq_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(McqViewModel.class);

        TextView tvScore = view.findViewById(R.id.tv_score);
        List<Mcq> questions = viewModel.getQuestions().getValue();
        int total = questions == null ? 0 : questions.size();
        int correct = viewModel.getCorrectCount();
        tvScore.setText(getString(R.string.result_score, total, correct));

        view.findViewById(R.id.btn_try_again).setOnClickListener(v ->
                Navigation.findNavController(view).popBackStack(R.id.mcqSetupFragment, false));
    }
}

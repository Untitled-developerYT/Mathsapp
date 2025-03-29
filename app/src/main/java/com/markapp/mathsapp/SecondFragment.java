package com.markapp.mathsapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.markapp.mathsapp.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private ItemViewModel viewModel;
    private Problem problem;
    private SoundPool sounds;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        SoundPool.Builder soundBuilder = new SoundPool.Builder();
        soundBuilder.setMaxStreams(2);
        sounds = soundBuilder.build();
        sounds.load(getContext(),R.raw.failure,1);
        sounds.load(getContext(),R.raw.success, 1);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        int choice = viewModel.getSelectedItem();
        TextView theText= (TextView)getActivity().findViewById(R.id.textview_second);
        EditText Answer= (EditText)getActivity().findViewById(R.id.editAnswer);
        System.out.println(choice);
        int min = viewModel.getMin();
        int max = viewModel.getMax();
        TextView correct_num= (TextView)getActivity().findViewById(R.id.correct_num);
        TextView wrong_num = (TextView)getActivity().findViewById(R.id.wrong_num);
        correct_num.setText(String.valueOf(viewModel.getCorrect()));
        wrong_num.setText(String.valueOf(viewModel.getWrong()));
        problem= new Problem(min, max, choice);
        problem.generate();
        theText.setText(problem.queryString());
        Answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans= Answer.getText().toString();
                boolean correct;
                try{
                    correct= problem.check(Integer.parseInt(ans));
                }
                catch(Exception e){
                    correct = false;
                }
                if (ans.length() > 0) {
                    if (correct) {
                        sounds.play(2, 1.0f, 1.0f, 0, 0, 1.0f);
                        Toast.makeText(getActivity().getApplicationContext(), "That's correct! Well done.", Toast.LENGTH_SHORT).show();
                        System.out.println("correct");
                        viewModel.setCorrect(viewModel.getCorrect()+1);
                        viewModel.writeData();
                        correct_num.setText(String.valueOf(viewModel.getCorrect()));
                        problem.generate();
                        theText.setText(problem.queryString());
                        Answer.setText("");
                    }
                    else {
                        viewModel.setWrong(viewModel.getWrong()+1);
                        viewModel.writeData();
                        wrong_num.setText(String.valueOf(viewModel.getWrong()));
                        sounds.play(1,1.0f,1.0f, 0, 0, 1.0f);
                        Answer.setText("");
                        Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Activity activity = getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
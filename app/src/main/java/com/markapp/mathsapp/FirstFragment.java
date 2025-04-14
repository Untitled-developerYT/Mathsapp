package com.markapp.mathsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.markapp.mathsapp.databinding.FragmentFirstBinding;

import java.io.File;
import java.io.RandomAccessFile;

public class FirstFragment extends Fragment {
    private ItemViewModel viewModel;
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        androidx.constraintlayout.widget.ConstraintLayout mainLayout = binding.mainLayout;
        Button[] buttons = new Button[10];
        for (int i = 0; i < 10; i++) {
            buttons[i] = new android.widget.Button(getContext());
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("button pressed: "+Integer.toString(view.getId()-99));
                }
            });
            buttons[i].setText(Integer.toString(i+1));
            buttons[i].setId(100+i);
            android.view.ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 100);
            buttons[i].setLayoutParams(params);
            mainLayout.addView(buttons[i]);
            ConstraintSet cSet = new ConstraintSet();
            cSet.clone(mainLayout);
            if (i < 2) {
                cSet.connect(buttons[i].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 100);
            } else {
                cSet.connect(buttons[i].getId(), ConstraintSet.TOP, buttons[i-2].getId(), ConstraintSet.BOTTOM, 70);
            }
            if (i % 2 == 0) {
                cSet.connect(buttons[i].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 100);
            } else {
                cSet.connect(buttons[i].getId(), ConstraintSet.LEFT, buttons[i-1].getId(), ConstraintSet.RIGHT, 70);
            }
            cSet.applyTo(mainLayout);
        }

        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        Context context= getContext();
        String filename = "myfile";
        try {
            File fos = new File(context.getFilesDir(), filename); //, Context.MODE_PRIVATE);
            viewModel.file = new RandomAccessFile(fos, "rw");
            //viewModel.writeData();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            viewModel.readData();
        } catch (Exception e) {
            System.out.println(e);
        }
        binding.buttonMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem(0);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem(1);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem(2);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem(3);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonSol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectItem(4);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
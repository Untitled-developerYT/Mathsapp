package com.markapp.mathsapp;

import android.content.Context;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Objects;


public class FirstFragment extends Fragment {
    private ItemViewModel viewModel;
    private FragmentFirstBinding binding;

    private String readTextFromUri(String filename) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getActivity().openFileInput(filename);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        androidx.constraintlayout.widget.ConstraintLayout mainLayout = binding.mainLayout;
        Context context = getContext();
        String filename = "problems.json";
        JSONArray jsonAr = null;
        try {
            // Open the JSON file from the assets folder
            System.out.println(filename);
            String content = readTextFromUri(filename);;
            System.out.println(content);
            // Parse the JSON content
            jsonAr = new JSONArray(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button[] buttons = new Button[10];
        for (int i = 0; i < jsonAr.length(); i++) {
            buttons[i] = new android.widget.Button(getContext());
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("button pressed: "+Integer.toString(view.getId()-99));
                }
            });
            try {
                System.out.println(jsonAr.get(i));
                buttons[i].setText(jsonAr.getJSONObject(i).getString("question"));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
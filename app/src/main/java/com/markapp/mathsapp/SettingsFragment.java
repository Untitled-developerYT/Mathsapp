package com.markapp.mathsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.snackbar.Snackbar;
import com.markapp.mathsapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private ItemViewModel viewModel;
    public SettingsFragment() {
        // Required empty public constructor
    }
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        EditText EditMin = (EditText)getActivity().findViewById(R.id.editMin);
        EditText EditMax = (EditText)getActivity().findViewById(R.id.editMax);
        EditMin.setText(Integer.toString(viewModel.getMin()));
        EditMax.setText(Integer.toString(viewModel.getMax()-1));
        Button buttonClear = (Button)getActivity().findViewById(R.id.clear_data);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setCorrect(0);
                viewModel.setWrong(0);
                viewModel.writeData();
                Snackbar.make(view, "Data cleared!", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
        Button buttonOk= (Button)getActivity().findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText EditMin = (EditText)getActivity().findViewById(R.id.editMin);
                EditText EditMax = (EditText)getActivity().findViewById(R.id.editMax);
                int min, max;
                try {
                    min= Integer.parseInt(EditMin.getText().toString());
                }
                catch (Exception e) {
                    min = 0;
                }
                try {
                    max= Integer.parseInt(EditMax.getText().toString()) + 1;
                }
                catch (Exception e) {
                    max = 0;
                }
                if (max > min) {
                    viewModel.set_min(min);
                    viewModel.set_max(max);
                    viewModel.writeData();
                }
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
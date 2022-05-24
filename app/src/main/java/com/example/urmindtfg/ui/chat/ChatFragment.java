package com.example.urmindtfg.ui.chat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urmindtfg.R;
import com.example.urmindtfg.databinding.FragmentChatBinding;
import com.example.urmindtfg.databinding.FragmentHomeBinding;
import com.example.urmindtfg.model.ChangeWindow;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnNewChat.setOnClickListener(e->{
            ChangeWindow.cambiarVentana(getActivity().getApplicationContext(), UsersActivity.class,true);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
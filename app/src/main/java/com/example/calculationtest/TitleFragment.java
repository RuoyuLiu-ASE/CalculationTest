package com.example.calculationtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculationtest.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {

    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel myViewModel;
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        myViewModel = viewModelProvider.get(MyViewModel.class);

        // 用 binding 来实现对 fragment_title 这个页面里的组件的控制
        FragmentTitleBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_title,container,false);
        binding.setData(myViewModel); // 所以 binding 实现了 controller 和 view 直接的reference的分离，不需要组件的reference来对组件进行控制
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v); // 生成控制 navigation 的对象
                controller.navigate(R.id.action_titleFragment_to_questionFragment);
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_title, container, false);
    }
}

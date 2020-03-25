package com.example.calculationtest;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final MyViewModel myViewModel;  //ViewModel 用来管理数据

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),this));
        myViewModel = viewModelProvider.get(MyViewModel.class);
        //myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class); // myViewModel is the same one with other ViewModel which is created in other files. Because these fragments belong to the same activity.
        myViewModel.generator(); // 开始出题，和数据相关的，所以是用ViewModel来管理的

        final FragmentQuestionBinding binding; //DataBinding 用来控制对应的 layout 页面上的组件显示
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        final StringBuilder builder_input_indicator = new StringBuilder();

        //将按键分为两组，所有只需要两个 Listener 就行
        View.OnClickListener listener = new View.OnClickListener() { // 第一个Listener
            @Override
            public void onClick(View v) { // 数字键的 listener ，处理事件的接口中的方法。即：负责处理发生的事件
                switch (v.getId()) {
                    case R.id.button0:
                        builder_input_indicator.append("0");
                        break;
                    case R.id.button1:
                        builder_input_indicator.append("1");
                        break;
                    case R.id.button2:
                        builder_input_indicator.append("2");
                        break;
                    case R.id.button3:
                        builder_input_indicator.append("3");
                        break;
                    case R.id.button4:
                        builder_input_indicator.append("4");
                        break;
                    case R.id.button5:
                        builder_input_indicator.append("5");
                        break;
                    case R.id.button6:
                        builder_input_indicator.append("6");
                        break;
                    case R.id.button7:
                        builder_input_indicator.append("7");
                        break;
                    case R.id.button8:
                        builder_input_indicator.append("8");
                        break;
                    case R.id.button9:
                        builder_input_indicator.append("9");
                        break;
                    case R.id.Clear_key:
                        builder_input_indicator.setLength(0); // 按下清除键之后，清除indicator的内容
                        break;
                }
                if (builder_input_indicator.length() == 0) { // 当 indicator 的内容为空时
                    binding.textView9.setText(getString(R.string.input_indicator));// 显示最开始的提示信息
                }else {
                    binding.textView9.setText(builder_input_indicator.toString());//显示输入的数字
                }
            }
        }; // 定义了 Listener 之后，把相应的button 添加相应的Listener
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.ClearKey.setOnClickListener(listener); // 清除键也是和数字键一个的 listener

        binding.SubmitKey.setOnClickListener(new View.OnClickListener() {// 提交键的Listener，处理该事件的方法是不同的
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(builder_input_indicator.toString()) == myViewModel.getAnswer().getValue()) {
                    // 当输入的数字之和等于 answer 的Value时
                    myViewModel.answerCorrect();
                    builder_input_indicator.setLength(0); //将输入的数字清零，并且显示相关的提示信息
                    binding.textView9.setText(R.string.answer_correct_messaage);
                }else {
                    NavController controller = Navigation.findNavController(v);
                    if (myViewModel.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);//跳到胜利的界面
                        myViewModel.win_flag = false;
                        myViewModel.save(); // 保存最高分的记录
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                    /****************************************************************/
                    //当答错之后，要把当前分数清零
                    myViewModel.setCurrentScore(0);
                    /****************************************************************/
                }
            }
        });

     return binding.getRoot(); // 返回这个 View
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }
}

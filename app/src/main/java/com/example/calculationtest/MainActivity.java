package com.example.calculationtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

        myViewModel = new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(MyViewModel.class);
    }

    public boolean onSupportNavigateUp() {
        if (navController.getCurrentDestination().getId() == R.id.questionFragment) {//当在提问的界面按下ActionBar时
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // 出现提示信息对话框
            builder.setTitle(getString(R.string.quit_dialog_title)); // 退出信息的对话框
            builder.setPositiveButton(R.string.dialog_positive_mes, new DialogInterface.OnClickListener() {
                @Override   // 当按下对话框的确定键时
                public void onClick(DialogInterface dialog, int which) {
                    navController.navigateUp(); //返回上一个 fragment
                    /****************************************************************/
                    myViewModel.setCurrentScore(0);  //按下确定键也得将分数清零
                    /****************************************************************/
                }
            });
            builder.setNegativeButton(R.string.dialog_negative_mes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 按下对话框的取消键时，什么也不做
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show(); // 先创建，在显示对话框。 前面一大段的代码是设置对话框的显示内容以及确定和取消键的对应动作
        }else { // 当在其他页面点击 ActionBar 时， 直接返回最开始的界面。不需要显示对话框
            navController.navigate(R.id.titleFragment); // 参数就是目的地fragment
        }

        return super.onSupportNavigateUp();     //这里其实不是很懂这个函数体
    }

    public void onBackPressed(){
        onSupportNavigateUp();  //当按下左下角的三角形箭头时的行为和按左上角的ActionBar的行为是一样的
    }
}

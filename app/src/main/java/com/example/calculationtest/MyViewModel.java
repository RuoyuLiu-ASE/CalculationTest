package com.example.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;

    private static String KEY_HIGH_SCORE = "key_high_score";
    private static String KEY_LEFT_NUMBER = "key_left_number";
    private static String KEY_RIGHT_NUMBER  = "key_right_number";
    private static String KEY_OPERATOR = "key_operator";
    private static String KEY_ANSWER = "key_answer";
    private static String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    private static String KEY_CURRENT_SCORE = "key_current_score";

    public boolean win_flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);

        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,0);
            handle.set(KEY_LEFT_NUMBER,0);
            handle.set(KEY_RIGHT_NUMBER,0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber(){
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getAnswer(){
        return handle.getLiveData(KEY_ANSWER);
    }

    public void generator() {
        int LEVEL = 20; // Level reflects the difficulty  of tasks
        Random random = new Random();
        int x,y; // represent the number which will be used in the tasks
        x = random.nextInt(LEVEL) + 1; // the range of x is between 1 ~ LEVEL
        y = random.nextInt(LEVEL) + 1;

        if (x % 2 == 0) { // When x is a even number, do add operation
            getOperator().setValue("+"); // set the operator to be +
            if (x > y) { // In the add, if x is bigger than y, let the x be the answer
                getAnswer().setValue(x); // x is the answer
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y); // x-y is the other adder
            } else { // when the y > x, let y be the answer, and x is an adder
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }// 在下一行写 else，与上面的那个 if 来对齐，代码会好看很多。而且这个分支也最好是在一开始写 if 的时候就生成比较好
        } else { // when x is an odd number
            getOperator().setValue("-");
            if (x > y) { // x is bigger
                getAnswer().setValue(x - y);
                getRightNumber().setValue(y);
                getLeftNumber().setValue(x);
            }else {
                getAnswer().setValue(y - x);
                getRightNumber().setValue(x);
                getLeftNumber().setValue(y);
            }
        }
    }

    public void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit(); // make editor to store data
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue()); // store the highest score
        editor.apply();
    }

    //If the answer is right
    public void answerCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1); // if the answer is right,score + 1
        if (getCurrentScore().getValue() > getHighScore().getValue()) { // Make the highest record
            getHighScore().setValue(getCurrentScore().getValue()); // Make the current score be the highest score
            win_flag = true;
        }
        generator(); // After record the score, generator the next task
    }

    //Set the current score to 0
    public void setCurrentScore(int currentScore){
        getCurrentScore().setValue(currentScore);
    }
}

package my.edu.tarc.ezcharge.Charging;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import my.edu.tarc.ezcharge.R;
public class ChargingPinActivity extends AppCompatActivity implements View.OnClickListener {

    //Testing for 2 activity in 1 activity
    String platNo = "1";

    View dot_1, dot_2, dot_3, dot_4;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_clear;

    View backPin;
    ArrayList<String> number_list = new ArrayList<>();

    String passcode = "";
    String num_01, num_02, num_03, num_04;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_pin);
        initializeCom();

        backPin = findViewById(R.id.imageViewBackPin);

        backPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initializeCom(){
        dot_1 = findViewById(R.id.dot_1);
        dot_2 = findViewById(R.id.dot_2);
        dot_3 = findViewById(R.id.dot_3);
        dot_4 = findViewById(R.id.dot_4);


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btn_clear = findViewById(R.id.btn_clear);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                number_list.add("1");
                passNumber(number_list);
                break;
            case R.id.btn2:
                number_list.add("2");
                passNumber(number_list);
                break;
            case R.id.btn3:
                number_list.add("3");
                passNumber(number_list);
                break;
            case R.id.btn4:
                number_list.add("4");
                passNumber(number_list);
                break;
            case R.id.btn5:
                number_list.add("5");
                passNumber(number_list);
                break;
            case R.id.btn6:
                number_list.add("6");
                passNumber(number_list);
                break;
            case R.id.btn7:
                number_list.add("7");
                passNumber(number_list);
                break;
            case R.id.btn8:
                number_list.add("8");
                passNumber(number_list);
                break;
            case R.id.btn9:
                number_list.add("9");
                passNumber(number_list);
                break;
            case R.id.btn0:
                number_list.add("0");
                passNumber(number_list);
                break;
            case R.id.btn_clear:
                number_list.clear();
                passNumber(number_list);
                break;
        }
    }

    private void passNumber(ArrayList<String> number_list) {
        if(number_list.size() == 0){
            dot_1.setBackgroundResource(R.drawable.pin_dot_grey);
            dot_2.setBackgroundResource(R.drawable.pin_dot_grey);
            dot_3.setBackgroundResource(R.drawable.pin_dot_grey);
            dot_4.setBackgroundResource(R.drawable.pin_dot_grey);
        }else{
            switch(number_list.size()){
                case 1:
                    num_01 = number_list.get(0);
                    dot_1.setBackgroundResource(R.drawable.pin_dot_enter);
                    break;
                case 2:
                    num_02 = number_list.get(1);
                    dot_2.setBackgroundResource(R.drawable.pin_dot_enter);
                    break;
                case 3:
                    num_03 = number_list.get(2);
                    dot_3.setBackgroundResource(R.drawable.pin_dot_enter);
                    break;
                case 4:
                    num_04 = number_list.get(3);
                    dot_4.setBackgroundResource(R.drawable.pin_dot_enter);
                    passcode = num_01 + num_02 + num_03 + num_04;
                    matchPassCode();
//                    if(getPasscode().length() == 0){
//                        savePassCode(passcode);
//                    }else{
//                        matchPassCode();
//                    }
                    break;
            }
        }
    }

    private void matchPassCode() {
        if(passcode.equals("1234")){ //Here need to integrate with user pin
            if(platNo.equals("")){//if null then view go to charging progress bar
                startActivity(new Intent(this, ChargingActivity.class));
            }else{//if not null then view go to receipt
                startActivity(new Intent(this, ChargingCompleteActivity.class));
            }
        }else{
            Toast.makeText(this, getString(R.string.pin_not_match), Toast.LENGTH_SHORT).show();
        }
    }

//    private SharedPreferences.Editor savePassCode(String passcode){
//        SharedPreferences preferences = getSharedPreferences("passcode_pref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("passcode", passcode);
//        editor.apply();
//
//        return editor;
//    }

//    private String getPasscode(){
//        SharedPreferences preferences = getSharedPreferences("passcode_pref", Context.MODE_PRIVATE);
//        return preferences.getString("passcode", "1234");
//    }
}

package com.example.android_feng37_bank;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int[] bank_savings = new int[10];
    int Calc_Sum_Saving()
    {
        int sum_saving = 0;
        for (int i = 0; i < 10; i++)
        {
            sum_saving += bank_savings[i];
        }
        return sum_saving;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner1 = findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.banks_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner1.setAdapter(adapter);
        EditText editText1 = findViewById(R.id.editText1);
        TextView textView4 = findViewById(R.id.textView4);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = spinner1.getSelectedItemPosition();
                editText1.setText(String.valueOf(bank_savings[index]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText1.getText().toString();
                int index = spinner1.getSelectedItemPosition();
                bank_savings[index] = Integer.valueOf(str);
                Toast.makeText(MainActivity.this, "已修改", Toast.LENGTH_SHORT).show();
                textView4.setText(String.valueOf(Calc_Sum_Saving()));
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("bank_savings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (int i = 0; i < 10; i++) {
                    editor.putInt(String.valueOf(i), bank_savings[i]);
                    editor.commit();
                    Log.d("寫檔： "+String.valueOf(i),String.valueOf(bank_savings[i]));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                Toast.makeText(MainActivity.this, "已存檔", Toast.LENGTH_SHORT).show();
                textView4.setText(String.valueOf(Calc_Sum_Saving()));
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Feng37_2025");
                builder.setMessage("委任第五職等\n簡任第十二職等\n第12屆臺北市長\n第23任總統\n中央銀行鋒兄分行");
                builder.create().show();
            }
        });



        try {
            SharedPreferences sharedPreferences2 = getSharedPreferences("bank_savings", MODE_PRIVATE);
            for (int i = 0; i < 10; i++) {
                if(sharedPreferences2.contains(String.valueOf(i))) {
                    int num = 0;
                    bank_savings[i] = sharedPreferences2.getInt(String.valueOf(i), 0);
                    Log.d("讀檔： " + String.valueOf(i), String.valueOf(num));
                }
            }
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "初始化", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 10; i++) {
                bank_savings[i] = 0;
            }
        }
        textView4.setText(String.valueOf(Calc_Sum_Saving()));
    }
}
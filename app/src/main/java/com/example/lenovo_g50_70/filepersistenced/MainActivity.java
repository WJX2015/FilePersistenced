package com.example.lenovo_g50_70.filepersistenced;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveData;
    private Button restoreData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定ButterKnife.
        ButterKnife.bind(this);
        /**
         * 内存泄漏
         * ActivityPool.getActivity().addActivity(this);
         */
        //文件存储
        editText= (EditText) findViewById(R.id.edit);
        String inputText =load();
        if(!TextUtils.isEmpty(inputText)){
            editText.setText(inputText);
            //输入光标移动到文本位置以便于继续输入
            editText.setSelection(inputText.length());
            Toast.makeText(MainActivity.this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
        }
        //SharedPreferences存储数据
        saveData= (Button) findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","Tom");
                editor.putInt("age",28);
                editor.putBoolean("married",false);
                editor.apply();
            }
        });
        //SharedPreferences读取数据
        restoreData = (Button) findViewById(R.id.restore_data);
        restoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                String name =sp.getString("name","");
                int age =sp.getInt("age",0);
                boolean married =sp.getBoolean("married",false);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","age is "+age);
                Log.d("MainActivity","married is "+married);
            }
        });
    }

    private String load() {
        FileInputStream in =null;
        BufferedReader reader =null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("data");
            reader =new BufferedReader(new InputStreamReader(in));
            String line ="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText=editText.getText().toString();
        save(inputText);
        /*
        如何查看数据在退出程序时保存成功
        在AS导航栏选中TOOL→Android→Android Device Monitor
        在File Explorer 找 /data/data/包名/files/目录
        看到一个data文件，右上角导出按钮导出到电脑
        ---------------查找sharedPreferences存储的位置
        在File Explorer 找 /data/data/包名/shared_prefs/目录
         */
    }

    public void save(String inputType){
        FileOutputStream out;
        BufferedWriter writer =null;
        try {
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer =new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputType);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

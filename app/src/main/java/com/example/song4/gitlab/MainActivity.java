package com.example.song4.gitlab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        context=this;
    }

    public void login(View v){
        EditText usernameText=(EditText)findViewById(R.id.username);
        EditText passwordText=(EditText)findViewById(R.id.password);
        String username=usernameText.getText().toString();
        String password=passwordText.getText().toString();
        JSONObject object=new JSONObject();

        try{
            object.put("username",username);
            object.put("password",password);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        final JSONObject object1=object;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url = new URL("http://115.29.184.56:8090/api/user/auth");
                    connection=(HttpURLConnection)url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(object1.toString().getBytes());
                    int response = connection.getResponseCode();            //获得服务器的响应码

                    if(response == HttpURLConnection.HTTP_OK) {
                        InputStream in=connection.getInputStream();
                        BufferedReader bufr=new BufferedReader(new InputStreamReader(in));
                        StringBuilder responseString=new StringBuilder();
                        String line= line=bufr.readLine();

                        if (line!=null) {
                            do {
                                responseString.append(line);
                            } while ((line = bufr.readLine()) != null);

                            if (connection != null) {
                                connection.disconnect();
                            }
                            JSONObject object2 = new JSONObject(responseString.toString());
                            String type = object2.getString("type");
                            if ("teacher".equals(type)) {
                                String authority=object2.getString("authority");
                                System.out.println(authority);
                                if("1".equals(authority)) {
                                    String input = object1.getString("username") + ":" + object1.getString("password");
                                    Intent intent = new Intent(context, TeacherActivity.class);
                                    byte[] encodeBase64 = Base64.encode(input.getBytes(), Base64.DEFAULT);
                                    intent.putExtra("tokan", encodeBase64);
                                    startActivity(intent);
                                }
                            } else if ("student".equals(type)) {
                                String number=object2.getString("number");
                                String input = object1.getString("username") + ":" + object1.getString("password");
                                Intent intent = new Intent(context, StudentActivity.class);
                                byte[] encodeBase64 = Base64.encode(input.getBytes(), Base64.DEFAULT);
                                intent.putExtra("tokan", encodeBase64);
                                intent.putExtra("studentId","227");
//                                intent.putExtra("studentId",number);
                                startActivity(intent);

                            }
                        }else {
                            if (connection != null) {
                                connection.disconnect();
                            }
                            Looper.prepare();
                            AlertDialog.Builder builder=new AlertDialog.Builder(context);
                            builder.setMessage(getString(R.string.noUsername));
                            builder.show();
                            Looper.loop();
                        }


                    }


                }catch (Exception e){
                    e.printStackTrace(System.out);
                }
            }
        }).start();
    }
}

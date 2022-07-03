package com.example.ifsc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView res;
    private TextInputEditText input;
    //private APICall apiCall;
    //private String ifsc_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        res = findViewById(R.id.result);
        input = findViewById(R.id.idEdtSource);
        Button search_result = findViewById(R.id.search);


        search_result.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {



        //retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bank-apis.justinclicks.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        //instance of interface
        APICall apiCall = retrofit.create(APICall.class);



        String ifsc_code = input.getText().toString();

        if(ifsc_code.equals(""))
        {
            Toast.makeText(MainActivity.this, "Please enter IFSC code", Toast.LENGTH_LONG).show();
        }
        else
        {
            res.setText("");
            Call<DataModel> call = apiCall.getData(ifsc_code);

            call.enqueue(new Callback<DataModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<DataModel> call, @NonNull Response<DataModel> response) {

                    if(response.code() != 200)
                    {
                        res.setText("INVALID IFCS CODE");
                        Toast.makeText(MainActivity.this, "Invalid IFCS Code", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String data = "";

                    //getting the to textview

                    assert response.body() != null;
                    data = "IFSC : " + response.body().getIFSC() +
                            "\n\nBANK : " + response.body().getBANK() +
                            "\n\nBRANCH : " + response.body().getBRANCH() +
                            "\n\nADDRESS : " + response.body().getADDRESS() +
                            "\n\nBANKCODE : " + response.body().getBANKCODE() +
                            "\n\nCONTACT : " + response.body().getCONTACT() +
                            "\n\nSTATE : " + response.body().getSTATE() +
                            "\n\nDISTRICT : " + response.body().getDISTRICT() +
                            "\n\nCITY : " + response.body().getCITY();


                    res.append(data);
                }

                @Override
                public void onFailure(@NonNull Call<DataModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failure ", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
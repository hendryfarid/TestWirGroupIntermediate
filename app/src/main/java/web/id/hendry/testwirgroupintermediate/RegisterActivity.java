package web.id.hendry.testwirgroupintermediate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import web.id.hendry.testwirgroupintermediate.utils.BaseApiService;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNama, edEmail, edtPassword, edtRePassword;
    private Button btnRegister;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNama = (EditText)findViewById(R.id.edtNama);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtRePassword = (EditText)findViewById(R.id.edtRePassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void doRegister(){
        mApiService.registerRequest(edtNama.getText().toString(), edEmail.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                JSONObject jsonStatus = jsonRESULTS.getJSONObject("status");
                                if (jsonStatus.getInt("code") == 200){



                                    JSONObject jsonUser = jsonRESULTS.getJSONObject("data").getJSONObject("user");

                                    String strName = jsonUser.getString("name");
                                    String strEmail = jsonUser.getString("email");

                                    Toast.makeText(getApplicationContext(), "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                    String nama = jsonUser.getJSONObject("user").getString("nama");
//                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
//                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
//                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                } else {
                                    // Jika login gagal
//                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(getApplicationContext(), "Error login", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }
}
package com.example.ssoheyli.ticketing_newdesign;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.DialogLoading;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetPriority;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Gettickettype;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_LoginGet;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_LoginPost;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_getProductname;
import com.example.ssoheyli.ticketing_newdesign.Model.post;
import com.example.ssoheyli.ticketing_newdesign.Ticket.Detail_Ticket_List;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public  class Login extends Activity
{

//    String TAG;
//    String s = "";
//    int res =0;
//    int rec=0;
//    ImageView imv;
//    String token = "";
//    Config config = new Config();
//    Animation btnanim;
//    String captchavalue = "-1";
//    SQLiteDatabase db;
//    DialogLoading dialogLoading;
//    EditText edtusername , edtpassword , edtcaptcha;
//    Button btn1 , btn2 , btn3 ;
//    TextView txv1 , txv2 , txv3 , txv4 , txv5 , txv6;
//    private static final int REQ_Per_FingerPerint = 12;
//    LazyLoader lp;
//    KeyStore keyStore;
//    String KeyName = "FingerPrintKey";
//    Cipher cipher;
//    KeyGenerator keyGenerator;
//    String Pass = "";
//    private String ANDROID_KEY_STORE = "AndroidKeyStore";
//    ImageView imvf1 , imvf2 , imvf3 , imvf4;
//    Animation  anim2;
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) throws SecurityException{
//        super.onCreate(savedInstanceState);
//
//        change_BaseUrl();
//
//        if(Build.VERSION.SDK_INT >= 21)
//        {
//            if(Config.getusername(Login.this).equals(""))
//            {
//                setContentView(R.layout.login);
//                edtusername = (EditText)findViewById(R.id.etusername);
//                edtpassword = (EditText)findViewById(R.id.etpassword);
//                edtcaptcha = (EditText)findViewById(R.id.etcaptcha);
//                btn1 = (Button)findViewById(R.id.confirm);
//                btn2 = (Button)findViewById(R.id.repas);
//                btn3 = (Button)findViewById(R.id.register);
//                edtusername.setText(Config.getusername(getApplicationContext()));
//                txv1 = findViewById(R.id.t1);
//                txv2 = findViewById(R.id.t2);
//                txv3 = findViewById(R.id.t3);
//                txv4 = findViewById(R.id.texuser);
//                txv5 = findViewById(R.id.textpass);
//                txv6 = findViewById(R.id.textView31);
//
//                lp = (LazyLoader) findViewById(R.id.dl);
//                lp.setVisibility(View.INVISIBLE);
//
//                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
//                }
//
//
//                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
//                txv1.setTypeface(atf);
//                txv2.setTypeface(atf);
//                txv3.setTypeface(atf);
//                txv4.setTypeface(atf);
//                txv5.setTypeface(atf);
//                txv6.setTypeface(atf);
//                btn1.setTypeface(atf);
//                btn2.setTypeface(atf);
//                btn3.setTypeface(atf);
//                edtusername.setTypeface(atf);
//                edtcaptcha.setTypeface(atf);
//                edtcaptcha.setTypeface(atf);
//
//                if(internetConnection() && !Config.getBaseUrl().equals(""))
//                {
//                    getcaptcha();
//                    gettickettype();
//                    getticketpriority();
//                    getproduct();
//                }
//                else
//                {
//                    Toast.makeText(Login.this , "لطفا اتصال خود به اینترنت را بررسی کنید" , Toast.LENGTH_LONG).show();
//                }
//
//
//                btn3.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//
//                        if(Config.getBaseUrl().equals(""))
//                        {
//                            Toast.makeText(Login.this, "لطفا آدرس سایت را وارد کنید", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Intent intent = new Intent(Login.this, Creatuser.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        lp.setVisibility(View.VISIBLE);
//                    }
//                });
//
//                btn1.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        lp.setVisibility(View.VISIBLE);
//                        if(captchavalue.equals("-1"))
//                        {
//                            Toast.makeText(Login.this, "لطفا اتصال خود به اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            if (captchavalue.equals(edtcaptcha.getText().toString())) {
//                                String s = Config.getBaseUrl();
//                                Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//
//                                API myApi2 = retrofit2.create(API.class);
//                                Model_LoginPost modellog = new Model_LoginPost();
//                                modellog.setUsername(edtusername.getText().toString());
//                                modellog.setPassword(edtpassword.getText().toString());
//
//                                Call<Model_LoginGet> model = myApi2.login(modellog);
//                                model.enqueue(new Callback<Model_LoginGet>() {
//                                    @Override
//                                    public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
//                                        if (response.isSuccessful()) {
//                                            res = response.body().getResult();
//                                            token = response.body().getToken();
//                                            int id = response.body().getUserid();
//                                            lp.setVisibility(View.GONE);
//                                            if (res == -1) {
//                                                Toast.makeText(Login.this, "لطفا نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            } else if (res == -3) {
//                                                lp.setVisibility(View.INVISIBLE);
//                                                Toast.makeText(Login.this, "مقدار تصویر را دوباره وارد کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//
//                                            } else if (res == -2) {
//                                                Toast.makeText(Login.this, "نام کاربری و رمز عبور را بررسی کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            } else if (res == 0) {
//                                                lp.setVisibility(View.INVISIBLE);
//                                                Config.SetToken(getApplicationContext(), token);
//                                                Config.SetData(getApplicationContext(), edtusername.getText().toString(), edtpassword.getText().toString(), id);
//                                                Intent intent = new Intent(Login.this, Digit_Password.class);
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                Toast.makeText(Login.this, "خطای سیستمی" + res, Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            }
//
//                                        } else {
//                                            lp.setVisibility(View.INVISIBLE);
//                                            Toast.makeText(Login.this, "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Model_LoginGet> call, Throwable t) {
//                                        Log.d(TAG, "Response" + t.getMessage());
//                                        lp.setVisibility(View.INVISIBLE);
//                                        Toast.makeText(Login.this, "لطفا اتصال اینترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } else {
//                                lp.setVisibility(View.INVISIBLE);
//                                Toast.makeText(Login.this, "مقدار تصویر را صحیح وارد کنید", Toast.LENGTH_SHORT).show();
//                                getcaptcha();
//                            }
//                        }
//                    }
//                });
//            }
//            else
//            {
//                setContentView(R.layout.confirm_digit_password);
//                final Button btn0 , btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7 , btn8 , btn9;
//                TextView txv1;
//                btn0 = findViewById(R.id.bn0);
//                btn1 = findViewById(R.id.bn1);
//                btn2 = findViewById(R.id.bn2);
//                btn3 = findViewById(R.id.bn3);
//                btn4 = findViewById(R.id.bn4);
//                btn5 = findViewById(R.id.bn5);
//                btn6 = findViewById(R.id.bn6);
//                btn7 = findViewById(R.id.bn7);
//                btn8 = findViewById(R.id.bn8);
//                btn9 = findViewById(R.id.bn9);
//                imvf1 = findViewById(R.id.ivf1);
//                imvf2 = findViewById(R.id.ivf2);
//                imvf3 = findViewById(R.id.ivf3);
//                imvf4 = findViewById(R.id.ivf4);
//                anim2 = AnimationUtils.loadAnimation(this, R.anim.shack_animation);
//                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
//                }
//
//                set_newToken();
//                gettickettype();
//                getticketpriority();
//                getproduct();
//                final ImageView imv1 = findViewById(R.id.ivfingerprint);
//                final ImageView imvremove = findViewById(R.id.bnremove);
//                txv1 = findViewById(R.id.tv1);
//
//                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
//                txv1.setTypeface(atf);
//
//                if(Build.VERSION.SDK_INT >= 23 && !Config.getusername(getApplicationContext()).equals("") && internetConnection())
//                {
//
//                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
//
//                    checkpermision();
//                    if(!haspermission())
//                    {
//                        Toast.makeText(Login.this, "مجوز دسترسی به اثرانگشت تایید نشده", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(fingerprintManager.isHardwareDetected())
//                    {
//                        if(!fingerprintManager.hasEnrolledFingerprints())
//                        {
//                            Toast.makeText(Login.this, "اثر انگشت در تنظیمات ثبت نشده است", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            generateKey();
//                            if(cipherInit())
//                            {
//                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                                CancellationSignal cancellationSignal = new CancellationSignal();
//                                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
//                                            @Override
//                                            public void onAuthenticationError(int errorCode, CharSequence errString) {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
//                                                imv1.setBackgroundResource(R.drawable.acceptfinger);
//                                                Intent intent = new Intent(Login.this , MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationFailed() {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//                                        },
//                                        null);
//                            }
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(Login.this, "eror", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                imvremove.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if(!Pass.equals(""))
//                        {
//                            Pass = Pass.substring(0, Pass.length() - 1);
//                            changepicture2();
//                        }
//                    }
//                });
//
//
//
//                btn0.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("0");
//                    }
//                });
//
//                btn1.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("1");
//                    }
//                });
//
//
//                btn2.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("2");
//                    }
//                });
//
//
//                btn3.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        getPas("3");
//                    }
//                });
//
//
//                btn4.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("4");
//                    }
//                });
//
//
//                btn5.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("5");
//                    }
//                });
//
//
//                btn6.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("6");
//                    }
//                });
//
//
//                btn7.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("7");
//                    }
//                });
//
//
//                btn8.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        getPas("8");
//                    }
//                });
//
//
//                btn9.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("9");
//                    }
//                });
//
//
//            }
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

//        else
//        {
//            if(Config.getusername(Login.this).equals(""))
//            {
//                setContentView(R.layout.loginsdk15);
//                edtusername = (EditText)findViewById(R.id.etusername);
//                edtpassword = (EditText)findViewById(R.id.etpassword);
//                edtcaptcha = (EditText)findViewById(R.id.etcaptcha);
//                btn1 = (Button)findViewById(R.id.confirm);
//                btn2 = (Button)findViewById(R.id.repas);
//                btn3 = (Button)findViewById(R.id.register);
//                edtusername.setText(Config.getusername(getApplicationContext()));
//                txv1 = findViewById(R.id.t1);
//                txv2 = findViewById(R.id.t2);
//                txv3 = findViewById(R.id.t3);
//                txv4 = findViewById(R.id.texuser);
//                txv5 = findViewById(R.id.textpass);
//                txv6 = findViewById(R.id.textView31);
//
//                lp = (LazyLoader) findViewById(R.id.dl);
//                lp.setVisibility(View.INVISIBLE);
//
//                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
//                }
//
//
//
//
//                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
//                txv1.setTypeface(atf);
//                txv2.setTypeface(atf);
//                txv3.setTypeface(atf);
//                txv4.setTypeface(atf);
//                txv5.setTypeface(atf);
//                txv6.setTypeface(atf);
//                btn1.setTypeface(atf);
//                btn2.setTypeface(atf);
//                btn3.setTypeface(atf);
//                edtusername.setTypeface(atf);
//                edtcaptcha.setTypeface(atf);
//                edtcaptcha.setTypeface(atf);
//
//                if(internetConnection() && !Config.getBaseUrl().equals(""))
//                {
//                    getcaptcha();
//                    gettickettype();
//                    getticketpriority();
//                    getproduct();
//                }
//                else
//                {
//                    Toast.makeText(Login.this , "لطفا اتصال خود به اینترنت را بررسی کنید" , Toast.LENGTH_LONG).show();
//                }
//
//
//                btn3.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//
//                        if(Config.getBaseUrl().equals(""))
//                        {
//                            Toast.makeText(Login.this, "لطفا آدرس سایت را وارد کنید", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Intent intent = new Intent(Login.this, Creatuser.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//
//                btn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        lp.setVisibility(View.VISIBLE);
//                    }
//                });
//
//                btn1.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        lp.setVisibility(View.VISIBLE);
//                        if(captchavalue.equals("-1"))
//                        {
//                            Toast.makeText(Login.this, "لطفا اتصال خود به اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            if (captchavalue.equals(edtcaptcha.getText().toString())) {
//                                String s = Config.getBaseUrl();
//                                Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//
//                                API myApi2 = retrofit2.create(API.class);
//                                Model_LoginPost modellog = new Model_LoginPost();
//                                modellog.setUsername(edtusername.getText().toString());
//                                modellog.setPassword(edtpassword.getText().toString());
//
//                                Call<Model_LoginGet> model = myApi2.login(modellog);
//                                model.enqueue(new Callback<Model_LoginGet>() {
//                                    @Override
//                                    public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
//                                        if (response.isSuccessful()) {
//                                            res = response.body().getResult();
//                                            token = response.body().getToken();
//                                            int id = response.body().getUserid();
//                                            lp.setVisibility(View.GONE);
//                                            if (res == -1) {
//                                                Toast.makeText(Login.this, "لطفا نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            } else if (res == -3) {
//                                                lp.setVisibility(View.INVISIBLE);
//                                                Toast.makeText(Login.this, "مقدار تصویر را دوباره وارد کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//
//                                            } else if (res == -2) {
//                                                Toast.makeText(Login.this, "نام کاربری و رمز عبور را بررسی کنید", Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            } else if (res == 0) {
//                                                lp.setVisibility(View.INVISIBLE);
//                                                Config.SetToken(getApplicationContext(), token);
//                                                Config.SetData(getApplicationContext(), edtusername.getText().toString(), edtpassword.getText().toString(), id);
//                                                Intent intent = new Intent(Login.this, Digit_Password.class);
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                Toast.makeText(Login.this, "خطای سیستمی" + res, Toast.LENGTH_SHORT).show();
//                                                getcaptcha();
//                                                lp.setVisibility(View.INVISIBLE);
//                                            }
//
//                                        } else {
//                                            lp.setVisibility(View.INVISIBLE);
//                                            Toast.makeText(Login.this, "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Model_LoginGet> call, Throwable t) {
//                                        Log.d(TAG, "Response" + t.getMessage());
//                                        lp.setVisibility(View.INVISIBLE);
//                                        Toast.makeText(Login.this, "لطفا اتصال اینترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } else {
//                                lp.setVisibility(View.INVISIBLE);
//                                Toast.makeText(Login.this, "مقدار تصویر را صحیح وارد کنید", Toast.LENGTH_SHORT).show();
//                                getcaptcha();
//                            }
//                        }
//                    }
//                });
//            }
//            else
//            {
//                setContentView(R.layout.confirm_digit_password_sdk15);
//                final Button btn0 , btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7 , btn8 , btn9;
//                TextView txv1;
//                btn0 = findViewById(R.id.bn0);
//                btn1 = findViewById(R.id.bn1);
//                btn2 = findViewById(R.id.bn2);
//                btn3 = findViewById(R.id.bn3);
//                btn4 = findViewById(R.id.bn4);
//                btn5 = findViewById(R.id.bn5);
//                btn6 = findViewById(R.id.bn6);
//                btn7 = findViewById(R.id.bn7);
//                btn8 = findViewById(R.id.bn8);
//                btn9 = findViewById(R.id.bn9);
//                imvf1 = findViewById(R.id.ivf1);
//                imvf2 = findViewById(R.id.ivf2);
//                imvf3 = findViewById(R.id.ivf3);
//                imvf4 = findViewById(R.id.ivf4);
//                anim2 = AnimationUtils.loadAnimation(this, R.anim.shack_animation);
//                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
//                }
//
//                set_newToken();
//                gettickettype();
//                getticketpriority();
//                getproduct();
//                final ImageView imv1 = findViewById(R.id.ivfingerprint);
//                final ImageView imvremove = findViewById(R.id.bnremove);
//                txv1 = findViewById(R.id.tv1);
//
//                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
//                txv1.setTypeface(atf);
//
//                if(Build.VERSION.SDK_INT >= 23 && !Config.getusername(getApplicationContext()).equals("") && internetConnection())
//                {
//
//                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
//
//                    checkpermision();
//                    if(!haspermission())
//                    {
//                        Toast.makeText(Login.this, "مجوز دسترسی به اثرانگشت تایید نشده", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(fingerprintManager.isHardwareDetected())
//                    {
//                        if(!fingerprintManager.hasEnrolledFingerprints())
//                        {
//                            Toast.makeText(Login.this, "اثر انگشت در تنظیمات ثبت نشده است", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            generateKey();
//                            if(cipherInit())
//                            {
//                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                                CancellationSignal cancellationSignal = new CancellationSignal();
//                                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
//                                            @Override
//                                            public void onAuthenticationError(int errorCode, CharSequence errString) {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
//                                                imv1.setBackgroundResource(R.drawable.acceptfinger);
//                                                Intent intent = new Intent(Login.this , MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }
//
//                                            @Override
//                                            public void onAuthenticationFailed() {
//                                                imv1.setBackgroundResource(R.drawable.denidefinger);
//                                                fingerprinte();
//                                            }
//                                        },
//                                        null);
//                            }
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(Login.this, "eror", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                imvremove.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if(!Pass.equals(""))
//                        {
//                            Pass = Pass.substring(0, Pass.length() - 1);
//                            changepicture2();
//                        }
//                    }
//                });
//
//
//
//                btn0.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("0");
//                    }
//                });
//
//                btn1.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("1");
//                    }
//                });
//
//
//                btn2.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("2");
//                    }
//                });
//
//
//                btn3.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        getPas("3");
//                    }
//                });
//
//
//                btn4.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("4");
//                    }
//                });
//
//
//                btn5.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("5");
//                    }
//                });
//
//
//                btn6.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("6");
//                    }
//                });
//
//
//                btn7.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("7");
//                    }
//                });
//
//
//                btn8.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        getPas("8");
//                    }
//                });
//
//
//                btn9.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        getPas("9");
//                    }
//                });
//
//            }
//        }

//    }
//
//    @TargetApi(23)
//    private void generateKey()
//    {
//        try {
//            keyStore = KeyStore.getInstance("AndroidKeyStore");
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES ,ANDROID_KEY_STORE) ;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to Get KeyGenerator Instance"+e.toString() , e);
//        }
//        catch (NoSuchProviderException e)
//        {
//            throw new RuntimeException("Failed to Get KeyGenerator Instance"+e.toString() , e);
//        }
//
//
//        try {
//            keyStore.load(null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                keyGenerator.init(new KeyGenParameterSpec.Builder(KeyName , KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
//            }
//
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void getcaptcha()
//    {
//        imv = findViewById(R.id.ivcaptcha);
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//
//        API api = retrofit2.create(API.class);
//        Call<List<post>> model2 = api.getcaptcha();
//        model2.enqueue(new Callback<List<post>>()
//        {
//            @Override
//            public void onResponse(Call<List<post>> call, Response<List<post>> response)
//            {
//                if(response.isSuccessful())
//                {
//                    List<post> post = response.body();
//                    String captcha = "";
//                    for (int i=0 ; i<post.size() ; i++)
//                    {
//                        captcha = response.body().get(i).getCaptcha();
//                        captchavalue = response.body().get(i).getCaptchavalue();
//                    }
//                    byte[] bytearray = Base64.decode(captcha , Base64.DEFAULT);
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
//                    imv.setImageBitmap(bitmap);
//
//                }
//                else
//                {
//                    Toast.makeText(Login.this , "خطای سرور 1011" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<post>> call, Throwable t)
//            {
//                Log.d(TAG , "Response" + t.getMessage());
//                Toast.makeText(Login.this , "خطا سرور 1012" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void gettickettype()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API api = retrofit2.create(API.class);
//        Call<List<Model_Gettickettype>> model = api.gettypeticket();
//        model.enqueue(new Callback<List<Model_Gettickettype>>() {
//            @Override
//            public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response)
//            {
//                if(response.isSuccessful())
//                {
//                    db = openOrCreateDatabase("TicketType_DB" , Context.MODE_PRIVATE , null);
//                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
//                    db.delete("TicketType_DB", null, null);
//
//                    List<Model_Gettickettype> list = response.body();
//                    for (int i=0 ; i<list.size() ; i++)
//                    {
//                        db = openOrCreateDatabase("TicketType_DB" , Context.MODE_PRIVATE , null);
//                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
//                        db.execSQL("INSERT INTO TicketType_DB(TicketType , idtickettype) VALUES ('"+list.get(i).getTitle()+"' , "+list.get(i).getId()+" )");
//
//                    }
//                }
//                else
//                {
//                    Toast.makeText(Login.this , "خطای سرور 1021" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t)
//            {
//                Log.d(TAG , "Response" + t.getMessage());
//                Toast.makeText(Login.this , "خطای سرور 1022" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    public void change_BaseUrl()
//    {
//        final boolean[] flag1 = {true};
//
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API api = retrofit2.create(API.class);
//        Call<List<Model_Gettickettype>> model = api.gettypeticket();
//        model.enqueue(new Callback<List<Model_Gettickettype>>() {
//            @Override
//            public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response)
//            {
//                if(response.isSuccessful())
//                {
//                    flag1[0] = true;
//                }
//                else
//                {
//                    Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//                    API api = retrofit2.create(API.class);
//                    Call<List<Model_Gettickettype>> model = api.gettypeticket();
//                    model.enqueue(new Callback<List<Model_Gettickettype>>() {
//                        @Override
//                        public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response)
//                        {
//                            if(response.isSuccessful())
//                            {
//                                Config.BaseUrl = "http://tavanasazan.com/";
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t) {
//
//                        }
//                    });
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t)
//            {
//                Log.d(TAG , "Response" + t.getMessage());
//                Toast.makeText(Login.this , "خطای سرور 1022" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    public void getticketpriority()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//        API api = retrofit2.create(API.class);
//        Call<List<Model_GetPriority>> model = api.getpriority();
//        model.enqueue(new Callback<List<Model_GetPriority>>() {
//            @Override
//            public void onResponse(Call<List<Model_GetPriority>> call, Response<List<Model_GetPriority>> response)
//            {
//                if(response.isSuccessful())
//                {
//                    db = openOrCreateDatabase("TicketPriority_DB" , Context.MODE_PRIVATE , null);
//                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
//                    db.delete("TicketPriority_DB", null, null);
//                    List<Model_GetPriority> list = response.body();
//                    for (int i=0 ; i<list.size() ; i++)
//                    {
//                        db = openOrCreateDatabase("TicketPriority_DB" , Context.MODE_PRIVATE , null);
//                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
//                        db.execSQL("INSERT INTO TicketPriority_DB(TicketPriority , idpriority) VALUES ('"+list.get(i).getTitle()+"' , "+list.get(i).getId()+" )");
//
//                    }
//                }
//                else
//                {
//                    Toast.makeText(Login.this , "خطای سرور 1031" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Model_GetPriority>> call, Throwable t)
//            {
//                Log.d(TAG , "Response" + t.getMessage());
//                Toast.makeText(Login.this , "خطای سرور 1032" , Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void  getproduct()
//    {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10,TimeUnit.SECONDS).build();
//
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).client(client).addConverterFactory(GsonConverterFactory.create()).build();
//        API api = retrofit2.create(API.class);
//        Call<List<Model_getProductname>> model = api.getproductname();
//        model.enqueue(new Callback<List<Model_getProductname>>()
//        {
//            @Override
//            public void onResponse(Call<List<Model_getProductname>> call, Response<List<Model_getProductname>> response)
//            {
//                if(response.isSuccessful())
//                {
//                    db = openOrCreateDatabase("TicketProduct_DB" , Context.MODE_PRIVATE , null);
//                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
//                    db.delete("TicketProduct_DB", null, null);
//                    List<Model_getProductname> list = response.body();
//                    for (int i=0 ; i<list.size() ; i++)
//                    {
//                        db = openOrCreateDatabase("TicketProduct_DB" , Context.MODE_PRIVATE , null);
//                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
//                        db.execSQL("INSERT INTO TicketProduct_DB(TicketProduct , idproduct) VALUES ('"+list.get(i).getName()+"' , "+list.get(i).getId()+" )");
//
//                    }
//                }
//                else
//                {
//                    String e = response.toString();
//                    Toast.makeText(Login.this , "خطای سرور 1041" , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Model_getProductname>> call, Throwable t)
//            {
//                Log.d(TAG , "Response" + t.getMessage());
//                Toast.makeText(Login.this , "خطای سرور 1042" , Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//
//    public boolean internetConnection ()
//    {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if (wifi != null && wifi.isConnected()){
//            return true;
//        }
//        NetworkInfo data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (data != null && data.isConnected()){
//            return true;
//        }
//        NetworkInfo active = cm.getActiveNetworkInfo();
//        if (active != null && active.isConnected()){
//            return true;
//        }
//
//
//        return  false;
//    }
//
//
//
//    private boolean haspermission()
//    {
//        return ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED;
//    }
//
//
//    public void checkpermision()
//    {
//        if(!haspermission())
//        {
//            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.USE_FINGERPRINT} , REQ_Per_FingerPerint);
//        }
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == REQ_Per_FingerPerint)
//        {
//            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(Login.this, "مجوز دسترسی به اثر انگشت تایید نشده", Toast.LENGTH_SHORT).show();
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private boolean cipherInit()
//    {
//        try {
//            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES +"/" +KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        }
//
//
//
//        try {
//            keyStore.load(null);
//            SecretKey key = (SecretKey) keyStore.getKey(KeyName , null);
//            SecretKey key2 = keyGenerator.generateKey();
//            cipher.init(Cipher.ENCRYPT_MODE , key);
//            return true;
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//
//    @TargetApi(23)
//    public void fingerprinte()
//    {
//        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
//
//        final ImageView imv1 = findViewById(R.id.ivfingerprint);
//
//        checkpermision();
//        if(!haspermission())
//        {
//            Toast.makeText(Login.this, "مجوز دسترسی به اثرانگشت تایید نشده", Toast.LENGTH_SHORT).show();
//        }
//        else if(fingerprintManager.isHardwareDetected())
//        {
//            if(!fingerprintManager.hasEnrolledFingerprints())
//            {
//                Toast.makeText(Login.this, "اثر انگشت در تنظیمات ثبت نشده است", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                generateKey();
//                if(cipherInit())
//                {
//                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                    CancellationSignal cancellationSignal = new CancellationSignal();
//                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
//                                @Override
//                                public void onAuthenticationError(int errorCode, CharSequence errString) {
//                                    imv1.setBackgroundResource(R.drawable.denidefinger);
//                                    fingerprinte();
//
//                                }
//
//                                @Override
//                                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//                                    imv1.setBackgroundResource(R.drawable.denidefinger);
//                                    fingerprinte();
//                                }
//
//                                @Override
//                                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result)
//                                {
//                                    imv1.setBackgroundResource(R.drawable.acceptfinger);
//                                    Intent intent = new Intent(Login.this , MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//
//                                @Override
//                                public void onAuthenticationFailed() {
//                                    imv1.setBackgroundResource(R.drawable.denidefinger);
//                                    fingerprinte();
//                                }
//                            },
//                            null);
//                }
//            }
//        }
//        else
//        {
//            Toast.makeText(Login.this, "eror", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void getPas(String num)
//    {
//        if(Pass.length() <4)
//        {
//            Pass +=num;
//            changepicture();
//            if(Pass.length() == 4)
//            {
//                if(Pass.equals(Config.get4DigitPassword(Login.this)))
//                {
//                    Intent intent = new Intent(Login.this , MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else
//                {
//                    final Button btn0 , btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7 , btn8 , btn9;
//                    btn0 = findViewById(R.id.bn0);
//                    btn1 = findViewById(R.id.bn1);
//                    btn2 = findViewById(R.id.bn2);
//                    btn3 = findViewById(R.id.bn3);
//                    btn4 = findViewById(R.id.bn4);
//                    btn5 = findViewById(R.id.bn5);
//                    btn6 = findViewById(R.id.bn6);
//                    btn7 = findViewById(R.id.bn7);
//                    btn8 = findViewById(R.id.bn8);
//                    btn9 = findViewById(R.id.bn9);
//                    imvf1.startAnimation(anim2);
//                    imvf2.startAnimation(anim2);
//                    imvf3.startAnimation(anim2);
//                    imvf4.startAnimation(anim2);
//                    btn0.startAnimation(anim2);
//                    btn1.startAnimation(anim2);
//                    btn2.startAnimation(anim2);
//                    btn3.startAnimation(anim2);
//                    btn4.startAnimation(anim2);
//                    btn5.startAnimation(anim2);
//                    btn6.startAnimation(anim2);
//                    btn7.startAnimation(anim2);
//                    btn8.startAnimation(anim2);
//                    btn9.startAnimation(anim2);
//                    imvf1.setBackgroundResource(R.drawable.f1);
//                    imvf2.setBackgroundResource(R.drawable.f1);
//                    imvf3.setBackgroundResource(R.drawable.f1);
//                    imvf4.setBackgroundResource(R.drawable.f1);
//                    Pass = "";
//                    Toast.makeText(Login.this, "رمز عبور را اشتباه وارد کرده اید", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//    }
//
//    public void changepicture()
//    {
//        if(Pass.length()==1)
//        {
//            imvf1.setBackgroundResource(R.drawable.f2);
//        }
//        else if(Pass.length() == 2)
//        {
//            imvf2.setBackgroundResource(R.drawable.f2);
//        }
//        else if(Pass.length() == 3)
//        {
//            imvf3.setBackgroundResource(R.drawable.f2);
//        }
//        else if(Pass.length() == 4)
//        {
//            imvf4.setBackgroundResource(R.drawable.f2);
//        }
//    }
//
//    public void changepicture2()
//    {
//        if(Pass.length()==0)
//        {
//            imvf1.setBackgroundResource(R.drawable.f1);
//        }
//        else if(Pass.length() == 1)
//        {
//            imvf2.setBackgroundResource(R.drawable.f1);
//        }
//        else if(Pass.length() == 2)
//        {
//            imvf3.setBackgroundResource(R.drawable.f1);
//        }
//        else if(Pass.length() == 3)
//        {
//            imvf4.setBackgroundResource(R.drawable.f1);
//        }
//    }
//
//    public void set_newToken()
//    {
//        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
//
//        API myApi2 = retrofit2.create(API.class);
//        Model_LoginPost modellog = new Model_LoginPost();
//        modellog.setUsername(Config.getusername(Login.this));
//        modellog.setPassword(Config.getpassword(Login.this));
//
//        Call<Model_LoginGet> model = myApi2.login(modellog);
//        model.enqueue(new Callback<Model_LoginGet>() {
//            @Override
//            public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
//                if (response.isSuccessful()) {
//                    res = response.body().getResult();
//                    token = response.body().getToken();
//                    int id = response.body().getUserid();
//                    if (res == -1) {
//                        Toast.makeText(Login.this, "لطفا نام کاربری و رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
//                    } else if (res == -3) {
//                        Toast.makeText(Login.this, "مقدار تصویر را دوباره وارد کنید", Toast.LENGTH_SHORT).show();
//
//                    } else if (res == -2) {
//                        Toast.makeText(Login.this, "نام کاربری و رمز عبور را بررسی کنید", Toast.LENGTH_SHORT).show();
//                    } else if (res == 0) {
//                        Config.SetToken(getApplicationContext(), token);
//                    } else {
//                        Toast.makeText(Login.this, "خطای سیستمی" + res, Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(Login.this, "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model_LoginGet> call, Throwable t) {
//                Log.d(TAG, "Response" + t.getMessage());
//                Toast.makeText(Login.this, "لطفا اتصال اینترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //after change

    String TAG;
    String s = "";
    int res = 0;
    int rec = 0;
    ImageView imv;
    String token = "";
    Config config = new Config();
    Animation btnanim;
    String captchavalue = "-1";
    SQLiteDatabase db;
    DialogLoading dialogLoading;
    EditText edtusername, edtpassword, edtcaptcha;
    Button btn1, btn3;
    TextView txv1, txv2, txv3, txv4, txv5, txv6, btn2;
    private static final int REQ_Per_FingerPerint = 12;
    LazyLoader lp;
    KeyStore keyStore;
    String KeyName = "FingerPrintKey";
    Cipher cipher;
    KeyGenerator keyGenerator;
    String Pass = "";
    private String ANDROID_KEY_STORE = "AndroidKeyStore";
    ImageView imvf1, imvf2, imvf3, imvf4;
    Animation anim2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) throws SecurityException {


        super.onCreate(savedInstanceState);
        change_BaseUrl();

        if (Build.VERSION.SDK_INT >= 21)
        {
            if (Config.getusername(Login.this).equals("")) {
                setContentView(R.layout.login2);

                edtusername = findViewById(R.id.etusername);
                edtpassword = findViewById(R.id.etpassword);
                edtcaptcha = findViewById(R.id.etcaptcha);
                btn1 = findViewById(R.id.confirm);
                btn2 = findViewById(R.id.tv_forget_password);
                btn3 = findViewById(R.id.register);
                edtusername.setText(Config.getusername(getApplicationContext()));

                lp = findViewById(R.id.dl);
                lp.setVisibility(View.INVISIBLE);

                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
                }

                if (internetConnection() && !Config.getBaseUrl().equals("")) {
                    getcaptcha();
                    gettickettype();
                    getticketpriority();
                    getproduct();
                } else {
                    Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
                }

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Config.getBaseUrl().equals("")) {
                            Toast.makeText(Login.this, getString(R.string.login_enteraddress), Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Login.this, Creatuser.class);
                            startActivity(intent);
                        }
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lp.setVisibility(View.VISIBLE);
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lp.setVisibility(View.VISIBLE);
                        if (captchavalue.equals("-1")) {
                            Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
                        } else {
                            if (captchavalue.equals(edtcaptcha.getText().toString())) {
                                String s = Config.getBaseUrl();
                                Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

                                API myApi2 = retrofit2.create(API.class);
                                Model_LoginPost modellog = new Model_LoginPost();
                                modellog.setUsername(edtusername.getText().toString());
                                modellog.setPassword(edtpassword.getText().toString());

                                Call<Model_LoginGet> model = myApi2.login(modellog);
                                model.enqueue(new Callback<Model_LoginGet>() {
                                    @Override
                                    public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
                                        if (response.isSuccessful()) {
                                            res = response.body().getResult();
                                            token = response.body().getToken();
                                            int id = response.body().getUserid();
                                            lp.setVisibility(View.GONE);
                                            if (res == -1) {
                                                Toast.makeText(Login.this, getString(R.string.login_enter_name_pass), Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            } else if (res == -3) {
                                                lp.setVisibility(View.INVISIBLE);
                                                Toast.makeText(Login.this, getString(R.string.login_reenter_captcha), Toast.LENGTH_SHORT).show();
                                                getcaptcha();

                                            } else if (res == -2) {
                                                Toast.makeText(Login.this, getString(R.string.login_check_userpass), Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            } else if (res == 0) {
                                                lp.setVisibility(View.INVISIBLE);
                                                Config.SetToken(getApplicationContext(), token);
                                                Config.SetData(getApplicationContext(), edtusername.getText().toString(), edtpassword.getText().toString(), id);
                                                Intent intent = new Intent(Login.this, Digit_Password.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(Login.this, getString(R.string.login_system_error) + res, Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            }

                                        } else {
                                            lp.setVisibility(View.INVISIBLE);
                                            Toast.makeText(Login.this, getString(R.string.login_server_error), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Model_LoginGet> call, Throwable t) {
                                        Log.d(TAG, "Response" + t.getMessage());
                                        lp.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                lp.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this, getString(R.string.login_reenter_captcha), Toast.LENGTH_SHORT).show();
                                getcaptcha();
                            }
                        }
                    }
                });
            }
            else
                {
                setContentView(R.layout.confirm_digit_password);
                final Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
                TextView txv1;
                btn0 = findViewById(R.id.bn0);
                btn1 = findViewById(R.id.bn1);
                btn2 = findViewById(R.id.bn2);
                btn3 = findViewById(R.id.bn3);
                btn4 = findViewById(R.id.bn4);
                btn5 = findViewById(R.id.bn5);
                btn6 = findViewById(R.id.bn6);
                btn7 = findViewById(R.id.bn7);
                btn8 = findViewById(R.id.bn8);
                btn9 = findViewById(R.id.bn9);
                imvf1 = findViewById(R.id.ivf1);
                imvf2 = findViewById(R.id.ivf2);
                imvf3 = findViewById(R.id.ivf3);
                imvf4 = findViewById(R.id.ivf4);
                anim2 = AnimationUtils.loadAnimation(this, R.anim.shack_animation);
                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
                }

                set_newToken();
                gettickettype();
                getticketpriority();
                getproduct();
                final ImageView imv1 = findViewById(R.id.ivfingerprint);
                final ImageView imvremove = findViewById(R.id.bnremove);
                txv1 = findViewById(R.id.tv1);

                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
                txv1.setTypeface(atf);

                if (Build.VERSION.SDK_INT >= 23 && !Config.getusername(getApplicationContext()).equals("") && internetConnection()) {

                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

                    checkpermision();
                    if (!haspermission()) {
                        Toast.makeText(Login.this, getString(R.string.login_fingerperm_denied), Toast.LENGTH_SHORT).show();
                    } else if (fingerprintManager.isHardwareDetected()) {
                        if (!fingerprintManager.hasEnrolledFingerprints()) {
                            Toast.makeText(Login.this, getString(R.string.login_nofinger_set), Toast.LENGTH_SHORT).show();
                        } else {
                            generateKey();
                            if (cipherInit()) {
                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                CancellationSignal cancellationSignal = new CancellationSignal();
                                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                                            @Override
                                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }

                                            @Override
                                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }

                                            @Override
                                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                                imv1.setBackgroundResource(R.drawable.acceptfinger);
                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onAuthenticationFailed() {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }
                                        },
                                        null);
                            }
                        }
                    } else {
                        Toast.makeText(Login.this, getString(R.string.login_error_2), Toast.LENGTH_SHORT).show();
                    }
                }

                imvremove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Pass.equals("")) {
                            Pass = Pass.substring(0, Pass.length() - 1);
                            changepicture2();
                        }
                    }
                });


                btn0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("0");
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("1");
                    }
                });


                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("2");
                    }
                });


                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getPas("3");
                    }
                });


                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("4");
                    }
                });


                btn5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("5");
                    }
                });


                btn6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("6");
                    }
                });


                btn7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("7");
                    }
                });


                btn8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getPas("8");
                    }
                });


                btn9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPas("9");
                    }
                });

            }
        }
        else
        {
            if(Config.getusername(Login.this).equals(""))
            {
                setContentView(R.layout.loginsdk15);
                edtusername = (EditText)findViewById(R.id.etusername);
                edtpassword = (EditText)findViewById(R.id.etpassword);
                edtcaptcha = (EditText)findViewById(R.id.etcaptcha);
                btn1 = (Button)findViewById(R.id.confirm);
                btn2 = (Button)findViewById(R.id.repas);
                btn3 = (Button)findViewById(R.id.register);
                edtusername.setText(Config.getusername(getApplicationContext()));
                txv1 = findViewById(R.id.t1);
                txv2 = findViewById(R.id.t2);
                txv3 = findViewById(R.id.t3);
                txv4 = findViewById(R.id.texuser);
                txv5 = findViewById(R.id.textpass);
                txv6 = findViewById(R.id.textView31);

                lp = (LazyLoader) findViewById(R.id.dl);
                lp.setVisibility(View.INVISIBLE);

                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
                }




                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
                txv1.setTypeface(atf);
                txv2.setTypeface(atf);
                txv3.setTypeface(atf);
                txv4.setTypeface(atf);
                txv5.setTypeface(atf);
                txv6.setTypeface(atf);
                btn1.setTypeface(atf);
                btn2.setTypeface(atf);
                btn3.setTypeface(atf);
                edtusername.setTypeface(atf);
                edtcaptcha.setTypeface(atf);
                edtcaptcha.setTypeface(atf);

                if(internetConnection() && !Config.getBaseUrl().equals(""))
                {
                    getcaptcha();
                    gettickettype();
                    getticketpriority();
                    getproduct();
                }
                else
                {
                    Toast.makeText(Login.this , getString(R.string.login_connection_check) , Toast.LENGTH_LONG).show();
                }


                btn3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        if(Config.getBaseUrl().equals(""))
                        {
                            Toast.makeText(Login.this, getString(R.string.login_enteraddress), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(Login.this, Creatuser.class);
                            startActivity(intent);
                        }
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lp.setVisibility(View.VISIBLE);
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        lp.setVisibility(View.VISIBLE);
                        if(captchavalue.equals("-1"))
                        {
                            Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (captchavalue.equals(edtcaptcha.getText().toString())) {
                                String s = Config.getBaseUrl();
                                Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

                                API myApi2 = retrofit2.create(API.class);
                                Model_LoginPost modellog = new Model_LoginPost();
                                modellog.setUsername(edtusername.getText().toString());
                                modellog.setPassword(edtpassword.getText().toString());

                                Call<Model_LoginGet> model = myApi2.login(modellog);
                                model.enqueue(new Callback<Model_LoginGet>() {
                                    @Override
                                    public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
                                        if (response.isSuccessful()) {
                                            res = response.body().getResult();
                                            token = response.body().getToken();
                                            int id = response.body().getUserid();
                                            lp.setVisibility(View.GONE);
                                            if (res == -1) {
                                                Toast.makeText(Login.this, getString(R.string.login_enter_info), Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            } else if (res == -3) {
                                                lp.setVisibility(View.INVISIBLE);
                                                Toast.makeText(Login.this, getString(R.string.login_reenter_captcha), Toast.LENGTH_SHORT).show();
                                                getcaptcha();

                                            } else if (res == -2) {
                                                Toast.makeText(Login.this, getString(R.string.login_check_userpass), Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            } else if (res == 0) {
                                                lp.setVisibility(View.INVISIBLE);
                                                Config.SetToken(getApplicationContext(), token);
                                                Config.SetData(getApplicationContext(), edtusername.getText().toString(), edtpassword.getText().toString(), id);
                                                Intent intent = new Intent(Login.this, Digit_Password.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(Login.this, getString(R.string.login_system_error) + res, Toast.LENGTH_SHORT).show();
                                                getcaptcha();
                                                lp.setVisibility(View.INVISIBLE);
                                            }

                                        } else {
                                            lp.setVisibility(View.INVISIBLE);
                                            Toast.makeText(Login.this, getString(R.string.login_server_error), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Model_LoginGet> call, Throwable t) {
                                        Log.d(TAG, "Response" + t.getMessage());
                                        lp.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                lp.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this, getString(R.string.login_enter_captcha), Toast.LENGTH_SHORT).show();
                                getcaptcha();
                            }
                        }
                    }
                });
            }
            else
            {
                setContentView(R.layout.confirm_digit_password_sdk15);
                final Button btn0 , btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7 , btn8 , btn9;
                TextView txv1;
                btn0 = findViewById(R.id.bn0);
                btn1 = findViewById(R.id.bn1);
                btn2 = findViewById(R.id.bn2);
                btn3 = findViewById(R.id.bn3);
                btn4 = findViewById(R.id.bn4);
                btn5 = findViewById(R.id.bn5);
                btn6 = findViewById(R.id.bn6);
                btn7 = findViewById(R.id.bn7);
                btn8 = findViewById(R.id.bn8);
                btn9 = findViewById(R.id.bn9);
                imvf1 = findViewById(R.id.ivf1);
                imvf2 = findViewById(R.id.ivf2);
                imvf3 = findViewById(R.id.ivf3);
                imvf4 = findViewById(R.id.ivf4);
                anim2 = AnimationUtils.loadAnimation(this, R.anim.shack_animation);
                if(ActivityCompat.checkSelfPermission(Login.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Detail_Ticket_List.WRITE_EXTERNAL_STORAGE);
                }

                set_newToken();
                gettickettype();
                getticketpriority();
                getproduct();
                final ImageView imv1 = findViewById(R.id.ivfingerprint);
                final ImageView imvremove = findViewById(R.id.bnremove);
                txv1 = findViewById(R.id.tv1);

                Typeface atf = Typeface.createFromAsset(getAssets(), "roboto-regular.ttf");
                txv1.setTypeface(atf);

                if(Build.VERSION.SDK_INT >= 23 && !Config.getusername(getApplicationContext()).equals("") && internetConnection())
                {

                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

                    checkpermision();
                    if(!haspermission())
                    {
                        Toast.makeText(Login.this, getString(R.string.login_fingerperm_denied), Toast.LENGTH_SHORT).show();
                    }
                    else if(fingerprintManager.isHardwareDetected())
                    {
                        if(!fingerprintManager.hasEnrolledFingerprints())
                        {
                            Toast.makeText(Login.this, getString(R.string.login_nofinger_set), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            generateKey();
                            if(cipherInit())
                            {
                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                CancellationSignal cancellationSignal = new CancellationSignal();
                                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                                            @Override
                                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }

                                            @Override
                                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }

                                            @Override
                                            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                                imv1.setBackgroundResource(R.drawable.acceptfinger);
                                                Intent intent = new Intent(Login.this , MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onAuthenticationFailed() {
                                                imv1.setBackgroundResource(R.drawable.denidefinger);
                                                fingerprinte();
                                            }
                                        },
                                        null);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(Login.this, getString(R.string.login_error_2), Toast.LENGTH_SHORT).show();
                    }

                }

                imvremove.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(!Pass.equals(""))
                        {
                            Pass = Pass.substring(0, Pass.length() - 1);
                            changepicture2();
                        }
                    }
                });



                btn0.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("0");
                    }
                });

                btn1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("1");
                    }
                });


                btn2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("2");
                    }
                });


                btn3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        getPas("3");
                    }
                });


                btn4.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("4");
                    }
                });


                btn5.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("5");
                    }
                });


                btn6.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("6");
                    }
                });


                btn7.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("7");
                    }
                });


                btn8.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        getPas("8");
                    }
                });


                btn9.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getPas("9");
                    }
                });

            }
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void change_BaseUrl() {
        final boolean[] flag1 = {true};

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Call<List<Model_Gettickettype>> model = api.gettypeticket();
        model.enqueue(new Callback<List<Model_Gettickettype>>() {
            @Override
            public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response) {
                if (response.isSuccessful()) {
                    flag1[0] = true;
                } else {
                    Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
                    API api = retrofit2.create(API.class);
                    Call<List<Model_Gettickettype>> model = api.gettypeticket();
                    model.enqueue(new Callback<List<Model_Gettickettype>>() {
                        @Override
                        public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response) {
                            if (response.isSuccessful()) {
                                Config.BaseUrl = "http://tavanasazan.com/";
                            }

                        }

                        @Override
                        public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.server_1022), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean internetConnection() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return true;
        }
        NetworkInfo data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (data != null && data.isConnected()) {
            return true;
        }
        NetworkInfo active = cm.getActiveNetworkInfo();
        if (active != null && active.isConnected()) {
            return true;
        }

        return false;
    }

    public void getcaptcha() {
        imv = findViewById(R.id.ivcaptcha);
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit2.create(API.class);
        Call<List<post>> model2 = api.getcaptcha();
        model2.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {
                if (response.isSuccessful()) {
                    List<post> post = response.body();
                    String captcha = "";
                    for (int i = 0; i < post.size(); i++) {
                        captcha = response.body().get(i).getCaptcha();
                        captchavalue = response.body().get(i).getCaptchavalue();
                    }
                    byte[] bytearray = Base64.decode(captcha, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                    imv.setImageBitmap(bitmap);

                } else {
                    Toast.makeText(Login.this, getString(R.string.server_1011), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.server_1012), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gettickettype() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Call<List<Model_Gettickettype>> model = api.gettypeticket();
        model.enqueue(new Callback<List<Model_Gettickettype>>() {
            @Override
            public void onResponse(Call<List<Model_Gettickettype>> call, Response<List<Model_Gettickettype>> response) {
                if (response.isSuccessful()) {
                    db = openOrCreateDatabase("TicketType_DB", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
                    db.delete("TicketType_DB", null, null);

                    List<Model_Gettickettype> list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        db = openOrCreateDatabase("TicketType_DB", Context.MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
                        db.execSQL("INSERT INTO TicketType_DB(TicketType , idtickettype) VALUES ('" + list.get(i).getTitle() + "' , " + list.get(i).getId() + " )");

                    }
                } else {
                    Toast.makeText(Login.this, getString(R.string.server_1021), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_Gettickettype>> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.server_1022), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getticketpriority() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Call<List<Model_GetPriority>> model = api.getpriority();
        model.enqueue(new Callback<List<Model_GetPriority>>() {
            @Override
            public void onResponse(Call<List<Model_GetPriority>> call, Response<List<Model_GetPriority>> response) {
                if (response.isSuccessful()) {
                    db = openOrCreateDatabase("TicketPriority_DB", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
                    db.delete("TicketPriority_DB", null, null);
                    List<Model_GetPriority> list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        db = openOrCreateDatabase("TicketPriority_DB", Context.MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
                        db.execSQL("INSERT INTO TicketPriority_DB(TicketPriority , idpriority) VALUES ('" + list.get(i).getTitle() + "' , " + list.get(i).getId() + " )");

                    }
                } else {
                    Toast.makeText(Login.this, getString(R.string.server_1031), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_GetPriority>> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.server_1032), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getproduct() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Call<List<Model_getProductname>> model = api.getproductname();
        model.enqueue(new Callback<List<Model_getProductname>>() {
            @Override
            public void onResponse(Call<List<Model_getProductname>> call, Response<List<Model_getProductname>> response) {
                if (response.isSuccessful()) {
                    db = openOrCreateDatabase("TicketProduct_DB", Context.MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
                    db.delete("TicketProduct_DB", null, null);
                    List<Model_getProductname> list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        db = openOrCreateDatabase("TicketProduct_DB", Context.MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
                        db.execSQL("INSERT INTO TicketProduct_DB(TicketProduct , idproduct) VALUES ('" + list.get(i).getName() + "' , " + list.get(i).getId() + " )");

                    }
                } else {
                    String e = response.toString();
                    Toast.makeText(Login.this, getString(R.string.server_1041), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_getProductname>> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.server_1042), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void set_newToken() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

        API myApi2 = retrofit2.create(API.class);
        Model_LoginPost modellog = new Model_LoginPost();
        modellog.setUsername(Config.getusername(Login.this));
        modellog.setPassword(Config.getpassword(Login.this));

        Call<Model_LoginGet> model = myApi2.login(modellog);
        model.enqueue(new Callback<Model_LoginGet>() {
            @Override
            public void onResponse(Call<Model_LoginGet> call, Response<Model_LoginGet> response) {
                if (response.isSuccessful()) {
                    res = response.body().getResult();
                    token = response.body().getToken();
                    int id = response.body().getUserid();
                    if (res == -1) {
                        Toast.makeText(Login.this, getString(R.string.login_enter_info), Toast.LENGTH_SHORT).show();
                    } else if (res == -3) {
                        Toast.makeText(Login.this, getString(R.string.login_reenter_captcha), Toast.LENGTH_SHORT).show();

                    } else if (res == -2) {
                        Toast.makeText(Login.this, getString(R.string.login_check_userpass), Toast.LENGTH_SHORT).show();
                    } else if (res == 0) {
                        Config.SetToken(getApplicationContext(), token);
                    } else {
                        Toast.makeText(Login.this, getString(R.string.login_system_error) + res, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, getString(R.string.login_server_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model_LoginGet> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(Login.this, getString(R.string.login_connection_check), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkpermision() {
        if (!haspermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, REQ_Per_FingerPerint);
        }

    }

    private boolean haspermission() {
        return ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_Per_FingerPerint) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Login.this, getString(R.string.login_fingerperm_denied), Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @TargetApi(23)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to Get KeyGenerator Instance" + e.toString(), e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("Failed to Get KeyGenerator Instance" + e.toString(), e);
        }


        try {
            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new KeyGenParameterSpec.Builder(KeyName, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            }

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KeyName, null);
            SecretKey key2 = keyGenerator.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @TargetApi(23)
    public void fingerprinte() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

        final ImageView imv1 = findViewById(R.id.ivfingerprint);

        checkpermision();
        if (!haspermission()) {
            Toast.makeText(Login.this, getString(R.string.login_fingerperm_denied), Toast.LENGTH_SHORT).show();
        } else if (fingerprintManager.isHardwareDetected()) {
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(Login.this, getString(R.string.login_nofinger_set), Toast.LENGTH_SHORT).show();
            } else {
                generateKey();
                if (cipherInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    CancellationSignal cancellationSignal = new CancellationSignal();
                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                                @Override
                                public void onAuthenticationError(int errorCode, CharSequence errString) {
                                    imv1.setBackgroundResource(R.drawable.denidefinger);
                                    fingerprinte();

                                }

                                @Override
                                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                    imv1.setBackgroundResource(R.drawable.denidefinger);
                                    fingerprinte();
                                }

                                @Override
                                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                                    imv1.setBackgroundResource(R.drawable.acceptfinger);
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onAuthenticationFailed() {
                                    imv1.setBackgroundResource(R.drawable.denidefinger);
                                    fingerprinte();
                                }
                            },
                            null);
                }
            }
        } else {
            Toast.makeText(Login.this, getString(R.string.login_error_2), Toast.LENGTH_SHORT).show();
        }

    }

    public void changepicture2() {
        if (Pass.length() == 0) {
            imvf1.setBackgroundResource(R.drawable.f1);
        } else if (Pass.length() == 1) {
            imvf2.setBackgroundResource(R.drawable.f1);
        } else if (Pass.length() == 2) {
            imvf3.setBackgroundResource(R.drawable.f1);
        } else if (Pass.length() == 3) {
            imvf4.setBackgroundResource(R.drawable.f1);
        }
    }

    public void changepicture() {
        if (Pass.length() == 1) {
            imvf1.setBackgroundResource(R.drawable.f2);
        } else if (Pass.length() == 2) {
            imvf2.setBackgroundResource(R.drawable.f2);
        } else if (Pass.length() == 3) {
            imvf3.setBackgroundResource(R.drawable.f2);
        } else if (Pass.length() == 4) {
            imvf4.setBackgroundResource(R.drawable.f2);
        }
    }

    public void getPas(String num) {
        if (Pass.length() < 4) {
            Pass += num;
            changepicture();
            if (Pass.length() == 4) {
                if (Pass.equals(Config.get4DigitPassword(Login.this))) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    final Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
                    btn0 = findViewById(R.id.bn0);
                    btn1 = findViewById(R.id.bn1);
                    btn2 = findViewById(R.id.bn2);
                    btn3 = findViewById(R.id.bn3);
                    btn4 = findViewById(R.id.bn4);
                    btn5 = findViewById(R.id.bn5);
                    btn6 = findViewById(R.id.bn6);
                    btn7 = findViewById(R.id.bn7);
                    btn8 = findViewById(R.id.bn8);
                    btn9 = findViewById(R.id.bn9);
                    imvf1.startAnimation(anim2);
                    imvf2.startAnimation(anim2);
                    imvf3.startAnimation(anim2);
                    imvf4.startAnimation(anim2);
                    btn0.startAnimation(anim2);
                    btn1.startAnimation(anim2);
                    btn2.startAnimation(anim2);
                    btn3.startAnimation(anim2);
                    btn4.startAnimation(anim2);
                    btn5.startAnimation(anim2);
                    btn6.startAnimation(anim2);
                    btn7.startAnimation(anim2);
                    btn8.startAnimation(anim2);
                    btn9.startAnimation(anim2);
                    imvf1.setBackgroundResource(R.drawable.f1);
                    imvf2.setBackgroundResource(R.drawable.f1);
                    imvf3.setBackgroundResource(R.drawable.f1);
                    imvf4.setBackgroundResource(R.drawable.f1);
                    Pass = "";
                    Toast.makeText(Login.this, getString(R.string.login_wrong_pass), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}



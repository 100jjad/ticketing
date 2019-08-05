package com.example.ssoheyli.ticketing_newdesign.TestActivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.devlomi.record_view.OnBasketAnimationEnd;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.FilePath;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetCreatDetail;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetCreatTicket;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetResFile;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_UserInfo;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_PostTicketDetail;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_postcreatticket;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.example.ssoheyli.ticketing_newdesign.Ticket.SendTicket2;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketTestActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath;
    private static final String TAG = SendTicket2.class.getSimpleName();
    ProgressDialog dialog;
    EditText edtsubject, edttext;
    TextView edtname, edtemail;
    SQLiteDatabase db;
    Animation btnanim;
    MaterialSpinner spinnerticketpriority;
    Spinner spinnertickettype, spinnerticketproduct;
    int typetic, prioritytic, produttick, typeid, priorityid, productid;
    int ticketid, ticketdetailid;
    boolean flagfile = false;
    Uri selectedFileUri;
    String outputFile;
    ArrayAdapter<String> tikettype_adapter;
    ArrayAdapter<String> product_adapter;
    boolean flagvoice = false;
    MediaRecorder myRecorder = new MediaRecorder();
    public static final int RECORD_AUDIO = 0;
    LazyLoader loader;
    LinearLayout btnchoosfile;

    RecordButton recordButton;
    RecordView recordView;
    private ImageView fab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Constans.theme);
        setContentView(R.layout.send_ticket_material2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, Constans.colorprimary));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        FloatingActionButton btnsendfile;
        LinearLayout btnvoice;
        edtname = findViewById(R.id.etname);
        edtemail = findViewById(R.id.etemail);
        edtsubject = findViewById(R.id.etsubject);
        edttext = findViewById(R.id.etmessage);
        spinnertickettype = findViewById(R.id.spcuntry);
        spinnerticketpriority = findViewById(R.id.sppriority);
        spinnerticketproduct = findViewById(R.id.spcity);
        btnvoice = findViewById(R.id.voice_record);
        btnchoosfile = findViewById(R.id.bnchoosfile);
        btnsendfile = findViewById(R.id.bnsendfile);
        loader = findViewById(R.id.loader);
        TextView textView21, textView33;
        textView21 = findViewById(R.id.textView21);
        textView33 = findViewById(R.id.textView33);
        textView21.setTextColor(ContextCompat.getColor(this, Constans.colorprimary_dark));
        textView33.setTextColor(ContextCompat.getColor(this, Constans.colorprimary_dark));
        btnanim = AnimationUtils.loadAnimation(this, R.anim.button_anim1);


        fab = findViewById(R.id.sendbtn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
        }

        // RECORD BUTTONS
        recordView = findViewById(R.id.record_view);

        recordButton = findViewById(R.id.record_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recordButton.setBackgroundTintList(ContextCompat.getColorStateList(this, Constans.colorprimary));
        }

        recordButton.setRecordView(recordView);
        //

        initialize();


        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/file_recorded.3gpp";


        if (ActivityCompat.checkSelfPermission(TicketTestActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TicketTestActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO);
        } else {
            outputFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/file_recorded.3gpp";
            myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myRecorder.setOutputFile(outputFile);
        }


        btnchoosfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edttext.getText().toString().equals("")) {
                    Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_changetext), Toast.LENGTH_SHORT).show();
                } else {
                    fab.startAnimation(btnanim);
                    loader.setVisibility(View.VISIBLE);
                    send_header_ticket();
                }
            }
        });

        // voice recorder
        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                //Start Recording..
                recordView.bringToFront();
                start(recordView);
                btnchoosfile.setVisibility(View.INVISIBLE);
                edttext.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancel() {
                //On Swipe To Cancel
                recordView.bringToFront();
                recordView.invalidate();
                btnchoosfile.setVisibility(View.VISIBLE);
                edttext.setVisibility(View.VISIBLE);

                stop(recordView);

                myRecorder = new MediaRecorder();
            }

            @Override
            public void onFinish(long recordTime) {
                //Stop Recording..
                String time = getHumanTimeText(recordTime);
                edttext.setText(getString(R.string.sendticket_voiceattach));
                recordButton.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);

                btnchoosfile.setVisibility(View.VISIBLE);
                edttext.setVisibility(View.VISIBLE);

                flagvoice = true;
                stop(recordView);

                myRecorder = new MediaRecorder();
                outputFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/file_recorded.3gpp";
                myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myRecorder.setOutputFile(outputFile);
            }

            @Override
            public void onLessThanSecond() {
                //When the record time is less than One Second
                btnchoosfile.setVisibility(View.VISIBLE);
                edttext.setVisibility(View.VISIBLE);
            }
        });
        recordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
            @Override
            public void onAnimationEnd() {
            }
        });

        edttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    recordButton.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);
                } else {
                    recordButton.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });

        spinnertickettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                db = openOrCreateDatabase("TicketType_DB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
                Cursor c = db.rawQuery("SELECT * FROM TicketType_DB", null);

                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        if (c.getPosition() == position) {
                            typeid = c.getInt(2);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerticketpriority.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                db = openOrCreateDatabase("TicketPriority_DB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
                Cursor c2 = db.rawQuery("SELECT * FROM TicketPriority_DB", null);
                if (c2.getCount() > 0) {
                    while (c2.moveToNext()) {
                        if (c2.getPosition() == position) {
                            priorityid = c2.getInt(2);
                        }
                    }
                }
            }
        });

        spinnerticketproduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                db = openOrCreateDatabase("TicketProduct_DB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
                Cursor c3 = db.rawQuery("SELECT * FROM TicketProduct_DB", null);

                if (c3.getCount() > 0) {
                    while (c3.moveToNext()) {
                        if (c3.getPosition() == position) {
                            productid = c3.getInt(2);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void initialize() {
        String[] tickettype;
        String[] ticketpriority;
        String[] ticketproduct;

        spinnertickettype = findViewById(R.id.spcuntry);
        spinnerticketpriority = findViewById(R.id.sppriority);
        spinnerticketproduct = findViewById(R.id.spcity);

        db = openOrCreateDatabase("TicketType_DB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS TicketType_DB(_id integer primary key autoincrement , TicketType varchar , idtickettype INTEGER)");
        Cursor c = db.rawQuery("SELECT * FROM TicketType_DB", null);

        tickettype = new String[c.getCount()];

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                int pos = c.getPosition();
                tickettype[c.getPosition()] = c.getString(1);
            }
        }

        tikettype_adapter = new ArrayAdapter<String>(TicketTestActivity.this, android.R.layout.simple_spinner_item, tickettype);
        spinnertickettype.setAdapter(tikettype_adapter);


        db = openOrCreateDatabase("TicketPriority_DB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS TicketPriority_DB(_id integer primary key autoincrement , TicketPriority varchar  , idpriority)");
        Cursor c2 = db.rawQuery("SELECT * FROM TicketPriority_DB", null);

        ticketpriority = new String[c2.getCount()];

        if (c2.getCount() > 0) {
            while (c2.moveToNext()) {
                ticketpriority[c2.getPosition()] = c2.getString(1);
            }
        } else {
            ticketpriority = new String[1];
            ticketpriority[0] = getString(R.string.sendticket_priority);
        }

        spinnerticketpriority.setItems(ticketpriority);


        db = openOrCreateDatabase("TicketProduct_DB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS TicketProduct_DB(_id integer primary key autoincrement , TicketProduct varchar , idproduct INTEGER)");
        Cursor c3 = db.rawQuery("SELECT * FROM TicketProduct_DB", null);

        ticketproduct = new String[c3.getCount()];

        if (c3.getCount() > 0) {
            while (c3.moveToNext()) {
                ticketproduct[c3.getPosition()] = c3.getString(1);
            }
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ticketproduct);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        product_adapter = new ArrayAdapter<String>(TicketTestActivity.this, android.R.layout.simple_spinner_item, ticketproduct);
        spinnerticketproduct.setAdapter(product_adapter);

//        spinnerticketproduct.setItems(ticketproduct);


        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API myApi2 = retrofit2.create(API.class);
        Call<List<Model_Get_UserInfo>> model = myApi2.Get_User_Info("" + Config.getuserid((getApplicationContext())));
        model.enqueue(new Callback<List<Model_Get_UserInfo>>() {
            @Override
            public void onResponse(Call<List<Model_Get_UserInfo>> call, Response<List<Model_Get_UserInfo>> response) {
                if (response.isSuccessful()) {
                    List<Model_Get_UserInfo> userInfos = response.body();
                    edtname.setText(userInfos.get(0).getFullname());
                    edtemail.setText(userInfos.get(0).getEmail());

                } else {
                    Log.d(TAG, "Response" + response.toString());

                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_UserInfo>> call, Throwable t) {

            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }

    public void send_header_ticket() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

        API myApi = retrofit2.create(API.class);
        Model_postcreatticket model_postcreatticket = new Model_postcreatticket();
        model_postcreatticket.setSubject(edtsubject.getText().toString());
        model_postcreatticket.setTypeticket(typeid);
        model_postcreatticket.setPriority(priorityid);
        model_postcreatticket.setProductid(productid);
        model_postcreatticket.setToken(Config.getToken(getApplicationContext()));
        model_postcreatticket.setUserid(Config.getuserid(getApplicationContext()));
        Call<Model_GetCreatTicket> model = myApi.creatticket(model_postcreatticket);
        model.enqueue(new Callback<Model_GetCreatTicket>() {
            @Override
            public void onResponse(Call<Model_GetCreatTicket> call, Response<Model_GetCreatTicket> response) {
                loader.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    int res = response.body().getResult();
                    if (res == -4) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_reenter_app), Toast.LENGTH_SHORT).show();
                    } else if (res == 0) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_failed), Toast.LENGTH_SHORT).show();
                    } else if (res > 0) {
                        ticketid = res;
                        send_detail_ticket();
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_result) + response.body(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "Response" + response.toString());
                    Toast.makeText(TicketTestActivity.this, getString(R.string.server_1101), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model_GetCreatTicket> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(TicketTestActivity.this, getString(R.string.server_1102), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void send_detail_ticket() {
        if (edttext.getText().toString().equals("")) {
            Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_changetext), Toast.LENGTH_SHORT).show();
        } else {
            Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();

            API myApi = retrofit2.create(API.class);
            Model_PostTicketDetail model_postTicketDetail = new Model_PostTicketDetail();
            model_postTicketDetail.setDescription(edttext.getText().toString());
            model_postTicketDetail.setTicketid(ticketid);
            model_postTicketDetail.setToken(Config.getToken(getApplicationContext()));
            Call<Model_GetCreatDetail> model = myApi.creatticketdetail(model_postTicketDetail);
            model.enqueue(new Callback<Model_GetCreatDetail>() {
                @Override
                public void onResponse(Call<Model_GetCreatDetail> call, Response<Model_GetCreatDetail> response) {
                    if (response.isSuccessful()) {
                        int result = response.body().getResult();
                        if (result == -4) {
                            Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_reenter_app), Toast.LENGTH_LONG).show();
                        } else if (result == 0) {
                            Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_notpossible), Toast.LENGTH_LONG).show();
                        } else if (result == -1) {
                            Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_servererror), Toast.LENGTH_LONG).show();
                        } else if (result > 0) {
                            edttext.setText("");
                            ticketdetailid = result;
                            if (flagfile) {
                                flagfile = false;
                                uploadFile(selectedFilePath);
                            }
                            if (flagvoice) {
                                flagvoice = false;
                                uploadFile(outputFile);
                            }
                            Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_success), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.server_1091), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model_GetCreatDetail> call, Throwable t) {
                    Toast.makeText(TicketTestActivity.this, getString(R.string.server_1092), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void uploadFile(final String selectedFilePath) {

        File file = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        Uri fileUri = Uri.parse(selectedFilePath);
        Uri fileUri2 = Uri.fromFile(file);
        API myApi = retrofit2.create(API.class);

        MediaType MEDIA_TYPE =
                MediaType.parse(getMimeType(fileUri2));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", "2")
                .addFormDataPart("ticketDetailId", "2")
                .addFormDataPart("token", Config.getToken(getApplicationContext()))
                .addFormDataPart("file", "" + file.getName(),
                        RequestBody.create(MEDIA_TYPE, file))
                .build();


        RequestBody requestFile =
                RequestBody.create(
                        MEDIA_TYPE,
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        String descriptionString = "" + ticketdetailid;
        RequestBody ticketdetailid =
                RequestBody.create(
                        MultipartBody.FORM, descriptionString);

        String descriptionString2 = "" + ticketid;
        RequestBody userid =
                RequestBody.create(
                        MultipartBody.FORM, descriptionString2);


        String descriptionString3 = Config.getToken(getApplicationContext());
        RequestBody token =
                RequestBody.create(
                        MultipartBody.FORM, descriptionString3);


        Call<Model_GetResFile> model = myApi.sendfile(ticketdetailid, userid, token, body);
        model.enqueue(new Callback<Model_GetResFile>() {
            @Override
            public void onResponse(Call<Model_GetResFile> call, Response<Model_GetResFile> response) {
                if (response.isSuccessful()) {
                    int res = response.body().getResult();
                    if (res == -4) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_reenter_app), Toast.LENGTH_SHORT).show();
                    } else if (res == 0) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_notreg), Toast.LENGTH_SHORT).show();
                    } else if (res == -1) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_size_toolarg), Toast.LENGTH_SHORT).show();
                    } else if (res > 0) {
                        Toast.makeText(TicketTestActivity.this, getString(R.string.sendticket_success), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(TicketTestActivity.this, getString(R.string.server_1081), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model_GetResFile> call, Throwable t) {
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(TicketTestActivity.this, getString(R.string.server_1082) + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getMimeType(Uri uri) {
        String mimeType = "";
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public void start(View view) {
        try {
            myRecorder.setOutputFile(outputFile);
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

    }

    public void stop(View view) {
        try {
            myRecorder.setOutputFile(outputFile);
            myRecorder.stop();
            myRecorder.release();
            myRecorder = null;

        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    return;
                }

                flagfile = true;
                selectedFileUri = data.getData();

                if (ContextCompat.checkSelfPermission(TicketTestActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(TicketTestActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(TicketTestActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

                    } else {
                        ActivityCompat.requestPermissions(TicketTestActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

                    }
                } else {
                    selectedFilePath = FilePath.getPath(this, selectedFileUri);
                    Log.i(TAG, "مسیر فایل انتخاب شده :" + selectedFilePath);

                    if (selectedFilePath != null && !selectedFilePath.equals("")) {

                        Toast.makeText(TicketTestActivity.this, selectedFilePath, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    {
                        if (ContextCompat.checkSelfPermission(TicketTestActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show();
                            selectedFilePath = FilePath.getPath(this, selectedFileUri);
                            Log.i(TAG, "Selected File Path:" + selectedFilePath);

                            if (selectedFilePath != null && !selectedFilePath.equals("")) {

                                Toast.makeText(TicketTestActivity.this, selectedFilePath, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "no permission granted", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                }
        }
    }

    @SuppressLint("DefaultLocale")
    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );
    }

    @Override
    public void onBackPressed() {
        if (recordButton.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }
        // cancel voice
        recordButton.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


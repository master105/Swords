package com.example.nezarsaleh.shareknitest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nezarsaleh.shareknitest.Arafa.Classes.GetData;
import com.example.nezarsaleh.shareknitest.LogIN.RegisterJsonParse;
import com.example.nezarsaleh.shareknitest.OnBoardDir.OnboardingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class RegisterNewTest extends AppCompatActivity implements View.OnClickListener {

    static final int DILOG_ID = 0;
    static RegisterNewTest registerNewTestActivity;
    final Calendar cal = Calendar.getInstance();
    int year_x, month_x, day_x;
    char i = 'M';
    int Nationality_ID;
    int Language_ID;
    EditText edit_fname;
    EditText edit_lname;
    EditText edit_phone;
    EditText edit_pass;
    EditText edit_user;
    String usertype = "1";
    ImageView driver_toggle;
    ImageView passenger_toogle;
    ImageView both_toggle;
    ImageView both_toggle_active;
    ImageView passenger_toggle_active;
    ImageView driver_toggle_active;
//    ImageView male_female;
//    ImageView female_male;
    Button btn_save;
    Button btn_upload_image;
    TextView txt_year;
//    TextView txt_month;
//    TextView txt_day;
    TextView txt_dayOfWeek;
    TextView txt_comma;
    TextView txt_beforeCal;
    TextView txt_appbar;
//    ScrollView scroll;
    String full_date;
    TextView txt_lang;
    AutoCompleteTextView txt_country;
    List<TreeMap<String, String>> Lang_List = new ArrayList<>();
    List<TreeMap<String, String>> Country_List = new ArrayList<>();
    ListView mListView;
    Dialog list_dialog;
    RelativeLayout btn_datepicker_id;
    Context mContext;

    TextView malefemale_txt, femalemale_txt;
    ImageView malefemale, femalemale;
    String uploadedImage;
    TextView Terms_And_Cond_txt;


    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            txt_beforeCal.setVisibility(View.INVISIBLE);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year_x);
            cal.set(Calendar.MONTH, month_x);
            cal.set(Calendar.DAY_OF_MONTH, day_x + 4);
            Date date = cal.getTime();
            String dayOfWeek = simpledateformat.format(date);
            String year_string = String.valueOf(year_x);
            String month_string = String.valueOf(month_x);
            String day_string = String.valueOf(day_x);
            full_date = day_string + "/" + month_string + "/" + year_string;
            txt_year.setText(full_date);
            txt_comma.setVisibility(View.VISIBLE);
            txt_dayOfWeek.setText(dayOfWeek);
            Log.d("Calendar test", full_date);
        }
    };

    public static RegisterNewTest getInstance() {
        return registerNewTestActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        registerNewTestActivity = this;
        mContext = this;

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);


        setContentView(R.layout.activity_register_new_test);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txt_year = (TextView) findViewById(R.id.txt_year);
        txt_beforeCal = (TextView) findViewById(R.id.txt_beforeCal);
        txt_dayOfWeek = (TextView) findViewById(R.id.txt_dayOfWeek);
        showDialogOnButtonClick();

        driver_toggle = (ImageView) findViewById(R.id.driver_toggle);
        passenger_toogle = (ImageView) findViewById(R.id.passenger_toggle);
        both_toggle = (ImageView) findViewById(R.id.both_toggle);
        both_toggle_active = (ImageView) findViewById(R.id.both_toggle_active);
        driver_toggle_active = (ImageView) findViewById(R.id.driver_toggle_active);
        passenger_toggle_active = (ImageView) findViewById(R.id.passenger_toggle_active);

        btn_save = (Button) findViewById(R.id.btn_register_id);
        btn_upload_image = (Button) findViewById(R.id.btnUploadPhotoReg);
        edit_fname = (EditText) findViewById(R.id.edit_reg_fname);
        edit_lname = (EditText) findViewById(R.id.edit_reg_lname);
        edit_phone = (EditText) findViewById(R.id.edit_reg_phone);
        edit_pass = (EditText) findViewById(R.id.edit_reg_pass);
        edit_user = (EditText) findViewById(R.id.edit_reg_username);
        txt_comma = (TextView) findViewById(R.id.Register_comma_cal);
        malefemale_txt = (TextView) findViewById(R.id.malefemale_txt);
        femalemale_txt = (TextView) findViewById(R.id.femalemale_txt);

        malefemale = (ImageView) findViewById(R.id.malefemale);
        femalemale = (ImageView) findViewById(R.id.femalemale);
        Terms_And_Cond_txt= (TextView) findViewById(R.id.Terms_And_Cond_txt);

        txt_lang = (TextView) findViewById(R.id.autocomplete_lang_id);
        txt_country = (AutoCompleteTextView) findViewById(R.id.autocompletecountry_id);

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RegisterNewTest.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0);
                        } else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), 1337);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        txt_country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                Boolean result = false;
                if (!hasFocus) {
                    if (Country_List.size() != 0 && txt_country.getText() != null && !txt_country.getText().toString().equals("Nationality")) {
                        for (int i = 0; i <= 193; i++) {
                            String a = Country_List.get(i).get("NationalityEnName");
                            String b = txt_country.getText().toString();
                            if (a.equals(b)) {
                                result = true;
                            }
                        }
                    }
                    if (!result) {
                        Toast.makeText(RegisterNewTest.this, "unknown Country ( Choose From The List )", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        txt_lang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                Boolean result = false;
                try {
                    if (!hasFocus) {
                        if (Lang_List.size() != 0 && txt_lang.getText() != null && !txt_lang.getText().toString().equals("Preferred Language")) {
                            for (int i = 0; i <= Lang_List.size(); i++) {
                                String a = Lang_List.get(i).get("NationalityEnName");
                                String b = txt_lang.getText().toString();
                                if (a.equals(b)) {
                                    result = true;
                                }
                            }
                        }
                        if (!result) {
                            Toast.makeText(RegisterNewTest.this, "unknown Language ( Choose From The List )", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(RegisterNewTest.this, "unknown Language ( Choose From The List )", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edit_pass != null) {
                        if (edit_pass.length() <= 4) {
                            Toast.makeText(RegisterNewTest.this, "Password must be more than 4 Character", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        edit_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edit_phone != null) {
                        if (edit_phone.length() == 7) {
                            Toast.makeText(RegisterNewTest.this, "So Short For Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        both_toggle.setOnClickListener(this);
        driver_toggle.setOnClickListener(this);
        passenger_toogle.setOnClickListener(this);
        both_toggle_active.setOnClickListener(this);
        passenger_toggle_active.setOnClickListener(this);
        driver_toggle_active.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar2);
        txt_appbar = (TextView) toolbar.findViewById(R.id.mytext_appbar);
        txt_appbar.setText(R.string.Reg_appBar_name);

        // get Languages
        new lang().execute();

        // get nationals
        new nat().execute();



        Terms_And_Cond_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =  new Intent(getBaseContext(),TermsAndCondition.class);
//                startActivity(intent);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_fname.getText() != null && edit_fname.getText().toString() != "First Name" && edit_lname.getText() != null && edit_lname.getText().toString() != "Last Name" && edit_phone.getText() != null && edit_phone.getText().toString() != "Mobile Number" && edit_pass.getText() != null && edit_pass.getText().toString() != "Password" && edit_user.getText() != null && edit_user.getText().toString() != "User Name (Your Email)" && txt_country.getText() != null && txt_country.getText().toString() != "Nationality" && txt_lang.getText() != null && txt_lang.getText().toString() != "Preferred Language" && full_date != null) {
                    if (uploadedImage == null){
                        Toast.makeText(RegisterNewTest.this, "Select Profile Image!!", Toast.LENGTH_SHORT).show();
                    }else{
                        String Fname = edit_fname.getText().toString();
                        String Lname = edit_lname.getText().toString();
                        String phone = edit_phone.getText().toString();
                        String pass = edit_pass.getText().toString();
                        String user = edit_user.getText().toString();
                        String country = txt_country.getText().toString();
//                        String lang = txt_lang.getText().toString();
                        char gender = i;
                        String birthdate = full_date;
                        int x = Language_ID;
                        int y = Nationality_ID;
                        RegisterJsonParse registerJsonParse = new RegisterJsonParse();

                        switch (usertype) {
                            case "Passenger":
                                registerJsonParse.stringRequest(GetData.DOMAIN + "RegisterPassenger?firstName=" + URLEncoder.encode(Fname) + "&lastName=" + URLEncoder.encode(Lname) + "&mobile=" + phone + "&username=" + user + "&password=" + pass + "&gender=" + gender + "&BirthDate=" + birthdate + "&NationalityId=" + y + "&PreferredLanguageId=" + x + "&photoName=" + uploadedImage, getBaseContext(), country, "P");
                                Log.d("Registration :",GetData.DOMAIN + "RegisterPassenger?firstName=" + URLEncoder.encode(Fname) + "&lastName=" + URLEncoder.encode(Lname) + "&mobile=" + phone + "&username=" + user + "&password=" + pass + "&gender=" + gender + "&BirthDate=" + birthdate + "&NationalityId=" + y + "&PreferredLanguageId=" + x + "&photoName=" + uploadedImage);
                                break;
                            case "1":
                                Toast.makeText(RegisterNewTest.this, "please Choose a user Type", Toast.LENGTH_SHORT).show();

                                break;
                            case "Driver":

                                registerJsonParse.stringRequest(GetData.DOMAIN + "RegisterDriver?firstName=" + URLEncoder.encode(Fname) + "&lastName=" + URLEncoder.encode(Lname) + "&mobile=" + phone + "&username=" + user + "&password=" + pass + "&gender=" + gender + "&BirthDate=" + birthdate + "&licenseScannedFileName=nofile.jpg" + "&TrafficFileNo=nofile.jpg" + "&photoName=" + uploadedImage + "&NationalityId=" + y + "&PreferredLanguageId=" + x, getBaseContext(), country, "D");

                                break;
                            case "Both":

                                registerJsonParse.stringRequest(GetData.DOMAIN + "RegisterDriver?firstName=" + URLEncoder.encode(Fname) + "&lastName=" + URLEncoder.encode(Lname) + "&mobile=" + phone + "&username=" + user + "&password=" + pass + "&gender=" + gender + "&BirthDate=" + birthdate + "&licenseScannedFileName=nofile.jpg" + "&TrafficFileNo=nofile.jpg" + "&photoName=" + uploadedImage + "&NationalityId=" + y + "&PreferredLanguageId=" + x, getBaseContext(), country, "D");

                                break;
                        }

                    }
                } else {
                    Toast.makeText(RegisterNewTest.this, R.string.fill_all_error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        malefemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malefemale.setVisibility(View.INVISIBLE);
                femalemale.setVisibility(View.VISIBLE);
                malefemale_txt.setTextColor(Color.GRAY);
                femalemale_txt.setTextColor(Color.RED);
                i = 'F';

            }
        });


        femalemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femalemale.setVisibility(View.INVISIBLE);
                malefemale.setVisibility(View.VISIBLE);
                malefemale_txt.setTextColor(Color.RED);
                femalemale_txt.setTextColor(Color.GRAY);
                i = 'M';
            }
        });


        femalemale_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malefemale_txt.setTextColor(Color.GRAY);
                femalemale_txt.setTextColor(Color.RED);

                malefemale.setVisibility(View.INVISIBLE);
                femalemale.setVisibility(View.VISIBLE);
                i = 'M';

            }
        });


        malefemale_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 'F';
                malefemale_txt.setTextColor(Color.RED);
                femalemale_txt.setTextColor(Color.GRAY);

                malefemale.setVisibility(View.VISIBLE);
                femalemale.setVisibility(View.INVISIBLE);
            }
        });


    }

    private class ImageUpload extends AsyncTask<String,Void,String>{
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RegisterNewTest.this);
            pDialog.setMessage("Loading" + "...");
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            hidePDialog();
            super.onPostExecute(s);
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }


        @Override
        protected String doInBackground(String... params) {
            callSOAPWebService(params[0]);
            return null;
        }

        private boolean callSOAPWebService(String data) {
            OutputStream out = null;
            int respCode;
            boolean isSuccess = false;
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(GetData.NonOpDomain);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                do {
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection", "keep-alive");
                    httpURLConnection.setRequestProperty("Content-Type", "text/xml");
                    httpURLConnection.setRequestProperty("SendChunked", "True");
                    httpURLConnection.setRequestProperty("UseCookieContainer", "True");
                    HttpURLConnection.setFollowRedirects(false);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(true);
                    httpURLConnection.setRequestProperty("Content-length", getReqData(data).length + "");
                    httpURLConnection.setReadTimeout(100 * 1000);
                    // httpURLConnection.setConnectTimeout(10 * 1000);
                    httpURLConnection.connect();
                    out = httpURLConnection.getOutputStream();
                    if (out != null) {
                        out.write(getReqData(data));
                        out.flush();
                    }
                    respCode = httpURLConnection.getResponseCode();
                    Log.e("respCode", ":" + respCode);
                } while (respCode == -1);

                // If it works fine
                if (respCode == 200) {
                    try {
                        InputStream responce = httpURLConnection.getInputStream();
                        String str = convertStreamToString(responce);
                        System.out.println(".....data....." + str);
                        InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
                        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();
                        myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparser.setInput(is, null);
                        parseXMLAndStoreIt(myparser);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    isSuccess = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out = null;
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    httpURLConnection = null;
                }
            }
            return isSuccess;
        }
    }



    public volatile boolean parsingComplete = true;
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("UploadImageResult")){
                            uploadedImage = text;
                            uploadedImage = uploadedImage.replace("\"", "");
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createSoapHeader() {
        String soapHeader;

        soapHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope "
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" + ">";
        return soapHeader;
    }

    public static byte[] getReqData(String data) {
        StringBuilder requestData = new StringBuilder();

        requestData.append(createSoapHeader());
        requestData.append("<soap:Body>" + "<UploadImage" + " xmlns=\"http://Sharekni-MobAndroid-Data.org/\">" + "<ImageContent>").append(data).append("</ImageContent>\n").append("<imageExtenstion>jpg</imageExtenstion>").append("</UploadImage> </soap:Body> </soap:Envelope>");
        Log.d("reqData: ",requestData.toString());
        return requestData.toString().trim().getBytes();
    }

    private static String convertStreamToString(InputStream is)
            throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                new ImageUpload().execute(encoded);
//                ivImage.setImageBitmap(thumbnail);
            } else if (requestCode == 1337) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                new ImageUpload().execute(encoded);
//                ivImage.setImageBitmap(bm);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_new_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == both_toggle) {
            both_toggle_active.setVisibility(View.VISIBLE);
            passenger_toggle_active.setVisibility(View.INVISIBLE);
            driver_toggle_active.setVisibility(View.INVISIBLE);
            usertype = "Both";
        }

        if (v == passenger_toogle) {
            passenger_toggle_active.setVisibility(View.VISIBLE);
            driver_toggle_active.setVisibility(View.INVISIBLE);
            both_toggle_active.setVisibility(View.INVISIBLE);
            usertype = "Passenger";
        }

        if (v == driver_toggle) {
            driver_toggle_active.setVisibility(View.VISIBLE);
            passenger_toggle_active.setVisibility(View.INVISIBLE);
            both_toggle_active.setVisibility(View.INVISIBLE);
            usertype = "Driver";
        }

//        if (v == male_female) {
//            male_female.setVisibility(View.INVISIBLE);
//            female_male.setVisibility(View.VISIBLE);
//            i = 'F';
//        } else if (v == female_male) {
//            i = 'M';
//            female_male.setVisibility(View.INVISIBLE);
//            male_female.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DILOG_ID) {
            DatePickerDialog dp = new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            DatePicker d = dp.getDatePicker();
//            long years = new Long(568025136000);
            d.setMaxDate(cal.get(Calendar.MILLISECOND));
            return dp;
        }
        return null;
    }

    public void showDialogOnButtonClick() {
        btn_datepicker_id = (RelativeLayout) findViewById(R.id.datepicker_id);
        btn_datepicker_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_ID);
            }
        });
    }

    private class lang extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            final SimpleAdapter adapter2 = new SimpleAdapter(RegisterNewTest.this, Lang_List
                    , R.layout.autocomplete_row
                    , new String[]{"LanguageId", "LanguageEnName"}
                    , new int[]{R.id.row_id, R.id.row_name});

            list_dialog = new Dialog(mContext);
            list_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            list_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            list_dialog.setContentView(R.layout.language_dialog);
            mListView = (ListView) list_dialog.findViewById(R.id.lang_dialog_lv_id);
            mListView.setAdapter(adapter2);
            txt_lang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_dialog.show();
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                            TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                            Language_ID = Integer.parseInt(txt_lang_id.getText().toString());
                            txt_lang.setText(txt_lang_name.getText().toString());
                            Log.d("id of lang", "" + Language_ID);
                            list_dialog.dismiss();
                        }
                    });
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(RegisterNewTest.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(RegisterNewTest.this, RegisterNewTest.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(RegisterNewTest.this, OnboardingActivity.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(RegisterNewTest.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    JSONArray j = new GetData().GetPrefLanguage();
                    for (int i = 0; i < j.length(); i++) {
                        TreeMap<String, String> valuePairs = new TreeMap<>();
                        JSONObject jsonObject = j.getJSONObject(i);
                        valuePairs.put("LanguageId", jsonObject.getString("LanguageId"));
                        valuePairs.put("LanguageEnName", jsonObject.getString("LanguageEnName"));
                        Lang_List.add(valuePairs);
                    }
                    Log.d("Language :", Lang_List.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Language 2 :", Lang_List.toString());
            }
            return null;
        }
    }

    private class nat extends AsyncTask {
        @Override
        protected void onPostExecute(Object o) {
            SimpleAdapter adapterCountry = new SimpleAdapter(RegisterNewTest.this, Country_List
                    , R.layout.autocomplete_row
                    , new String[]{"ID", "NationalityEnName"}
                    , new int[]{R.id.row_id, R.id.row_name});
            txt_country.setAdapter(adapterCountry);

            txt_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt_lang_name = (TextView) view.findViewById(R.id.row_name);
                    TextView txt_lang_id = (TextView) view.findViewById(R.id.row_id);
                    Nationality_ID = Integer.parseInt(txt_lang_id.getText().toString());
                    txt_country.setText(txt_lang_name.getText().toString());
                    Log.d("id of lang", "" + Nationality_ID);
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] params) {
            boolean exists = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress("www.google.com", 80);
                Socket sock = new Socket();
                int timeoutMs = 20000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                exists = true;
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(RegisterNewTest.this)
                                .setTitle("Connection Problem!")
                                .setMessage("Make sure you have internet connection")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentToBeNewRoot = new Intent(RegisterNewTest.this, RegisterNewTest.class);
                                        ComponentName cn = intentToBeNewRoot.getComponent();
                                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                        startActivity(mainIntent);
                                    }
                                })
                                .setNegativeButton("Exit!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
                        Toast.makeText(RegisterNewTest.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (exists) {
                try {
                    JSONArray j = new GetData().GetNationalities();
                    for (int i = 0; i < j.length(); i++) {
                        TreeMap<String, String> valuePairs = new TreeMap<>();
                        JSONObject jsonObject = j.getJSONObject(i);
                        valuePairs.put("ID", jsonObject.getString("ID"));
                        valuePairs.put("NationalityEnName", jsonObject.getString("NationalityEnName"));
                        Country_List.add(valuePairs);
                    }
                    Log.d("Nat :", Country_List.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Nat 2 :", Country_List.toString());
            }
            return null;
        }
    }
}


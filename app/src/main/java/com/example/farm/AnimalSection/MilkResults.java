package com.example.farm.AnimalSection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.Adapters.MilkResultsAdapter;
import com.example.farm.Employees.Employees;
import com.example.farm.Modals.EmployeeModel;
import com.example.farm.Modals.MilkResultsModel;
import com.example.farm.R;
import com.example.farm.Urls.SessionManager;
import com.example.farm.Urls.Urls;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MilkResults extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MilkResultsModel> mData;
    MilkResultsAdapter adapter;
    TextView error_message_balance, no_message_balance, seeltecttime;
    //    SessionManager sessionManager;
    Urls urls;
    String getID;

    Spinner milktimeslot;
    EditText selectedtimessss;
    ImageView closesearching,searching;
    MaterialCardView scroll_filter;
    String fileFinalName;
    LinearLayout download_results_linear, show_results_linear,
            wrong_download_results_linear, linear_fromdate,linear_todate,linear_menu,li_show_results;
    MaterialCardView cardexportrecords, cardfilterrecords, getdate_from, getdate_to,
            export_filter;
    DatePicker datePickerFrom, datePickerTo;
    TextView fromDates, toDates, scrFrom, scrTo, from_results,
            to_results, show_error, open_folder_show_doc,filterresultsreports,filterresultstext,totalss;
    String fileName = "Milk report";
    Dialog loadingUI,loading;
    Animation outFade,anifade;
    String selectedtimes,farmname;
    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    MaterialButton cardExport;
    LottieAnimationView animationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milkresults);
        loading = new Dialog(this);
        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);

        recyclerView = findViewById(R.id.recyclerview_milking_results);
        mData = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MilkResultsAdapter(this, mData);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);
        seeltecttime = findViewById(R.id.seeltecttime);
        totalss = findViewById(R.id.total);


        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.card_filter_export);

        /*these are the sharing parts*/
        scroll_filter = loadingUI.findViewById(R.id.scroll_filter);
        export_filter = loadingUI.findViewById(R.id.export_filter);
        scrFrom = loadingUI.findViewById(R.id.scrFrom);
        scrTo = loadingUI.findViewById(R.id.scrTo);
        from_results = loadingUI.findViewById(R.id.from_results);
        to_results = loadingUI.findViewById(R.id.to_results);
        show_results_linear = loadingUI.findViewById(R.id.show_results_linear);
        download_results_linear = loadingUI.findViewById(R.id.download_results_linear);
        wrong_download_results_linear = loadingUI.findViewById(R.id.wrong_download_results_linear);
        show_error = loadingUI.findViewById(R.id.show_error);
        open_folder_show_doc = loadingUI.findViewById(R.id.open_folder_show_doc);

        fromDates = (TextView) loadingUI.findViewById(R.id.fromDates);
        toDates = (TextView) loadingUI.findViewById(R.id.toDates);
        datePickerFrom = (DatePicker) loadingUI.findViewById(R.id.datePickerFrom);
        datePickerTo = (DatePicker) loadingUI.findViewById(R.id.datePickerTo);
        getdate_from = (MaterialCardView) loadingUI.findViewById(R.id.getdate_from);
        getdate_to = (MaterialCardView) loadingUI.findViewById(R.id.getdate_to);
        linear_fromdate = (LinearLayout) loadingUI.findViewById(R.id.linear_fromdate);
        linear_todate = (LinearLayout) loadingUI.findViewById(R.id.linear_todate);
        filterresultsreports = (TextView) loadingUI.findViewById(R.id.filterresultsreports);
        filterresultstext = (TextView) loadingUI.findViewById(R.id.filterresultstext);
        cardfilterrecords = (MaterialCardView) loadingUI.findViewById(R.id.cardfilterrecords);
        cardexportrecords = (MaterialCardView) loadingUI.findViewById(R.id.cardexportrecords);
        cardExport = (MaterialButton) findViewById(R.id.cardExport);

        linear_menu = (LinearLayout) findViewById(R.id.linear_menu);
        li_show_results = (LinearLayout) findViewById(R.id.li_show_results);
        closesearching = (ImageView) findViewById(R.id.closesearching);
        searching = (ImageView) findViewById(R.id.searching);


        getdate_from.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                fromDates.setText(String.format("%d-%02d-%d", datePickerFrom.getYear(), datePickerFrom.getMonth() + 1, datePickerFrom.getDayOfMonth()));
                Toast.makeText(MilkResults.this, "From: " + fromDates.getText().toString(), Toast.LENGTH_SHORT).show();
                String ff = fromDates.getText().toString();
                if (!ff.isEmpty()) {
                    linear_fromdate.setVisibility(View.GONE);
                    linear_todate.setVisibility(View.VISIBLE);

                }
            }
        });

        getdate_to.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                toDates.setText(String.format("%d-%02d-%d", datePickerTo.getYear(), datePickerTo.getMonth() + 1, datePickerTo.getDayOfMonth()));
                Toast.makeText(MilkResults.this, "To: " + toDates.getText().toString(), Toast.LENGTH_SHORT).show();
                String ff = toDates.getText().toString();
            }
        });


        fromDates.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                String ff = fromDates.getText().toString();
                if (!ff.isEmpty()){
                    linear_fromdate.setVisibility(View.VISIBLE);
                    linear_todate.setVisibility(View.GONE);
                }
            }
        });

        toDates.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                String ff = toDates.getText().toString();
                if (!ff.isEmpty()){
                    linear_fromdate.setVisibility(View.GONE);
                    linear_todate.setVisibility(View.VISIBLE);
                }
            }
        });


        cardfilterrecords.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                sendFilterDates();

            }
        });

        cardexportrecords.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                sendFilterDatesReport();

            }
        });


        /**GETTING THE SELECTED TIME FRAME TO BE USED IN FILTERING MILK*/
        selectedtimessss = findViewById(R.id.selectedtimessss);
        milktimeslot = findViewById(R.id.milktimeslot);

        milktimeslot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String timeslot = String.valueOf(milktimeslot.getSelectedItem());
//
                selectedtimessss.setText(timeslot);
                Toast.makeText(MilkResults.this, "sdsds" + selectedtimessss.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//
//        if (!timeslot.equals("Select time frame")) {
//
//        }else {
//            Toast.makeText(this, "sappp", Toast.LENGTH_SHORT).show();
//        }


        /*receive the selected time frame*/
         selectedtimes = getIntent().getStringExtra("time");
         if (selectedtimes.isEmpty()){
             li_show_results.setVisibility(View.GONE);
         }
        seeltecttime.setText(selectedtimes);
        /*check for the selected time frame and search against that and the current date*/
        switch (selectedtimes) {
            case "Morning":
            case "Afternoon":
            case "Evening":
            case "":// for showing all the results
                loadMilkingResults("","",selectedtimes);
                break;
        }


        /*run the selected option through the filter*/
//        selectedtimessss.setFocusableInTouchMode(false);
//        selectedtimessss.setFocusable(false);
        selectedtimessss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Clear();
                loadMilkingResults("","",selectedtimes);
            }
        });

    }

    public void goBack(View view) {
        startActivity(new Intent(MilkResults.this, MilkActivity.class));
    }

    private void loadMilkingResults(String fromdate,String todate, String selectedtime) {
        loading = new Dialog(this);
        loading.setContentView(R.layout.right);
        animationView = loading.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_MILKING_RESULTS,
                response -> {
                    loading.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message_balance.setVisibility(View.VISIBLE);
                            cardExport.setVisibility(View.GONE);
                            searching.setVisibility(View.GONE);
                            totalss.setText("0");


                        } else {
                            no_message_balance.setVisibility(View.GONE);
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);

                                String id = inputsObjects.getString("id");
                                String total = inputsObjects.getString("total");
                                String home = inputsObjects.getString("home");// for home consumption
                                String diary = inputsObjects.getString("diary");// for diary consumption
                                String date = inputsObjects.getString("date");
                                String timesent = inputsObjects.getString("timesent");
                                String comment = inputsObjects.getString("comment");// what happened for the milk coming late
                                String totals =  inputsObjects.getString("totals");
                                totalss.setText(totals);

                                MilkResultsModel inputsModel =
                                        new MilkResultsModel(id, total, home, diary, comment, date, timesent
                                        );
                                mData.add(inputsModel);
                            }
                            loading.setCancelable(false);
                            loading.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(loading.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            loadingUI.show();
                            adapter = new MilkResultsAdapter(MilkResults.this, mData);
                            recyclerView.setAdapter(adapter);
                            error_message_balance.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                        error_message_balance.setVisibility(View.VISIBLE);
//                        error_message_balance.setText(e.toString());
                         Toast.makeText(this, "Something went wrong, swipe down to try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loading.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
//            error_message_balance.setText(error.toString());
            Toast.makeText(this, "Something went wrong, check your connection and please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("selectedtime", selectedtime);
                params.put("fromdate", fromdate);
                params.put("todate", todate);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*opens the dialog with just filtering options and exporting option*/
    public void exportMilk(View view) {
        cardexportrecords.setVisibility(View.VISIBLE);
        cardfilterrecords.setVisibility(View.GONE);
        filterresultsreports.setVisibility(View.VISIBLE);
        filterresultstext.setVisibility(View.GONE);
        scroll_filter.setVisibility(View.VISIBLE);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();
    }
    /*opens the dialog with just filtering options*/
    public void openDialogFilter(View view) {
        cardexportrecords.setVisibility(View.GONE);
        cardfilterrecords.setVisibility(View.VISIBLE);
        filterresultsreports.setVisibility(View.GONE);
        filterresultstext.setVisibility(View.VISIBLE);
        scroll_filter.setVisibility(View.VISIBLE);
        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingUI.show();
    }

    /*handle filtering records dialog*/
    public void openMenu(View view) {
        linear_menu.setVisibility(View.VISIBLE);
        closesearching.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);

    }
    public void openCloseMenu(View view) {
        linear_menu.setVisibility(View.GONE);
        closesearching.setVisibility(View.GONE);
        searching.setVisibility(View.VISIBLE);
        adapter.clear();
        loadMilkingResults("","",selectedtimes);
    }


    private void filter(String text) {
        ArrayList<MilkResultsModel> filteredList = new ArrayList<>();

        for (MilkResultsModel item : mData) {
            if (item.getTime().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


    /**THE ACTUAL EXPORTING AND DOWNLOADING AND FILTERING*/
    public void sendFilterDates() {

        String fromDatesss = fromDates.getText().toString().trim();
        String toDatesss = toDates.getText().toString().trim();

        /*check to see if all the dates are selected*/
        if (fromDatesss.isEmpty() && toDatesss.isEmpty()) {
            Toast.makeText(this, "Please select the start date and the end date", Toast.LENGTH_SHORT).show();
            scroll_filter.setVisibility(View.GONE);

        } else if (fromDatesss.isEmpty() || toDatesss.isEmpty()) {
            Toast.makeText(this, "Please select the start date and the end date", Toast.LENGTH_SHORT).show();
            scroll_filter.setVisibility(View.GONE);
        } else {
            adapter.clear();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to filter your records with the selected dates");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String fromDatess = fromDates.getText().toString().trim();
                            String toDatess = toDates.getText().toString().trim();
                            from_results.setText(fromDatess);
                            to_results.setText(toDatess);
                            download_results_linear.setVisibility(View.GONE);
                            wrong_download_results_linear.setVisibility(View.GONE);
                            loadMilkingResults(fromDatess, toDatess,selectedtimes);

                            scroll_filter.setVisibility(View.GONE);
                            loadingUI.dismiss();
                        }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }


    public void sendFilterDatesReport() {

        String fromDatesss = fromDates.getText().toString().trim();
        String toDatesss = toDates.getText().toString().trim();

        /*check to see if all the dates are selected*/
        if (fromDatesss.isEmpty() && toDatesss.isEmpty()) {
            Toast.makeText(this, "Please select the start date and the end date", Toast.LENGTH_SHORT).show();
            scroll_filter.setVisibility(View.GONE);

        } else if (fromDatesss.isEmpty() || toDatesss.isEmpty()) {
            Toast.makeText(this, "Please select the start date and the end date", Toast.LENGTH_SHORT).show();
            scroll_filter.setVisibility(View.GONE);
        } else {
            adapter.clear();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, you want to export these filtered results to an excel file");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String fromDatess = fromDates.getText().toString().trim();
                            String toDatess = toDates.getText().toString().trim();
                            from_results.setText(fromDatess);
                            to_results.setText(toDatess);
                            export_filter.setVisibility(View.VISIBLE);
                            download_results_linear.setVisibility(View.GONE);
                            wrong_download_results_linear.setVisibility(View.GONE);
                            show_results_linear.setVisibility(View.VISIBLE);
                            exportMilks(fromDatess, toDatess,"");

                            scroll_filter.setVisibility(View.GONE);
                        }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }


    public void close_filter(View view) {
        scroll_filter.setVisibility(View.GONE);
    }

    public void open_filer_export(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, you want to export these filtered results to an excel file");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String fromDatess = fromDates.getText().toString().trim();
                        String toDatess = toDates.getText().toString().trim();
                        exportMilks(fromDatess, toDatess,"");
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void exportMilks(String fromDatess, String toDatess,String selected) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.EXPORT_MILK_URL,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                            show_results_linear.setVisibility(View.VISIBLE);
                            wrong_download_results_linear.setVisibility(View.GONE);
                            download_results_linear.setVisibility(View.VISIBLE);
                         exportFile();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wrong_download_results_linear.setVisibility(View.VISIBLE);
                    }
                }, error -> {
            wrong_download_results_linear.setVisibility(View.VISIBLE);
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("farmname", farmname);
                params.put("todate", toDatess);
                params.put("fromdate", fromDatess);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void exportFile() {
        try {
            File imageStorageDir = new File(Environment.getExternalStorageDirectory() + "/Documents");
            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs();
            }
            String imgExtension = ".xls";

            String date = DateFormat.getDateTimeInstance().format(new Date());
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
            fileFinalName = fileName + " " + timeStamp.replace(" ", " ").
                    replace(":", ":").replace(".", ".") + imgExtension;
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse("https://jehooshfamily.com/myfarm/excel_files/milk/Milk production-report" +" (" +farmname +")"+".xls");
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileFinalName)
                    .setTitle(fileFinalName).setDescription(getString(Integer.parseInt(String.valueOf(R.string.save_img))))
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true)// Set if download is allowed on roaming network
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            dm.enqueue(request);

            open_folder_show_doc.setVisibility(View.VISIBLE);


        } catch (IllegalStateException ex) {
            Toast.makeText(getApplicationContext(), "Storage Error"+ ex.toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Unable to save file, please check your connection and try again", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void close_export(View view) {
        export_filter.setVisibility(View.GONE);
        adapter.clear();
        String fromDatess = "";
        String toDatess = "";
        loadMilkingResults("","",selectedtimes);
    }
    /*clears the recyclerview once a message is sent*/
    @SuppressLint("NotifyDataSetChanged")
    public void Clear() {
        mData.clear();
        adapter.notifyDataSetChanged();
    }


    public void open_intents(View view) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File path = new File(Environment.getExternalStorageDirectory() + "/" + "Downloads" );

        Uri uri = Uri.fromFile(path);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


}
package com.example.android.cricfuzz;


import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class liveScore extends AppCompatActivity {


    private String url="https://cricapi.com/api/matches/?apikey=rOuJzoDxPxTlrbwID8DCiYX5Lb03";

    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        //remove shadow from action bar

        ActionBar actionBar=getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setTitle("Live Scores");

        mRecylerView=findViewById(R.id.recyclerview);

        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        modelList=new ArrayList<>();

        loadUrlData();//method to call get/show data from website

    }
    private void loadUrlData(){
        //progress bar to be displayed while data is retrieving
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading.....");
        pd.show();

        //requesting data
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try{
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("matches");

                    for(int i=0;i<jsonArray.length();i++){
                        try{
                            String uniqueId=jsonArray.getJSONObject(i).getString("unique_id");
                            String team1=jsonArray.getJSONObject(i).getString("team-1");
                            String team2=jsonArray.getJSONObject(i).getString("team-2");
                            String matchType=jsonArray.getJSONObject(i).getString("type");
                            String matchStatus=jsonArray.getJSONObject(i).getString("matchStarted");
                            if(matchStatus.equals("true")){
                                matchStatus="Match Started";

                            }
                            else
                            {
                                matchStatus="Match not Started";
                            }
                            String dateTimeGMT=jsonArray.getJSONObject(i).getString("dateTimeGMT");
                            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Date date=format1.parse(dateTimeGMT);

                            SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String dateTime=format2.format(date);

                            // set data
                            Model model=new Model(uniqueId,team1,team2,matchType,matchStatus,dateTime);
                            modelList.add(model);
                        }
                        catch(Exception e){

                            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    //adapter to be set o recyclerview
                    mAdapter=new RecyclerViewAdapter(modelList,getApplicationContext());
                    mRecylerView.setAdapter(mAdapter);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if fail to load
                Toast.makeText(getApplicationContext(),"Error: "+ error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

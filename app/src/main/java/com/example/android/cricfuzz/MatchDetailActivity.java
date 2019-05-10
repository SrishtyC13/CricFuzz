package com.example.android.cricfuzz;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MatchDetailActivity extends AppCompatActivity {

    TextView mTeam1Tv,mTeam2Tv,mMatchStatusTv,mScoreTv,mDescriptionTv,mDateTv;
    private String url="https://cricapi.com/api/cricketScore/?apikey=rOuJzoDxPxTlrbwID8DCiYX5Lb03&unique_id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        //action bar
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();

        //enable back button in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        String id=intent.getStringExtra("match_id");
        String date=intent.getStringExtra("date");

        url=url+id;

        mTeam1Tv=findViewById(R.id.team1Tv);
        mTeam2Tv=findViewById(R.id.team2Tv);
        mMatchStatusTv=findViewById(R.id.matchStatusTv);
        mScoreTv=findViewById(R.id.scoreTv);
        mDescriptionTv=findViewById(R.id.descriptionTv);
        mDateTv=findViewById(R.id.dateTv);

        mDateTv.setText(date);
        //get/set data
        loadData();

    }
    private void loadData(){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading.....");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String team1=jsonObject.getString("team-1");

                            String team2=jsonObject.getString("team-2");
                            String matchStatus=jsonObject.getString("matchStarted");
                            if(matchStatus.equals("true")){
                                matchStatus="Match Started";
                            }
                            else{
                                matchStatus=" Match not started";
                            }
                            mTeam1Tv.setText(team1);
                            mTeam2Tv.setText(team2);
                            mMatchStatusTv.setText(matchStatus);
                            try{

                                String score=jsonObject.getString("score");
                                String description=jsonObject.getString("description");
                                mScoreTv.setText(score);
                                mDescriptionTv.setText(description);
                            }
                            catch(Exception e){
                                Toast.makeText(getApplicationContext()," "+ e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext()," "+ e.getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if error
                Toast.makeText(getApplicationContext(),"Error: "+ error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

package com.example.stud.appmusic.topsongs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.stud.appmusic.R;
import com.example.stud.appmusic.api.ApiService;

import com.example.stud.appmusic.api.TrendingList;
import com.example.stud.appmusic.api.TrendingSingle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;





public class TopSongsActivity extends AppCompatActivity {

    RecyclerView rvList;
    List<TrendingSingle> trendingSingles = new ArrayList<>( 0 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_songs);

        getSupportActionBar().setDisplayHomeAsUpEnabled( true ); //górny pasek aplikacji

        rvList = findViewById(R.id.rvList);

        TopSongsAdapter topSongsAdapter = new TopSongsAdapter( trendingSingles );
        rvList .setAdapter(topSongsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
        rvList .setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        rvList.setLayoutManager(linearLayoutManager);




        Call<TrendingList> trendingListCall = ApiService.getService().getTrendingList( "us" ,
                "itunes", "singles");
        trendingListCall.enqueue(new Callback<TrendingList>() {
            @Override
            public void onResponse(@NonNull Call<TrendingList> call, @NonNull
                    Response<TrendingList> response) {
                TrendingList trendingList = response.body();

                if (trendingList != null ) {
                    TopSongsActivity.this.trendingSingles.clear();
                    TopSongsActivity.this.trendingSingles.addAll(trendingList.trending );
                    TopSongsActivity.this.rvList.getAdapter().notifyDataSetChanged();
                }

            }




            @Override
            public void onFailure( @NonNull Call<TrendingList> call, Throwable t) {
                Toast.makeText(TopSongsActivity. this , "Blad pobierania danych: " +
                        t.getLocalizedMessage(), Toast. LENGTH_SHORT ).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true ;
    }
}


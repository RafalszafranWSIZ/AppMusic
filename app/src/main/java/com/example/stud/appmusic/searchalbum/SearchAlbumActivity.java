package com.example.stud.appmusic.searchalbum;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stud.appmusic.R;
import com.example.stud.appmusic.api.ApiService;
import com.example.stud.appmusic.api.SearchAlbum;
import com.example.stud.appmusic.api.SearchAlbums;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAlbumActivity extends AppCompatActivity {

    EditText etQuery;
    RecyclerView rvList;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        sharedPreferences = getPreferences(MODE_PRIVATE);

        etQuery = findViewById(R.id.etQuery);
        rvList = findViewById(R.id.rvList);

        try {
            etQuery.setText( sharedPreferences.getString( "query" , null ));
        } catch (ClassCastException e) {
            Log.e ( "TAG" , "błąd" , e);
        }

        String artist = sharedPreferences.getString("query", null);
        etQuery.setText(artist);

        Button bSearch = findViewById(R.id.bSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = etQuery.getText().toString();
                rememberQuery(query);

            }
        });
    }

    private  void rememberQuery(String query){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "query" , query);
        editor.apply();

    }

    private void searchAlbums(String query){

        getSupportActionBar().setSubtitle(query);
        if (query == null || query.isEmpty())
            if (query == null || query.isEmpty()) {
                Toast.makeText(this, "Pusta fraza", Toast.LENGTH_SHORT).show();
                return;
            }

        Call<SearchAlbums> searchAlbumsCall = ApiService.getService().searchAlbums(query);
        searchAlbumsCall.enqueue( new Callback<SearchAlbums>() {
            @Override
            public void onResponse(@NonNull Call<SearchAlbums> call, @NonNull
                    Response<SearchAlbums> response) {
                SearchAlbums searchAlbums = response.body();
                if (searchAlbums == null || searchAlbums. album == null ||
                        searchAlbums. album .isEmpty()) {
                    Toast. makeText (SearchAlbumActivity. this , "Brak wyników" ,
                            Toast. LENGTH_SHORT ).show();
                    return;
                }
                Toast. makeText (SearchAlbumActivity. this , "Znaleziono " +
                        searchAlbums. album .size() + " wyników" , Toast. LENGTH_SHORT ).show();
            }
            @Override
            public void onFailure( @NonNull Call<SearchAlbums> call, Throwable t) {
                Toast. makeText (SearchAlbumActivity. this , "Błąd pobierania danych: " +
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

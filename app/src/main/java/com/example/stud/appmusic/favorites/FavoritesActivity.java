package com.example.stud.appmusic.favorites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.stud.appmusic.R;
import com.example.stud.appmusic.database.Favorite;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Favorite> favorites = realm
                .where(Favorite.class)
                .sort("data", Sort.DESCENDING)
                .findAll();

        if (favorites.size() > 0 ) {
            Toast.makeText ( this , "Pobrano ulubione" , Toast. LENGTH_SHORT ).show();
            for (Favorite favorite : favorites) {
                Log.d ( "FAV" , favorite.getArtist() + " - " + favorite.getTrack());
            }
        } else {
            Toast.makeText ( this , "Brak ulubionych" , Toast. LENGTH_SHORT ).show();
        }
    }
}

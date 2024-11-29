package com.example.apilast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebSettings;

import com.example.apilast.models.InfoFilm;
import com.example.apilast.models.RootTrailer;
import com.example.apilast.models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Information extends YouTubeBaseActivity {

    ImageView imageView;
    TextView name, info, year, top;
    ApiInterface APIInterface;
    int id;
    YouTubePlayerView youTubePlayer;
    String api_key = "AIzaSyAWhtyr0J0wXt_bCaoRjB-UTEzVQH965pM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        imageView = findViewById(R.id.FilmImg);
        name = findViewById(R.id.FilmName);
        info = findViewById(R.id.FilmInfo);
        year = findViewById(R.id.FilmDate);
        top = findViewById(R.id.TopRating);

        youTubePlayer = (YouTubePlayerView)findViewById(R.id.YouTubePlay);
        APIInterface = RequestBuilder.buildRequest().create(ApiInterface.class);
        id = getIntent().getIntExtra("item", 0);

        Call<InfoFilm> getFilm = APIInterface.getFilmById(id);

        getFilm.enqueue(new Callback<InfoFilm>() {
            @Override
            public void onResponse(Call<InfoFilm> call, Response<InfoFilm> response) {
               if (response.isSuccessful()){
                   InfoFilm film = response.body();
                   Picasso.with(getApplicationContext()).load(film.getPosterUrl()).into(imageView);
                   name.setText(film.getNameRu());
                   info.setText(film.getDescription());
                   year.setText(String.valueOf(film.getYear()));
                   top.setText(String.valueOf(film.getRatingImdb()));
                   getVideo(response.body().kinopoiskId);
               }
            }

            @Override
            public void onFailure(Call<InfoFilm> call, Throwable t) {
                Toast.makeText(Information.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed(){
        Intent a = new Intent(this, MainActivity.class);
        startActivity(a);
        overridePendingTransition(R.anim.firstanimation,0);
    }

    private void getVideo(int idGet){

        Call<RootTrailer> getTrailer = APIInterface.getFilmTrailer(idGet);
        getTrailer.enqueue(new Callback<RootTrailer>() {
            @Override
            public void onResponse(Call<RootTrailer> call, Response<RootTrailer> response) {
                if (response.isSuccessful()){
                    String url;
                    for (int i = 0; i < response.body().getItems().size();i++){
                        if (response.body().getItems().get(i).site.equals("YOUTUBE")){
                            Trailer trailer =  response.body().getItems().get(i);
                            if (trailer.getUrl().contains("v=")){
                                url = trailer.getUrl().split("v=")[1];
                                setVideo(url);
                            }
                            else if (trailer.getUrl().contains("/v/")){
                                url = trailer.getUrl().split("/v/")[1];
                                setVideo(url);
                            }
                            else if (trailer.getUrl().contains("/")){
                                url = trailer.getUrl().split("youtu.be/")[1];
                                setVideo(url);
                            }
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RootTrailer> call, Throwable t) {
            }
        });
    }

    private void setVideo(String url){
        Log.d("video",url);
        youTubePlayer.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(String.valueOf(url));
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
package com.example.spotifyconnectiontest;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spotifyconnectiontest.databinding.ActivityMainBinding;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "4e5e333774674e6d9b9a34f9f68fe051";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private static final int REQUEST_CODE = 1337;

    private String songTrack = "spotify:track:5ruzrDWcT0vuJIOMW7gMnW";
    private String top50 = "spotify:playlist:37i9dQZEVXbMDoHDwVN2tF";
    private String top100 = "spotify:playlist:4zzUm9eZmeb4t4nUCaNoo5";
    private String song1 = "spotify:track:5ruzrDWcT0vuJIOMW7gMnW";
    private String song2 = "spotify:track:3vWCRttEOSRmXZs8jfAPSX";



    private SpotifyAppRemote mSpotifyAppRemote;

    private ImageView SongImage;
    private TextView SongName;


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //SongImage = findViewById(R.id.songImage);
        //SongName = findViewById(R.id.songName);

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(MainActivity.this, REQUEST_CODE, request);

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                        // Play a playlist
                        binding.top50.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpotifyAppRemote.getPlayerApi().play(top50);
                            }
                        });

                        binding.top100.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpotifyAppRemote.getPlayerApi().play(top100);
                            }
                        });

                        binding.song1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpotifyAppRemote.getPlayerApi().play(song1);
                            }
                        });

                        binding.song2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSpotifyAppRemote.getPlayerApi().play(song2);
                            }
                        });


                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    Log.d("SpotifyAuth" , "Thành Công: TOKEN "  + response.getAccessToken());
                    break;

                // Auth flow returned an error
                case ERROR:
                    Log.d("SpotifyAuth" , "Lỗi: TOKEN "  + response.getAccessToken());
                    break;

                // Most likely auth flow was cancelled
                default:
                    Log.d("SpotifyAuth" , "Cancel "  + response.getAccessToken());            }
        }
    }

    // Set the connection parameters
    ConnectionParams connectionParams =
            new ConnectionParams.Builder(CLIENT_ID)
                    .setRedirectUri(REDIRECT_URI)
                    .showAuthView(true)
                    .build();

    @Override
    protected void onStart() {
        super.onStart();
        // We will start writing our code here.
    }

    private void connected() {
        // Then we will write some more code here.
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Aaand we will finish off here.
    }

}
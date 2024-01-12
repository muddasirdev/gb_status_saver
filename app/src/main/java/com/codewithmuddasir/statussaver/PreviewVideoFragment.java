package com.codewithmuddasir.statussaver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.codewithmuddasir.statussaver.model.StatusModel;
import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.FileUtils;

public class PreviewVideoFragment extends Fragment {

    StatusModel model;

    Activity activity;

    AppName appName;

    public PreviewVideoFragment(Activity activity, StatusModel model, AppName appName) {
        this.activity = activity;
        this.model = model;
        this.appName = appName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preview_video, container, false);

        PlayerView img = view.findViewById(R.id.player_view);

        Player player = new ExoPlayer.Builder(view.getContext()).build();

        MediaItem mediaItem = MediaItem.fromUri(model.getPathUri());

        img.setPlayer(player);

        player.setMediaItem(mediaItem);
        player.prepare();

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            player.pause();
            if (appName.equals(AppName.OWN_APP)) {
                WhatsappFragment fragment = new WhatsappFragment(activity, AppName.OWN_APP);
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.root, fragment);
                    ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                    ft.commit();
                }
            } else {
                WhatsappFragment fragment = new WhatsappFragment(activity, AppName.WHATSAPP);
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.root, fragment);
                    ft.addToBackStack(null);  // Optional: Add the transaction to the back stack
                    ft.commit();
                }
            }
        });

        LinearLayout whatsappShare = view.findViewById(R.id.linearLayout);

        whatsappShare.setOnClickListener(v -> {
            player.pause();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(model.getPathUri()));
            intent.setPackage("com.whatsapp");
            intent.setType("video/mp4");

            startActivity(intent);
        });

        LinearLayout whatsappBusinessShare = view.findViewById(R.id.linearLayout3);

        whatsappBusinessShare.setOnClickListener(v -> {
            player.pause();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(model.getPathUri()));
            intent.setPackage("com.whatsapp.w4b");
            intent.setType("video/mp4");

            startActivity(intent);
        });

        LinearLayout downloadShare = view.findViewById(R.id.download);
        ImageView statusDownload = view.findViewById(R.id.statusDownload);

        if (appName.equals(AppName.OWN_APP)) {
            downloadShare.setVisibility(View.GONE);
        }


        if (FileUtils.isStatusExist(view.getContext(), model.getFileName())) {
            statusDownload.setImageResource(R.drawable.ic_downloaded);
        } else {
            downloadShare.setOnClickListener(v -> {
                boolean isDownloaded = FileUtils.saveStatus(view.getContext(), model);
                if (isDownloaded) {
                    // Status is downloaded
                    Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    statusDownload.setImageResource(R.drawable.ic_downloaded);
                } else {
                    // Unable to download status
                    Toast.makeText(view.getContext(), "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
package com.codewithmuddasir.statussaver.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codewithmuddasir.statussaver.DashboardFragment;
import com.codewithmuddasir.statussaver.PreviewImageFragment;
import com.codewithmuddasir.statussaver.PreviewVideoFragment;
import com.codewithmuddasir.statussaver.R;
import com.codewithmuddasir.statussaver.model.StatusModel;
import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.FileType;
import com.codewithmuddasir.statussaver.util.FileUtils;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private List<StatusModel> mListData;

    private Activity activity;

    View view;

    private AppName appName;

    public StatusAdapter(List<StatusModel> mListData, Activity activity, AppName appName) {
        this.mListData = mListData;
        this.activity = activity;
        this.appName = appName;
    }

    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(activity).inflate(R.layout.card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        StatusModel model = mListData.get(position);

        Glide.with(activity)
                .load(Uri.parse(model.getPathUri()))
                .into(holder.statusImage);

        if (!appName.equals(AppName.OWN_APP)) {
            if (model.getType().equals(FileType.IMAGE_FILE)) {
                holder.statusPlay.setVisibility(View.GONE);
            } else {
                holder.statusPlay.setVisibility(View.VISIBLE);
            }

            if (FileUtils.isStatusExist(view.getContext(), model.getFileName())) {
                holder.downloaded.setImageResource(R.drawable.ic_downloaded);
            } else {
                holder.downloaded.setOnClickListener(v -> {
                    boolean isDownloaded = FileUtils.saveStatus(view.getContext(), model);
                    if (isDownloaded) {
                        // Status is downloaded
                        Toast.makeText(view.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        holder.downloaded.setImageResource(R.drawable.ic_downloaded);
                    } else {
                        // Unable to download status
                        Toast.makeText(view.getContext(), "Unable to Save", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            if (model.getType().equals(FileType.IMAGE_FILE)) {
                holder.statusPlay.setVisibility(View.GONE);
            } else {
                holder.statusPlay.setVisibility(View.VISIBLE);
            }

            holder.downloaded.setVisibility(View.GONE);
        }

        holder.view.setOnClickListener(v -> {
            if (model.getType().equals(FileType.IMAGE_FILE)) {
                PreviewImageFragment imageFragment = new PreviewImageFragment(activity, model, appName);

                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.root, imageFragment);
                ft.addToBackStack(null);
                ft.commit();
            } else {
                PreviewVideoFragment videoFragment = new PreviewVideoFragment(activity, model, appName);

                AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.root, videoFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;

        public ImageView statusImage;
        public ImageView statusPlay;
        public ImageView downloaded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;


            statusImage = view.findViewById(R.id.status_image);

            statusPlay = view.findViewById(R.id.status_play);

            downloaded = view.findViewById(R.id.status_download);

        }
    }
}

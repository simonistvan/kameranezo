package com.example.kameranezo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kameranezo.R;
import com.example.kameranezo.model.Camera;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.MediaItem;

import java.util.List;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraViewHolder> {

    private Context context;
    private List<Camera> cameraList;

    public CameraAdapter(Context context, List<Camera> cameraList) {
        this.context = context;
        this.cameraList = cameraList;
    }

    @NonNull
    @Override
    public CameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_camera, parent, false);
        return new CameraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolder holder, int position) {
        Camera camera = cameraList.get(position);
        holder.tvNev.setText(camera.getNev());
        holder.tvCim.setText(camera.getCim());

        ExoPlayer player = new ExoPlayer.Builder(context).build();
        holder.playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(camera.getKepUrl());
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    public int getItemCount() {
        return cameraList.size();
    }

    public static class CameraViewHolder extends RecyclerView.ViewHolder {
        TextView tvNev, tvCim;
        PlayerView playerView;

        public CameraViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNev = itemView.findViewById(R.id.kamera_nev);
            tvCim = itemView.findViewById(R.id.kamera_cim);
            playerView = itemView.findViewById(R.id.player_view);
        }
    }
}

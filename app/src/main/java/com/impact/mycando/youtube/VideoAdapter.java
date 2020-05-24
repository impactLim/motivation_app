package com.impact.mycando.youtube;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.impact.mycando.R;

import java.util.List;

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YoutubeVideos> youtubeVideoList;
    private ViewGroup parent;
    private int viewType;

    public VideoAdapter() {
    }


    public VideoAdapter(List<YoutubeVideos> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.youtube_item, parent, false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        holder.videoWeb.loadData( youtubeVideoList.get(position).getVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {

            } );
        }
    }

//    @Override
//    public void onViewAttachedToWindow(@NonNull VideoViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        holder.videoWeb.onPause();
//
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.videoWeb.onResume();
//
//    }
}
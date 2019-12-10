package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.models.ArticlesList;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.fragmentVH> {
    private List<ArticlesList> list;
    private OnItemListener mOnItemListener;

    public RVAdapter(List<ArticlesList> list, OnItemListener onItemListener) {
        this.list = list;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public fragmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new fragmentVH(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull fragmentVH holder, int position) {
        ArticlesList articlesList = list.get(position);

        String title = articlesList.getTitle();
        String author = articlesList.getAuthor();
        String date = articlesList.getPublishedAt();
        String thumbnail = articlesList.getThumbnail();

        holder.title.setText(title);
        holder.author.setText(author);
        holder.date.setText(date);

            /*RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_football)
                    .error(R.drawable.ic_pizza_slice)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);*/

            Glide.with(holder.itemView)
                    .load(thumbnail)
                    .placeholder(R.drawable.no_preview)
                    .error(R.drawable.no_preview)
//                    .apply(options)
                    .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }
    }

    class fragmentVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title, author, date;
        OnItemListener mOnItemListener;

        public fragmentVH(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
        imageView = itemView.findViewById(R.id.book_image);
        title = itemView.findViewById(R.id.title_txt);
        author = itemView.findViewById(R.id.author_txt);
        date = itemView.findViewById(R.id.date_txt);
        this.mOnItemListener = onItemListener;
        itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClicked(int position);
    }
}

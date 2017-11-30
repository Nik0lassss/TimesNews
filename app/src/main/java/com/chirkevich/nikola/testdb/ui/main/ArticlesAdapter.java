package com.chirkevich.nikola.testdb.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chirkevich.nikola.testdb.R;
import com.chirkevich.nikola.testdb.data.remote.model.Article;
import com.chirkevich.nikola.testdb.data.remote.model.Media;
import com.chirkevich.nikola.testdb.utils.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Колян on 29.05.2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private final static int BIG_SIZE_IMAGE = 2;
    private List<Article> articles;

    @Inject
    public ArticlesAdapter() {
        articles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_most_popular, parent, false);
        return new ArticlesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {

        Article article = articles.get(position);
        holder.descriptionTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource());
        holder.textViewPublicshedDate.setText(article.getPublished_date());
        for(Media media:article.getMedia())
        {
            ImageLoadUtil.loadIntoView(holder.imageViewNewImage,media.getMedia_metadata().get(BIG_SIZE_IMAGE).url);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticlesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_source)
        TextView sourceTextView;

        @BindView(R.id.text_description)
        TextView descriptionTextView;

        @BindView(R.id.image_view_news_image)
        ImageView imageViewNewImage;

        @BindView(R.id.text_published_date)
        TextView textViewPublicshedDate;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

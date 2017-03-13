package flicks.codepath.com.flicks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import flicks.codepath.com.flicks.R;
import flicks.codepath.com.flicks.models.Movie;

/**
 * Created by seonglee on 3/12/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolderMovie viewHolder;

        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(new okhttp3.OkHttpClient());

        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder = new ViewHolderMovie();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderMovie) convertView.getTag();
        }

        Picasso picasso = new Picasso.Builder(convertView.getContext())
                .downloader(okHttp3Downloader)
                .build();

        if(movie != null) {
            if(convertView.getResources().getConfiguration().orientation == 1) {
                picasso
                        .with(convertView.getContext())
                        .load(movie.getPosterPath())
                        .into(viewHolder.ivImage);
            } else if(convertView.getResources().getConfiguration().orientation == 2) {
                picasso
                        .with(convertView.getContext())
                        .load(movie.getBackdropPath())
                        .into(viewHolder.ivImage);
            } else {
                System.out.println("Should never get here...");
            }

            viewHolder.tvOverview.setText(movie.getOverview());
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
        }

        return convertView;
    }

    static class ViewHolderMovie {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }
}

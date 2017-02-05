package com.example.latheeshvirupakshi.popularmoviesstage1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.latheeshvirupakshi.popularmoviesstage1.Model.Movie;
import com.example.latheeshvirupakshi.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by latheeshvirupakshi on 1/30/17.
 */





public class MovieAdapater extends ArrayAdapter<Movie> {

        private Context mContext;
        private int layoutResourceId;
        private ArrayList<Movie> objects = new ArrayList<Movie>();

        public MovieAdapater(Context mContext, int layoutResourceId, ArrayList<Movie> objects) {
            super(mContext, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.objects = objects;
        }
        public void setdata(ArrayList<Movie> objects) {
            this.objects = objects;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;

            if (convertView == null) {
                LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layoutResourceId, parent, false);
                imageView = (ImageView) convertView.findViewById(R.id.movie_image);
                convertView.setTag(imageView);
            } else {
                imageView = (ImageView) convertView;
            }

            Movie movie = objects.get(position);
            Picasso.with(mContext).load(movie.getImage()).into(imageView);
            return convertView;
        }


    }



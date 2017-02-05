package com.example.latheeshvirupakshi.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by latheeshvirupakshi on 1/30/17.
 */

public class MovieDetails extends ActionBarActivity{

    private TextView fulltitle;

    private ImageView imageView;

    private TextView fulldescrption;

private TextView fulldate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        final RatingBar ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        Bundle bundle=getIntent().getExtras();

        String title1 = bundle.getString("title");
        String image = bundle.getString("poster");
        String descrption = bundle.getString("description");
        String date = bundle.getString("date");
        String rating = bundle.getString("rating");

        fulltitle = (TextView) findViewById(R.id.title);
        fulltitle.setText(title1);

        fulldate=(TextView)findViewById(R.id.date);
        fulldate.setText(date);

        fulldescrption = (TextView) findViewById(R.id.description);
        fulldescrption.setText(descrption);


        imageView=(ImageView)findViewById(R.id.poster);
        Picasso.with(this).load(image).into(imageView);

        float fRating = Float.parseFloat(rating);
        ratingBar.setRating(fRating/2);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.grid_movies) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

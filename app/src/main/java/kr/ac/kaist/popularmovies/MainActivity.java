package kr.ac.kaist.popularmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kaist.popularmovies.adapter.MoviesAdapter;
import kr.ac.kaist.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeContainer;
    ProgressDialog pd;

    public static final String LOG_TAG = MoviesAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inviteViews();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                inviteViews();
                Toast.makeText(MainActivity.this, "Movies Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void inviteViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching movies...");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this.movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void loadJSON() {

    }
}

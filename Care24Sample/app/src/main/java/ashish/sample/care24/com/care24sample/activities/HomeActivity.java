package ashish.sample.care24.com.care24sample.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import ashish.sample.care24.com.care24sample.R;
import ashish.sample.care24.com.care24sample.adapters.HomeActivityListAdapter;
import ashish.sample.care24.com.care24sample.application.ZApplication;
import ashish.sample.care24.com.care24sample.extras.Urls;
import ashish.sample.care24.com.care24sample.objects.FeedObject;
import ashish.sample.care24.com.care24sample.serverApi.AppRequestListener;
import ashish.sample.care24.com.care24sample.serverApi.CustomStringRequest;

/**
 * Created by ashis_000 on 2/23/2016.
 */
public class HomeActivity extends BaseActivity implements Urls, AppRequestListener, View.OnClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    HomeActivityListAdapter adapter;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        setProgressLayoutVariablesAndErrorVariables();

        recyclerView = (RecyclerView) findViewById(R.id.homerecyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Care 24");

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        findViewById(R.id.connection_error_layout).setOnClickListener(this);

        loadData();
    }

    void loadData() {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, feedsUrl, feedsUrl, this, null);
        ZApplication.getInstance().addToRequestQueue(request, feedsUrl);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equals(feedsUrl)) {
            hideErrorLayout();
            showLoadingLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equals(feedsUrl)) {
            hideLoadingLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equals(feedsUrl)) {
            hideLoadingLayout();
            hideErrorLayout();

            FeedObject mData = new Gson().fromJson(response, FeedObject.class);
            setAdapterData(mData);
        }
    }

    private void setAdapterData(FeedObject mData) {
        adapter = new HomeActivityListAdapter(this, mData.getData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connection_error_layout:
                loadData();
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
    }
}

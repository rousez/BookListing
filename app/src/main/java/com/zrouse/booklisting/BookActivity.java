package com.zrouse.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private static final String LOG_TAG = BookActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String GOOGLE_BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?maxResults=40&q=";

    private BookAdapter mAdapter;
    private EditText mEditText;
    private Button mSearchButton;
    private String queryBooks;
    private ListView bookListView;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookListView = (ListView) findViewById(R.id.listview);
        mEditText = (EditText) findViewById(R.id.search_text);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        mSearchButton = (Button) findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.clearFocus();
                findViewById(R.id.mainLayout).requestFocus();

                queryBooks = mEditText.getText().toString().replaceAll(" ", "+");
                Log.i(LOG_TAG, "The search query from the user is: " + queryBooks);

                if (queryBooks != null && !queryBooks.equals("")) {
                    String searchQuery = GOOGLE_BOOK_REQUEST_URL + queryBooks;
                    Log.i(LOG_TAG, "URL_QUERY IS: " + searchQuery);
                    // Get a reference to the ConnectivityManager to check state of network connectivity
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);

                    // Get details on the currently active default data network
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {

                        BookAsyncTask bookQueryAsyncTask = new BookAsyncTask();
                        bookQueryAsyncTask.execute(searchQuery);

                    } else {
                        // Update empty state with no connection error message
                        mEmptyStateTextView.setText(R.string.no_internet_connection);
                    }

                } else {
                    mAdapter.clear();
                    Log.i(LOG_TAG, "Search Query is empty and there are no books to display");
                    mEmptyStateTextView.setText(R.string.no_books);
                }
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Book> queryResults = QueryUtils.fetchBookData(urls[0]);
            return queryResults;
        }
        @Override
        protected void onPostExecute(List<Book> result) {
            super.onPostExecute(result);
            mAdapter.clear();

            if (result != null && !result.isEmpty()) {

                mAdapter.addAll(result);

            } else {
                mAdapter.clear();
                mEmptyStateTextView.setText(R.string.no_books);
            }
        }
    }
}

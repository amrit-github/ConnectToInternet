package com.example.amrit.connecttointernet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleTextView;
    private TextView mAuthorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleTextView = findViewById(R.id.titleText);
        mAuthorTextView = findViewById(R.id.authorText);
    }

    public void searchBooks(View view) {

        String queryString = mBookInput.getText().toString();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(mTitleTextView, mAuthorTextView).execute(queryString);
            mAuthorTextView.setText("");
            mTitleTextView.setText(R.string.loading);
        }

        else {
            if (queryString.length() == 0) {
                mAuthorTextView.setText("");
                mTitleTextView.setText("Please enter a search term");
            } else {
                mAuthorTextView.setText("");
                mTitleTextView.setText("Please check your network connection and try again.");
            }
        }



    }
}

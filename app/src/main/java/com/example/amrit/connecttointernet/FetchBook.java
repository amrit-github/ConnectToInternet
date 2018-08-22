package com.example.amrit.connecttointernet;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String,Void,String> {

    private TextView titleTextView;
    private TextView authorTextView;

    public FetchBook(TextView mTitleTextView, TextView mAuthorTextView) {
        titleTextView = mTitleTextView;
        authorTextView = mAuthorTextView;
    }

    @Override
    protected String doInBackground(String... strings) {

        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++){

                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String author = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    author = volumeInfo.getString("authors");
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(title != null && author != null){
                    titleTextView.setText(title);
                    authorTextView.setText(author);
                    return;
                }
            }
        } catch (JSONException e) {
            titleTextView.setText("No Results Found");
            authorTextView.setText("");
            e.printStackTrace();
        }

    }
}

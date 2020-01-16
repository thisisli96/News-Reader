package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> noted = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    ListView listView ;
    InputStream in, inputStream ;
    InputStreamReader reader, inputStreamReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        noted.add("Add a new place");
        noted.add("second web");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noted);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, noted.toString(), Toast.LENGTH_SHORT).show(); // to see list array yang di klik
                Intent intent = new Intent (getApplicationContext(), webView.class);
                //intent.putExtra("position",noted.get(position)); menampilkan yang pertama
                intent.putExtra("position",position); // menampilkan kedua
                startActivity(intent);
            }
        });
        DownloadTask task = new DownloadTask();
        task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty"); // api yang ingin di akses


    }
    public class DownloadTask extends AsyncTask<String, Void, String>
    {


        @Override
        protected String doInBackground(String... urls) {
            String result ="";
            URL url;
            HttpURLConnection urlConnection = null;

            try{

                url = new URL(urls[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                 inputStream = urlConnection.getInputStream();
                 inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                while (data != -1){

                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }
// ini buat menampilkan data title dan url dan info info dari database
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = 20;

                if (jsonArray.length() < 20){
                    numberOfItems = jsonArray.length();
                }

                for (int i =0; i < numberOfItems; i++){
                    String articleId = jsonArray.getString(i);

                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty" );
                    urlConnection = (HttpURLConnection) url.openConnection();

                    inputStream = urlConnection.getInputStream();
                    inputStreamReader = new  InputStreamReader(inputStream);
                    data = inputStreamReader.read();
                    String articleInfo = " ";

                    while (data != -1) {
                        char current = (char) data;
                        articleInfo += current;
                        data = inputStreamReader.read();
                    }
                   // Log.i("articleInfo", articleInfo); info dari articelInfo
                    // mengambil khusus title dan url dari APi

                    JSONObject jsonObject = new JSONObject(articleInfo);
                    if (!jsonObject.isNull("title")&& !jsonObject.isNull("url")){
                        String articleTitle = jsonObject.getString("title");
                        String articleUrl = jsonObject.getString("url");
                        Log.i("ini Title and URL ", articleTitle + articleUrl);
                    }
                }
// sampai sini tambahan untuk menambilkan data info dari API.

                Log.i("titlenya", result);
                return result;

            } catch (Exception e){

                e.printStackTrace();
                return "failed";
            }

        }

//        protected void onPostExecute(String s){
//            super.onPostExecute(s);
//
//            //  Log.i("JSON", s);
//            try { // step 2
//                JSONObject jsonObject = new JSONObject(s);
//                String weatherInfo = jsonObject.getString("weather");
//                Log.i("weather content", weatherInfo); // data yang diambil hanya weather dari API
//
//                JSONArray arr = new JSONArray(weatherInfo);
//                for (int i=0; i < arr.length(); i++){
//                    JSONObject jsonpart = arr.getJSONObject(i);
//
//                    Log.i("main",jsonpart.getString("main"));
//                    Log.i("description",jsonpart.getString("description"));
//                }
//
//
//            }catch (Exception e){
//
//                e.printStackTrace();
//            }
//        }
//    }//part one function atau apakah namanya ini pokoknya buat mengambil api dari web yang sudah menyediakan


}}

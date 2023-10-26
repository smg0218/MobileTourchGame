//package com.example.touch_game;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//public class ScoreRequest extends AppCompatActivity {
//    private static String IP_ADDRESS = "10.0.2.2";
//    private static String TAG = "scoreinput";
//
//    String _ID;
//    String score;
//    String date;
//
//    InsertData task = new InsertData();
//    task.execute("http://" + IP_ADDRESS + "/test.php", _ID, score, date);
//
//    class InsertData extends AsyncTask<String, void, String> {
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = ProgressDialog.show(ScoreRequest.this, "Please Wait", null, true, true);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            progressDialog.dismiss();
//            Log.d(TAG, "POST response - " + result);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String _ID = (String) params[1];
//            String score = (String) params[2];
//            String date = (String) params[3];
//
//            String serverURL = (String) params[0];
//            String postParameters = "_ID=" + _ID + "&score=" + score + "&date=" + date;
//
//            try {
//                URL url = new URL(serverURL);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                httpURLConnection.setReadTimeout(5000);
//                httpURLConnection.setConnectTimeout(5000);
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.connect();
//
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(postParameters.getBytes("UTF-8"));
//                outputStream.flush();
//                outputStream.close();
//
//                int responseStatusCode = httpURLConnection.getResponseCode();
//                Log.d(TAG, "POST response code - " + responseStatusCode);
//
//                InputStream inputStream;
//                if (responseStatusCode == HttpURLConnection.HTTP_OK)
//                    inputStream = httpURLConnection.getInputStream();
//                else
//                    inputStream = httpURLConnection.getErrorStream();
//
//                InputStreamReader inputStramReader = new InputStreamReader(inputStream, "UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(inputStramReader);
//
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                bufferedReader.close();
//
//                return sb.toString();
//            } catch (Exception e) {
//                Log.d(TAG, "InsertData : Error ", e);
//
//                return new String("Error: " + e.getMessage());
//            }
//        }
//    }
//}

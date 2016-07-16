package com.jason.listviewtest.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jason.listviewtest.Helpter.APIHelper;
import com.jason.listviewtest.R;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.db.TravelDB;
import com.jason.listviewtest.model.Spot;
import com.jason.listviewtest.model.SpotBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private volatile int mDataCount = 0;

    private ProgressBar mProgressBar;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mButton = (Button) findViewById(R.id.save_to_db);

        Utils.initSpotList(this);

        for(SpotBase spotBase : Utils.listSpot){
            if(!queryDB(spotBase.getStrSpotName())){
                //Cannot find info from db,download it from internet.
                mProgressBar.setVisibility(View.VISIBLE);
                String strSpotDetailArgs = "id=" + spotBase.getStrSpotNameEn() + "&ak=" + APIHelper.BAIDU_KEY + "&output=json";
                String strSpotDetailUrl = APIHelper.BAIDU_SOPT_QUERY_URL + "?" + strSpotDetailArgs;

                String strTicketArgs = "id=" + spotBase.getStrQueryID();
                String strTicketUrl = APIHelper.QUNAER_TICKET_URL + "?" + strTicketArgs;
                new DownloadTask().execute(strTicketUrl, strSpotDetailUrl,spotBase.getStrSpotName());
            }
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.storeToDB(MainActivity.this);
            }
        });

//        Log.v(TAG,Utils.listSpot.get(0).getStrAbstract());

    }

    private boolean queryDB(String strSpotName) {
        TravelDB db = TravelDB.getInstance(MainActivity.this);
        String strSQL = "select * from Spot where spot_name=\"" + strSpotName + "\"";
        return db.rawQuerySpot(strSQL).size() != 0;
    }

    /**
     * Download information needed from the Internet.
     * param: String QUNAER_URL, BAIDU_URL, SpotName
     * progress: int
     * return: List<String>: QUNAER_RES, BAIDU_RES, SpotName
     */
    private class DownloadTask extends AsyncTask<String, Integer, List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {
            String strTicketResult = null;
            String strSpotResult = null;
            StringBuilder sbf_ticket = new StringBuilder();
            StringBuilder sbf_spot = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("GET");
                // 填入apikey到HTTP header
                connection.setRequestProperty("apikey", APIHelper.QUNAER_KEY);
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf_ticket.append(strRead);
                    sbf_ticket.append("\r\n");
                }
                reader.close();

                strTicketResult = sbf_ticket.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(params[1]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf_spot.append(strRead);
                    sbf_spot.append("\r\n");
                }
                reader.close();

                strSpotResult = sbf_spot.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<String> res = new ArrayList<>();
            res.add(strTicketResult);
            res.add(strSpotResult);
            res.add(params[2]);

            return res;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            try {
                Spot spot = new Spot(find(strings.get(2)));

                if (spot == null) {
                    Log.v(TAG, "Cannot find base information of spot...Please load xml first");
                    return;
                }
                if(strings.get(0).length() == 0 || strings.get(1).length() == 0){
                    Log.v(TAG, "Something went wrong while getting information from the internet.");
                    return;
                }


                if(!"-1".equals(spot.getStrQueryID())) {
                    JSONObject jsonObject = new JSONObject(strings.get(0));
                    JSONObject ticket = jsonObject.getJSONObject("retData")
                            .getJSONObject("ticketDetail")
                            .getJSONObject("data")
                            .getJSONObject("display")
                            .getJSONObject("ticket");

                    spot.setStrAddress(ticket.getString("address"));
                    spot.setStrSpotImgUrl(ticket.getString("imageUrl"));
                    spot.setStrSpotProvince(ticket.getString("province"));
                    spot.setStrSpotCity(ticket.getString("city"));
                    Object obj = ticket.get("priceList");
                    if (obj instanceof JSONObject) {
                        spot.setStrBookUrl(((JSONObject) obj).getString("bookUrl"));
                    } else if (obj instanceof JSONArray)
                        spot.setStrBookUrl(((JSONArray) obj).getJSONObject(0).getString("bookUrl"));
                }

                JSONObject jsonObject_spot = new JSONObject(strings.get(1));
                JSONObject result = jsonObject_spot.getJSONObject("result");

                spot.setStrAbstract(result.getString("abstract"));
                spot.setStrDescription(result.getString("description"));
                spot.setStrTel(result.getString("telephone"));
                spot.setStrStar(result.getString("star"));

                JSONObject ticketInfo = result.getJSONObject("ticket_info");
                spot.setStrOpenTime(ticketInfo.getString("open_time"));
                spot.setStrTicketInfo(ticketInfo.getString("price"));

                Log.d(TAG,spot.getStrSpotName() + " is finished.");

                mProgressBar.incrementProgressBy(1);
                if(++mDataCount == Utils.listSpot.size()){
                    Log.w(TAG,String.valueOf(mDataCount));
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                Log.w(TAG,strings.get(2));
                e.printStackTrace();
            }

        }
    }

    private SpotBase find(String strSpotName) {
        for (SpotBase spot: Utils.listSpot) {
            if(strSpotName.equals(spot.getStrSpotName()))
                return spot;
        }
        return null;
    }


}

package com.jason.listviewtest.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jason.listviewtest.Helpter.APIHelper;
import com.jason.listviewtest.R;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.adapter.SpotContentAdapter;
import com.jason.listviewtest.db.TravelDB;
import com.jason.listviewtest.imageloader.ImageLoader;
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

public class SpotDetailActivity extends AppCompatActivity {

    private TextView tvSpotName;
    private TextView tvSpotRate;
    private LinearLayout mLayout;

    private RecyclerView mRecyclerView;

    private SpotBase mSpotBase = null;

    //ImageLoader
    private ImageLoader imageLoader;

    private final static String TAG = "SpotDetailActivity";

    private String strSpotName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);

        if(!Utils.listSpot.isEmpty())
            Utils.initSpotList(this);

        mSpotBase = Utils.listSpot.get(getIntent().getIntExtra("SpotPos", 0));

        strSpotName = mSpotBase.getStrSpotName();

//        String jsonResult = request(strTicketUrl, strTicketArgs);

        tvSpotName = (TextView) findViewById(R.id.spot_detail_name);
        tvSpotRate = (TextView) findViewById(R.id.spot_detail_rate);

        mLayout = (LinearLayout) findViewById(R.id.spot_detail_layout_image);

        imageLoader = ImageLoader.build(SpotDetailActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_spot_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        List<Spot> resSpot = queryFromDB(strSpotName);

        if(resSpot.size() == 0) {
            //Cannot find info from db,download it from internet.
            String strSpotDetailArgs = "id=" + mSpotBase.getStrSpotNameEn() + "&ak=" + APIHelper.BAIDU_KEY + "&output=json";
            String strSpotDetailUrl = APIHelper.BAIDU_SOPT_QUERY_URL + "?" + strSpotDetailArgs;

            String strTicketArgs = "id=" + mSpotBase.getStrQueryID();
            String strTicketUrl = APIHelper.QUNAER_TICKET_URL + "?" + strTicketArgs;
            new DownloadTask().execute(strTicketUrl, strSpotDetailUrl);
        }else{
            updateUI(resSpot.get(0));
        }

    }

    private List<Spot> queryFromDB(String strSpotName) {
        TravelDB db = TravelDB.getInstance(SpotDetailActivity.this);
        String strSQL = "select * from Spot where spot_name=\"" + strSpotName + "\"";
        return db.rawQuerySpot(strSQL);
    }

    private class DownloadTask extends AsyncTask<String, Integer, List<String>>{

        @Override
        protected List<String> doInBackground(String... params) {
            String strTicketResult = null;
            String strSpotResult = null;
            StringBuffer sbf_ticket = new StringBuffer();
            StringBuffer sbf_spot = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("GET");
                // 填入apikey到HTTP header
                connection.setRequestProperty("apikey", APIHelper.QUNAER_KEY);
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
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

            return res;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            try {
                Spot spot = new Spot(mSpotBase);

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

                saveSpotToDB(SpotDetailActivity.this,spot);

                updateUI(spot);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void updateUI(Spot spot) {
        String[] rate = {"★☆☆☆☆","★★☆☆☆","★★★☆☆","★★★★☆","★★★★★"};

        tvSpotName.setText(spot.getStrSpotName());
        String subTitle = "星级： " + rate[Integer.valueOf(spot.getStrStar())-1];
        tvSpotRate.setText(subTitle);

        imageLoader.bindBitmap(spot.getStrSpotImgUrl(),mLayout,0,0);

        SpotContentAdapter adapter = new SpotContentAdapter(this);
        adapter.initList(spot);

        mRecyclerView.setAdapter(adapter);

    }

    private void saveSpotToDB(Context context, Spot spot) {
        TravelDB db = TravelDB.getInstance(context);
        db.saveSpot(spot);
    }

}

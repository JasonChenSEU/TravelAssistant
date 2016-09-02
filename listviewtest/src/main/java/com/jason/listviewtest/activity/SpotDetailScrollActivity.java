package com.jason.listviewtest.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jason.listviewtest.Helpter.APIHelper;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.R;
import com.jason.listviewtest.adapter.SpotContentAdapter;
import com.jason.listviewtest.db.TravelDB;
import com.jason.listviewtest.imageloader.ImageLoader;
import com.jason.listviewtest.model.GetSpotDetailInfo;
import com.jason.listviewtest.model.GetSpotTicketInfo;
import com.jason.listviewtest.model.Spot;
import com.jason.listviewtest.model.SpotBase;
import com.jason.listviewtest.model.SpotDetailInfoFromBAIDU;
import com.jason.listviewtest.model.SpotTicketInfoFromQUNAER;

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
import java.util.concurrent.ThreadFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class SpotDetailScrollActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private SpotBase mSpotBase = null;

    private String strSpotName;

    private ProgressDialog mProgressDialog;

    private ImageLoader imageLoader;

    private ImageView mImgView;

    private String strBookUrl;
    private String strSpotCity;

    public volatile boolean isCanceled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail_scroll);

        if(!Utils.listSpot.isEmpty())
            Utils.initSpotList(this);

        mSpotBase = Utils.listSpot.get(getIntent().getIntExtra("SpotPos", 0));

        strSpotName = mSpotBase.getStrSpotName();

        isCanceled = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(strSpotName);
//        toolbar.setSubtitle("Recommend: 5stars");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.spot_scroll_weather:
                        if(strSpotCity != null){
                            Intent i = new Intent(SpotDetailScrollActivity.this, WeatherActivity.class);
                            i.putExtra("Name", strSpotCity);
                            startActivity(i);
                        }
                        break;
                    case R.id.spot_scroll_book:
                        if(strBookUrl != null){
                            Intent i = new Intent(SpotDetailScrollActivity.this, WebViewActivity.class);
                            i.putExtra("URL", strBookUrl);
                            startActivity(i);
                        }
                        break;
                }
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        Utils.initSpotList(this);
        initDataAndUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spot_detail_scroll,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initDataAndUI() {

        imageLoader = ImageLoader.build(SpotDetailScrollActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_spot_content_scroll);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mImgView = (ImageView) findViewById(R.id.spot_detail_image);

        List<Spot> resSpot = queryFromDB(strSpotName);

        if(resSpot.size() == 0) {
//            getDataFromNetwork(strTicketUrl,strSpotDetailUrl);
            getDataFromNetworkWithRxJavaAndRetrofit();
        }else{
            updateUI(resSpot.get(0));
        }
    }

    private void getDataFromNetworkWithRxJavaAndRetrofit() {

        mProgressDialog = ProgressDialog.show(SpotDetailScrollActivity.this,"Loading...", "数据获取中");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    isCanceled = true;
                }
                return false;
            }
        });

        //Retrofit for QUNAER ticket info
        Retrofit retrofit_qunaer_ticket = new Retrofit.Builder()
                .baseUrl(APIHelper.QUNAER_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetSpotTicketInfo getData = retrofit_qunaer_ticket.create(GetSpotTicketInfo.class);

        Observable<SpotTicketInfoFromQUNAER> spotFromNetworkObservable = getData.dataByRxJava(APIHelper.QUNAER_KEY,mSpotBase.getStrQueryID());

        //Retrofit for Baidu detail info
        Retrofit retrofit_baidu = new Retrofit.Builder()
                .baseUrl(APIHelper.BAIDU_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetSpotDetailInfo getSpotDetailInfo = retrofit_baidu.create(GetSpotDetailInfo.class);

        Observable<SpotDetailInfoFromBAIDU> spotDetailFromBaiduObservable = getSpotDetailInfo.dataByRxJava_Detail(mSpotBase.getStrSpotNameEn(),APIHelper.BAIDU_KEY,"json");

        //RxJava
        Observable
                .zip(spotFromNetworkObservable, spotDetailFromBaiduObservable, new Func2<SpotTicketInfoFromQUNAER, SpotDetailInfoFromBAIDU, Spot>() {
                    @Override
                    public Spot call(SpotTicketInfoFromQUNAER spotTicketInfoFromQUNAER, SpotDetailInfoFromBAIDU spotDetailInfoFromBAIDU) {
//                        Log.e("Record", "ZIP in " + Thread.currentThread());
                        Spot spot = new Spot(mSpotBase);
                        spot.buildWithTicketInfo(spotTicketInfoFromQUNAER);
                        spot.buildWithDetailInfo(spotDetailInfoFromBAIDU);
                        return spot;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressDialog.show();
                        isCanceled = false;
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<Spot>() {
                    @Override
                    public void call(Spot spot) {
                        Log.e("Record", "Save to db in " + Thread.currentThread());
//                        saveSpotToDB(SpotDetailScrollActivity.this, spot);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Spot>() {
                    @Override
                    public void onCompleted() {
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Snackbar.make(mRecyclerView,e.getMessage(),Snackbar.LENGTH_SHORT)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
//                        Toast.makeText(SpotDetailScrollActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Spot spot) {
                        if(!isCanceled)
                            updateUI(spot);
                        Log.e("Record", "update UI in " + Thread.currentThread());
                    }
                });
    }


    private void getDataFromNetwork() {
        //Cannot find info from db,download it from internet.
        String strSpotDetailArgs = "id=" + mSpotBase.getStrSpotNameEn() + "&ak=" + APIHelper.BAIDU_KEY + "&output=json";
        String strSpotDetailUrl = APIHelper.BAIDU_SOPT_QUERY_URL + "?" + strSpotDetailArgs;

        String strTicketArgs = "id=" + mSpotBase.getStrQueryID();
        String strTicketUrl = APIHelper.QUNAER_TICKET_URL + "?" + strTicketArgs;
        new DownloadTask().execute(strTicketUrl, strSpotDetailUrl);
    }



    private List<Spot> queryFromDB(String strSpotName) {
        TravelDB db = TravelDB.getInstance(SpotDetailScrollActivity.this);
        String strSQL = "select * from Spot where spot_name=\"" + strSpotName + "\"";
        return db.rawQuerySpot(strSQL);
    }

    private class DownloadTask extends AsyncTask<String, Integer, List<String>> {

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
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(SpotDetailScrollActivity.this,"Loading...", "数据获取中");
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            try {
                final Spot spot = new Spot(mSpotBase);

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

                saveSpotToDB(SpotDetailScrollActivity.this,spot);

                updateUI(spot);

                mProgressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void updateUI(final Spot spot) {

        imageLoader.bindBitmap(spot.getStrSpotImgUrl(),mImgView, getWindowManager().getDefaultDisplay().getWidth(), 240,false);

        SpotContentAdapter adapter = new SpotContentAdapter();
        adapter.initList(spot);

        strBookUrl = spot.getStrBookUrl();
        strSpotCity = spot.getStrSpotCity();


        mRecyclerView.setAdapter(adapter);

    }

    private void saveSpotToDB(Context context, Spot spot) {
        TravelDB db = TravelDB.getInstance(context);
        db.saveSpot(spot);
    }


}

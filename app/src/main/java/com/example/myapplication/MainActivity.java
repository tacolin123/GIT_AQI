package com.example.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.aqi.AQIPresenter;
import com.example.myapplication.dailquote.DQPresenter;
import com.example.myapplication.MyHScrollView.OnScrollChangedListener;
import com.example.myapplication.data.SqliteController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String URL_DAILQUOTE = "http://www.appledaily.com.tw/index/dailyquote/";
    private static final String URL_AQI = "https://opendata.epa.gov.tw/Data/Contents/AQI/";
    //private static final String URL_AQI = "https://opendata.epa.gov.tw/Data/ContentsView/AQI/";

    private Button btnStart;
    private TextView dailyText;
    private TextView publishTime;
    private ListView aqiListView;
    private AQIAdapter aqiAdapter;
    RelativeLayout mHead;
    //private ProgressBar progressBarLoading;
    private Dialog loadingDialog;
    private DQPresenter dailyQuote;
    private AQIPresenter aqi;
    private int mErrorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dailyText = (TextView)findViewById(R.id.text_dailquote); // 每日一句
        publishTime = (TextView) findViewById(R.id.publistime);  // 空氣品質指標時間
        //progressBarLoading = (ProgressBar) findViewById(R.id.progress_loading);

        mHead = (RelativeLayout) findViewById(R.id.head);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setBackgroundColor(Color.parseColor("#b2d235"));
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取蘋果日報每日一句
                if (aqiAdapter != null)
                {
                    aqiAdapter.mItemList.clear();
                    aqiAdapter.notifyDataSetChanged();
                }
                dailyQuote.getDailQuote(URL_DAILQUOTE); // 測試aqi暫時取消
                aqi.getAQIData(URL_AQI);
            }
        });

        aqiListView = (ListView) findViewById(R.id.listview_aqi);
        aqiListView.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setTitle("Loading . . . . . ");
        mErrorCount = 0; // 190424_t+ >= 2 關閉loadingDialog視窗
        dailyQuote = new DQPresenter(this);
        aqi = new AQIPresenter(this);
    }


    @Override
    public void showLoading() {
        //progressBarLoading.setVisibility(View.VISIBLE);
        mErrorCount++;
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        //progressBarLoading.setVisibility(View.GONE);
        // 190424_t~ 新增控制關閉loading視窗
        mErrorCount--;
        if (mErrorCount == 0)
            loadingDialog.dismiss();
    }

    @Override
    public void showDailQuote(String dailQuote) {

        dailyText.setText(dailQuote);
    }

    @Override
    public void showAQIData(List<Map<String,String>> list) {

        if (list.size() > 1)
        {
            list.remove(0); // 第一筆資料為 名稱 目前還沒使用到
            publishTime.setText("DataCount: " + list.size() + " PublishTime :" + list.get(1).get("publishtime"));
            aqiAdapter = new AQIAdapter(this, list, R.layout.item);
            aqiListView.setAdapter(aqiAdapter);
            /*
            aqiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String stringText;

                    //in normal case
                    stringText= ((TextView)view).getText().toString();

                    //in case if listview has separate item layout
                    //TextView textview=(TextView)view.findViewById(R.id.textview_id_of_listview_Item);
                    //stringText=textview.getText().toString();

                    //show selected
                    Toast.makeText(parent.getContext(), stringText, Toast.LENGTH_LONG).show();
                }
            });
            */
        }
    }


    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // 在listview 的標頭與item上有touch時，將touch的事件分發給 ScrollView
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }

    public class AQIAdapter extends BaseAdapter {
        public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

        private Context context;

        int layout_id;
        LayoutInflater mInflater;
        private LayoutInflater mLayInf;
        private List<Map<String, String>> mItemList;

        public AQIAdapter(Context context, List<Map<String, String>> itemList, int layout_id) {
            super();
            this.context = context;
            this.layout_id = layout_id;
            mInflater = LayoutInflater.from(context);
            mItemList = itemList;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mItemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parentView) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (MainActivity.this) {
                    convertView = mInflater.inflate(layout_id, null);
                    holder = new ViewHolder();

                    MyHScrollView scrollView1 = (MyHScrollView) convertView
                            .findViewById(R.id.horizontalScrollView1);

                    holder.scrollView = scrollView1;
                    holder.delete = (Button) convertView.findViewById(R.id.bt_delete);
                    holder.delete.setVisibility(View.VISIBLE);

                    holder.sitename = (TextView) convertView.findViewById(R.id.textView1);
                    //holder.sitename.setText(mItemList.get(position).get("sitename"));

                    holder.county = (TextView) convertView.findViewById(R.id.textView2);
                    //holder.county.setText(mItemList.get(position).get("county"));

                    holder.aqi = (TextView) convertView.findViewById(R.id.textView3);
                    //holder.aqi.setText(mItemList.get(position).get("aqi"));

                    holder.pollutant = (TextView) convertView.findViewById(R.id.textView4);
                    //holder.pollutant.setText(mItemList.get(position).get("pollutant"));

                    holder.status = (TextView) convertView.findViewById(R.id.textView5);
                    //holder.status.setText(mItemList.get(position).get("status"));

                    holder.so2 = (TextView) convertView.findViewById(R.id.textView6);
                    //holder.so2.setText(mItemList.get(position).get("so2"));

                    holder.co = (TextView) convertView.findViewById(R.id.textView7);
                    //holder.co.setText(mItemList.get(position).get("co"));

                    holder.co_8hr = (TextView) convertView.findViewById(R.id.textView8);
                    //holder.co_8hr.setText(mItemList.get(position).get("co_8hr"));

                    holder.o3 = (TextView) convertView.findViewById(R.id.textView9);
                    //holder.o3.setText(mItemList.get(position).get("o3"));

                    holder.o3_8hr = (TextView) convertView.findViewById(R.id.textView10);
                    //holder.o3_8hr.setText(mItemList.get(position).get("o3_8hr"));

                    holder.pm10 = (TextView) convertView.findViewById(R.id.textView11);
                    //holder.pm10.setText(mItemList.get(position).get("pm10"));

                    holder.pm25 = (TextView) convertView.findViewById(R.id.textView12);
                    //holder.pm25.setText(mItemList.get(position).get("pm25"));

                    holder.no2 = (TextView) convertView.findViewById(R.id.textView13);
                    //holder.no2.setText(mItemList.get(position).get("no2"));

                    holder.nox = (TextView) convertView.findViewById(R.id.textView14);
                    //holder.nox.setText(mItemList.get(position).get("nox"));

                    holder.no = (TextView) convertView.findViewById(R.id.textView15);
                    //holder.no.setText(mItemList.get(position).get("no"));

                    holder.windspeed = (TextView) convertView.findViewById(R.id.textView16);
                    //holder.windspeed.setText(mItemList.get(position).get("windspeed"));

                    holder.winddirec = (TextView) convertView.findViewById(R.id.textView17);
                    //holder.winddirec.setText(mItemList.get(position).get("winddirec"));

                    holder.publishtime = (TextView) convertView.findViewById(R.id.textView18);
                    //holder.publishtime.setText(mItemList.get(position).get("publishtime"));

                    holder.pm25_avg = (TextView) convertView.findViewById(R.id.textView19);
                    //holder.pm25_avg.setText(mItemList.get(position).get("pm25_avg"));

                    holder.pm10_avg = (TextView) convertView.findViewById(R.id.textView20);
                    //holder.pm10_avg.setText(mItemList.get(position).get("pm10_avg"));

                    holder.so2_avg = (TextView) convertView.findViewById(R.id.textView21);
                    //holder.so2_avg.setText(mItemList.get(position).get("so2_avg"));

                    holder.longitude = (TextView) convertView.findViewById(R.id.textView22);
                    //holder.longitude.setText(mItemList.get(position).get("longitude"));

                    holder.latitude = (TextView) convertView.findViewById(R.id.textView23);
                    //holder.latitude.setText(mItemList.get(position).get("latitude"));

                    MyHScrollView headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
                    headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

                    convertView.setTag(holder);
                    mHolderList.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.sitename.setText(mItemList.get(position).get("sitename"));
            holder.county.setText(mItemList.get(position).get("county"));
            holder.aqi.setText(mItemList.get(position).get("aqi"));
            holder.pollutant.setText(mItemList.get(position).get("pollutant"));
            holder.status.setText(mItemList.get(position).get("status"));
            holder.so2.setText(mItemList.get(position).get("so2"));
            holder.co.setText(mItemList.get(position).get("co"));
            holder.co_8hr.setText(mItemList.get(position).get("co_8hr"));
            holder.o3.setText(mItemList.get(position).get("o3"));
            holder.o3_8hr.setText(mItemList.get(position).get("o3_8hr"));
            holder.pm10.setText(mItemList.get(position).get("pm10"));
            holder.pm25.setText(mItemList.get(position).get("pm25"));
            holder.no2.setText(mItemList.get(position).get("no2"));
            holder.nox.setText(mItemList.get(position).get("nox"));
            holder.no.setText(mItemList.get(position).get("no"));
            holder.windspeed.setText(mItemList.get(position).get("windspeed"));
            holder.winddirec.setText(mItemList.get(position).get("winddirec"));
            holder.publishtime.setText(mItemList.get(position).get("publishtime"));
            holder.pm25_avg.setText(mItemList.get(position).get("pm25_avg"));
            holder.pm10_avg.setText(mItemList.get(position).get("pm10_avg"));
            holder.so2_avg.setText(mItemList.get(position).get("so2_avg"));
            holder.longitude.setText(mItemList.get(position).get("longitude"));
            holder.latitude.setText(mItemList.get(position).get("latitude"));

            holder.delete.setOnClickListener(new ItemButton_Click(parentView.getContext(), convertView, position));
            if ((position % 2) == 1) // 190425_t+ 增加底色
                convertView.setBackgroundColor(Color.parseColor("#C9E2F3CA"));
            else
                convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));

            return convertView;
        }

        class OnScrollChangedListenerImp implements OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
                mScrollViewArg = scrollViewar;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                mScrollViewArg.smoothScrollTo(l, t);
            }
        };

        //刪除按鈕事件
        class ItemButton_Click implements OnClickListener {
            private Context context;
            private int position;
            //private MainActivity mainActivity;

            ItemButton_Click(Context context, View view, int pos) {
                this.context = context;
                this.position = pos;
            }

            public void onClick(View v) {
                Log.d("DEL", "ListView item " + position);
                // 刪除listview選擇的item
                // listview removeViewAt 無法直接刪除
                try {
                    SqliteController sqlctrl = new SqliteController(context);
                    if (sqlctrl.deleteAQIContents(mItemList.get(position))) {
                        mItemList.remove(position);
                        ((AQIAdapter) aqiListView.getAdapter()).notifyDataSetChanged();
                        if (mItemList.size() > 0)
                            publishTime.setText("DataCount: " + mItemList.size() + " PublishTime :" + mItemList.get(0).get("publishtime"));
                        else
                            publishTime.setText("DataCount: " + mItemList.size() + " PublishTime :");
                        //((AQIAdapter)v.getContext()).getAdapter().notifyDataSetChanged;
                        //((ListView)context).getAdapter().notifyDataSetChanged();
                        //((AQIAdapter)mainActivity.aqiListView.getAdapter()).notifyDataSetChanged();
                        //mainActivity.aqiListView.removeViewAt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        class ViewHolder {
            Button delete;
            TextView sitename;
            TextView county;
            TextView aqi;
            TextView pollutant;
            TextView status;

            TextView so2;
            TextView co;
            TextView co_8hr;
            TextView o3;
            TextView o3_8hr;

            TextView pm10;
            TextView pm25;
            TextView no2;
            TextView nox;
            TextView no;

            TextView windspeed;
            TextView winddirec;
            TextView publishtime;
            TextView pm25_avg;
            TextView pm10_avg;

            TextView so2_avg;
            TextView longitude;
            TextView latitude;

            HorizontalScrollView scrollView;
        }
    }// end class AQIAdapter


}

package chailei.com.practisemodule;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chailei.com.practisemodule.adapters.ImagePagerAdapter;

public class ListViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,Runnable, View.OnClickListener, AbsListView.OnScrollListener {

    private ListView listView;
    private ViewPager pager;
    private boolean flag=true;

    public  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case 1:
                    pager.setCurrentItem(msg.arg1);
                    break;
                case 2:
                    break;
            }

        }
    };
    private int count;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private View lastTab;
    private View tab1_view;
    private View tab2_view;
    private float top=1;
    private View float_tab;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.list_view);
        float_tab = findViewById(R.id.float_head_tab);
        frame = (FrameLayout) findViewById(R.id.frame);
        float_tab.setVisibility(View.GONE);
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("chailei" + i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        View view = initHead();
        listView.addHeaderView(view);
        listView.setOnScrollListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("position",""+position);
        count = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public View initHead(){
        View view = LayoutInflater.from(this).inflate(R.layout.head_item, null);
        View layout = view.findViewById(R.id.head_tab);
        top = layout.getX();
//        layout.setVisibility(View.GONE);
        TextView tab1 = (TextView) layout.findViewById(R.id.tab1);
//        top = tab1.getHeight();
        TextView tab2 = (TextView) layout.findViewById(R.id.tab2);
        tab1_view = layout.findViewById(R.id.tab1_view);
        tab2_view = layout.findViewById(R.id.tab2_view);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        pager = (ViewPager) view.findViewById(R.id.pager);
        List<ImageView> images = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
//            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,100));
//            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.snow);
            images.add(imageView);
        }
        pager.setAdapter(new ImagePagerAdapter(images, this));
        pager.addOnPageChangeListener(this);
        Thread thread = new Thread(this);
        thread.start();
        return view;
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
    }

    @Override
    public void run() {
//        count = 0;
        while (flag) {
            try {
                Thread.sleep(3000);
                Message message = handler.obtainMessage();
                message.what = 1;
                count++;
                if(count >4){
                    count = 0;
                }
                message.arg1 = count;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v) {

        if(lastTab !=null){
            lastTab.setSelected(false);

        }
        v.setSelected(true);
        list.clear();
        int id = v.getId();
        switch (id){
            case R.id.tab1:
                list.addAll(getTabFirstData());
                tab1_view.setSelected(true);
                tab2_view.setSelected(false);
                break;
            case R.id.tab2:
                list.addAll(getTabSecondData());
                tab1_view.setSelected(false);
                tab2_view.setSelected(true);
                break;
        }
        lastTab = v;
        adapter.notifyDataSetChanged();
    }
    public List<String> getTabSecondData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("hyj"+i);
        }
        return list;
    }
    public List<String> getTabFirstData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("chailei"+i);
        }
        return list;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
     Log.d("top","top="+top);
//        if(top<0){
//            float_tab.setVisibility(View.VISIBLE);
//        }else {
//            float_tab.setVisibility(View.GONE);
//        }
        
    }

//    @Override
//    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        if(top<0){
//            float_tab.setVisibility(View.VISIBLE);
//        }else {
//            float_tab.setVisibility(View.GONE);
//        }
//    }
}

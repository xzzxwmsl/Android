package com.example.asus.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.asus.shopping.dummy.DummyContent;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends Fragment implements MyItemRecyclerViewAdapter.OnListFragmentInteractionListener {
    private String type;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        type = "list";
        String url = "http://c.m.163.com/nc/auto/list/5bmz6aG25bGx/0-20.html";
        setDatas(url);
        return view;
    }


    private void setDatas(String url) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                        .build();
                return chain.proceed(newRequest);
            }
        });
        OkHttpClient okHttpClient = builder.build();
//        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络连接失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String jsonString = response.body().string();
                try {
                    //转到主线程再使用Toast，否则会程序崩溃
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData(jsonString, recyclerView);
                        }
                    });

                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        });


    }

    private List<DummyContent.DummyItem> cityList = new ArrayList<>();

    public void setData(String json, RecyclerView recyclerView) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.optJSONArray(type);
            if (arr != null && arr.length() > 0) {
                //遍历JSON数组得到其中的数据
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jobj = arr.getJSONObject(i);
                    if (!TextUtils.isEmpty(jobj.optString("url"))) {
                        DummyContent.DummyItem city = new DummyContent.DummyItem(
                                jobj.optString("docid"), jobj.optString("title"),
                                jobj.optString("url")
                        );
                        cityList.add(city);
                    }

                }
                if (!type.equals("T1348647853363")) {
                    type = "T1348647853363";
                    String url = "http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html";
                    setDatas(url);
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(cityList, this));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("url", item.url);
        startActivity(intent);
    }
}



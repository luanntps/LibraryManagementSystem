package com.example.phuongnamlib.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phuongnamlib.Adapters.RecycleMemberAdapter;
import com.example.phuongnamlib.Adapters.RecycleTopTenAdapter;
import com.example.phuongnamlib.DAO.MemberManager;
import com.example.phuongnamlib.Models.BorrowedKind;
import com.example.phuongnamlib.Models.Revenue;
import com.example.phuongnamlib.Models.TopTenBook;
import com.example.phuongnamlib.R;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopBorrowedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopBorrowedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopBorrowedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopBorrowedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopBorrowedFragment newInstance(String param1, String param2) {
        TopBorrowedFragment fragment = new TopBorrowedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    String token;
    ArrayList<PieEntry> pieEntries;
    PieChart pieChart;
    LinearLayout bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    TextView openTop10;
    TextView touchToShow;
    RecyclerView rcvTopTen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_top_borrowed, container, false);
        token = getContext().getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        pieEntries = new ArrayList<>();
        pieChart = view.findViewById(R.id.piechart);
        touchToShow = view.findViewById(R.id.touchToShow);
        touchToShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pieChart.setVisibility(View.VISIBLE);
                touchToShow.setVisibility(View.GONE);
            }
        });
        rcvTopTen=view.findViewById(R.id.rcvTop10);
        SetPieChart();
        SetTopTen();
        return view;

    }
    void SetPieChart(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetAllTopBorrowed(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<BorrowedKind>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<BorrowedKind> list) {
                        int total=0;
                        for(int i = 0; i< list.size(); i++){
                            pieEntries.add(new PieEntry(list.get(i).getCount(), list.get(i).getName()));
                            total=total+list.get(i).getCount();
                        }
                        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Thể loại sách");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        pieDataSet.setValueTextColor(Color.BLACK);
                        pieDataSet.setValueTextSize(16f);
                        pieDataSet.setFormSize(6);
                        PieData pieData = new PieData(pieDataSet);

                        int finalTotal = total;
                        pieChart.setDrawCenterText(false);
                        pieChart.setCenterTextRadiusPercent(100);
                        pieChart.getDescription().setTextSize(18);
                        pieChart.getDescription().setText("Tỉ lệ lượt mượn theo thể loại");
                        pieChart.setData(pieData);
                        pieChart.setFitsSystemWindows(true);
                        pieChart.animate();
                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                Toast.makeText(getContext(),Math.round(((h.getY()*100)/ finalTotal))+"%",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected() {
                            }
                        });
                    }
                    @Override
                    public void onError( Throwable e) {

                    }
                    @Override
                    public void onComplete() {}
                });
    }
    void SetTopTen(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetAllTopTen(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<TopTenBook>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<TopTenBook> list) {
                        rcvTopTen.setHasFixedSize(true);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rcvTopTen.setLayoutManager(manager);
                        RecycleTopTenAdapter adapter = new RecycleTopTenAdapter(list, getContext());
                        rcvTopTen.setAdapter(adapter);
                    }
                    @Override
                    public void onError( Throwable e) {

                    }
                    @Override
                    public void onComplete() {}
                });
    }
}
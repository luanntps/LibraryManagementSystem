package com.example.phuongnamlib.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phuongnamlib.Models.Revenue;
import com.example.phuongnamlib.R;
import com.example.phuongnamlib.Services.ServiceAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragmentYear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragmentYear extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RevenueFragmentYear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueFragmentYear.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragmentYear newInstance(String param1, String param2) {
        RevenueFragmentYear fragment = new RevenueFragmentYear();
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
    ArrayList<BarEntry> barEntries;
    BarChart barChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_year, container, false);
        token = getContext().getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        barEntries = new ArrayList<>();
        barChart = view.findViewById(R.id.barchartYear);
        RevenueByYear();

        return view;
    }
    public void RevenueByYear(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetAllRevenueByYear(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Revenue>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext(ArrayList<Revenue> revenues) {
                        for(int i = 0; i< revenues.size(); i++){
                            barEntries.add(new BarEntry(revenues.get(i).getDate(), revenues.get(i).getRevenue()));
                        }
                        BarDataSet barDataSet = new BarDataSet(barEntries,"Doanh thu theo năm");
                        barDataSet.setColors(Color.GREEN);

                        BarData barData = new BarData(barDataSet);
                        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                Toast.makeText(getContext(),h.getY()+"",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                        barChart.setMaxVisibleValueCount(0);
                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Đơn vị VND");
                        barChart.animateY(2000);
                    }
                    @Override
                    public void onError( Throwable e) {

                    }
                    @Override
                    public void onComplete() {}
                });
    }
}
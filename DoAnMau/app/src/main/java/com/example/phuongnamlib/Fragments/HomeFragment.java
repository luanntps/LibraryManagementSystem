 package com.example.phuongnamlib.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phuongnamlib.Activities.UserMainActivity;
import com.example.phuongnamlib.DAO.BookManager;
import com.example.phuongnamlib.DAO.CallCardManager;
import com.example.phuongnamlib.DAO.KindManager;
import com.example.phuongnamlib.DAO.MemberManager;
import com.example.phuongnamlib.Helpers.PhuongNamLibSQLite;
import com.example.phuongnamlib.Models.Book;
import com.example.phuongnamlib.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    TextView tvCallcardQuantity;
    String token;
    PhuongNamLibSQLite phuongNamLibSQLite;
    CallCardManager callCardManager;
    KindManager kindManager;
    BookManager bookManager;
    MemberManager memberManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        token = getContext().getSharedPreferences("CurrentLibrarian", Context.MODE_PRIVATE).getString("currentToken", "");
        phuongNamLibSQLite = new PhuongNamLibSQLite(getContext());

        callCardManager=new CallCardManager(phuongNamLibSQLite,token);
        kindManager = new KindManager(phuongNamLibSQLite,token);
        bookManager = new BookManager(phuongNamLibSQLite,token);
        memberManager = new MemberManager(phuongNamLibSQLite,token);

        TextView callcard = view.findViewById(R.id.tvCallcardQuantity);
        TextView book = view.findViewById(R.id.tvBookQuantity);
        TextView kind = view.findViewById(R.id.tvKindQuantity);
        TextView member = view.findViewById(R.id.tvMemberQuantity);

        int callcardQuantity = callCardManager.GetQuantityCallcard();
        callcard.setText(callcardQuantity+"");
        int bookQuantity = bookManager.GetQuantityBook();
        book.setText(bookQuantity+"");
        int kindQuantity = kindManager.GetQuantityKind();
        kind.setText(kindQuantity+"");
        int memberQuantity = memberManager.GetQuantityMember();
        member.setText(memberQuantity+"");
        return view;
    }
}
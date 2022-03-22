package com.example.customnavigationdrawer.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customnavigationdrawer.R;

public class SupportFragment extends Fragment {

    View mView;

    TextView tvThongtin, tvKhaibao, tvTracuu;
    Button btnBack, btnCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_support, container, false);

        String htmlcontent =
                "<a href=\"https://covid19.gov.vn/\">Tình hình dịch COVID-19</a>";


        tvThongtin = (TextView) mView.findViewById(R.id.tvThongtin);
        tvThongtin.setText(android.text.Html.fromHtml(htmlcontent));
        tvThongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://covid19.gov.vn/"));
                startActivity(intent);
            }
        });

        String htmlcontent1 =
                "<a href=\"https://tokhaiyte.vn/\">Khai báo y tế</a>";

        tvKhaibao = (TextView) mView.findViewById(R.id.tvKhaibao);
        tvKhaibao.setText(android.text.Html.fromHtml(htmlcontent1));
        tvKhaibao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://tokhaiyte.vn/"));
                startActivity(intent);
            }
        });

        String htmlcontent2 =
                "<a href=\"https://tiemchungcovid19.gov.vn/portal/search\">Tra cứu thông tin tiêm chủng</a>";

        tvTracuu = (TextView) mView.findViewById(R.id.tracuu);
        tvTracuu.setText(android.text.Html.fromHtml(htmlcontent2));
        tvTracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://tiemchungcovid19.gov.vn/portal/search"));
                startActivity(intent);
            }
        });

        btnCall = (Button) mView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 19009095"));
                startActivity(intent);
            }
        });

        return mView;
    }
}

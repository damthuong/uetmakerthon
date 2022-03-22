package com.example.customnavigationdrawer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customnavigationdrawer.R;
import com.example.customnavigationdrawer.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoryFragment extends Fragment {

    View mView;

    Button btnBack;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;
    //    ArrayList<String> list;
//   // ArrayList<String> listTemp;
//    ArrayAdapter<String> adapter;
    int sum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_history, container, false);

        user = new User();
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        //  listView = (ListView) findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID+"/quet");

        //     list = new ArrayList<>();
        //  listTemp = new ArrayList<>();
        //    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        TextView tvSum = mView.findViewById(R.id.tv_sum);



        //   list.add("Lịch sử tiếp xúc gần: ");

        //     Query query = ref.orderByChild("quet");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    sum = (int) snapshot.getChildrenCount();
                    tvSum.setText("Tổng số lượt tiếp xúc: " + Integer.toString(sum) + " người");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText()
            }
        });

        //   tvSum.setText(String.valueOf(sum));
//   //     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                showInfo(i);
//            }
//        });

//        btnBack = (Button) mView.findViewById(R.id.btnbackHis);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), HomeFragment.class);
//            }
//        });
        return mView;
    }
}

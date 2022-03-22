package com.example.customnavigationdrawer.fragment;

import static com.example.customnavigationdrawer.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.customnavigationdrawer.MainActivity;
import com.example.customnavigationdrawer.R;
import com.example.customnavigationdrawer.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyProfileFragment extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private EditText edtFullname, edtEmail, edtNewPhone, edtNewMuitiem;
    private Button btnUpdateProfile, btnUpdateEmail;
    private MainActivity mMainActivity;
    private ProgressDialog progressDialog;
    private Uri mUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        initUi();
        setUserInformation();
        initListener();
        return mView;
    }

    private void initUi(){
        progressDialog = new ProgressDialog(getActivity());
        mMainActivity = (MainActivity) getActivity();
        imgAvatar = mView.findViewById(R.id.img_avatar);
        edtFullname = mView.findViewById(R.id.edt_full_name);
        edtNewPhone = mView.findViewById(R.id.edt_new_phone);
        edtNewMuitiem = mView.findViewById(R.id.edt_new_muitiem);
        edtEmail = mView.findViewById(R.id.edt_email_frag);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);
        btnUpdateEmail = mView.findViewById(R.id.btn_update_email);

    }

    public void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database;
        DatabaseReference ref;
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID);

        String name = user.getDisplayName();

        if(user == null){
            return;
        }
        if(name == "" || name == null){
            getNewName();
        }else {
            edtFullname.setText(name);
            ref.child("name").setValue(edtFullname.getText().toString().trim());
        }
        edtEmail.setText(user.getEmail());
        getNewPhone();
        getNewMuitiem();
        //set ảnh để sau part 10
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.avatar_default).into(imgAvatar);
    }

    private void getNewName(){
        FirebaseDatabase database;
        DatabaseReference ref;
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID+"/name");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                edtFullname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText()
                // tvName.setText("123");
            }
        });
    }

    private void getNewPhone(){
       // User user = new User();
        FirebaseDatabase database;
        DatabaseReference ref;
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID+"/phone");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.getValue(String.class);
                edtNewPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText()
                // tvName.setText("123");
            }
        });
    }

    private void getNewMuitiem(){
        FirebaseDatabase database;
        DatabaseReference ref;
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID+"/muitiem");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String muitiem = snapshot.getValue(String.class);
                edtNewMuitiem.setText(muitiem);
                //  tvName.setText("234");
                //listView.setAdapter(adapterTemp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText()
                // tvName.setText("123");
            }
        });
    }

    private void initListener(){
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfile();
            }
        });
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateEmail();
            }
        });
    }

    private void onClickUpdateProfile(){
        User userInfo = new User();
        String DeviceID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        FirebaseDatabase database;
        DatabaseReference ref;
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/"+DeviceID);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            return;
        }

        if(edtFullname.getText().toString().trim().equals("")){
            edtFullname.setError("Bạn cần nhập tên.");
        }else if(edtNewPhone.getText().toString().trim().equals("")){
            edtNewPhone.setError("Bạn cần nhập số điện thoại.");
        }else if(edtNewMuitiem.getText().toString().trim().equals("")){
            edtNewMuitiem.setError("Bạn cần nhập số lượng mũi đã tiêm.");
        } else {
            progressDialog.show();
            String strFullName = edtFullname.getText().toString().trim();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(strFullName).setPhotoUri(mUri)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                Map<String, Object> map = new HashMap<>();
                                map.put("phone", edtNewPhone.getText().toString().trim());
                                map.put("muitiem", edtNewMuitiem.getText().toString().trim());

                                ref.updateChildren(map);

                                Toast.makeText(getActivity(), "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                                mMainActivity.showUserInformation();
                               // userInfo.setName(edtFullname.getText().toString().trim());
                               // ref.child("name").setValue(edtFullname.getText().toString().trim());
                            } else {
                                Toast.makeText(getActivity(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void onClickUpdateEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(edtEmail.getText().toString().trim().equals("")){
            edtEmail.setError("Bạn cần nhập tên.");
        }else {
            String strNewEmail = edtEmail.getText().toString().trim();
            progressDialog.show();
            if (user == null) {
                return;
            }
            user.updateEmail(strNewEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Đã cập nhật email mới", Toast.LENGTH_SHORT).show();
                                mMainActivity.showUserInformation();
                            } else {
                                Toast.makeText(getActivity(), "Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void onClickRequestPermission(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            return;
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }

        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, MY_REQUEST_CODE);

        }
    }

    public void setBitmapImageView(Bitmap bitmapImageView){
        imgAvatar.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
}

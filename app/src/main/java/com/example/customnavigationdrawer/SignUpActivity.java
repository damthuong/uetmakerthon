package com.example.customnavigationdrawer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailedit, passedit,Phone,Name, Muitiem;
    private Button btnregis;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference ref;
    BluetoothDevice device;
    FirebaseUser userAcc;

    String DeviceID;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        emailedit = findViewById(R.id.email);
        passedit = findViewById(R.id.password);
        btnregis = findViewById(R.id.btnregis);
        Phone = findViewById(R.id.phone);
        Name = findViewById(R.id.name);
        Muitiem = findViewById(R.id.tiemchung);
        database = FirebaseDatabase.getInstance();




        user = new User();
        user.setID("mophongblz");

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });
    }

    private void getValues(){
        String phone = Phone.getText().toString().trim();
        user.setPhone(phone);
        String name = Name.getText().toString().trim();
        user.setName(name);
        user.setAddress(DeviceID);
        String muitiem = Muitiem.getText().toString().trim();
        user.setQuet("0");
        // tao child cho de luu data
        //ref.child(user.getName()).setValue(user);
        ref.child(user.getAddress()).setValue(user);
    }


    private void register() {
        String email, pass;
        if(emailedit.getText().toString().trim().equals("")){
            emailedit.setError("Bạn cần nhập email.");
            Toast.makeText(this, "Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
            return;
            //             dialog.dismiss();
        }
        else if(passedit.getText().toString().trim().length() < 6){
            passedit.setError("Mật khẩu cần ít nhất 6 chữ số.");
            //             dialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(Name.getText().toString().trim().equals("")){
            Name.setError("Bạn cần nhập tên.");
            //             dialog.dismiss();
        }
        else if(Phone.getText().toString().trim().equals("")){
            Phone.setError("Bạn cần nhập số điện thoại.");
            //             dialog.dismiss();
        }
        else if(Muitiem.getText().toString().trim().equals("")){
            Muitiem.setError("Bạn cần nhập số lượng mũi đã tiêm.");
            //             dialog.dismiss();
        }
        else {

            DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            ref = database.getReference("User");

            ref.child(DeviceID).setValue(user);

            email = emailedit.getText().toString().trim();
            pass = passedit.getText().toString().trim();

            String name = Name.getText().toString().trim();
            user.setName(name);

            // tao moi email va pass
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        getValues();
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Thông tin không hợp lệ hoặc đã được sử dụng.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//
//            String phone = Phone.getText().toString().trim();
//            user.setPhone(phone);
//            user.setAddress(DeviceID);
//            String muitiem = Muitiem.getText().toString().trim();
//            user.setMuitiem(muitiem);
//            user.setQuet("0");
//            ref.child(user.getAddress()).setValue(user);
        }


    }
}

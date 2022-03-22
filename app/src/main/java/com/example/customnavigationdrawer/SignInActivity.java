package com.example.customnavigationdrawer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private LinearLayout layoutSignUp;
    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    private LinearLayout layoutForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUi();
        initListener();
    }

    private void initUi(){
        progressDialog = new ProgressDialog(this);
        layoutSignUp = findViewById(R.id.layout_sign_up);
        layoutForgotPassword = findViewById(R.id.layout_forgot_pasword);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.btn_sign_in);
    }

    private void initListener() {
       // User userInfo = new User();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        DatabaseReference ref = database.getReference("User");
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new AlertDialog.Builder(SignInActivity.this)
                            .setTitle("Lưu ý!")
                            .setMessage("Bạn sẽ xóa toàn bộ dữ liệu nếu có trước đây khi tạo tài khoản mới.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ref.child(DeviceID).removeValue();

//                                    user.delete()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                               //         Log.d(TAG, "User account deleted.");
//                                                    }
//                                                }
//                                            });
                                    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });

        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickForgotPassword();
            }
        });
    }

    private void onClickSignIn() {
        String strEmail;
        String strPassword;

        if (edtEmail.getText().toString().trim().equals("") && edtPassword.getText().toString().trim().equals("")) {
                edtEmail.setError("Bạn cần nhập email.");
                //             dialog.dismiss();
                edtPassword.setError("Bạn cần nhập mật khẩu.");
                //             dialog.dismiss();
        } else if(edtEmail.getText().toString().trim().equals("")){
            edtEmail.setError("Bạn cần nhập email.");
        } else if(edtPassword.getText().toString().trim().equals("")){
            edtPassword.setError("Bạn cần nhập mật khẩu.");
        } else{
            strEmail = edtEmail.getText().toString().trim();
            strPassword = edtPassword.getText().toString().trim();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();
            auth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                progressDialog.dismiss();
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Đăng nhập không thành công.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void onClickForgotPassword(){
        //progressDialog.show();
        //String emailAddress = "user@example.com";
        openDialogUpdateItem();

    }

    private void openDialogUpdateItem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_forgot_password);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtConfirmEmail = dialog.findViewById(R.id.edt_confirm_email);
        Button btnConfirmEmail = dialog.findViewById(R.id.btn_confirm_email);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        btnConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress;
                if(edtConfirmEmail.getText().toString().trim().equals("")){
                    edtConfirmEmail.setError("Bạn cần nhập email.");
       //             dialog.dismiss();
                }
                else {
                    emailAddress = edtConfirmEmail.getText().toString().trim();
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(SignInActivity.this, "Đã gửi email. VUi lòng kiểm tra!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Bạn cần nhập đúng email đã lưu.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });

        dialog.show();
    }
}
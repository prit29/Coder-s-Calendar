package com.noobsever.codingcontests.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noobsever.codingcontests.MainActivity;
import com.noobsever.codingcontests.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainLogin extends AppCompatActivity {

    private boolean isSigninScreen = true;

    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private  TextView forgetpass;

    private Button btnSignup;
    private Button btnSignin;


    FirebaseAuth auth;
    EditText email_signin,password_signin,email_signup,password_signup,cpassword_signup;
    LinearLayout llsignin,llsignup;
    LinearLayout display;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent it = new Intent(MainLogin.this,MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            finish();
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.dismiss();
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);


        email_signin = findViewById(R.id.email_signin);
        password_signin = findViewById(R.id.password_signin);
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        cpassword_signup = findViewById(R.id.confirmpassword_signup);
        forgetpass  = findViewById(R.id.forgetpass);


        display = findViewById(R.id.display);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),"SignUp",Toast.LENGTH_SHORT).show();

                if(ValidateSignUp(email_signup,password_signup,cpassword_signup))
                {
                        register(email_signup.getText().toString(),password_signup.getText().toString());
                }

            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidateSignIn(email_signin,password_signin))
                {
                        login(email_signin.getText().toString(),password_signin.getText().toString());
                }
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate(email_signin)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainLogin.this);
                    builder.setMessage("By clicking OK you will recieve an email to. reset your password.")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ResetPassword(email_signin.getText().toString());

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                        email_signin.setError("Enter Email.");
                        email_signin.requestFocus();
                }
            }
        });


    }


    private void showSignupForm() {

        double dis =( (float) display.getWidth())/100;

        LinearLayout.LayoutParams paramsLogin = new LinearLayout.LayoutParams((int) (15*dis),(int) display.getHeight());
        llSignin.setLayoutParams(paramsLogin);

        LinearLayout.LayoutParams paramsSignup = new LinearLayout.LayoutParams((int) (85*dis),(int) display.getHeight());
        llSignup.setLayoutParams(paramsSignup);

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        double dis =( (float) display.getWidth())/100;

        LinearLayout.LayoutParams paramsLogin = new LinearLayout.LayoutParams((int) (85*dis),(int) display.getHeight());
        llSignin.setLayoutParams(paramsLogin);

        LinearLayout.LayoutParams paramsSignup = new LinearLayout.LayoutParams((int) (15*dis),(int) display.getHeight());
        llSignup.setLayoutParams(paramsSignup);

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }


    boolean validate(TextView view)
    {
        if(view.getText().toString().equals(""))
        {
                return false;
        }
        else
        {
                return true;
        }
    }



    private void register(String email, String password) {

        progressDialog.setMessage("Signing Up...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Intent it = new Intent(MainLogin.this,MainActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(it);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(MainLogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    private void login(String email,String password) {

        progressDialog.setMessage("Signing In...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainLogin.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(MainLogin.this,MainActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(it);
                            finish();
                        }
                        else {
                            Toast.makeText(MainLogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void ResetPassword(String emailsend)
    {
        progressDialog.setMessage("Sending mail...");
        progressDialog.show();
        auth.sendPasswordResetEmail(emailsend).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainLogin.this,"Email Sent on entered email Address",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String error = task.getException().getMessage();
                    Toast.makeText(MainLogin.this,error,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    boolean ValidateSignUp(EditText email,EditText password,EditText cpassword)
    {
            if(!validate(email))
            {
                    email.setError("Enter email");
                    email.requestFocus();
                    return false;
            }
            if(!validate(password))
            {
                password.setError("Enter Password");
                password.requestFocus();
                return false;
            }
            if(!validate(cpassword))
            {
                cpassword.setError("Confirm Password");
                cpassword.requestFocus();
                return false;
            }

            if(!password.getText().toString().equals(cpassword.getText().toString()))
            {
                cpassword.setError("Password not Matching");
                cpassword.requestFocus();
                return false;
            }

                return true;
    }


    boolean ValidateSignIn(EditText email,EditText password)
    {

        if(!validate(email))
        {
            email.setError("Enter email");
            email.requestFocus();
            return false;
        }
        if(!validate(password))
        {
            password.setError("Enter Password");
            password.requestFocus();
            return false;
        }
        return true;
    }

}
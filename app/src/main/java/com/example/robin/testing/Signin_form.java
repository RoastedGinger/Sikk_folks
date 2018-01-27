package com.example.robin.testing;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by robin on 9/1/18.
 */

public class Signin_form extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    EditText email,pass;
    Button signin;
    String email1,pass1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.signin_form,container,false);

        if(savedInstanceState==null)
        {
            email1=null;
            pass1=null;
        }
        else {
            email1 = savedInstanceState.getString("email");
            pass1 = savedInstanceState.getString("password");
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",email1);
        outState.putString("password",pass1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        email = getActivity().findViewById(R.id.email);
        pass = getActivity().findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        signin = getActivity().findViewById(R.id.signin);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    if (getActivity() != null) {

                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M){
                            Intent intent = new Intent(getActivity(), Home_page.class);
                            startActivity(intent);
                        }
                        else {
                            if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M) {
                                Intent intent = new Intent(getContext(), Home_page.class);
                                startActivity(intent);
                            }
                        }

                    }
                }
            }

        };
        final ConnectivityManager connec1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email1 = email.getText().toString();
                pass1 = pass.getText().toString();
                if(TextUtils.isEmpty(email1)||TextUtils.isEmpty(pass1))
                {
                    Toast.makeText(getActivity(),"enter both values",Toast.LENGTH_SHORT).show();
                }
                else if(connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED){
                    auth.signInWithEmailAndPassword(email1,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(getActivity(),"Incorrect user name or password",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else if(connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED){
                    Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
            public void onStart() {
                super.onStart();
                auth.addAuthStateListener(authStateListener);
            }
     }


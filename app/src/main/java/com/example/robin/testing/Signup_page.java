package com.example.robin.testing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by robin on 10/1/18.
 */

public class Signup_page extends Fragment {
    EditText email,name,password;
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    Button signup;
    TextView signin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_page,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = getActivity().findViewById(R.id.name);
        email = getActivity().findViewById(R.id.email);
        password = getActivity().findViewById(R.id.password);
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PostLike");
        signup =getActivity().findViewById(R.id.signup);
        signin = getActivity().findViewById(R.id.signin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()))
                {
                    mauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = mauth.getCurrentUser().getUid();
                                DatabaseReference current_user_database = databaseReference.child(userId);
                                current_user_database.child("Name").setValue(name.getText().toString());
                                startActivity(new Intent(getActivity(),Home_page.class));
                            }
                        }
                    });
                }

                else{
                    Toast.makeText(getActivity(),"please fill out the form correctly",Toast.LENGTH_LONG).show();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                Signin_form signin_form = new Signin_form();
                Signup_page signup_page = (Signup_page) fragmentManager.findFragmentByTag("signup");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(signup_page);
                fragmentTransaction.addToBackStack("check");
                fragmentTransaction.add(R.id.auth_class,signin_form,"signin");
                fragmentTransaction.commit();
            }
        });

    }
}

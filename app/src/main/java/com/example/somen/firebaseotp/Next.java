package com.example.somen.firebaseotp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Next extends AppCompatActivity {
    EditText editnames,editemails,editpasss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        editnames= (EditText)findViewById(R.id.editname);
        editemails=(EditText)findViewById(R.id.editemail);
        editpasss=(EditText)findViewById(R.id.editpass);
        Button but=(Button)findViewById(R.id.button);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");
        final HashMap<String,String> map=new HashMap<String, String>();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=(editnames).getText().toString();
                String email=(editemails).getText().toString();
                String pass=(editpasss).getText().toString();
                map.put(name,email);
                myRef.push().setValue(map);
                Toast.makeText(getApplicationContext(),"Data Insert",Toast.LENGTH_SHORT).show();

            }
        });
        ArrayList<String> list=new ArrayList<String>();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String ss=dataSnapshot.getValue().toString();
                String sss[]=ss.split("=");
                Toast.makeText(getApplicationContext(),ss,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

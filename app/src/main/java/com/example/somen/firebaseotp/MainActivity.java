package com.example.somen.firebaseotp;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText editText, editTextOtp;
    Button verify;
    private FirebaseAuth mAuth;
    String Verification;
   // String TAG = "Message";
   PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

       @Override
       public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
           Toast.makeText(getApplicationContext(), "verification complete directly", Toast.LENGTH_SHORT).show();
           signinwithcredential(phoneAuthCredential);
       }


       @Override
       public void onVerificationFailed(FirebaseException e) {
           //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onCodeSent(String verificationId,
                              PhoneAuthProvider.ForceResendingToken token) {
           Toast.makeText(getApplicationContext(), "code send", Toast.LENGTH_SHORT).show();
           Verification = verificationId;
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.PhoneNo);
        verify=(Button)findViewById(R.id.VerifyNo);
        mAuth=FirebaseAuth.getInstance();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss=editText.getText().toString();
                //PhoneAuthProvider.getInstance().verifyPhoneNumber(ss,60,TimeUnit.SECONDS,this,mcallback);
                codesend(ss);
            }
        });
        editTextOtp=(EditText)findViewById(R.id.Otp);
        Button otp=(Button)findViewById(R.id.verifyotp);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sss=editTextOtp.getText().toString();
                verifycode(Verification,sss);
            }
        });
        Button resend=(Button)findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ss=editText.getText().toString();
                Toast.makeText(getApplicationContext(),"otp resending",Toast.LENGTH_SHORT).show();
                codesend(ss);
            }
        });
        Button go=(Button)findViewById(R.id.Go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Next.class);
                startActivity(i);

            }
        });

    }


    private void verifycode(String verification, String sss) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verification,sss);
        if(sss!=verification)
        {
            Toast.makeText(getApplicationContext(),"Wrong otp input",Toast.LENGTH_SHORT).show();
        }
        signinwithcredential(credential);
    }
  //  public void cl() {
         /*PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getApplicationContext(), "verification complete directly", Toast.LENGTH_SHORT).show();
                signinwithcredential(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(getApplicationContext(), "code send", Toast.LENGTH_SHORT).show();
                Verification = verificationId;
            }
        };*/


    private void signinwithcredential(PhoneAuthCredential Credential) {
        mAuth.signInWithCredential(Credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"task complete",Toast.LENGTH_SHORT).show();
                    FirebaseUser user=task.getResult().getUser();
                    Intent i=new Intent(MainActivity.this,Next.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"task incomplete",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // FirebaseUser muser=mAuth.getCurrentUser();
    }

    private void codesend(String ss) {
      //  cl();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(ss,60,TimeUnit.SECONDS,this,mcallback);
    }
}

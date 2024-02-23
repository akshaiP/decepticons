package com.example.sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class testActivity extends AppCompatActivity {
private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        logout=findViewById(R.id.button2);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


    }
    private void logout() {
        GoogleSignInOptions gso = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso); // Replace 'this' with your Activity or Context

        googleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        googleSignInClient.revokeAccess();
                        Toast.makeText(testActivity.this, "Log out Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(testActivity.this,loginActivity.class);
                        startActivity(intent);
                    } else {
                        // Sign-out failed, handle the error
                        Toast.makeText(testActivity.this, "Log out Failed", Toast.LENGTH_SHORT).show();
                        Exception exception = task.getException();
                        // Handle the error appropriately (e.g., show an error message)
                    }
                });

    }

}

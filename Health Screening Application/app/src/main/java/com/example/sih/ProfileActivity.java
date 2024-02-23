package com.example.sih;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private EditText name,age,email,phoneno;
    private Button save,Logout;
    private ImageView profile;

    private FirebaseFirestore fstore;
     private FirebaseAuth mAuth;

     String UserId;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.editText);
        age = findViewById(R.id.editText2);
        email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phone);
        save = findViewById(R.id.button3);
        Logout = findViewById(R.id.button4);
        profile=findViewById(R.id.imageView4);

        fstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        UserId=mAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle any errors here
                    return;
                }

                if (value != null && value.exists()) {
                    // Data from Firestore exists for the user
                    String userEmail = value.getString("Email"); // Assuming "email" is the field name in Firestore
                    String userName = value.getString("Name");
                    String userage = value.getString("age");
                    String userphone = value.getString("phone");
                    // Assuming "name" is the field name in Firestore

                    // Set the email and name fields in your UI

                    phoneno.setText(userphone);
                    age.setText(userage);
                    email.setText(userEmail);
                    name.setText(userName);
                }
            }
        });

         gso = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        GoogleSignInAccount acct =GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personename=acct.getDisplayName();
            String personemail=acct.getEmail();
            name.setText(personename);
            email.setText(personemail);

            Uri personPhoto = acct.getPhotoUrl();
            if (personPhoto != null) {
                String photoUrl = personPhoto.toString();
                Picasso.get().load(photoUrl).into(profile);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values entered by the user
                String userAge = age.getText().toString();
                String userPhone = phoneno.getText().toString();

                // Update Firestore with the new values
                updateFirestoreData(userAge, userPhone);
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(ProfileActivity.this, "Log out Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ProfileActivity.this,loginActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign-out failed, handle the error
                            Toast.makeText(ProfileActivity.this, "Log out Failed", Toast.LENGTH_SHORT).show();
                            Exception exception = task.getException();
                            // Handle the error appropriately (e.g., show an error message)
                        }
                    });

        }

    private void updateFirestoreData(String newAge, String newPhone) {
        DocumentReference documentReference = fstore.collection("users").document(UserId);

        // Create a map to update only the specific fields you want to change
        Map<String, Object> updates = new HashMap<>();
        updates.put("age", newAge);
        updates.put("phone", newPhone);

        // Update the document in Firestore
        documentReference.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document successfully updated
                        Toast.makeText(ProfileActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occur during the update
                        Toast.makeText(ProfileActivity.this, "Error updating data", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error updating document", e);
                    }
                });
    }


}
package com.example.sih;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sih.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class DashboardActivity extends AppCompatActivity {

    private ImageView Profile;

    private ImageView Voicecall;

    private ImageView question,Map ,Chat,calander,chatbot,helpline;
    private TextView Name;
    GoogleSignInOptions gso;
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;



    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Profile=findViewById(R.id.imageView4);
        Name=findViewById(R.id.textView3);
        question=findViewById(R.id.ques);
        Voicecall=findViewById(R.id.voicecall);
        Map=findViewById(R.id.Map);
        Chat=findViewById(R.id.chat);
        calander=findViewById(R.id.calander);
        chatbot=findViewById(R.id.chatbot);
        helpline=findViewById(R.id.helpline);

        fstore= FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

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
                    // Data from Firestore exists for the user// Assuming "email" is the field name in Firestore
                    String userName = value.getString("Name"); // Assuming "name" is the field name in Firestore

                    // Set the email and name fields in your UI

                    Name.setText(userName);
                }
            }
        });


        gso = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personename=acct.getDisplayName();
            String email=acct.getEmail();
            Name.setText(personename);

            Uri personPhoto = acct.getPhotoUrl();
            if (personPhoto != null) {
                String photoUrl = personPhoto.toString();
                Picasso.get().load(photoUrl).into(Profile);
            }
        }
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DashboardActivity.this,QuestionaireActivity.class);
                startActivity(intent1);
            }
        });
        Voicecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+1860-317-6113"));
                startActivity(intent);
            }
        });
        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1800-599-0019"));
                startActivity(intent);
            }
        });
       Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the search query
                String query = "Pyschiatrist near me";

                // Create a Uri with the query to open in Google Maps
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);

                // Create an Intent to open Google Maps with the search query
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set the package for the Intent to ensure it opens in Google Maps
                mapIntent.setPackage("com.google.android.apps.maps");

                // Check if Google Maps is installed on the device
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    // Open Google Maps
                    startActivity(mapIntent);
                } else {
                    // Handle the case where Google Maps is not installed
                    // You can prompt the user to install Google Maps or use a different map app.
                }
            }
        });
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace 'https://example.com' with the URL you want to redirect to
                Intent intent =new Intent(DashboardActivity.this, chatActivity.class);
                startActivity(intent);
            }
        });

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace 'https://example.com' with the URL you want to redirect to
               Intent intent =new Intent(DashboardActivity.this, chatActivity.class);
               startActivity(intent);
            }
        });
        calander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleCalendar();

            }
        });

    }
    private void openGoogleCalendar() {
        // Create an intent with a specific URI to open the Google Calendar app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.google.android.calendar");

        // Try to open the Google Calendar app
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If the Google Calendar app is not available, you can take another action,
            // such as opening a web page with Google Calendar.
            // For example:
            Uri calendarWebUri = Uri.parse("https://calendar.google.com");
             Intent webIntent = new Intent(Intent.ACTION_VIEW, calendarWebUri);
             startActivity(webIntent);
        }
    }


}
package com.example.sih;

import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {

    public static FirebaseFirestore g_firestore;
   // public static List<CategoryModel> g_catlist = new ArrayList<>();
   // public static int g_Selected_cat_index = 0;
   // public static List<TestModel> g_testList = new ArrayList<>();
   // public static int g_Selected_test_index = 0;
    //public static List<QuestionModel> g_quesList = new ArrayList<>();


    public static void createuserData(String email, String name, mycompletelistner completelistner) {
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("EMAIL_ID", email);
        userData.put("NAME", name);
        userData.put("TOTAL_SCORE", 0);

        DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        WriteBatch batch = g_firestore.batch();
        batch.set(userDoc, userData);

        DocumentReference countDoc = g_firestore.collection("USERS").document("TOTAL_USERS");
        batch.update(countDoc, "COUNT", FieldValue.increment(1));

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        completelistner.onSuccess();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completelistner.onFailure();
                    }
                });

    }
    public static void loadcategories(final mycompletelistner completelistner) {
       // g_catlist.clear();
        g_firestore.collection("QUIZ").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            docList.put(doc.getId(), doc);
                        }
                        QueryDocumentSnapshot catListdoc = docList.get("Categories");


                        long catCount = catListdoc.getLong("COUNT");
                        for (int i = 1; i <= catCount; i++) {
                            String catID = catListdoc.getString("CAT" + String.valueOf(i) + "_ID");
                            QueryDocumentSnapshot catDoc = docList.get(catID);

                            int noofTest = catDoc.getLong("NO_OF_TESTS").intValue();
                            String catname = catDoc.getString("NAME");
                            Log.wtf("Test", String.valueOf(noofTest));
                           // g_catlist.add(new CategoryModel(catID, catname, noofTest));
                        }

                        completelistner.onSuccess();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completelistner.onFailure();

                    }
                });
    }
}

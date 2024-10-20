package com.example.newsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class Edit extends AppCompatActivity {

    private TextInputEditText inputName, inputMobileNo;
    private Button btnUpdate;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit); // Ensure this matches your layout file name

        // Set up padding for the main layout to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        // Find views by ID
        inputName = findViewById(R.id.input_name);

        inputMobileNo = findViewById(R.id.input_mobileno);
        btnUpdate = findViewById(R.id.btn_update);

        // Load current user data
        loadUserData();

        // Update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserData();
            }
        });
    }

    private void loadUserData() {
        // Fetch user data from Firebase Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String mobileNo = dataSnapshot.child("mobileNo").getValue(String.class);
                    // You can omit password for security reasons

                    // Set data to EditText
                    inputName.setText(name);
                    inputMobileNo.setText(mobileNo);
                } else {
                    Toast.makeText(Edit.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Edit.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        // Get updated values
        String updatedName = inputName.getText().toString().trim();
        String updatedMobileNo = inputMobileNo.getText().toString().trim();

        // Update user data in Firebase
        databaseReference.child("name").setValue(updatedName);
        databaseReference.child("mobileNo").setValue(updatedMobileNo);



        // Show a success message and navigate back to the profile fragment
        Toast.makeText(Edit.this, "Profile updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Edit.this, MainActivity.class); // Change to your profile activity if different
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

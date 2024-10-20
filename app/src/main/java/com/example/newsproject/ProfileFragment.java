package com.example.newsproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    // Declare your views
    private Button logout, editProfile;
    private TextView nameTextView, emailTextView, phoneTextView, passwordTextView,profilename;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        // Find views by ID
        logout = view.findViewById(R.id.logout);
        editProfile = view.findViewById(R.id.editProfile);
        nameTextView = view.findViewById(R.id.profile_name);
        emailTextView = view.findViewById(R.id.profile_email);
        phoneTextView = view.findViewById(R.id.profile_phone);
        passwordTextView = view.findViewById(R.id.profile_password);
        profilename= view.findViewById(R.id.profilename);


        // Fetch and display user data
        loadUserData();

        // Set up listeners for buttons
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Edit.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), SignIn.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

    private void loadUserData() {
        // Fetch user data from Firebase Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming your User class has getters for name, email, phone, etc.
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String phone = dataSnapshot.child("mobileNo").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);

                    // Set data to TextViews
                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    phoneTextView.setText(phone);
                    profilename.setText(name);

                    passwordTextView.setText(password); // You might want to hide or encrypt the password for security reasons
                } else {
                    Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

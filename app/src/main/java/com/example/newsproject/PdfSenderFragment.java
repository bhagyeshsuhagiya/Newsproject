package com.example.newsproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class PdfSenderFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<PostModel> articleList;
    private FloatingActionButton fab;

    // Firebase Realtime Database reference
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public PdfSenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf_sender, container, false);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the data list and adapter
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(articleList, getContext());
        recyclerView.setAdapter(articleAdapter); // Attach the adapter to the RecyclerView

        // Load data from Firebase Realtime Database
        loadDataFromRealtimeDatabase();

        // Initialize the FloatingActionButton
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start PDFadd activity when the FAB is clicked
                Intent intent = new Intent(getActivity(), PDFadd.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Method to upload an image to Firebase Storage
    private void uploadImageToStorage(Uri fileUri) {
        // Reference to store the image in Firebase Storage
        final StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");

        imageRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();

                        // Now store this download URL in Realtime Database
                        storeImageDataInDatabase(downloadUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("PdfSenderFragment", "Error uploading image", e);
            }
        });
    }

    // Method to store image data in Firebase Realtime Database
    private void storeImageDataInDatabase(String imageUrl) {
        String postId = databaseReference.push().getKey();
        PostModel post = new PostModel(imageUrl, "Title", "Date", "Time", "Link");

        // Store the post object in Realtime Database
        if (postId != null) {
            databaseReference.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("PdfSenderFragment", "Data stored successfully.");
                    } else {
                        Log.e("PdfSenderFragment", "Error storing data", task.getException());
                    }
                }
            });
        }
    }

    // Method to load data from Realtime Database
    private void loadDataFromRealtimeDatabase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Extract data and create PostModel objects
                    PostModel post = snapshot.getValue(PostModel.class);
                    if (post != null) {
                        articleList.add(post);
                    }
                }

                // Notify the adapter that data has changed
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PdfSenderFragment", "Error loading data: " + databaseError.getMessage());
            }
        });
    }
}
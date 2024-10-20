
package com.example.newsproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Calendar;

public class PDFadd extends AppCompatActivity {
    private EditText inputDate, inputTime, inputName, inputUrl;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button addImageButton, addPostButton;
    private ImageView articleImage;
    private Uri imageUri;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfadd);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        storageReference = FirebaseStorage.getInstance().getReference("post_images");

        addImageButton = findViewById(R.id.add_image_button);
        articleImage = findViewById(R.id.article_image);
        inputName = findViewById(R.id.input_name);
        inputUrl = findViewById(R.id.input_url);
        inputDate = findViewById(R.id.input_date);
        inputTime = findViewById(R.id.input_time);
        addPostButton = findViewById(R.id.add_post_button);

        addImageButton.setOnClickListener(v -> openGallery());
        inputDate.setOnClickListener(v -> showDatePickerDialog());
        inputTime.setOnClickListener(v -> showTimePickerDialog());
        addPostButton.setOnClickListener(v -> savePostToFirebase());
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                articleImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> inputDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view, int hourOfDay, int minute1) -> inputTime.setText(String.format("%02d:%02d", hourOfDay, minute1)),
                hour, minute, true);
        timePickerDialog.show();
    }

    private void savePostToFirebase() {
        String name = inputName.getText().toString().trim();
        String url = inputUrl.getText().toString().trim();
        String date = inputDate.getText().toString().trim();
        String time = inputTime.getText().toString().trim();

        if (name.isEmpty() || url.isEmpty() || date.isEmpty() || time.isEmpty() || imageUri == null) {
            Toast.makeText(this, "All fields and image are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String postId = databaseReference.push().getKey();
        StorageReference fileReference = storageReference.child(postId + ".jpg");

        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                PostModel post = new PostModel(imageUrl, name,date, time, url);
                databaseReference.child(postId).setValue(post)
                        .addOnSuccessListener(aVoid -> {
                            // Clear all fields and image view
                            inputName.setText("");
                            inputUrl.setText("");
                            inputDate.setText("");
                            inputTime.setText("");
                            articleImage.setImageDrawable(null);
                            imageUri = null;

                            // Navigate to PDFSenderActivity
                            Intent intent = new Intent(PDFadd.this, PdfSenderFragment.class);
                            startActivity(intent);
                            finish(); // Close the current activity

                        })
                        .addOnFailureListener(e -> Toast.makeText(PDFadd.this, "Failed to add post", Toast.LENGTH_SHORT).show());
            });
        }).addOnFailureListener(e -> Toast.makeText(PDFadd.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
    }
}

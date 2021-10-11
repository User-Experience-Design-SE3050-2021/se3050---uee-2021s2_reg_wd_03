package com.example.adsmobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnBack, btnCart;
    public ImageView itemImage;
    private EditText etTitle, etDescription, etPrice;
    private TextView tvCategory;
    private Button btnPostAd, btnReset;
    private String ad_Title, ad_Description, ad_category, ad_Price;
    private String saveCurrentDate, saveCurrentTime, productRandomKey, downloadImageUrl;
    public Uri ImageUri;
    private ProgressDialog loadingBar;
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = new ProgressDialog(this);


        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("postAds");

        //btnBack = (ImageButton) findViewById(R.id.btnBack);
        itemImage = (ImageView) findViewById(R.id.itemImage);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etPrice = (EditText) findViewById(R.id.etPrice);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        btnPostAd = (Button) findViewById(R.id.btnPostAd);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pick category
                categoryDialog();
            }
        });
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPhoneGallery();
            }
        });
        btnPostAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });

    }

    private void OpenPhoneGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent.createChooser(galleryIntent, "select picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            itemImage.setImageURI(ImageUri);

        }
    }

    private void categoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Product Category")
                .setItems(Constants.productCategories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // get picked category
                        String category = Constants.productCategories[which];

                        // set picked category
                        tvCategory.setText(category);
                    }
                })
                .show();
    }

    private void ValidateProductData() {

        ad_Title = etTitle.getText().toString().trim();
        ad_Description = etDescription.getText().toString().trim();
        ad_category = tvCategory.getText().toString().trim();
        ad_Price = etPrice.getText().toString().trim();

        if (TextUtils.isEmpty(ad_Title)) {
            Toast.makeText(this, "Ad Title is required....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ad_Description)) {
            Toast.makeText(this, "Ad Description is required....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ad_category)) {
            Toast.makeText(this, "Ad Category is required....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ad_Price)) {
            Toast.makeText(this, "Ad Price is required....", Toast.LENGTH_SHORT).show();
            return;
        }

        storeProductInformation();
    }

    private void storeProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(MainActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(MainActivity.this, "getting product image Url successfully", Toast.LENGTH_SHORT).show();

                            SaveProductInfortoDatabase();
                        }
                    }
                });
            }
        });


    }

    private void SaveProductInfortoDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("productId", productRandomKey);
        productMap.put("currentDate", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("productDescription", ad_Description);
        productMap.put("productIcon", downloadImageUrl);
        productMap.put("productTitle", ad_Title);
        productMap.put("productPrice", ad_Price);
        productMap.put("productCategory", ad_category);

        productsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, ShowSellerProducts.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                            clearData();
                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(MainActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void clearData() {
        // clear data after inserted
        etTitle.setText("");
        etDescription.setText("");
        etPrice.setText("");
        tvCategory.setText("");
        itemImage.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
        ImageUri = null;
    }

}
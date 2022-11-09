package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView profile_name, profile_email, profile_id_number,
            profile_phone_number, profile_bloodgroup,profile_type;

    private Toolbar toolbar;

    private CircleImageView profile_circle_image_view;

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_id_number = findViewById(R.id.profile_id_number);
        profile_phone_number = findViewById(R.id.profile_phone_number);
        profile_bloodgroup = findViewById(R.id.profile_bloodgroup);
        profile_type = findViewById(R.id.profile_type);
        profile_circle_image_view = findViewById(R.id.profile_image);

        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    profile_name.setText(snapshot.child("name").getValue().toString());
                    profile_type.setText(snapshot.child("type").getValue().toString());
                    profile_email.setText(snapshot.child("email").getValue().toString());
                    profile_id_number.setText(snapshot.child("idnumber").getValue().toString());
                    profile_phone_number.setText(snapshot.child("phonenumber").getValue().toString());
                    profile_bloodgroup.setText(snapshot.child("bloodgroup").getValue().toString());

                    String profile_image_url = snapshot.child("profilepictureurl").getValue().toString();
                    Glide.with(getApplicationContext()).load(profile_image_url).into(profile_circle_image_view);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            finish();
            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
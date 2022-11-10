package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.blooddonationapp.Adapter.UserAdapter;
import com.example.blooddonationapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectedActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView category_recycler_view;

    private List<User> userList;
    private UserAdapter userAdapter;

    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        category_recycler_view = findViewById(R.id.category_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        category_recycler_view.setLayoutManager(linearLayoutManager);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(CategorySelectedActivity.this, userList);
        category_recycler_view.setAdapter(userAdapter);

        if (getIntent().getExtras() != null) {
            title = getIntent().getStringExtra("group");
            getSupportActionBar().setTitle("Bloodgroup" + " " + title);

            if(title.equals("Compatible with me")) {
                getCompatibleUsers();
                getSupportActionBar().setTitle("Compatible with me");
            }else {
                readUsers();
            }

        }
    }

    private void getCompatibleUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();
                if(type.equals("donor")){
                    result = "recipient";
                }else{
                    result = "donor";
                }

                String bloodgroup = snapshot.child("bloodgroup").getValue().toString();


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
                Query query = reference.orderByChild("search").equalTo(result + bloodgroup);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            userList.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void readUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String type = snapshot.child("type").getValue().toString();
                if(type.equals("donor")){
                    result = "recipient";
                }else{
                    result = "donor";
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
                Query query = reference.orderByChild("search").equalTo(result + title);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            userList.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
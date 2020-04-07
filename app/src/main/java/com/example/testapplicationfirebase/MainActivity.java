package com.example.testapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText txtID, txtName, txtAdd, txtConNo;
    Button btnSave, btnShow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Student std;

    private void clearControls() {
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID.findViewById(R.id.editTextid);
        txtName.findViewById(R.id.editTextName);
        txtAdd.findViewById(R.id.editTextAddress);
        txtConNo.findViewById(R.id.editTextNumber);

        btnSave.findViewById(R.id.buttonSave);
        btnDelete.findViewById(R.id.buttonDelete);
        btnShow.findViewById(R.id.buttonShow);
        btnUpdate.findViewById(R.id.buttonUpdate);

        std = new Student();
    }
    @Override
    protected void onResume() {
        super.onResume();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
                try {
                    if (TextUtils.isEmpty(txtID.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter an ID", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtName.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter an Name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txtAdd.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter an Address", Toast.LENGTH_SHORT).show();
                    } else {
                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAdd.getText().toString().trim());
                        std.setConNo(txtConNo.getText().toString().trim());

                        dbRef.push().setValue(std);

                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txtID.setText(dataSnapshot.child("ID").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                            txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());

                        } else
                            Toast.makeText(getApplicationContext(), "No Sourse to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
                        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("Std1")) {
                                    try {
                                        std.setID(txtID.getText().toString().trim());
                                        std.setName(txtName.getText().toString().trim());
                                        std.setAddress(txtAdd.getText().toString().trim());
                                        std.setConNo(txtConNo.getText().toString().trim());

                                        dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                                        dbRef.setValue(std);
                                        clearControls();

                                        Toast.makeText(getApplicationContext(), "DATA UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                    } catch (NumberFormatException e) {
                                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                    Toast.makeText(getApplicationContext(), "No source to update", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("Std1")) {
                                    dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                                    dbRef.removeValue();
                                    clearControls();
                                    Toast.makeText(getApplicationContext(), "Data Successfully Deleted ", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getApplicationContext(), "No source to Delete", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

        }
    }


package com.example.projectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TampilDataGuru extends AppCompatActivity implements PegawaiAdaptor.datalistener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data_guru);

        recyclerView = findViewById(R.id.masterdataguru);
        MyRecyclerView();
        GetData();
    }

    private DatabaseReference reference;
    private ArrayList<Pegawai> dataKaryawans;

    private void GetData() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("AbsenPegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataKaryawans = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Pegawai karyawan = snapshot.getValue(Pegawai.class);
                    karyawan.setKey(snapshot.getKey());
                    dataKaryawans.add(karyawan);
                }
                adapter = new PegawaiAdaptor(dataKaryawans, TampilDataGuru.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void MyRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDeleteData(Pegawai data, int position) {
        if (reference != null) {
            reference.child("AbsenPegawai")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(TampilDataGuru.this, "Data Berhasil Dihapus",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }}
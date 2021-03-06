package com.example.projectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormPegawai extends AppCompatActivity {

    EditText xnopeg,xnmapeg,xjabatan;
    Button tblSimpan;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Absen");
    Pegawai pegawai = new Pegawai();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pegawai);

        xnopeg=findViewById(R.id.no_pegawai);
        xnmapeg=findViewById(R.id.nama_pegawai);
        xjabatan=findViewById(R.id.jabatan);
        tblSimpan=findViewById(R.id.tombolsimpan);

        tblSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegawai.setNomor_peg(xnopeg.getText().toString().trim());
                pegawai.setNama_peg(xnmapeg.getText().toString().trim());
                pegawai.setJabatan(xjabatan.getText().toString().trim());

                dbref.push().setValue(pegawai);
            }
        });
    }
    public void kesana(View view) {
        Intent intent=new Intent(FormPegawai.this,TampilDataGuru.class);
        startActivity(intent);
    }
}
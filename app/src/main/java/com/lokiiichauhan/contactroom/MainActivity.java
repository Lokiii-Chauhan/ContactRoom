package com.lokiiichauhan.contactroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lokiiichauhan.contactroom.adapter.RecyclerViewAdapter;
import com.lokiiichauhan.contactroom.model.Contact;
import com.lokiiichauhan.contactroom.model.ContactViewModel;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements RecyclerViewAdapter.OnContactClickListener {

    public static final String CONTACT_ID = "contact_id";
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    private ContactViewModel contactViewModel;

    private LiveData<List<Contact>> contactList;

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.add_contact_fab);
        recyclerView = findViewById(R.id.recycler_view);


        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewContact.class);
            startActivityForResult(intent,
                    NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });

        contactViewModel = new ViewModelProvider.
                AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this,
                contacts -> {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerViewAdapter = new RecyclerViewAdapter(contacts, this, this);
                    recyclerView.setAdapter(recyclerViewAdapter);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            assert data != null;
           Contact contact = new Contact(data.getStringExtra(NewContact.NAME_REPLY), data.getStringExtra(NewContact.OCCUPATION_REPLY));
           ContactViewModel.insert(contact);

        }
    }

    @Override
    public void onContactClick(int position) {
        Log.d(TAG, "onContactClick: " + position);
        Contact contact = Objects.requireNonNull(contactViewModel.allContacts.getValue()).get(position);
        contactViewModel.get(contact.getId());
        Intent intent = new Intent(this, NewContact.class);
        intent.putExtra(CONTACT_ID, contact.getId());
        startActivity(intent);
    }
}
package com.lokiiichauhan.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lokiiichauhan.contactroom.model.Contact;
import com.lokiiichauhan.contactroom.model.ContactViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        contactViewModel = new ViewModelProvider.
                AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this,
                contacts -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Contact contact: contacts) {

                        stringBuilder.append(" - ")
                                .append(contact.getName())
                                .append(" ")
                                .append(contact.getOccupation());
                        textView.setText(stringBuilder.toString());
                    }
        });

    }
}
package com.lokiiichauhan.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lokiiichauhan.contactroom.model.Contact;
import com.lokiiichauhan.contactroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION_REPLY = "occupation_reply";
    private EditText enterName, enterOccupation;
    private Button saveInfoButton;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        saveInfoButton = findViewById(R.id.save_button);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this.getApplication()).
                create(ContactViewModel.class);

        saveInfoButton.setOnClickListener(v -> {

            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterName.getText().toString()) &&
                    !TextUtils.isEmpty(enterOccupation.getText().toString())){

                String name = enterName.getText().toString();
                String occupation = enterOccupation.getText().toString();
                Contact contact = new Contact(enterName.getText().toString(),
                        enterOccupation.getText().toString());

                replyIntent.putExtra(NAME_REPLY,name);
                replyIntent.putExtra(OCCUPATION_REPLY, occupation);
                setResult(RESULT_OK, replyIntent);


               // ContactViewModel.insert(contact);
        }else {
                setResult(RESULT_CANCELED,replyIntent);
            }
            finish();
        });

    }
}
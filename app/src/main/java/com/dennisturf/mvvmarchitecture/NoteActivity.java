package com.dennisturf.mvvmarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.dennisturf.mvvmarchitecture.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.dennisturf.mvvmarchitecture.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.dennisturf.mvvmarchitecture.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.dennisturf.mvvmarchitecture.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        if (getSupportActionBar() != null) {
            Intent intent = getIntent();

            if (intent.hasExtra(EXTRA_ID)) {

                setTitle("Edit Note");
                editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

            }
            else {

                setTitle("Add Note");

            }
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int priority = numberPickerPriority.getValue();


        if ( TextUtils.isEmpty(title) || TextUtils.isEmpty(description) ) {
            Toast.makeText(this, "Field can't be empty!", Toast.LENGTH_SHORT).show();

        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) { // only will trigger if its to update some value, using the ID
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        }

    }
}

package com.xstudioo.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle, nContent;
    TextView mNumber;
    Calendar c;
    String todaysDate;
    String currentTime;
    String mTitle;
    int mNumberTimesEdited;
    long nId;

    TextWatcher mTxtHandler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            getSupportActionBar().setTitle(mTitle);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() != 0) {
                getSupportActionBar().setTitle(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }


    private String pad(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            mNumberTimesEdited++;
            Note note = new Note(nId, nTitle.getText().toString(), nContent.getText().toString(), todaysDate, currentTime, mNumberTimesEdited);
            Log.d("EDITED", "edited: before saving id -> " + note.getId());
            SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
            long id = sDB.editNote(note);
            Log.d("EDITED", "EDIT: id " + id);
            goToMain();
            Toast.makeText(this, "Nota editada.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID", 0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);

        this.mTitle = note.getTitle();
        String content = note.getContent();
        nTitle = findViewById(R.id.idEtNoteTitle);
        nContent = findViewById(R.id.idEtNoteDetails);
        nTitle.addTextChangedListener(mTxtHandler);


        mNumberTimesEdited = note.getNumberTimesEdited();
        nTitle.setText(mTitle);
        nContent.setText(content);

        // set current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: " + todaysDate);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));
        Log.d("TIME", "Time: " + currentTime);
    }
}

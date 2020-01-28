package com.xstudioo.noteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mNoteTitle, mNoteDetails;
    Calendar mCalendar;
    String mTodaysDate;
    String mCurrentTime;
    int mNumberTimesEdited;

    TextWatcher mTxtHandler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() != 0){
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
        setContentView(R.layout.activity_add_note);
        init();
    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            if(mNoteTitle.getText().length() != 0){
                Note note = new Note(mNoteTitle.getText().toString(), mNoteDetails.getText().toString(), mTodaysDate, mCurrentTime,mNumberTimesEdited);
                SimpleDatabase sDB = new SimpleDatabase(this);
                long id = sDB.addNote(note);
                Note check = sDB.getNote(id);
                Log.d("inserted", "Nota: "+ id + " -> Título:" + check.getTitle()+" Data: "+ check.getDate());
                onBackPressed();

                Toast.makeText(this, "Nota Guardada", Toast.LENGTH_SHORT).show();
            }else {
                mNoteTitle.setError("Titlulo não pode estar vazio");
            }

        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void init(){
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nova Nota");

        mNoteDetails = findViewById(R.id.idEtNoteDetails);
        mNoteTitle = findViewById(R.id.idEtNoteTitle);

        mNoteTitle.addTextChangedListener(mTxtHandler);

        mNumberTimesEdited = 0;
        mCalendar = Calendar.getInstance();
        mTodaysDate = mCalendar.get(Calendar.YEAR)+"/"+(mCalendar.get(Calendar.MONTH)+1)+"/"+ mCalendar.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Data: "+ mTodaysDate);
        mCurrentTime = pad(mCalendar.get(Calendar.HOUR_OF_DAY))+":"+pad(mCalendar.get(Calendar.MINUTE));
        Log.d("TIME", "Hora: "+ mCurrentTime);

    }
}

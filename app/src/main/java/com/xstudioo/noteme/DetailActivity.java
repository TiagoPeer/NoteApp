package com.xstudioo.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    long id;
    TextView details;
    TextView number;
    String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit){
            Intent i = new Intent(this, EditActivity.class);
            i.putExtra("ID",id);
            startActivity(i);

        }else if(item.getItemId() == R.id.email){

            String subject = mTitle;
            String message = details.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an email client"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        Intent i = getIntent();
        id = i.getLongExtra("ID",0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        details = findViewById(R.id.idTvNoteDesc);
        details.setText(note.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());
        mTitle = note.getTitle();
        number = findViewById(R.id.idTvNumberTimesEdited);
        number.setText(String.valueOf(note.getNumberTimesEdited()));

        FloatingActionButton fab = findViewById(R.id.idBtnDelete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDatabase db = new SimpleDatabase(getApplicationContext());
                db.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Nota apagada",Toast.LENGTH_SHORT).show();
                goToMain();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

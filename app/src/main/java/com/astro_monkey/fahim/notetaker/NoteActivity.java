package com.astro_monkey.fahim.notetaker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.astro_monkey.notetaker.db.NoteDataSource;
import com.astro_monkey.notetaker.model_class.Notes;


public class NoteActivity extends ActionBarActivity {

    private NoteDataSource dataSource;

    private boolean is_adding_new_note;

    private EditText et_title, et_note;

    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        et_title = (EditText) findViewById(R.id.et_note_title);
        et_note = (EditText) findViewById(R.id.et_note);

        dataSource = new NoteDataSource(this);
        dataSource.open();

        is_adding_new_note = getIntent().getExtras().getBoolean("is_adding_new_note");

        if(is_adding_new_note == false){
            id = getIntent().getExtras().getLong("id");
            Notes n = dataSource.find(id);
            et_title.setText(n.getTitle());
            et_note.setText(n.getNote());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int item_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item_id == R.id.menu_note_done) {
            if(!et_note.getText().toString().isEmpty()){
                Notes n = new Notes();
                n.setTitle(et_title.getText().toString());
                n.setNote(et_note.getText().toString());
                if(is_adding_new_note == true) {
                    dataSource.insert(n);
                }

                else {
                    n.setId(id);
                    dataSource.update(n);
                }

                finish();
            }
            else
                Toast.makeText(NoteActivity.this,"The Note is empty !!!", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

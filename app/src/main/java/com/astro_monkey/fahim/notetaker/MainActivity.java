package com.astro_monkey.fahim.notetaker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.astro_monkey.notetaker.db.NoteDataSource;
import com.astro_monkey.notetaker.model_class.Notes;

import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    NoteDataSource dataSource;

    List<Notes> notes;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_main);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        dataSource = new NoteDataSource(this);
        dataSource.open();

        view_list();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        view_list();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int item_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item_id == R.id.menu_add_new) {
            Intent intent = new Intent(MainActivity.this,NoteActivity.class);
            intent.putExtra("is_adding_new_note",true);
            startActivity(intent);

            return true;
        }

        else if(item_id == R.id.menu_delete_all){
            AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
            alert_builder.setMessage("Delete all notes ?");
            alert_builder.setCancelable(true);
            alert_builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                   dataSource.deleteAll();

                    view_list();
                }
            });
            alert_builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });
            AlertDialog alert = alert_builder.create();
            alert.show();

            return true;
        }


        else if(item_id == R.id.menu_about_dev){
            Intent intent = new Intent(MainActivity.this,About_dev.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void view_list(){
        notes = dataSource.findAll();

        lv.setAdapter(new ArrayAdapter<Notes>(MainActivity.this,android.R.layout.simple_list_item_1,notes));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,NoteActivity.class);
        intent.putExtra("is_adding_new_note",false);
        intent.putExtra("id",notes.get((int)id).getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(MainActivity.this);
        alert_builder.setMessage("Delete " + notes.get((int)id).getTitle() + " ?");
        alert_builder.setCancelable(true);
        alert_builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dataSource.delete(notes.get((int)id).getId());

                view_list();
            }
        });
        alert_builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        AlertDialog alert = alert_builder.create();
        alert.show();

        return true;
    }
}

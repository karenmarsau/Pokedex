package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static ImageView imgPokShiny;

    public static int counter;
    public static ArrayList<String> pokemonList = new ArrayList<String>();
    public int count = 0;
    public boolean searchByType = false;
    public static int pokemonId = 0;
    public static String urlShiny = "";


    public static ImageView [] imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgPokShiny = findViewById(R.id.imgPokShiny);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

        showResult("1");

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTxtSearch();
            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchByType();
            }
        });

        ImageButton btnRight = findViewById(R.id.imageBtnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(searchByType) {
                    count++;
                    showResult(pokemonList.get(count));
                } else {
                    pokemonId++;
                    showResult(String.valueOf(pokemonId));
                }
            }
        });

        ImageButton btnLeft = findViewById(R.id.imageBtnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(searchByType) {
                    count--;
                    if(count<0) {
                        count = 0;
                    }
                    showResult(pokemonList.get(count));
                } else {
                    pokemonId--;
                    if(pokemonId<=0) {
                        pokemonId = 1;
                    }
                    showResult(String.valueOf(pokemonId));
                }
            }
        });

    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                showResult(pokSearch);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void showResult(String pokemon){

        FetchData process = new FetchData(pokemon);
        process.execute();

    }

    private void searchByType(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a type")
                .setItems(R.array.pokTypes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(which);
                        String type = String.valueOf(checkedItem).toLowerCase();

                        FetchDataType process = new FetchDataType(type);
                        process.execute();

                        searchByType = true;
                    }
                });

        builder.show();
    }

}


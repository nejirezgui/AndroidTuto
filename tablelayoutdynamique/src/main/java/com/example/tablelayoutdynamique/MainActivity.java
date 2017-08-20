package com.example.tablelayoutdynamique;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout table = (TableLayout) findViewById(R.id.table);

        TableRow Entete = new TableRow(this);

        Entete.setBackgroundColor(Color.BLUE);
        Entete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


        TextView[][] tableTextView = new TextView[50][8];
        for (int l = 0; l < 50; l++) {
            TableRow ligne = new TableRow(this);


            if (l % 2 == 0)
                ligne.setBackgroundColor(Color.GREEN);
            else

                ligne.setBackgroundColor(Color.WHITE);
            ligne.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int i = 0; i < 8; i++) {

                TextView txt = new TextView(this);

                txt.setTextSize(20.0f);
                tableTextView[l][i] = txt;

                txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));



                if (l % 2 == 0)
                    txt.setTextColor(Color.WHITE);
                else

                    txt.setTextColor(Color.BLACK);
                if (i == 7) {
                    txt.setText("  ligne  " + l);
                    txt.setTextColor(Color.RED);

                } else
                    txt.setText("  colonne  " + i);


                txt.setGravity(Gravity.CENTER);
                ligne.addView(txt);
            }
            table.addView(ligne);

        }

        //acces

        tableTextView[0][0].setTextColor(Color.RED);
        tableTextView[0][0].setText("Je suis la ");

    }
}

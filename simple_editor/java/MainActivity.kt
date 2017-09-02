package com.example.win.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*  // kotlin extensions

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ed_content.requestFocus()


        var filename: String = filesDir.absolutePath + "/test.txt"


        txt_filename.setText("FILE : " + filename)



        btn_save.setOnClickListener(View.OnClickListener {

            if (!ed_content.text.isEmpty()) {

                saveFile(filename, ed_content.text.toString())


                Toast.makeText(this, "file is saved with success", Toast.LENGTH_SHORT).show()


            } else {

                Toast.makeText(this, "content is empty !!!!", Toast.LENGTH_SHORT).show()
            }

        })
        btn_new.setOnClickListener(View.OnClickListener {

            ed_content.setText("")

        })



        btn_open.setOnClickListener(View.OnClickListener {


            var str: String = openFile(filename)


            ed_content.setText(str)

        })

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("edit")
        }
    }

    external fun saveFile(str: String, content: String): Unit
    external fun openFile(str: String): String


}

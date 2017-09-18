import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_genPDF.setOnClickListener(
                {

                    // verifier le contenu des edittextes  :)
                    createPdf(txt_nom.text.toString(), txt_prenom.text.toString(), txt_adr.text.toString())
                }

        )

    }

    private fun createPdf(nom: String, Prenom: String, Adresse: String) {
        // create a new document
        val document = PdfDocument()

        // crate a page description
        var pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(350, 400, 1).create()

        // start a page
        var page: PdfDocument.Page = document.startPage(pageInfo)

        var canvas = page.canvas

        var paint = Paint()




        paint.color = Color.BLUE

        paint.textSize = 25f
        canvas.drawText("Android Tuto القائمة  ", 50f, 50f, paint)
        // finish the page
        paint.textSize = 20f
        paint.color = Color.BLACK
        canvas.drawText("Nom     :" + nom, 100f, 150f, paint)
        canvas.drawText("Prenom  :" + Prenom, 100f, 175f, paint)

        canvas.drawText("Adresse :" + Adresse, 100f, 195f, paint)






        document.finishPage(page)

        // Create Page 2
        pageInfo = PdfDocument.PageInfo.Builder(350, 400, 2).create()
        page = document.startPage(pageInfo)
        canvas = page.canvas
        paint = Paint()
        paint.color = Color.BLUE

        canvas.drawText("عرض الرسومات :", 50f, 50f, paint)

        val bmp: Bitmap = BitmapFactory.decodeFile("/sdcard/kotlinlogo.png");

        val scaledbmp:Bitmap =Bitmap.createScaledBitmap(bmp,70,100,true)
        canvas.drawBitmap(bmp ,50f,50f,paint)

        canvas.drawBitmap(scaledbmp ,100f,300f,paint)


        canvas.drawCircle(50f, 200f, 100f, paint)
        paint.textSize=25f
        paint.color=Color.WHITE
        canvas.drawText("I Love kotlin", 10f, 200f, paint)


        document.finishPage(page)

        // write the document content
        val targetPdf = "/sdcard/demopdf.pdf"
        val filePath = File(targetPdf)
        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show()
        }

        // close the document
        document.close()
    }

}

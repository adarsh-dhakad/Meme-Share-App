package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //val textView = findViewById<TextView>(R.id.textView)

    var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       loadMeme()

//textView.text= "post link is this $"
//   val  sharebutton= findViewById<Button>(R.id.sharebutton)
//        sharebutton.setOnClickListener(View.OnClickListener {
//
//
//        })
    }

private  fun loadMeme(){
//val textview = findViewById<TextView>(R.id.text)
// Instantiate the RequestQueue.
    progressbar.visibility=View.VISIBLE

    val url ="https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.

    val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
        { response ->
            // Display the first 500 characters of the response string.
       //   textView.text = "Response is: ${response.substring(0, 500)}"
           val postLink:String? = response.getString("postLink")
        //    postlinkText.text= "post link is this $postLink"
        currentImageUrl = response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    progressbar.visibility=View.GONE
                    return false
                }

            }).into(imageview)
           // Log.d("success request", response.substring(0, 500))
        },
        {  // textview.text = "That didn't work!"



        })

// Add the request to the RequestQueue.
   MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)

  }
    fun nextMeme(view: View){
        loadMeme()
    }
    fun shareMeme(view: View){
      //  val toast=Toast.makeText(this,"this is a share Button",Toast.LENGTH_LONG)
     //   toast.show()
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"hay , check out this cool meme i got from reddit $currentImageUrl")
        val chooser=Intent.createChooser(intent,"share this meme using")
        startActivity(chooser)
    }

}
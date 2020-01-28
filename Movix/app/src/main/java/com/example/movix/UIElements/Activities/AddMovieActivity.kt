package com.example.movix.UIElements.Activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movix.R
import android.Manifest.permission
import android.app.Activity
import android.content.Intent

import com.karumi.dexter.Dexter


import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.movix.AppLogic.Enums.MovieGenre
import com.example.movix.AppLogic.Enums.MovieStatus
import com.example.movix.AppLogic.Enums.MovieType
import com.example.movix.AppLogic.Models.Movie
import com.example.movix.Database.DBContext
import com.example.movix.Database.MoviesTable


import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.movie_item.*
import kotlinx.android.synthetic.main.movie_item.tvMovieName


class AddMovieActivity : AppCompatActivity() {
    var imageURI = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        g



        Dexter.withActivity(this).withPermission(permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {

//****************************************************************************************

                    imgMoviee.setOnClickListener {
                        val i =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i, 100)
                    }
                    val genres = mutableListOf<String>(
                        MovieGenre.Romance.genre,
                        MovieGenre.Action.genre,
                        MovieGenre.Fantasia.genre,
                        MovieGenre.Historical.genre,
                        MovieGenre.Horror.genre,
                        MovieGenre.Comedy.genre,
                        MovieGenre.Documentary.genre,
                        MovieGenre.ScienceFiction.genre
                        )
                    spGenre.adapter = ArrayAdapter<String>(this@AddMovieActivity,android.R.layout.simple_list_item_1,genres)
                    val types = mutableListOf<String>(
                        MovieType.english.type,
                        MovieType.arabic.type,
                        MovieType.hindi.type
                    )
                    spType.adapter = ArrayAdapter<String>(this@AddMovieActivity,android.R.layout.simple_list_item_1,types)

                    btnAddMovie.setOnClickListener {
                        if (etMovieNameAdd.text.isNotEmpty() && etRatingAdd.text.isNotEmpty() && imageURI.isNotEmpty()){
                            val name = etMovieNameAdd.text.toString()
                            val rating = etRatingAdd.text.toString().toDouble()
                            val genre = MovieGenre.getGenre(spGenre.selectedItem.toString())
                            val type = MovieType.getType(spType.selectedItem.toString())
                            Log.e("muath",genre.genre+"  "+type.type)
                            Log.e("muath",imageURI)
                            val movie = Movie(name,rating,type,genre,MovieStatus.available,imageURI)
                          val result =   DBContext(this@AddMovieActivity,MoviesTable(),movie).add()
                            if (result){
                                Toast.makeText(this@AddMovieActivity,"Movie $name is added successfully",Toast.LENGTH_LONG).show()
                                val i = Intent(this@AddMovieActivity,HomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }
                    }
//****************************************************************************************
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    finish()

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?, token:
                    PermissionToken?
                ) {
                    token?.continuePermissionRequest()


                }
            }).check()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            imageURI = data!!.data.toString()

            imgMoviee.setImageURI(data.data)
        }
    }
}

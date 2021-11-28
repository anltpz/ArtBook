package com.example.artbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artbook.databinding.ActivityDetailsBinding
import com.example.artbook.databinding.ActivityMainBinding
import java.lang.Exception
import java.security.cert.Extension

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var artList: ArrayList<Art>
    private lateinit var artAdapter: ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        artList = ArrayList<Art>()
        artAdapter = ArtAdapter(artList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = artAdapter



        try {
            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM arts", null)

            val artNameIndex = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")
            while (cursor.moveToNext()) {
                val name = cursor.getString(artNameIndex)
                val id = cursor.getInt(idIx)
                val art = Art(name, id)
                artList.add(art)
            }
            artAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //inflater xml ile kod birbirine baglamamızı saglar
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.art_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_art_item) {
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            intent.putExtra("info","new")

            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)

    }
}
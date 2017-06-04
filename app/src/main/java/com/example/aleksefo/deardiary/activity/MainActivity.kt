package com.example.aleksefo.deardiary.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.aleksefo.deardiary.R
import com.example.aleksefo.deardiary.R.id.toolbar
import com.example.aleksefo.deardiary.adapters.RecAdapter
import com.example.aleksefo.deardiary.model.Entry
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    private var realm: Realm? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: RecAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val addTaskIntent = Intent(this@MainActivity, EditActivity::class.java)
            startActivity(addTaskIntent)
        }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        realm = Realm.getDefaultInstance()
        recyclerView = findViewById(R.id.recycler) as RecyclerView
        adapter = RecAdapter(realm!!.where(Entry::class.java).findAll(), this)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_bydate) {
            adapter = RecAdapter(realm!!.where(Entry::class.java).findAll().sort("date"), this)
            recyclerView!!.adapter = adapter
        }
        if (id == R.id.action_bytitle) {
            adapter = RecAdapter(realm!!.where(Entry::class.java).findAll().sort("title"), this)
            recyclerView!!.adapter = adapter
        }
        return super.onOptionsItemSelected(item)
    }

    //to prevent memory leak
    public override fun onDestroy() {
        super.onDestroy()
        realm!!.close()
    }

}

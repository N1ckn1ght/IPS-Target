package org.altbeacon.beaconreference

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private lateinit var tvTables: TextView
    private lateinit var btGen: Button
    private lateinit var btGet: Button

    private lateinit var beacons: MutableList<Int>
    private lateinit var tables: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.input)
        tvTables = findViewById(R.id.tables)
        btGen = findViewById(R.id.gen)
        btGet = findViewById(R.id.get)

        beacons = mutableListOf()
    }

    fun onAddClick(view: View) {
        val id = etInput.text.toString().toInt()
        Log.d("d/main", "${id}")
        if (beacons.contains(id)) {
            Toast.makeText(this@MainActivity, "Duplicate id!", Toast.LENGTH_SHORT).show()
        }
        else {
            Log.d("d/main", "else")
            beacons.add(id)
            etInput.setText("")
            tvTables.text = tvTables.text.toString() + "${id}\n"

            btGen.isEnabled = true
            btGen.isClickable = true
        }
    }

    fun onGenClick(view: View) {
        tables = List(beacons.size){ i -> i }.shuffled()
        var text = ""
        for (i in beacons.indices) {
            text += "${beacons[i]}\t:\t${tables[i]}\n"
        }
        tvTables.text = text

        btGet.isEnabled = true
        btGet.isClickable = true
    }

    fun onGetClick(view: View) {
        val rnd: Int = Random().nextInt(tables.size)
        val intent = Intent(this, TraceActivity::class.java)
        intent.putExtra("id", beacons[rnd])
        intent.putExtra("table", tables[rnd])
        startActivity(intent)
    }
}
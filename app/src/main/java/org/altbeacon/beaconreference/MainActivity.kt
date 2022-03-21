package org.altbeacon.beaconreference

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var etInput: EditText
    lateinit var tvTables: TextView
    lateinit var btGen: Button
    lateinit var btGet: Button

    lateinit var beacons: MutableList<Int>
    lateinit var tables: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.input)
        tvTables = findViewById(R.id.tables)
        btGen = findViewById(R.id.gen)
        btGet = findViewById(R.id.get)
    }

    private fun onAddClick(view: View) {
        val id = etInput.text.toString().toInt()
        if (beacons.contains(id)) {
            Toast.makeText(this, "Duplicate id!", Toast.LENGTH_SHORT).show()
        }
        else {
            beacons.add(id)
            etInput.setText("")
            tvTables.text = tvTables.toString() + "${id}\n"

            btGen.isEnabled = true
            btGen.isClickable = true
        }
    }

    private fun onGenClick(view: View) {
        tables = List(beacons.size){ i -> i }.shuffled()
        var text = ""
        for (i in beacons.indices) {
            text += "${beacons[i]}\t:\t${tables[i]}"
        }

        btGet.isEnabled = true
        btGet.isClickable = true
    }

    private fun onGetClick(view: View) {
        val rnd: Int = Random().nextInt(tables.size)
        val intent = Intent(this, TraceActivity::class.java)
        intent.putExtra("id", beacons[rnd])
        intent.putExtra("table", tables[rnd])
        startActivity(intent)
    }
}
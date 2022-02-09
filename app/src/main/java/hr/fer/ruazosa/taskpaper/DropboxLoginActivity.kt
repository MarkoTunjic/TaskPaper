package hr.fer.ruazosa.taskpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_dropbox_login.*

class DropboxLoginActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropbox_login)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.selection,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropboxLoginDropdownButton.adapter = adapter
        val intent = Intent(this, HomeActivity::class.java)
        dropboxLoginDropdownButton.setSelection(0,false)
        dropboxLoginDropdownButton.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
            }
            1 -> {
                val dropboxLoginIntent = Intent(this, DropboxLoginActivity::class.java)
                startActivity(dropboxLoginIntent)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}
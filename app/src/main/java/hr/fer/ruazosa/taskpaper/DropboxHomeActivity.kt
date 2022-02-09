package hr.fer.ruazosa.taskpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hr.fer.ruazosa.taskpaper.adapters.MainTaskDropboxAdapter
import hr.fer.ruazosa.taskpaper.models.DropboxTaskRepository
import hr.fer.ruazosa.taskpaper.models.MainTaskTransfer
import hr.fer.ruazosa.taskpaper.parser.TaskPaperParser
import hr.fer.ruazosa.taskpaper.viewmodels.MainTaskDropboxViewModel
import kotlinx.android.synthetic.main.activity_dropbox_home.*

class DropboxHomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var mainTaskAdapter: MainTaskDropboxAdapter
    lateinit var viewModel: MainTaskDropboxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropbox_home)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.selection,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropboxDropdownButton.adapter = adapter
        dropboxDropdownButton.setSelection(1, false)
        dropboxDropdownButton.onItemSelectedListener = this
        dropboxAddTaskButton.setOnClickListener {
            MainTaskTransfer.position=null
            MainTaskTransfer.mainTask=null
            val addTaskIntent = Intent(this, TaskDetailsDropboxActivity::class.java)
            startActivity(addTaskIntent)
        }

        dropboxListOfTasksView.layoutManager = LinearLayoutManager(applicationContext)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainTaskDropboxViewModel::class.java
            )

        mainTaskAdapter = MainTaskDropboxAdapter(viewModel, applicationContext)
        dropboxListOfTasksView.adapter = mainTaskAdapter

        viewModel.tasksList.observe(this) {
            mainTaskAdapter.notifyDataSetChanged()
        }

        fillTaskRepository()
    }

    private fun fillTaskRepository() {
        DropboxTaskRepository.tasksList.clear()
        val parser = TaskPaperParser()
        val text = "Groceries:\n\t-honey @done @priority(2)\n\t-milk"
        parser.parse(text)
        viewModel.saveTaskToRepository(parser.getLastParsingResult().result)
        viewModel.saveTaskToRepository(parser.getLastParsingResult().result)
        viewModel.saveTaskToRepository(parser.getLastParsingResult().result)
        viewModel.saveTaskToRepository(parser.getLastParsingResult().result)
        viewModel.saveTaskToRepository(parser.getLastParsingResult().result)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMainTaskRepository()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p2 == 0) {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}
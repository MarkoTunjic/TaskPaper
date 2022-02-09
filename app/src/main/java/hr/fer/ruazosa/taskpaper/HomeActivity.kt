package hr.fer.ruazosa.taskpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.fer.ruazosa.taskpaper.adapters.MainTaskAdapter
import hr.fer.ruazosa.taskpaper.models.LocalTasksRepository
import hr.fer.ruazosa.taskpaper.models.MainTaskTransfer
import hr.fer.ruazosa.taskpaper.parser.TaskPaperParser
import hr.fer.ruazosa.taskpaper.viewmodels.MainTaskViewModel

class HomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var mainTaskAdapter: MainTaskAdapter
    lateinit var viewModel: MainTaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.selection,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mainScreenDropdownButton.adapter = adapter
        mainScreenDropdownButton.setSelection(0, false)
        mainScreenDropdownButton.onItemSelectedListener = this
        mainScreenAddTaskButton.setOnClickListener {
            val addTaskIntent = Intent(this, TaskDetailsActivity::class.java)
            MainTaskTransfer.mainTask = null
            MainTaskTransfer.position = null
            startActivity(addTaskIntent)
        }

        mainScreenListOfTasks.layoutManager = LinearLayoutManager(applicationContext)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainTaskViewModel::class.java
            )

        mainTaskAdapter = MainTaskAdapter(viewModel, applicationContext)
        mainScreenListOfTasks.adapter = mainTaskAdapter

        viewModel.tasksList.observe(this) {
            mainTaskAdapter.notifyDataSetChanged()
        }

        fillTaskRepository()
    }

    private fun fillTaskRepository() {
        LocalTasksRepository.tasksList.clear()
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
        if (p2 == 1) {
            val dropBoxHomeIntent = Intent(this, DropboxHomeActivity::class.java)
            startActivity(dropBoxHomeIntent)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
package hr.fer.ruazosa.taskpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.fer.ruazosa.taskpaper.adapters.SubTaskAdapter
import hr.fer.ruazosa.taskpaper.models.MainTaskTransfer
import hr.fer.ruazosa.taskpaper.models.SubtasksRepository
import hr.fer.ruazosa.taskpaper.viewmodels.SubTaskViewModel
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity() {

    lateinit var subTaskAdapter: SubTaskAdapter
    lateinit var viewModel: SubTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        detailsScreenBackButton.setOnClickListener {
            var dialog = CustomDialog(this)
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_corners)
            dialog.show()
            dialog.submitButton.setOnClickListener {
                finish()
            }
            dialog.cancelButton.setOnClickListener {
                finish()
            }
        }

        SubtasksRepository.subTasksList.clear()
        if (MainTaskTransfer.mainTask != null) {
            SubtasksRepository.subTasksList.clear()
            SubtasksRepository.subTasksList.addAll(MainTaskTransfer.mainTask!!.subtasks)
            detailsScreenTaskNameText.setText(MainTaskTransfer.mainTask!!.name)
        }

        detailsScreenListOfSubtasks.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.main_task_decorator
            )!!
        )
        detailsScreenListOfSubtasks.addItemDecoration(decorator)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                SubTaskViewModel::class.java
            )

        subTaskAdapter = SubTaskAdapter(viewModel, applicationContext)
        detailsScreenListOfSubtasks.adapter = subTaskAdapter

        viewModel.subTasksList.observe(this) {
            subTaskAdapter.notifyDataSetChanged()
        }

        detailsScreenSaveButton.setOnClickListener {
            var dialog = CustomDialog(this)
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_corners)
            dialog.show()
            dialog.submitButton.setOnClickListener {

            }
            dialog.cancelButton.setOnClickListener {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubTaskRepository()
    }
}
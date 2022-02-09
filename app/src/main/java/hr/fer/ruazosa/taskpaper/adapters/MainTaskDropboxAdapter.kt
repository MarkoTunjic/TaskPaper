package hr.fer.ruazosa.taskpaper.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import hr.fer.ruazosa.taskpaper.R
import hr.fer.ruazosa.taskpaper.TaskDetailsDropboxActivity
import hr.fer.ruazosa.taskpaper.models.MainTaskTransfer
import hr.fer.ruazosa.taskpaper.viewmodels.MainTaskDropboxViewModel

class MainTaskDropboxAdapter(listOfMainTasksViewModel: MainTaskDropboxViewModel, var parentContext:Context) :
    RecyclerView.Adapter<MainTaskDropboxAdapter.ViewHolder>() {
    var listOfMainTasks: MainTaskDropboxViewModel = listOfMainTasksViewModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var taskName: TextView? = null
        var downloadButton: AppCompatImageButton? = null
        var deleteButton: AppCompatImageButton? = null
        init {
            taskName = itemView.findViewById(R.id.dropboxMainTaskElementNameText)
            downloadButton = itemView.findViewById(R.id.dropboxMainTaskElementDownloadButton)
            deleteButton = itemView.findViewById(R.id.dropboxMainTaskElementDeleteButton)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        parentContext=parent.context
        val inflater = LayoutInflater.from(context)
        val mainTaskListElement = inflater.inflate(R.layout.main_task_dropbox_element, parent, false)
        return ViewHolder(mainTaskListElement)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listOfMainTasks != null) {
            holder.taskName?.text = listOfMainTasks.tasksList.value!![position].name
            holder.itemView.setOnClickListener {
                var detailsIntent= Intent(parentContext,TaskDetailsDropboxActivity::class.java)
                MainTaskTransfer.mainTask=listOfMainTasks.tasksList.value!![position]
                MainTaskTransfer.position=position
                parentContext.startActivity(detailsIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        if (listOfMainTasks.tasksList.value != null) {
            return listOfMainTasks.tasksList.value!!.count()
        }
        return 0
    }

}
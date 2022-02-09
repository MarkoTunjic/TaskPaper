package hr.fer.ruazosa.taskpaper.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import hr.fer.ruazosa.taskpaper.R
import hr.fer.ruazosa.taskpaper.utils.Utils
import hr.fer.ruazosa.taskpaper.viewmodels.SubTaskViewModel

class SubTaskAdapter(listOfSubTasksViewModel: SubTaskViewModel,var parentContext:Context) :
    RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {

    var listOfSubTasks: SubTaskViewModel = listOfSubTasksViewModel


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subTaskName: TextView? = null
        var firstButton: AppCompatImageButton? = null
        var deleteButton: AppCompatImageButton? = null

        init {
            subTaskName = itemView.findViewById(R.id.subtaskElementNameText)
            firstButton = itemView.findViewById(R.id.subtaskElementDoneButton)
            deleteButton = itemView.findViewById(R.id.subtaskElementDeleteButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val subTaskListElement = inflater.inflate(R.layout.sub_task_element, parent, false)
        return ViewHolder(subTaskListElement)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listOfSubTasks != null) {
            holder.subTaskName?.text = listOfSubTasks.subTasksList.value!![position].text
            val dp7=Utils.getPixelsFromDp(7,parentContext)
            val dp5=Utils.getPixelsFromDp(5,parentContext)
            val dp9=Utils.getPixelsFromDp(8,parentContext)
            if (listOfSubTasks.subTasksList.value!![position].completed){
                holder.subTaskName?.paintFlags =
                    Paint.STRIKE_THRU_TEXT_FLAG or holder.subTaskName?.paintFlags!!
                holder.firstButton?.setImageResource(R.drawable.reload)
                holder.firstButton?.setBackgroundResource(R.drawable.orange_rounded_corners)
                holder.firstButton?.setPadding(dp7,dp5,dp7,dp5)
            }else{
                holder.subTaskName?.paintFlags=0
                holder.firstButton?.setImageResource(R.drawable.tick)
                holder.firstButton?.setBackgroundResource(R.drawable.green_rounded_corners)
                holder.firstButton?.setPadding(dp7,dp9,dp7,dp9)
            }
        }
    }

    override fun getItemCount(): Int {
        if (listOfSubTasks.subTasksList.value != null) {
            return listOfSubTasks.subTasksList.value!!.count()
        }
        return 0
    }
}
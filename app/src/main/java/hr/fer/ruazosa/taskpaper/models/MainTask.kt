package hr.fer.ruazosa.taskpaper.models

import java.io.Serializable
import java.lang.StringBuilder

class MainTask(var name:String,var subtasks:MutableList<SubTask>):Serializable{

    fun addSubTask(subtask:SubTask){
        subtasks.add(subtask)
    }

    override fun toString(): String {
        var builder= StringBuilder()
        builder.append(this.name).append(":")
        for(subTask in this.subtasks){
            builder.append("\n\t-").append(subTask.text)
            if(subTask.completed)
                builder.append(" @done")
            if(subTask.priority>0)
                builder.append(String.format(" @priority(%d)",subTask.priority))
        }
        return builder.toString()
    }
}
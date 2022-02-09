package hr.fer.ruazosa.taskpaper.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.fer.ruazosa.taskpaper.models.LocalTasksRepository
import hr.fer.ruazosa.taskpaper.models.MainTask

class MainTaskViewModel: ViewModel() {
    var tasksList = MutableLiveData<List<MainTask>>()

    fun getMainTaskRepository() {
        tasksList.value = LocalTasksRepository.tasksList
    }

    fun saveTaskToRepository(task: MainTask) {
        LocalTasksRepository.tasksList.add(task)
        getMainTaskRepository()
    }

    fun deleteTaskFromRepository(position:Int) {
        LocalTasksRepository.tasksList.removeAt(position)
        getMainTaskRepository()
    }

    fun edited(){
        getMainTaskRepository()
    }
}
package hr.fer.ruazosa.taskpaper.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.fer.ruazosa.taskpaper.models.DropboxTaskRepository
import hr.fer.ruazosa.taskpaper.models.MainTask

class MainTaskDropboxViewModel: ViewModel() {
    var tasksList = MutableLiveData<List<MainTask>>()

    fun getMainTaskRepository() {
        tasksList.value = DropboxTaskRepository.tasksList
    }

    fun saveTaskToRepository(task: MainTask) {
        DropboxTaskRepository.tasksList.add(task)
        getMainTaskRepository()
    }

    fun deleteTaskFromRepository(position:Int) {
        DropboxTaskRepository.tasksList.removeAt(position)
        getMainTaskRepository()
    }

    fun edited(){
        getMainTaskRepository()
    }
}
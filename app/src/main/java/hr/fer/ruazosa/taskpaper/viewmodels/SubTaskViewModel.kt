package hr.fer.ruazosa.taskpaper.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.fer.ruazosa.taskpaper.models.LocalTasksRepository
import hr.fer.ruazosa.taskpaper.models.SubTask
import hr.fer.ruazosa.taskpaper.models.SubtasksRepository

class SubTaskViewModel : ViewModel() {
    var subTasksList = MutableLiveData<List<SubTask>>()

    fun getSubTaskRepository() {
        subTasksList.value = SubtasksRepository.subTasksList
    }

    fun saveSubTaskToRepository(subTask: SubTask) {
        SubtasksRepository.subTasksList.add(subTask)
        getSubTaskRepository()
    }

    fun deleteSubTaskFromRepository(position: Int) {
        SubtasksRepository.subTasksList.removeAt(position)
        getSubTaskRepository()
    }

    fun edited() {
        getSubTaskRepository()
    }
}
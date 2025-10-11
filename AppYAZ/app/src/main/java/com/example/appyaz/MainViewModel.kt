package com.example.appyaz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appyaz.db.UazRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository: UazRepository = App.repository

    val models: Flow<List<ItemModelUAZ>> = repository.items

    fun addModel() {
        viewModelScope.launch {
            val newModel = ItemModelUAZ(
                id = 0,
                name = "Новая модель",
                cost = "Цена модели",
                description = "Описание модели"
            )
            repository.create(newModel)
        }
    }

    fun editModel(model: ItemModelUAZ) {
        viewModelScope.launch {
            repository.update(model)
        }
    }

    fun createModel(model: ItemModelUAZ) {
        viewModelScope.launch {
            repository.create(model)
        }
    }

    fun removeModel(modelId: Int) {
        viewModelScope.launch {
            repository.delete(modelId)
        }
    }

    suspend fun getModelById(id: Int): ItemModelUAZ? {
        val allModels = models.firstOrNull() ?: return null
        return allModels.find { it.id == id }
    }
}
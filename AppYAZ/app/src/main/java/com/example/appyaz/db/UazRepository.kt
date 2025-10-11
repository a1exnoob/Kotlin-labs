package com.example.appyaz.db

import com.example.appyaz.ItemModelUAZ
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UazRepository(
    private val uazDao: UazDao
) {
    val items: Flow<List<ItemModelUAZ>> = uazDao.observeAll().map { entities ->
        entities.map { it.toItemModel() }
    }

    suspend fun create(item: ItemModelUAZ) {
        uazDao.insert(item.toEntity())
    }

    suspend fun update(item: ItemModelUAZ) {
        uazDao.update(item.toEntity())
    }

    suspend fun delete(id: Int) {
        uazDao.deleteById(id)
    }

    private fun UazEntity.toItemModel(): ItemModelUAZ {
        return ItemModelUAZ(
            id = this.id,
            name = this.name,
            cost = this.cost,
            description = this.description
        )
    }

    private fun ItemModelUAZ.toEntity(): UazEntity {
        return UazEntity(
            id = this.id,
            name = this.name,
            cost = this.cost,
            description = this.description
        )
    }
}
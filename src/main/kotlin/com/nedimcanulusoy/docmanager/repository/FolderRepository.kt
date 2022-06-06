package com.nedimcanulusoy.docmanager.repository

import com.nedimcanulusoy.docmanager.entity.Folder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FolderRepository : JpaRepository<Folder, UUID> {
    fun findAllByParentFolderId(id: UUID): MutableList<Folder>
}
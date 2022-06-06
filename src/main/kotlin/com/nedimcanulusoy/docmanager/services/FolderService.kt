package com.nedimcanulusoy.docmanager.services

import com.nedimcanulusoy.docmanager.dto.FolderDTO
import java.util.*

interface FolderService {
    fun getAllFolders(): List<FolderDTO>

    fun getAllFoldersByParentId(parentId: UUID): List<FolderDTO>

    fun getFolderById(id: UUID): FolderDTO?

    fun saveFolder(folderDTO: FolderDTO)

    fun delete(item: FolderDTO)
}
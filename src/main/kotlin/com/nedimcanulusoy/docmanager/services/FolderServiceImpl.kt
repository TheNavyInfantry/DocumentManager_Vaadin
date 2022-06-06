package com.nedimcanulusoy.docmanager.services


import com.nedimcanulusoy.docmanager.dto.FolderDTO
import com.nedimcanulusoy.docmanager.dto.FolderDTO.Companion.toFolder
import com.nedimcanulusoy.docmanager.entity.Folder.Companion.toFolderDTO
import com.nedimcanulusoy.docmanager.repository.FolderRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class FolderServiceImpl(private val repository: FolderRepository) : FolderService {
    override fun getAllFolders(): List<FolderDTO> = repository.findAll().map { it.toFolderDTO() }

    override fun getAllFoldersByParentId(parentId: UUID): List<FolderDTO> =
        repository.findAllByParentFolderId(parentId).map { it.toFolderDTO() }

    override fun getFolderById(id: UUID): FolderDTO? {
        val result = repository.findById(id)

        return if (result.isPresent)
            result.get().toFolderDTO()
        else
            null
    }

    override fun saveFolder(folderDTO: FolderDTO) {
        repository.save(folderDTO.toFolder())
    }

    override fun delete(item: FolderDTO) {
        repository.delete(item.toFolder())
    }
}
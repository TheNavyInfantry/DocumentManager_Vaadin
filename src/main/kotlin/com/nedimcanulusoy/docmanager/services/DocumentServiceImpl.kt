package com.nedimcanulusoy.docmanager.services


import com.nedimcanulusoy.docmanager.dto.DocumentDTO
import com.nedimcanulusoy.docmanager.dto.DocumentDTO.Companion.toDocument
import com.nedimcanulusoy.docmanager.entity.Document.Companion.toDocumentDTO
import com.nedimcanulusoy.docmanager.repository.DocumentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DocumentServiceImpl(private val repository: DocumentRepository) : DocumentService {
    override fun getAllDocuments(): List<DocumentDTO> = repository.findAll().map { it.toDocumentDTO() }

    override fun getAllDocumentsByParentId(parentId: UUID): List<DocumentDTO> =
        repository.findAllByParentFolderId(parentId).map { it.toDocumentDTO() }

    override fun delete(item: DocumentDTO) {
        repository.delete(item.toDocument())
    }

    override fun getDocumentById(id: UUID): DocumentDTO? {
        val result = repository.findById(id)

        return if (result.isPresent)
            result.get().toDocumentDTO()
        else
            null
    }

    override fun save(item: DocumentDTO) {
        repository.save(item.toDocument())
    }
}
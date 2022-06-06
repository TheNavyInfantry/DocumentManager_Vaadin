package com.nedimcanulusoy.docmanager.services

import com.nedimcanulusoy.docmanager.dto.DocumentDTO
import java.util.*

interface DocumentService {
    fun getAllDocuments(): List<DocumentDTO>

    fun getAllDocumentsByParentId(parentId: UUID): List<DocumentDTO>

    fun getDocumentById(id: UUID): DocumentDTO?

    fun save(item: DocumentDTO)

    fun delete(item: DocumentDTO)
}
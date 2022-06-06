package com.nedimcanulusoy.docmanager.repository

import com.nedimcanulusoy.docmanager.entity.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DocumentRepository : JpaRepository<Document, UUID> {
    fun findAllByParentFolderId(id: UUID): MutableList<Document>
}
package com.nedimcanulusoy.docmanager.dto

import com.nedimcanulusoy.docmanager.entity.Document
import com.nedimcanulusoy.docmanager.entity.Folder
import com.nedimcanulusoy.docmanager.enum.MimeType
import com.nedimcanulusoy.docmanager.models.Item

import java.util.*

data class DocumentDTO(
    override val id: UUID?,
    override val name: String,
    override val description: String,
    override val type: MimeType,
    val parentFolder: Folder?
) : Item() {
    companion object {
        fun DocumentDTO.toDocument() = Document(
            this.id,
            this.name,
            this.description,
            this.type,
            this.parentFolder
        )
    }
}


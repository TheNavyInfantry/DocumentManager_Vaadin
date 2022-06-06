package com.nedimcanulusoy.docmanager.entity

import com.nedimcanulusoy.docmanager.dto.DocumentDTO
import com.nedimcanulusoy.docmanager.enum.MimeType
import java.util.*
import javax.persistence.*

@Entity
class Document(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val name: String,
    val description: String,
    val type: MimeType,

    @ManyToOne
    @JoinColumn(name = "folder_id")
    val parentFolder: Folder?
) {
    companion object {
        fun Document.toDocumentDTO() =
            DocumentDTO(
                this.id,
                this.name,
                this.description,
                this.type,
                this.parentFolder
            )
    }
}
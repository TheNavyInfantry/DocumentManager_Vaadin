package com.nedimcanulusoy.docmanager.dto

import com.nedimcanulusoy.docmanager.entity.Document
import com.nedimcanulusoy.docmanager.entity.Folder
import com.nedimcanulusoy.docmanager.enum.Colour
import com.nedimcanulusoy.docmanager.models.Item
import java.util.*

data class FolderDTO(
    override val id: UUID?,
    override val name: String,
    override val colour: Colour,
    override val description: String,
    val parentFolder: Folder?,
    val documents: Set<Document> = setOf(),
    val subFolders: Set<Folder> = setOf()
) : Item() {
    companion object {
        fun FolderDTO.toFolder() = Folder(
            this.id,
            this.name,
            this.colour,
            this.description,
            this.parentFolder,
            this.documents,
            this.subFolders
        )
    }
}

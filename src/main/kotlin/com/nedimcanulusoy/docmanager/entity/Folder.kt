package com.nedimcanulusoy.docmanager.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nedimcanulusoy.docmanager.dto.FolderDTO
import com.nedimcanulusoy.docmanager.enum.Colour
import java.util.*
import javax.persistence.*

@Entity
class Folder(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val name: String,
    val color: Colour,
    val description: String,

    @ManyToOne
    @JsonIgnore
    val parentFolder: Folder? = null,

    @OneToMany(mappedBy = "parentFolder", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val documents: Set<Document> = setOf(),

    @OneToMany(mappedBy = "parentFolder", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val subFolders: Set<Folder> = setOf()
) {
    companion object {
        fun Folder.toFolderDTO() =
            FolderDTO(
                this.id,
                this.name,
                this.color,
                this.description,
                this.parentFolder,
                this.documents,
                this.subFolders
            )
    }
}
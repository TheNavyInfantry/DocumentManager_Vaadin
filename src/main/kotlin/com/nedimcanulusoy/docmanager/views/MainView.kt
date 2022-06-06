package com.nedimcanulusoy.docmanager.views

import com.nedimcanulusoy.docmanager.dto.DocumentDTO
import com.nedimcanulusoy.docmanager.dto.FolderDTO
import com.nedimcanulusoy.docmanager.dto.FolderDTO.Companion.toFolder
import com.nedimcanulusoy.docmanager.entity.Folder.Companion.toFolderDTO
import com.nedimcanulusoy.docmanager.enum.Colour
import com.nedimcanulusoy.docmanager.enum.MimeType
import com.nedimcanulusoy.docmanager.models.Item
import com.nedimcanulusoy.docmanager.services.DocumentService
import com.nedimcanulusoy.docmanager.services.FolderService
import com.vaadin.flow.component.HtmlComponent
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.renderer.ComponentRenderer
import com.vaadin.flow.router.Route

@Route
class MainView(private val folderService: FolderService, private val documentService: DocumentService) :
    VerticalLayout() {
    private val homepageView = VerticalLayout()
    private val addFolderView = VerticalLayout()
    private val addFileView = VerticalLayout()
    private val editView = VerticalLayout()
    private val showFileView = VerticalLayout()

    private var dontClick = false

    init {
        add(homepageView)
        add(addFolderView)
        add(addFileView)
        add(editView)
        add(showFileView)

        showHomepage()
    }

    private fun showHomepage(folderDTO: FolderDTO? = null) {
        hideAllViews()
        homepageView.isVisible = true
        homepageView.removeAll()

        val upperFolderButton = Button("Upper Folder")
        upperFolderButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        upperFolderButton.addClickListener {
            showHomepage(folderDTO?.parentFolder?.toFolderDTO())
        }

        var path = folderDTO?.name ?: ""
        var currentFolder = folderDTO

        while (currentFolder?.parentFolder != null) {
            path = currentFolder.parentFolder!!.name + "/" + path
            currentFolder = currentFolder.parentFolder!!.toFolderDTO()
        }


        val pathText = H3("Current Path: /$path")
        val folderView = showFolderView(folderDTO)
        homepageView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, pathText)

        val addFolderButton = Button("Add Folder")
        addFolderButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)
        val addFileButton = Button("Add File")
        addFileButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        addFolderButton.addClickListener {
            showAddFolderPage(folderDTO)
        }

        addFileButton.addClickListener {
            showAddFilePage(folderDTO)
        }

        if (folderDTO != null) {
            homepageView.add(upperFolderButton)
            homepageView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, upperFolderButton)
        }

        val buttonHorizontalLayout = HorizontalLayout(addFolderButton, addFileButton)

        homepageView.add(
            pathText,
            folderView,
            buttonHorizontalLayout,
        )

        homepageView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, buttonHorizontalLayout)
    }

    private fun showAddFolderPage(parentFolder: FolderDTO? = null) {
        hideAllViews()
        addFolderView.isVisible = true
        addFolderView.removeAll()

        addFolderView.width = "500px"

        val backButton = Button("Folders")
        backButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        backButton.addClickListener {
            showHomepage(parentFolder)
        }


        val title = H2("Add New Folder")
        addFolderView.add(title)
        addFolderView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, title)

        val name = TextField("Name")
        val color = ComboBox<Colour>("Colour")
        val description = TextField("Description")
        val createButton = Button("Create")

        color.setItems(Colour.values().toList())
        color.setItemLabelGenerator(Colour::name)

        color.value = Colour.Green

        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        createButton.icon = Icon(VaadinIcon.PLUS)

        createButton.addClickListener {
            folderService.saveFolder(
                FolderDTO(
                    null,
                    name.value,
                    color.value,
                    description.value,
                    parentFolder?.toFolder()
                )
            )

            showHomepage(parentFolder)
        }

        val formLayout = FormLayout()

        formLayout.add(
            name,
            color,
            description,
            createButton
        )

        formLayout.setColspan(description, 1)

        addFolderView.add(formLayout)

        addFolderView.add(backButton)
        addFolderView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, backButton)

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, addFolderView)
    }

    private fun showAddFilePage(parentFolder: FolderDTO? = null) {
        hideAllViews()
        addFileView.isVisible = true
        addFileView.removeAll()

        addFileView.width = "500px"

        val backButton = Button("Folders")
        backButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        backButton.addClickListener {
            showHomepage(parentFolder)
        }



        val title = H2("Add New File")

        addFileView.add(title)
        addFileView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, title)

        val name = TextField("Name")
        val description = TextField("Description")
        val mimeType = ComboBox<MimeType>("Mime Type")
        val createButton = Button("Create")

        mimeType.setItems(MimeType.values().toList())
        mimeType.setItemLabelGenerator(MimeType::name)

        mimeType.value = MimeType.Other

        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        createButton.icon = Icon(VaadinIcon.PLUS)

        createButton.addClickListener {
            documentService.save(
                DocumentDTO(
                    null,
                    name.value,
                    description.value,
                    mimeType.value,
                    parentFolder?.toFolder()
                )
            )

            showHomepage(parentFolder)
        }

        val formLayout = FormLayout()

        formLayout.add(
            name,
            description,
            mimeType,
            createButton
        )

        formLayout.setColspan(description, 1)

        addFileView.add(formLayout)

        addFileView.add(backButton)
        addFileView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, backButton)

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, addFileView)
    }

    private fun showEditPage(item: Item, parentFolder: FolderDTO? = null) {
        hideAllViews()
        editView.isVisible = true
        editView.removeAll()

        editView.width = "500px"

        val backButton = Button("Folders")
        backButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        backButton.addClickListener {
            showHomepage(parentFolder)
        }



        val title = H2("Edit " + if (item is FolderDTO) "Folder" else "Document")

        editView.add(title)
        editView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, title)

        val name = TextField("Name")
        val description = TextField("Description")
        val saveButton = Button("Save")

        val colour = ComboBox<Colour>("Colour")

        colour.setItems(Colour.values().toList())
        colour.setItemLabelGenerator(Colour::name)

        val mimeType = ComboBox<MimeType>("Mime Type")

        mimeType.setItems(MimeType.values().toList())
        mimeType.setItemLabelGenerator(MimeType::name)

        name.value = item.name
        description.value = item.description

        if (item is FolderDTO) {
            colour.value = item.colour
        }

        if (item is DocumentDTO) {
            mimeType.value = item.type
        }

        saveButton.icon = Icon(VaadinIcon.RECORDS)
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

        saveButton.addClickListener {
            if (item is FolderDTO) {
                folderService.saveFolder(
                    FolderDTO(
                        item.id,
                        name.value,
                        colour.value,
                        description.value,
                        item.parentFolder
                    )
                )
            }

            if (item is DocumentDTO) {
                documentService.save(
                    DocumentDTO(
                        item.id,
                        name.value,
                        description.value,
                        mimeType.value,
                        item.parentFolder
                    )
                )
            }

            if (item is FolderDTO) {
                if (item.parentFolder != null) {
                    showHomepage(item.parentFolder.toFolderDTO())
                }
            }

            if (item is DocumentDTO) {
                if (item.parentFolder != null) {
                    showHomepage(item.parentFolder.toFolderDTO())
                }
            }

            showHomepage()
        }

        val formLayout = FormLayout()

        if (item is FolderDTO) {
            formLayout.add(
                name,
                colour,
                description
            )
        }

        if (item is DocumentDTO) {
            formLayout.add(
                name,
                description,
                mimeType
            )
        }

        formLayout.add(saveButton)

        formLayout.setColspan(description, 1)

        editView.add(formLayout)

        editView.add(backButton)
        addFolderView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, backButton)


        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, editView)
    }

    private fun showFolderView(folderDTO: FolderDTO? = null): VerticalLayout {
        val folderViewLayout = VerticalLayout()

        val gridLayout = Grid<Item>()
        val items = mutableListOf<Item>()

        val folders =
            if (folderDTO == null)
                folderService.getAllFolders().filter { it.parentFolder == null }
            else
                folderService.getAllFoldersByParentId(folderDTO.id!!)

        val documents =
            if (folderDTO == null)
                documentService.getAllDocuments().filter { it.parentFolder == null }
            else
                documentService.getAllDocumentsByParentId(folderDTO.id!!)

        items.addAll(folders)
        items.addAll(documents)

        gridLayout.setItems(items)

        gridLayout.addColumn(ComponentRenderer label@{ item ->
            if (item is FolderDTO) {
                return@label VaadinIcon.FOLDER_O.create()
            } else {
                return@label VaadinIcon.FILE_O.create()
            }
        }).flexGrow = 0
        gridLayout.addColumn(Item::id).setHeader("ID")
        gridLayout.addColumn(Item::name).setHeader("Name").flexGrow = 0
        gridLayout.addColumn(Item::description).setHeader("Description")
        gridLayout.addColumn({ item -> if (item is FolderDTO) "Folder" else item.type!!.name }).setHeader("Mime Type")
        gridLayout.addColumn({ item -> if (item is FolderDTO) item.colour.name else " " }).setHeader("Folder Colour")

        gridLayout.addColumn(ComponentRenderer label@{ item ->
            val button = Button(Icon(VaadinIcon.EDIT))
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

            button.addClickListener {
                dontClick = true
                showEditPage(item)
            }

            return@label button
        }).setFrozen(true).flexGrow = 0
        gridLayout.addColumn(ComponentRenderer label@{ item ->
            val button = Button(Icon(VaadinIcon.TRASH))
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS)

            button.addClickListener {
                val newItemsList = items.toMutableList()

                if (item is FolderDTO) {
                    folderService.delete(item)
                }

                if (item is DocumentDTO) {
                    documentService.delete(item)
                }

                newItemsList.remove(item)

                gridLayout.setItems(newItemsList)

                items.remove(item)
            }

            return@label button
        }).setFrozen(true).flexGrow = 0

        gridLayout.addItemClickListener {
            if (!dontClick && it.item != null)
                if (it.item is FolderDTO) {
                    showHomepage(it.item as FolderDTO)
                } else {
                    showFileView(it.item as DocumentDTO)
                }
            else {
                dontClick = false
            }
        }

        folderViewLayout.add(gridLayout)

        return folderViewLayout
    }

    private fun showFileView(documentDTO: DocumentDTO) {
        hideAllViews()
        showFileView.isVisible = true
        showFileView.removeAll()

        showFileView.width = "500px"

        val backButton = Button("Folders")

        backButton.addClickListener {
            showHomepage(documentDTO.parentFolder?.toFolderDTO())
        }

        val title = H2("File Detail Information")

        showFileView.add(title)
        showFileView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, title)

        var path = ""
        var parentFolder = documentDTO.parentFolder

        while (parentFolder != null) {
            path = if (path == "") {
                "/" + parentFolder.name
            } else {
                "/" + parentFolder.name + path
            }

            parentFolder = parentFolder.parentFolder
        }

        val id = Text("Document ID: ${documentDTO.id}")
        val name = Text("Document Name: ${documentDTO.name}")
        val description = Text("Document Description: ${documentDTO.description}")
        val type = Text("Document Type: ${documentDTO.type.name}")
        val pathText = Text("Document Path: $path")

        showFileView.add(id)
        showFileView.add(HtmlComponent("BR"))
        showFileView.add(name)
        showFileView.add(HtmlComponent("BR"))
        showFileView.add(description)
        showFileView.add(HtmlComponent("BR"))
        showFileView.add(type)
        showFileView.add(HtmlComponent("BR"))
        showFileView.add(pathText)

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, showFileView)

        showFileView.add(backButton)
        backButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS)
        showFileView.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, backButton)
    }

    private fun hideAllViews() {
        homepageView.isVisible = false
        addFolderView.isVisible = false
        addFileView.isVisible = false
        editView.isVisible = false
        showFileView.isVisible = false
    }
}
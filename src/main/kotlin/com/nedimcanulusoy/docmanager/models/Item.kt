package com.nedimcanulusoy.docmanager.models

import com.nedimcanulusoy.docmanager.enum.Colour
import com.nedimcanulusoy.docmanager.enum.MimeType
import java.util.*

abstract class Item {
    abstract val id: UUID?
    abstract val name: String
    abstract val description: String
    open val type: MimeType? = null
    open val colour: Colour? = null
}
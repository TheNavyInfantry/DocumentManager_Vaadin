package com.nedimcanulusoy.docmanager

import com.vaadin.flow.component.Text
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route
class MainView : VerticalLayout() {
    init {
        add(Text("TEST TEXT"))
    }
}
package com.pte.cars.security;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("403")
@Route("403")
public class ForbiddenView extends VerticalLayout {
    public ForbiddenView(){
        //TODO
        //add(new MenuComponent());
        add("Access denied");
    }
}

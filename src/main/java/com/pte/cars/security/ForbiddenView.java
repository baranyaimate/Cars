package com.pte.cars.security;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

@PageTitle("403")
@Route("403")
public class ForbiddenView extends VerticalLayout implements PageConfigurator {

    public ForbiddenView() {
        add("Access denied");
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addFavIcon("icon", "images/car.png", "256x256");
    }

}

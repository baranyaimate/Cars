package com.pte.cars.manufacturer.view;

import com.pte.cars.MainView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@PageTitle("Manufacturers")
@Route(layout = MainView.class)
public class ManufacturerView extends HorizontalLayout {

    @PostConstruct
    public void init() {

    }

}

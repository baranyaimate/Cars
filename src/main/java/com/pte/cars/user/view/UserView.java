package com.pte.cars.user.view;

import com.pte.cars.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@PageTitle("Users")
@Route(layout = MainView.class)
public class UserView extends Div {

    @PostConstruct
    public void init() {

    }

}

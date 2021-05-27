package com.pte.cars.menu;

import com.pte.cars.security.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MenuComponent extends HorizontalLayout {

    public MenuComponent() {
        Anchor main = new Anchor();
        main.setText("Main page");
        main.setHref("/");
        add(main);

        Anchor link = new Anchor();
        link.setText("Car page");
        link.setHref("/car");
        add(link);

        if(SecurityUtils.isAdmin()){
            /*Anchor author = new Anchor();
            author.setText("Author page");
            author.setHref("/author");
            add(author);*/

            Anchor user = new Anchor();
            user.setText("Users page");
            user.setHref("/user");
            add(user);
        }


    }
}

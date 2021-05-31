package com.pte.cars.security;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

import java.util.Collections;

@Tag("sa-login-view")
@PageTitle("Login")
@Route
@CssImport("style.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver, PageConfigurator {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        loginForm.setAction("login");
        add(loginForm);
        addClassName("login-container");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        // (yes, the API for resolving query parameters is annoying...)
        if (!beforeEnterEvent.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            loginForm.setError(true);
        }
        loginForm.setForgotPasswordButtonVisible(false);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addFavIcon("icon", "images/car.png", "256x256");
    }
}

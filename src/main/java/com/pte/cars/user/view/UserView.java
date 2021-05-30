package com.pte.cars.user.view;

import com.pte.cars.MainView;
import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.car.service.CarService;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;
import com.pte.cars.user.entity.RoleEntity;
import com.pte.cars.user.entity.UserEntity;
import com.pte.cars.user.service.RoleService;
import com.pte.cars.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@PageTitle("Users")
@Route(layout = MainView.class)
public class UserView extends Div {

    private final Grid<UserEntity> grid = new Grid<>(UserEntity.class, false);
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button remove = new Button("", VaadinIcon.TRASH.create());
    private TextField username;
    private TextField firstName;
    private TextField lastName;
    private ComboBox<RoleEntity> role;
    private UserEntity selectedUser;
    private Binder<UserEntity> binder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void init() {
        // Create UI
        addClassNames("about-view", "flex", "flex-col", "h-full");
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        // Configure Grid
        role.setItems(roleService.getAll());
        grid.setItems(userService.getAll());

        Grid.Column<UserEntity> idColumn = grid.addColumn(UserEntity::getId).setHeader("Id").setAutoWidth(true);
        Grid.Column<UserEntity> usernameColumn = grid.addColumn(UserEntity::getUsername).setHeader("Username").setAutoWidth(true);
        Grid.Column<UserEntity> firstNameColumn = grid.addColumn(UserEntity::getFirstname).setHeader("First name").setAutoWidth(true);
        Grid.Column<UserEntity> lastNameColumn = grid.addColumn(UserEntity::getLastname).setHeader("Last name").setAutoWidth(true);
        Grid.Column<UserEntity> roleColumn = grid.addColumn(UserEntity::getAuthorities).setHeader("Role").setAutoWidth(true);

        TextField idFilterField = new TextField();
        TextField usernameFilterField = new TextField();
        TextField firstNameFilterField = new TextField();
        TextField lastNameFilterField = new TextField();
        ComboBox<RoleEntity> roleFilterComboBox = new ComboBox<>();

        idFilterField.setPlaceholder("Filter");
        idFilterField.setSizeFull();
        idFilterField.setClearButtonVisible(true);
        //idFilterField.addValueChangeListener(e -> grid.setItems(carService.getIdFiltered(idFilterField.getValue())));
        idFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        usernameFilterField.setPlaceholder("Filter");
        usernameFilterField.setSizeFull();
        usernameFilterField.setClearButtonVisible(true);
        //usernameFilterField.addValueChangeListener(e -> grid.setItems(carService.getIdFiltered(idFilterField.getValue())));
        usernameFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        firstNameFilterField.setPlaceholder("Filter");
        firstNameFilterField.setSizeFull();
        firstNameFilterField.setClearButtonVisible(true);
        //firstNameFilterField.addValueChangeListener(e -> grid.setItems(carService.getIdFiltered(idFilterField.getValue())));
        firstNameFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        lastNameFilterField.setPlaceholder("Filter");
        lastNameFilterField.setSizeFull();
        lastNameFilterField.setClearButtonVisible(true);
        //lastNameFilterField.addValueChangeListener(e -> grid.setItems(carService.getIdFiltered(idFilterField.getValue())));
        lastNameFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        HeaderRow filterRow = grid.appendHeaderRow();
        filterRow.getCell(idColumn).setComponent(idFilterField);
        filterRow.getCell(usernameColumn).setComponent(usernameFilterField);
        filterRow.getCell(firstNameColumn).setComponent(firstNameFilterField);
        filterRow.getCell(lastNameColumn).setComponent(lastNameFilterField);
        //filterRow.getCell(manufacturerColumn).setComponent(manufacturerFilterField);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        username = new TextField("Username");
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        role = new ComboBox<>("Role");
        Component[] fields = new Component[]{username, firstName, lastName, role};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        remove.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(save, cancel, remove);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.setItems(userService.getAll());
    }

}

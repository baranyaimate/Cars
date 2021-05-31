package com.pte.cars.manufacturer.view;

import com.pte.cars.MainView;
import com.pte.cars.car.service.CarService;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;
import com.pte.cars.manufacturer.service.ManufacturerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@PageTitle("Manufacturers")
@Route(layout = MainView.class)
public class ManufacturerView extends Div {

    private final Grid<ManufacturerEntity> grid = new Grid<>(ManufacturerEntity.class, false);
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button remove = new Button("", VaadinIcon.TRASH.create());
    private TextField name;
    private ManufacturerEntity selectedManufacturer;
    private Binder<ManufacturerEntity> binder;

    @Autowired
    private CarService carService;
    @Autowired
    private ManufacturerService manufacturerService;

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
        grid.setItems(manufacturerService.getAll());

        Grid.Column<ManufacturerEntity> idColumn = grid.addColumn(ManufacturerEntity::getId).setHeader("Id").setAutoWidth(true);
        Grid.Column<ManufacturerEntity> nameColumn = grid.addColumn(ManufacturerEntity::getName).setHeader("Name").setAutoWidth(true);

        TextField idFilterField = new TextField();
        TextField nameFilterField = new TextField();

        idFilterField.setPlaceholder("Filter");
        idFilterField.setSizeFull();
        idFilterField.setClearButtonVisible(true);
        idFilterField.addValueChangeListener(e -> grid.setItems(manufacturerService.getIdFiltered(idFilterField.getValue())));
        idFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilterField.setPlaceholder("Filter");
        nameFilterField.setSizeFull();
        nameFilterField.setClearButtonVisible(true);
        nameFilterField.addValueChangeListener(e -> grid.setItems(manufacturerService.getNameFiltered(nameFilterField.getValue())));
        nameFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        HeaderRow filterRow = grid.appendHeaderRow();
        filterRow.getCell(idColumn).setComponent(idFilterField);
        filterRow.getCell(nameColumn).setComponent(nameFilterField);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedManufacturer = event.getValue();
            binder.setBean(selectedManufacturer);
        });

        binder = new Binder<>(ManufacturerEntity.class);
        binder.forField(name)
                .asRequired("Name is required")
                .bind(ManufacturerEntity::getName, ManufacturerEntity::setName);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> refreshGrid());

        save.addClickListener(e -> {
            try {
                if (selectedManufacturer == null) {
                    selectedManufacturer = new ManufacturerEntity();
                    selectedManufacturer.setName(name.getValue());
                    manufacturerService.add(selectedManufacturer);
                } else {
                    manufacturerService.update(selectedManufacturer);
                }
                binder.writeBean(selectedManufacturer);

                refreshGrid();
                Notification.show("Manufacturer details stored.");
                UI.getCurrent().navigate(ManufacturerView.class);
            } catch (Exception ex) {
                Notification.show("Name must be unique");
            }
        });

        remove.addClickListener(e -> {
            try {
                manufacturerService.remove(selectedManufacturer);
                refreshGrid();
                Notification.show("Manufacturer details deleted.");
                UI.getCurrent().navigate(ManufacturerView.class);
            } catch (Exception ex) {
                Notification.show("An exception happened while trying to delete the manufacturer details.");
            }
        });

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        Component[] fields = new Component[]{name};

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
        grid.setItems(manufacturerService.getAll());
    }

}

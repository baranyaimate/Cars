package com.pte.cars.car.view;

import com.pte.cars.MainView;
import com.pte.cars.car.entity.CarEntity;
import com.pte.cars.car.service.CarService;
import com.pte.cars.manufacturer.entity.ManufacturerEntity;
import com.pte.cars.manufacturer.service.ManufacturerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Cars")
@Route(layout = MainView.class)
public class CarView extends Div implements BeforeEnterObserver {

    private final String CAR_ID = "carID";
    private final String CAR_EDIT_ROUTE_TEMPLATE = "car/%d/edit";

    private Grid<CarEntity> grid = new Grid<>(CarEntity.class, false);

    private TextField doors;
    private TextField type;
    private TextField yearOfManufacture;
    private ComboBox manufacturer;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<CarEntity> binder;

    private CarEntity carEntity;

    CarService carService;

    ManufacturerService manufacturerService;

    public CarView(@Autowired CarService carService, @Autowired ManufacturerService manufacturerService) {
        addClassNames("about-view", "flex", "flex-col", "h-full");
        this.carService = carService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout, manufacturerService);

        add(splitLayout);
        // Configure Grid
        grid.addColumn("doors").setAutoWidth(true);
        grid.addColumn("type").setAutoWidth(true);
        grid.addColumn("yearOfManufacture").setAutoWidth(true);
        grid.addColumn("manufacturer").setAutoWidth(true);
        grid.addColumn("id").setAutoWidth(true);

        grid.setItems(carService.getAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
           /* if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CAR_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(CarView.class);
            }*/
        });

        // Configure Form
        binder = new BeanValidationBinder<>(CarEntity.class);

        // Bind fields. This where you'd define e.g. validation rules

        //binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.carEntity == null) {
                    this.carEntity = new CarEntity();
                }
                binder.writeBean(this.carEntity);

                carService.update(this.carEntity);
                clearForm();
                refreshGrid();
                Notification.show("Car details stored.");
                UI.getCurrent().navigate(CarView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the car details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> carId = event.getRouteParameters().getLong(CAR_ID);
        if (carId.isPresent()) {
            CarEntity carEntityFromBackend = carService.findById(carId.get());
            Notification.show(
                    String.format("The requested car was not found, ID = %d", carId.get()), 3000,
                    Notification.Position.BOTTOM_START);
            // when a row is selected but the data is no longer available,
            // refresh grid
            refreshGrid();
            event.forwardTo(CarView.class);
        }
    }

    private void createEditorLayout(SplitLayout splitLayout, ManufacturerService manufacturerService) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        doors = new TextField("Doors");
        type = new TextField("Type");
        yearOfManufacture = new TextField("Year");
        manufacturer = new ComboBox("Manufacturer");
        manufacturer.setItems(manufacturerService.getAll());
        Component[] fields = new Component[]{doors, type, yearOfManufacture, manufacturer};

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
        buttonLayout.add(save, cancel);
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
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(CarEntity value) {
        this.carEntity = value;
        binder.readBean(this.carEntity);

    }

}

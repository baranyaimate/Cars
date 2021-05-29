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
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Cars")
@Route(layout = MainView.class)
public class CarView extends Div {

    private Grid<CarEntity> grid = new Grid<>(CarEntity.class, false);

    private TextField doors;
    private TextField type;
    private TextField yearOfManufacture;
    private ComboBox<ManufacturerEntity> manufacturer;

    private TextField idFilterField;
    private TextField doorsFilterField;
    private TextField typeFilterField;
    private TextField yearOfManufactureFilterField;
    private TextField manufacturerFilterField;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private CarEntity selectedCar;
    private Binder<CarEntity> binder;

    @Autowired
    private CarService carService;
    @Autowired
    private ManufacturerService manufacturerService;

    @PostConstruct
    public void init() {
        addClassNames("about-view", "flex", "flex-col", "h-full");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
        // Configure Grid
        manufacturer.setItems(manufacturerService.getAll());
        grid.setItems(carService.getAll());

        Grid.Column<CarEntity> idColumn = grid.addColumn(CarEntity::getId).setHeader("Id").setAutoWidth(true);
        Grid.Column<CarEntity> doorsColumn = grid.addColumn(CarEntity::getDoors).setHeader("Doors").setAutoWidth(true);
        Grid.Column<CarEntity> typeColumn = grid.addColumn(CarEntity::getType).setHeader("Type").setAutoWidth(true);
        Grid.Column<CarEntity> yearOfManufactureColumn = grid.addColumn(CarEntity::getYearOfManufacture).setHeader("year").setAutoWidth(true);
        Grid.Column<CarEntity> manufacturerColumn = grid.addColumn(CarEntity::getManufacturer).setHeader("Manufacturer").setAutoWidth(true);

        idFilterField = new TextField();
        doorsFilterField = new TextField();
        typeFilterField = new TextField();
        yearOfManufactureFilterField = new TextField();
        manufacturerFilterField = new TextField();

        idFilterField.setPlaceholder("Filter");
        idFilterField.setSizeFull();
        idFilterField.addValueChangeListener(event -> {
            grid.setItems(carService.getIdFiltered(idFilterField.getValue()));
        });
        idFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        doorsFilterField.setPlaceholder("Filter");
        doorsFilterField.setSizeFull();
        doorsFilterField.addValueChangeListener(event -> {
            grid.setItems(carService.getDoorsFiltered(doorsFilterField.getValue()));
        });
        doorsFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        typeFilterField.setPlaceholder("Filter");
        typeFilterField.setSizeFull();
        typeFilterField.addValueChangeListener(event -> {
            grid.setItems(carService.getTypeFiltered(typeFilterField.getValue()));
        });
        typeFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        yearOfManufactureFilterField.setPlaceholder("Filter");
        yearOfManufactureFilterField.setSizeFull();
        yearOfManufactureFilterField.addValueChangeListener(event -> {
            grid.setItems(carService.getYearFiltered(yearOfManufactureFilterField.getValue()));
        });
        yearOfManufactureFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        manufacturerFilterField.setPlaceholder("Filter");
        manufacturerFilterField.setSizeFull();
        manufacturerFilterField.addValueChangeListener(event -> {
            grid.setItems(carService.getManufacturerFiltered(manufacturerFilterField.getValue()));
        });
        manufacturerFilterField.setValueChangeMode(ValueChangeMode.EAGER);

        HeaderRow filterRow = grid.appendHeaderRow();
        filterRow.getCell(idColumn).setComponent(idFilterField);
        filterRow.getCell(doorsColumn).setComponent(doorsFilterField);
        filterRow.getCell(typeColumn).setComponent(typeFilterField);
        filterRow.getCell(yearOfManufactureColumn).setComponent(yearOfManufactureFilterField);
        filterRow.getCell(manufacturerColumn).setComponent(manufacturerFilterField);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedCar = event.getValue();
            binder.setBean(selectedCar);
        });

        binder = new Binder<>(CarEntity.class);
        binder.forField(doors)
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(door -> door >= 1, "Must be larger then 1")
                .withValidator(door -> door <= 10, "Must be lower then 10")
                .bind(CarEntity::getDoors, CarEntity::setDoors);

        binder.forField(yearOfManufacture)
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .withValidator(year -> year >= 1900, "Must be larger then 1900")
                .withValidator(year -> year <= 2100, "Must be lower then 2100")
                .bind(CarEntity::getYearOfManufacture, CarEntity::setYearOfManufacture);

        binder.forField(type)
                .asRequired("Type is required")
                .bind(CarEntity::getType, CarEntity::setType);

        binder.forField(manufacturer)
                .asRequired("Manufacturer is required")
                .bind(CarEntity::getManufacturer, CarEntity::setManufacturer);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.selectedCar == null) {
                    this.selectedCar = new CarEntity();
                    selectedCar.setDoors(Integer.parseInt(doors.getValue()));
                    selectedCar.setType(type.getValue());
                    selectedCar.setYearOfManufacture(Integer.parseInt(yearOfManufacture.getValue()));
                    selectedCar.setManufacturer(manufacturer.getValue());
                    carService.add(selectedCar);
                } else {
                    carService.update(this.selectedCar);
                }
                binder.writeBean(this.selectedCar);

                refreshGrid();
                Notification.show("Car details stored.");
                UI.getCurrent().navigate(CarView.class);
            } catch (Exception ex) {
                Notification.show("An exception happened while trying to store the car details.");
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
        doors = new TextField("Doors");
        type = new TextField("Type");
        yearOfManufacture = new TextField("Year");
        manufacturer = new ComboBox("Manufacturer");
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
        grid.setItems(carService.getAll());
    }

}

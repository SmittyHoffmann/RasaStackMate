package rasaCore.controller;

import main.util.EditCategoricalSlotDialog;
import main.util.EditFloatSlotDialog;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Pair;
import rasaCore.model.domain.DomainManager;
import rasaCore.model.slot.*;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DomainSlotController implements Initializable {


    @FXML TextField slotNameTextField;
    @FXML ChoiceBox<SLOTTYPE> slotTypeChoiceBox;
    @FXML Button addSlotButton;
    @FXML Button deleteSlotButton;
    @FXML Button editSlotButton;
    @FXML TableView<Slot> slotTableView;
    @FXML TableColumn<Slot,String> nameColumn;
    @FXML TableColumn<Slot,String> typeColumn;

    @Inject
    SlotManager slotManager;

    @Inject
    DomainManager domainManager;

    @Inject
    SlotFactory factory;

    SuggestionProvider<String> provider;
    private ObservableList<String> suggestions = FXCollections.observableArrayList();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupComponents();
        setHandler();



        }

    /**
     * Fügt GUI-Elementen Eventhandler hinzu
     */
    private void setHandler() {
        suggestions.addListener((ListChangeListener<String>) event -> {
            provider.clearSuggestions();
            provider.addPossibleSuggestions(suggestions);
        });

        addSlotButton.setOnAction(event -> {
            String slotName = slotNameTextField.getText();
            SLOTTYPE type = slotTypeChoiceBox.getSelectionModel().getSelectedItem();

            if(!slotName.isEmpty() && type != null) {
                Slot slot = factory.getSlot(slotName, type);
                if (slot.getType() == SLOTTYPE.FLOAT) {
                    Dialog<Pair<Float, Float>> dialog = new EditFloatSlotDialog();
                    Optional<Pair<Float, Float>> result = dialog.showAndWait();
                    FloatSlot floatSlot = (FloatSlot) slot;
                    result.ifPresent(resultData -> {
                        floatSlot.setMinValue(resultData.getKey());
                        floatSlot.setMaxValue(resultData.getValue());
                        slotManager.addSlot(floatSlot);
                    });

                } else if (slot.getType() == SLOTTYPE.CATEGORICAL) {
                    Dialog<List<String>> dialog = new EditCategoricalSlotDialog();
                    Optional<List<String>> result = dialog.showAndWait();
                    CategoricalSlot categorySlot = (CategoricalSlot) slot;
                    result.ifPresent(resultData -> {
                        categorySlot.setValues(resultData);
                        slotManager.addSlot(categorySlot);
                    });
                } else{
                    slotManager.addSlot(slot);
                }
                slotNameTextField.clear();
            }
        });

        deleteSlotButton.setOnAction(event -> {
            Slot selectedSlot = slotTableView.getSelectionModel().getSelectedItem();
            if(selectedSlot != null){
                slotManager.removeSlot(selectedSlot);
            }
        });


        editSlotButton.setOnAction(event -> {
            Slot selectedSlot = slotTableView.getSelectionModel().getSelectedItem();
            if(selectedSlot != null){
                if(selectedSlot.getType() == SLOTTYPE.CATEGORICAL){
                    Dialog<List<String>> dialog = new EditCategoricalSlotDialog((CategoricalSlot)selectedSlot);
                    Optional<List<String>> result = dialog.showAndWait();
                    result.ifPresent(resultData -> {
                        ((CategoricalSlot) selectedSlot).setValues(resultData);
                    });
                }
                else if(selectedSlot.getType() == SLOTTYPE.FLOAT){
                    Dialog<Pair<Float,Float>> dialog = new EditFloatSlotDialog((FloatSlot)selectedSlot);
                    Optional<Pair<Float,Float>> result = dialog.showAndWait();
                    result.ifPresent(resultData -> {
                        ((FloatSlot) selectedSlot).setMinValue(resultData.getKey());
                        ((FloatSlot) selectedSlot).setMaxValue(resultData.getValue());
                    });
                }

            }
        });
    }

    /**
     * GUI-Elemente werden befüllt und vorbereitet
     */
    private void setupComponents() {
        slotTypeChoiceBox.getItems().setAll(SLOTTYPE.values());

        suggestions = (ObservableList<String>) domainManager.getEntities();


        this.provider = SuggestionProvider.create(suggestions);

        TextFields.bindAutoCompletion(slotNameTextField,provider);

        slotTableView.setItems(slotManager.getSlots());
        slotTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().getType()));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        slotTableView.setEditable(true);
        nameColumn.setEditable(true);
    }

}


package controller.rasaCore.domain;

import controller.util.AddFloatSlotDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.rasaCore.DomainManager;
import model.rasaCore.slot.*;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DomainSlotController implements Initializable {


    @FXML
    TextField slotNameTextField;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        slotTypeChoiceBox.getItems().setAll(SLOTTYPE.values());
        TextFields.bindAutoCompletion(slotNameTextField,domainManager.getEntities());

        slotTableView.setItems(slotManager.getSlots());
        slotTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().getType()));

        addSlotButton.setOnAction(event -> {
            String slotName = slotNameTextField.getText();
            SLOTTYPE type = slotTypeChoiceBox.getSelectionModel().getSelectedItem();

            if(!slotName.isEmpty() && type != null) {
                Slot slot = factory.getSlot(slotName, type);
                if(slot.getType() == SLOTTYPE.FLOAT){
                    Dialog<Pair<Float,Float>> dialog = new AddFloatSlotDialog();
                    Optional<Pair<Float,Float>> result = dialog.showAndWait();
                    FloatSlot floatSlot = (FloatSlot) slot;
                    result.ifPresent(resultData -> {
                        floatSlot.setMinValue(resultData.getKey());
                        floatSlot.setMaxValue(resultData.getValue());
                        slotManager.addSlot(floatSlot);
                    });

                }else if(slot.getType() == SLOTTYPE.CATEGORICAL){

                }

        });
        }

    }
}

package posSystem.UI;

import posSystem.model.Item;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import posSystem.model.ItemDb;
import prog24178.utils.*;

/**
 * Class name: AddItemScene Authors: Kendrick Tsz-Kin Yeung, Malcolm Busari,
 * Manpreet Kaur Date: 9 Apr 2019
 *
 * Description: This class will create an AddItemScene scene that will be used
 * in other classes
 */
public class AddItemScene extends Stage {

    private Scene scene;
    private GridPane pane;
    private TextField txtID, txtName, txtPrice, txtAmt;
    private Button btnAdd, btnPlus, btnMinus, btnReset, btnReturn;
    POSSystem main = POSSystem.getInstance();

    /**
     * Default constructor
     */
    public AddItemScene() {
        super();
        getComponents();
        scene = new Scene(pane);
        this.setScene(scene);
        this.setTitle("Add new Item");
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);
    }

    /**
     * This method will create all the necessary components for the pane
     */
    private void getComponents() {
        pane = new GridPane();
        pane.setPadding(new Insets(20));
        txtID = new TextField();
        txtID.setPromptText("ID");
        txtID.setFocusTraversable(false);
        txtName = new TextField();
        txtName.setPromptText("Name");
        txtName.setFocusTraversable(false);
        txtPrice = new TextField();
        txtPrice.setPromptText("Price");
        txtPrice.setFocusTraversable(false);
        txtAmt = new TextField("0");
        txtAmt.setFocusTraversable(false);
        btnPlus = new Button("+");
        btnPlus.setOnAction(e -> eventCode(e));
        btnPlus.setFocusTraversable(false);
        btnMinus = new Button("-");
        btnMinus.setOnAction(e -> eventCode(e));
        btnMinus.setFocusTraversable(false);
        btnAdd = new Button("Add to Inventory");
        btnAdd.setOnAction(e -> eventCode(e));
        btnAdd.setFocusTraversable(false);
        btnReset = new Button("Reset");
        btnReset.setOnAction(e -> eventCode(e));
        btnReset.setFocusTraversable(false);
        btnReturn = new Button("Return");
        btnReturn.setOnAction(e -> eventCode(e));
        btnReturn.setFocusTraversable(false);

        HBox pnlBtns = new HBox(10, btnAdd, btnReset, btnReturn);

        pane.add(txtID, 0, 0, 3, 1);
        pane.add(txtName, 0, 1, 3, 1);
        pane.add(txtPrice, 0, 2, 3, 1);
        pane.add(txtAmt, 0, 3);
        pane.add(btnPlus, 1, 3);
        pane.add(btnMinus, 2, 3);
        pane.add(pnlBtns, 0, 4, 3, 1);
        pane.setAlignment(Pos.CENTER);
    }

    private void eventCode(Event e) {
        if (e.getSource() == btnPlus) {
            Integer newNum = Integer.parseInt(txtAmt.getText()) + 1;
            txtAmt.setText(newNum.toString());
        }
        if (e.getSource() == btnMinus) {
            if (Integer.parseInt(txtAmt.getText()) > 0) {
                Integer newNum = Integer.parseInt(txtAmt.getText()) - 1;
                txtAmt.setText(newNum.toString());
            }
        }
        if (e.getSource() == btnAdd) {
            Validator validator = new Validator();
            if (!txtID.getText().matches("[0-9]{1,5}")) {
                invalidInput("Please enter a numberic ID, 5 digit max.");
            } else if (!validator.isValidDouble(txtPrice.getText())
                    || Double.parseDouble(txtPrice.getText()) > 100000
                    || Double.parseDouble(txtPrice.getText()) < 0) {
                invalidInput("Please enter a valid Price");
            } else if (!validator.isValidInteger(txtAmt.getText())
                    || Integer.parseInt(txtAmt.getText()) > 100000
                    || Integer.parseInt(txtAmt.getText()) < 0) {
                invalidInput("Please enter a valid amount");
            } else {
                try {
                    addToItemList();
                    String contentText = txtName.getText() + " is added to the inventory.";
                    CheckOutPane.showMessage("Updated", Alert.AlertType.INFORMATION, contentText);
                    main.updateList();
                    clear();
                } catch (Exception ex) {
                    CheckOutPane.showMessage("Error", Alert.AlertType.ERROR, ex.toString());
                }
            }
        }
        if (e.getSource() == btnReset) {
            clear();
        }
        if (e.getSource() == btnReturn) {
            main.root.setCenter(new CheckOutPane());
            this.close();
        }
    }

    /**
     * This method will first check the validity of the entered data, and will
     * add to the itemList if the item doesn't exist
     */
    private void addToItemList() throws Exception {
        Item item = null;
        String[] names = main.itemList.getItemNames();
        String[] IDs = main.itemList.getItemIDs();
        String itemId = txtID.getText();
        double itemPrice = Double.parseDouble(txtPrice.getText());
        String itemName = txtName.getText();
        int inventory = Integer.parseInt(txtAmt.getText());
        if (itemId.length() < 5) {
                String zero = "";
                for (int i = 0; i < (5 - itemId.length()); i++) {
                    zero += "0";
                }
                itemId = zero + itemId;
            }
        for (int i = 0; i < names.length; i++) {
            if (IDs[i].equals(itemId) || names[i].equals(itemName)) {
                item = main.itemList.get(i);
                break;
            }
        }
        if (item != null) {
            item = foundExistItem(item);
            if (item != null) {
                UpdateItemScene update = new UpdateItemScene(item);
                update.showAndWait();
            }
        } else {
            ItemDb.AddItem(itemId, itemName, itemPrice, inventory);
        }
    }

    /**
     * The method will warn the user that the new item matches either the name
     * or ID already, and will ask if user wants to modify that item
     *
     * @param i index of the existing item
     */
    private Item foundExistItem(Item item) {
        Item foundItem = null;
        Alert alert = new Alert(AlertType.WARNING, "Item Exist Already\n"
                + "Do you want to modify this item?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        Optional<ButtonType> contSelect = alert.showAndWait();
        if (contSelect.get() == ButtonType.YES) {
            foundItem = item;
        }
        return foundItem;
    }

    /**
     * This method will display an error alert and abort the action
     *
     * @param contentText error message
     */
    private void invalidInput(String contentText) {
        Alert alert = new Alert(AlertType.ERROR, contentText);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * This method will reset the data inputed
     */
    private void clear() {
        txtID.clear();
        txtName.clear();
        txtPrice.clear();
        txtAmt.setText("0");
    }
}

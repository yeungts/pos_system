package posSystem.UI;

import posSystem.model.Item;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import posSystem.model.ItemDb;

/**
 * Class name: ReceiptScene Authors: Kendrick Tsz-Kin Yeung, Malcolm Busari,
 * Manpreet Kaur
 *
 * Description: This class will create a ReceiptScene scene that will be used in
 * other classes
 */
public class ReceiptScene extends Stage {

    private Scene scene;
    private BorderPane pane;
    private TextArea txaReceipt;
    private Button btnConfirm, btnCancel;
    POSSystem main = POSSystem.getInstance();
    private final double TAX_RATE = 0.13;
    private int transactionid;

    /**
     * Default constructor
     */
    public ReceiptScene() {
        super();
        getComponents();
        scene = new Scene(pane);
        this.setScene(scene);
        this.setTitle("Receipt");
        initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * This method will create all the necessary components for the pane, then
     * write the receipt into the TextArea
     */
    private void getComponents() {
        pane = new BorderPane();
        pane.setPadding(new Insets(0, 10, 5, 10));
        txaReceipt = new TextArea();
        txaReceipt.setEditable(false);
        txaReceipt.setMouseTransparent(true);
        txaReceipt.setPrefSize(300, 500);
        txaReceipt.setStyle("-fx-font: 12px Monospace;");

        btnConfirm = new Button("_Confirm");
        btnCancel = new Button("Canc_el");

        btnConfirm.setMnemonicParsing(true);
        btnConfirm.setOnAction(e -> eventCode(e));
        btnCancel.setMnemonicParsing(true);
        btnCancel.setOnAction(e -> eventCode(e));

        HBox pnlBtn = new HBox(10, btnConfirm, btnCancel);
        pnlBtn.setAlignment(Pos.CENTER);
        try {
            transactionid = ItemDb.getTransactionId();
            writeReceipt();
            pane.setCenter(txaReceipt);
            pane.setBottom(pnlBtn);
        } catch (Exception ex) {
            CheckOutPane.showMessage("Error", Alert.AlertType.ERROR, ex.toString());
        }
    }

    private void eventCode(Event e) {
        if (e.getSource() == btnConfirm) {
            try {
                for (Item item : CheckOutPane.selectedItems) {
                    ItemDb.purchaseItem(item.newInventory(), item.getPurchaseQty(), item.getItemId(), transactionid);

                }
            } catch (Exception ex) {
                CheckOutPane.showMessage("Error", Alert.AlertType.ERROR, ex.toString());
            }
            CheckOutPane.clear();
            HBox pnlBtn = new HBox(btnCancel);
            pnlBtn.setAlignment(Pos.CENTER);
            pane.setBottom(pnlBtn);
            btnCancel.setText("_Close");
        }
        if (e.getSource() == btnCancel) {
            this.close();
        }
    }

    /**
     * This method will get the receipt string from each selectedItem, calculate
     * the subtotal, tax and total, then display the receipt in the TextArea
     */
    private void writeReceipt() {
        String output = "\t\tReceipt\n\t     Transaction: " + transactionid;
        for (Item selectedItem : CheckOutPane.selectedItems) {
            output += selectedItem.toReceipt();
        }
        output += String.format("\n======================================="
                + "\n%9s\t\t\t%5.2f\n%9s\t\t\t%5.2f\n%9s\t\t\t%5.2f",
                "Subtotal:", getSubTotal(), "HST:", getHST(),
                "Total:", getTotal());
        txaReceipt.setText(output);
    }

    /**
     * This method will calculate the subtotal amount of all the selected Items
     *
     * @return the subtotal amount of all the selected Items
     */
    private double getSubTotal() {
        double total = 0;
        for (Item selectedItem : CheckOutPane.selectedItems) {
            total += selectedItem.getTotalPrice();
        }
        return total;
    }

    /**
     * This method will calculate the tax of all selectedItems
     *
     * @return the tax of all items
     */
    public double getHST() {
        return getSubTotal() * TAX_RATE;
    }

    /**
     * This method will calculate the subtotal after calculating the tax
     *
     * @return subtotal after calculating the tax
     */
    public double getTotal() {
        return getSubTotal() + getHST();
    }
}

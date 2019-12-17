package posSystem.UI;

import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import posSystem.model.ItemDb;
import posSystem.model.User;
import posSystem.model.UserDb;

/**
 * Class name: LoginPane
 * Authors: Kendrick Tsz-Kin Yeung, Malcolm Busari, Manpreet Kaur
 * Date: 9 Apr 2019
 *
 * Description:
 * This class will creates a LoginPane pane that will be used by other classes
 */
public class LoginPane extends GridPane {

    TextField txtName;
    PasswordField txtPassword;
    Button btnLogin, btnCancel;
    User user;
    POSSystem main = POSSystem.getInstance();

    /**
     * Default constructor
     */
    public LoginPane() {
        getComponents();
    }

    /**
     * This method will create all the necessary components for the pane
     */
    private void getComponents(){
        String imgSrc = this.getClass().getResource("/img/pos.png").toExternalForm();
        Image img = new Image(imgSrc);
        ImageView imgView = new ImageView(img);
        Label lblTitle = new Label("POS System");
        lblTitle.getStyleClass().add("login-pane-title");
        VBox title = new VBox(imgView, lblTitle);
        title.setAlignment(Pos.CENTER);
        Label lblName = new Label("Name:");
        lblName.getStyleClass().add("login-pane-labels");
        txtName = new TextField();
        txtName.getStyleClass().add("login-pane-text-field");
        GridPane.setConstraints(title, 0, 0, 2, 1);
        GridPane.setConstraints(lblName, 0, 1);
        GridPane.setConstraints(txtName, 1, 1);

        Label lblPassword = new Label("Password:");
        lblPassword.getStyleClass().add("login-pane-labels");
        txtPassword = new PasswordField();
        txtPassword.getStyleClass().add("login-pane-text-field");
        GridPane.setConstraints(lblPassword, 0, 2);
        GridPane.setConstraints(txtPassword, 1, 2);

        btnLogin = new Button("_Login");
        btnLogin.setOnAction(e -> eventCode(e));
        btnLogin.getStyleClass().add("login-pane-buttons");
        btnLogin.setMnemonicParsing(true);
        btnLogin.setDefaultButton(true);
        btnCancel = new Button("_Cancel");
        btnCancel.setOnAction(e -> eventCode(e));
        btnCancel.getStyleClass().add("login-pane-buttons");
        btnCancel.setMnemonicParsing(true);
        HBox buttons = new HBox(20, btnLogin, btnCancel);
        buttons.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttons, 0, 3, 2, 1);

        this.setAlignment(Pos.CENTER);
        this.setVgap(20);
        this.getChildren().addAll(title, lblName, txtName, lblPassword,
                txtPassword, buttons);
    }

    private void eventCode(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            try {
                if (loginCredentials()) {
                    main.setUser(user);
                    main.itemList = ItemDb.getItems();
                    main.root.setCenter(new CheckOutPane());
                    main.updateMenuItems();
                }
            } catch (Exception ignored) {
                CheckOutPane.showMessage("Error", Alert.AlertType.WARNING,
                            "Either username and password is not correct");
            }
        }
        if (e.getSource() == btnCancel) {
            txtName.clear();
            txtPassword.clear();
        }
    }
    
    /**
     * This method will check if the entered login credentials is correct
     * @return true if the entered login credentials is correct
     */
    private boolean loginCredentials() throws Exception{
        boolean valid = false;
        if (validUser() != null) {
            valid = true;
        }
        return valid;
    }
    
    private User validUser() throws Exception{
        String userName = txtName.getText();
        String password = txtPassword.getText();
        user = UserDb.getUser(userName, password);
        return user;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posSystem.model;

/**
 *
 * @author kenye
 */
public class User {
    private String ID;
    private String userName;
    private boolean manager;

    public User() {
    }

    public User(String ID, String userName, int manager) {
        this.ID = ID;
        this.userName = userName;
        if (manager == 0) {
            this.manager = false;
        } else if (manager == 1) {
            this.manager = true;
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    
}

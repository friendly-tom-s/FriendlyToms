package com.project;

/**
 * This servers the exact same purpose as the OrderHistory class so it inherits it.
 *
 * The logic in OrderHistory class depends on the value of the previousWin variable, this is why a separate class is used.
 */
public class AdminViewOrder extends OrderHistory {

    public AdminViewOrder(){
        super("View Order", "Back", "AdminMenu");
    }
}

========================

SYSTEM DESCRIPTION
========================



*****************************************************
*           Points Of Sale System(POSS)             *
*                                                   *
*        Created by: Kendrick Tsz-Kin Yeung,        *
*          Malcolm Busari,  Manpreet Kaur           *
*****************************************************

This application is created for Inventory management and sales purposes for small stores. When you run this
application you will be introduced to the login page, which you fill your username and password, as well as
this page contain two buttons(Login, Cancel).

Login username: user5197
Login password: HellOTesT

============================================================================================================

After logging in, you will reach the main pane of the application which contains two major areas. There is 
a TableView on the left half of page, which will display an item's information when you add an item to the
checkout list.

============================================================================================================

On the top of the main pane you will see a menubar which contains two menus (option and Help).

For option menu:
There are three items in the option menu: 'Log Out', 'add item to inventory' and 'exit'
	1. 'Log Out' is used to log out the current user and return to the login pane. If this button
	was clicked, a confirmation dialog will pop up and ask if the user really want to log out or not.
	user will be logged out if the user select 'yes' in the dialog.
	2. 'add item to inventory' is used to add a new item record. If this button was clicked, a input
	dialog will pop up and ask for the user to enter a passcode and to confirm that the userhave
	the privilege to modify the inventory. If the user enters the right passcode, a new scene will
	show up which allows you to add a new item.
	3. 'exit' is used to exit the program. If this button was clicked, a confirmation dialog will pop
	up and ask if the user really want to exit or not. The program will be terminated if the user 
	select 'yes' in the dialog.

passcode for privileged action: f4E84zQ6I

For Help menu:
There is only one item in the Help menu: 'About this system'
	1. 'About this system' is used to read the README.txt for the system that located in the program
	folder, and will write the content of the README.txt to a TextArea, then display the content with
	a new window.

============================================================================================================

On the right half of the page, you will see a bunch of contorl options

For each control option:
	1. ComboBox with the 'Select..' prompt text: This control stores the names of all the items inside
	the inventory, user can select an item with this comboBox.
	2. 'Search..' button: If the user click on the button, a input dialog will pop up and ask for user's
	input of either the name or ID or the item that the user wants to look up.
	3. TextField with the value of '0': The value of this contorl can only be changed by the '+' and '-'
	buttons.
	4. Buttons with '+' and '-': Will add/minus the value inside the TextField.
	5. 'Select this item' button: Will add the item to the tableView that was selected in the comboBox.
	6. 'Remove' button: If there's any item being selected in the tableView, that item will be removed
	if the button was clicked.
	7. A label that display the price: Will display the total price of all selected items.
	8. 'Check Out' button: If this button was clicked, a new window will pop up and show the receipt.
	9. 'Check Inventory' button: If this button was clicked, a scene that contain a tableView which
	display all the items and it's information.

============================================================================================================

Some testing case for the program:

	To test the validations while adding a new Item/modify an Item:
		Try to add a new item with the 'add item to inventory' button in the option menu, with the
		datas "ID=test, Name=Some names, Price=price, amount=amt".
		Then try with "ID=1, Name=Some names, Price=price, amount=amt".
		Then try with "ID=1, Name=Some names, Price=0.69, amount=amt".
		Then try with "ID=1, Name=Some names, Price=0.69, amount=8".
		You can also try with negative numbers.

		Try to modify the item that you have just created. (option > update an Item)
		**You can only edit the price and inventory**

		Try to delete the item that you have just created.
		Wipe out the datas stored inside "/datas/inventory.dat".

		Try to modify the empty inventory again. (option > update an Item)

	To test the 'search' button:
		Press the button, enter either a numberic ID or the name of an exist item.
		Press the button, enter the name of an non-exist item.

	To test the 'Select this item' button:
		Press the button while the comboBox was displaying "Select..".
		Press the button after you have selected an item from the comboBox with the qty value of '0'.
		Press the button after you have selected an item from the comboBox with the qty value more
		than '0'.

	To test the 'Remove' button:
		Press the button without selecting any item in the tableView.
		Press the button with selecting any item in the tableView.

	To test the 'Check out' button:
		Press the button when there's nothing in the tableView.
		Press the button when there's an item in the tableView.
		Press the button when there's more than one items in the tableView.

	To test the Check out methods:
		First check the inventory, pay attention to the item's inventory.
		Then add some items to check out, click the confirm button under the receipt.
		Then close the receipt and check the inventory again, check those item's new inventory.

	To read this file in the program:
		Either (Help > About This system) or CTRL + R.
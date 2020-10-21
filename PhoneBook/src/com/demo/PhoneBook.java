package com.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.demo.helper.PhoneBookDB;

public class PhoneBook implements ActionListener {
	private JFrame jframe;
	private JPanel panel;
	private static JTable table;
	private JButton add, remove, search, clear;
	private static JLabel firstName;
	private static JLabel secondName;
	private JLabel phone;
	private JLabel checkboxPrivate;
	private JLabel surnameForename;
	private JLabel forenameSurname;
	private static JTextField firstNameField;
	private static JTextField secondNameField;
	private static JTextField phoneField;
	private static JCheckBox checkbox;
	private static JRadioButton radio1;
	private static JRadioButton radio2;
	private JMenuBar menuBar;
	private JMenu menu1, menu2, menu3;
	private static DefaultTableModel model;

	public PhoneBook() {
		jframe = new JFrame("Phone Book");// setting title
		jframe.setBounds(200, 100, 950, 600);// 950px width and 600px height
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for closing frame when we hit cross icon in frame
		panel = new JPanel(null);
		panel.setBackground(Color.WHITE);

		// creating menu bar starts here
		menuBar = new JMenuBar();

		// menu
		menu1 = new JMenu("File");
		// add menu item
		JMenuItem exit = new JMenuItem("Exit");
		// adding menu items to menu3
		menu1.add(exit);
		// adding action event to menu items
		exit.addActionListener(this);

		menu2 = new JMenu("Edit");
		// add menu items
		JMenuItem addItem = new JMenuItem("Add");
		JMenuItem removeItem = new JMenuItem("Clear");
		JMenuItem editItem = new JMenuItem("Update");
		JMenuItem clearItem = new JMenuItem("Delete");
		addItem.addActionListener(this);
		removeItem.addActionListener(this);
		editItem.addActionListener(this);
		clearItem.addActionListener(this);

		// adding menu items to menu2
		menu2.add(addItem);
		menu2.add(removeItem);
		menu2.add(editItem);
		menu2.add(clearItem);

		menu3 = new JMenu("Help");
		// add menu item
		JMenuItem about = new JMenuItem("About");
		// adding menu items to menu3
		menu3.add(about);
		about.addActionListener(this);
		// adding Menu to Menu,Bar
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);

		// adding menu to panel
		jframe.setJMenuBar(menuBar);
		// menu bar creation ends here

		add = new JButton("ADD"); // JButton for adding value
		add.setBounds(500, 420, 200, 50);// setting position of button into panel
		add.addActionListener(this);

		remove = new JButton("CLEAR"); // JButton for removing value
		remove.setBounds(700, 420, 200, 50);
		remove.addActionListener(this);

		search = new JButton("UPDATE"); // JButton for Searching value
		search.setBounds(500, 470, 200, 50);
		search.addActionListener(this);

		clear = new JButton("DELETE"); // JButton for deleting value
		clear.setBounds(700, 470, 200, 50);
		clear.addActionListener(this);

		firstName = new JLabel("First name ");
		firstName.setBounds(500, 50, 80, 50);

		firstNameField = new JTextField();
		firstNameField.setBounds(610, 50, 200, 40);

		secondName = new JLabel("Seond name ");
		secondName.setBounds(500, 100, 80, 50);

		secondNameField = new JTextField();
		secondNameField.setBounds(610, 100, 200, 40);

		phone = new JLabel("Phone ");
		phone.setBounds(500, 150, 80, 50);

		phoneField = new JTextField();
		phoneField.setBounds(610, 150, 200, 40);

		checkboxPrivate = new JLabel("Private");
		checkboxPrivate.setBounds(500, 200, 80, 50);

		checkbox = new JCheckBox();
		checkbox.setBounds(610, 220, 20, 20);

		radio1 = new JRadioButton("fs");
		radio1.setBounds(500, 315, 20, 20);

		forenameSurname = new JLabel("Forename , Surname");
		forenameSurname.setBounds(530, 300, 200, 50);

		radio2 = new JRadioButton();
		radio2.setBounds(500, 365, 20, 20);
		radio2.addActionListener(this);

		surnameForename = new JLabel("Surname , Forename");
		surnameForename.setBounds(530, 350, 200, 50);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(radio1);
		btnGroup.add(radio2);
		
		// creating JTable
		table = new JTable();

		model = new DefaultTableModel();
		Object[] columns = { "id", "First Name", "Second Name", "Phone Number", "Private" };

		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setBackground(Color.white);
		table.setForeground(Color.black);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 480, 600);
		panel.add(pane);

		// Adding components on panel
		panel.add(add);
		panel.add(remove);
		panel.add(search);
		panel.add(clear);

		panel.add(firstName);
		panel.add(firstNameField);

		panel.add(secondName);
		panel.add(secondNameField);

		panel.add(checkboxPrivate);
		panel.add(checkbox);

		panel.add(phone);
		panel.add(phoneField);

		panel.add(surnameForename);
		panel.add(radio1);

		panel.add(forenameSurname);
		panel.add(radio2);

		jframe.add(panel);
		refreshTable();// for refreshing value while frame is loading/constructor is called
		dataShow();

		jframe.setVisible(true);
	}

	public static void refreshTable() {
		radio1.setSelected(true);
		
		try {
			// remove all data from JTable
			model.setRowCount(0);

			ResultSet resultSet = PhoneBookDB.selectRecord();

			while (resultSet.next()) {
				String isPrivate = resultSet.getString("private");
				if (isPrivate.contentEquals("0"))
					isPrivate = "private";
				else
					isPrivate = "not private";

				model.addRow(new Object[] { resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("phone"), isPrivate, });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Action Events For All Menu Items Starts Here

		// Action listener for menu item 'exit'
		if (e.getActionCommand().contentEquals("Exit")) {
			System.exit(0);
		}

		// Action Listener for 'about'
		if (e.getActionCommand().contentEquals("About")) {
			JFrame frame = new JFrame("oops!!");
			frame.setBounds(400, 200, 420, 200);
			frame.setVisible(true);
			frame.setLayout(null);
			Font font = new Font("", 1, 20);
			JLabel label = new JLabel("This application is still in trial version.");
			label.setBounds(10, 40, 400, 40);
			label.setFont(font);
			frame.add(label);
		}

		// Adding Items when clicking 'Add' button
		if (e.getActionCommand().contentEquals("ADD") || e.getActionCommand().contentEquals("Add")) {
			String fName = firstNameField.getText();
			String lName = secondNameField.getText();
			String phone = phoneField.getText();

			int isPrivate;
			if (checkbox.isSelected()) {
				isPrivate = 0;
			} else {
				isPrivate = 1;
			}
			// adding data to database table when button is clicked
			PhoneBookDB.insertRecord(fName, lName, phone, isPrivate);

			// refreshing JTable after inserting data to Database
			refreshTable();
		}

		// Remove event of 'REMOVE' button and 'Remove' menu-item
		if (e.getActionCommand().contentEquals("DELETE") || e.getActionCommand().contentEquals("Delete")) {
			String row = JOptionPane.showInputDialog(panel, "Enter a row number that you want to delete: ", "Question",
					JOptionPane.QUESTION_MESSAGE);

			int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure that you want to delete row?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			try {
				if (confirm == JOptionPane.YES_OPTION) {
					PhoneBookDB.deleteDelect(Integer.parseInt(row));
					model.setRowCount(0);
					refreshTable();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(panel, "You must enter numerical value representing a row",
						"Error Processing", JOptionPane.ERROR_MESSAGE);
			} catch (ArrayIndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(panel, "The row number that you have provided doesn't exist.",
						"Row doesn't exist", JOptionPane.ERROR_MESSAGE);

			}
			refreshTable();
		}

		// Search event of 'UPDATE' button and 'Update' menu-item
		if (e.getActionCommand().contentEquals("Update") || e.getActionCommand().contentEquals("UPDATE")) {
			// update event logic goes here
			int selectedRow = table.getSelectedRow();
			int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

			String fName = firstNameField.getText();
			String lName = secondNameField.getText();
			String phone = phoneField.getText();

			int isPrivate;
			if (checkbox.isSelected()) {
				isPrivate = 0;
			} else {
				isPrivate = 1;
			}
			PhoneBookDB.updateRecord(id, fName, lName, phone, isPrivate);

			refreshTable();
			clearTextField();
		}

		// Search event of 'CLEAR' button and 'Clear' menu-item
		if (e.getActionCommand().contentEquals("CLEAR") || e.getActionCommand().contentEquals("Clear")) {
			// clear event logic goes here
			clearTextField();
		}

	}

	public static void clearTextField() {
		firstNameField.setText("");
		secondNameField.setText("");
		phoneField.setText("");
		checkbox.setSelected(false);
	}

	public static void main(String[] args) {
		new PhoneBook();
	}

	public static void dataShow() {
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedRow = table.getSelectedRow();
				firstNameField.setText(model.getValueAt(selectedRow, 1).toString());
				secondNameField.setText(model.getValueAt(selectedRow, 2).toString());
				phoneField.setText(model.getValueAt(selectedRow, 3).toString());

				String value = model.getValueAt(selectedRow, 4).toString();
				if (value.contentEquals("not private"))
					checkbox.setSelected(false);
				else
					checkbox.setSelected(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

}

package org.fisco.bcos.asset.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fisco.bcos.asset.client.AssetClient;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class View extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField1;
	private JLabel lblReceiver;
	private JLabel lblTitle;
	private JLabel lblMsg;
	
	private AssetClient client;

	/**
	 * Create the frame.
	 */
	public View(AssetClient c, String title, boolean top, boolean down) {
		client = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 680, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(top);
		textField.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(160, 104, 360, 40);
		contentPane.add(textField);
		
		textField1 = new JTextField();
		textField1.setEditable(down);
		textField1.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField1.setColumns(10);
		textField1.setBounds(160, 170, 360, 40);
		contentPane.add(textField1);
		
		lblReceiver = new JLabel("收款方地址");
		lblReceiver.setHorizontalAlignment(SwingConstants.CENTER);
		lblReceiver.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblReceiver.setBounds(60, 104, 100, 40);
		contentPane.add(lblReceiver);
		
		JLabel lblAmount = new JLabel("单据金额");
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmount.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAmount.setBounds(60, 170, 100, 40);
		contentPane.add(lblAmount);
		
		lblTitle = new JLabel(title);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 28));
		lblTitle.setBounds(265, 37, 150, 40);
		contentPane.add(lblTitle);
		
		lblMsg = new JLabel("");
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMsg.setBounds(0, 233, 680, 20);
		contentPane.add(lblMsg);
		
		JButton btnLeft = new JButton("确认");
		btnLeft.addActionListener(this);
		btnLeft.setForeground(Color.WHITE);
		btnLeft.setFont(new Font("Dialog", Font.BOLD, 14));
		btnLeft.setBackground(new Color(102, 204, 0));
		btnLeft.setBounds(190, 280, 100, 30);
		contentPane.add(btnLeft);
		
		JButton btnRight = new JButton("返回");
		btnRight.addActionListener(this);
		btnRight.setForeground(Color.WHITE);
		btnRight.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRight.setBackground(new Color(204, 0, 0));
		btnRight.setBounds(390, 280, 100, 30);
		contentPane.add(btnRight);

		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "返回") {
			new Menu(client);
			this.dispose();
		}
		else if(arg0.getActionCommand() == "确认"){
			if(lblTitle.getText() == "签订单据") {
				System.out.println(textField.getText());
				System.out.println(new BigInteger(textField1.getText()));
				client.receivable_account(textField.getText(), new BigInteger(textField1.getText()));
			} else if(lblTitle.getText() == "转让单据") {
				client.transfer(textField.getText(), new BigInteger(textField1.getText()));
			} else if(lblTitle.getText() == "融资") {
				client.financing(new BigInteger(textField1.getText()));
			} else {
				client.account_settlement();
			}
		}
	}
}

package org.fisco.bcos.asset.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.event.ActionEvent;
import java.awt.Color;

import org.fisco.bcos.asset.client.*;
import org.fisco.bcos.asset.client.AssetClient;
import org.fisco.bcos.asset.client.Menu;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

public class CommpanySignUp extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JLabel lblAddr;
	private JLabel lblName;
	private JLabel lblProperty;
	private JLabel lblCredit;
	private JLabel lblSignup;
	private JLabel lblMsg;

	/**
	 * Create the frame.
	 */
	public CommpanySignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 680, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField1 = new JTextField();
		textField1.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField1.setBounds(160, 80, 360, 40);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		textField2 = new JTextField();
		textField2.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField2.setColumns(10);
		textField2.setBounds(160, 132, 360, 40);
		contentPane.add(textField2);
		
		textField3 = new JTextField();
		textField3.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField3.setColumns(10);
		textField3.setBounds(160, 184, 360, 40);
		contentPane.add(textField3);
		
		lblAddr = new JLabel("私钥");
		lblAddr.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddr.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAddr.setBounds(60, 80, 100, 40);
		contentPane.add(lblAddr);
		
		lblName = new JLabel("名称");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblName.setBounds(60, 132, 100, 40);
		contentPane.add(lblName);
		
		lblProperty = new JLabel("资产");
		lblProperty.setHorizontalAlignment(SwingConstants.CENTER);
		lblProperty.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblProperty.setBounds(60, 184, 100, 40);
		contentPane.add(lblProperty);
		
		lblSignup = new JLabel("企业注册");
		lblSignup.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignup.setFont(new Font("Dialog", Font.BOLD, 28));
		lblSignup.setBounds(265, 12, 150, 40);
		contentPane.add(lblSignup);
		
		JButton btnOkay = new JButton("确认");
		btnOkay.addActionListener(this);
		btnOkay.setFont(new Font("Dialog", Font.BOLD, 14));
		btnOkay.setBackground(Color.LIGHT_GRAY);
		btnOkay.setForeground(new Color(255, 255, 255));
		btnOkay.setBounds(184, 265, 106, 75);
		contentPane.add(btnOkay);
		
		JButton btnCancel = new JButton("返回");
		btnCancel.addActionListener(this);
		btnCancel.setForeground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(322, 265, 100, 75);
		contentPane.add(btnCancel);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "返回") {
			new SignIn();
			this.dispose();
		}
		else {
			AssetClient admin = new AssetClient();
			try {
				admin.init(AssetClient.getAdminKey());
				String addr = GenCredential.create(textField1.getText()).getAddress();
				admin.register(addr, textField2.getText(), new BigInteger(textField3.getText()), new BigInteger(textField4.getText()));
				System.out.printf("name: %s, address: %s\n", textField2.getText(), addr);
			} catch (Exception e) {
				System.out.println("Sign up failed:" + e.getMessage());
			}
			AssetClient client = new AssetClient();
			try {
				client.init(textField1.getText());
			} catch (Exception e) {
				System.out.println("Sign in failed:" + e.getMessage());
			}
			new Menu(client);
			this.dispose();
		}
	}
}

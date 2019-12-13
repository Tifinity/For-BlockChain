package org.fisco.bcos.asset.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fisco.bcos.asset.client.AssetClient;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class SignIn extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblAddr;
	private JLabel lblSignin;
	private JLabel lblMsg;

	public SignIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 680, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(160, 140, 360, 40);
		contentPane.add(textField);
		
		lblAddr = new JLabel("私钥");
		lblAddr.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddr.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAddr.setBounds(60, 140, 100, 40);
		contentPane.add(lblAddr);
		
		lblSignin = new JLabel("登录");
		lblSignin.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignin.setFont(new Font("Dialog", Font.BOLD, 28));
		lblSignin.setBounds(265, 67, 150, 40);
		contentPane.add(lblSignin);
		
		lblMsg = new JLabel("");
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setFont(new Font("DejaVu Sans Light", Font.BOLD, 12));
		lblMsg.setBounds(0, 300, 680, 15);
		contentPane.add(lblMsg);
		
		JButton btnSignin = new JButton("登录");
		btnSignin.addActionListener(this);
		btnSignin.setForeground(Color.WHITE);
		btnSignin.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSignin.setBackground(new Color(102, 204, 0));
		btnSignin.setBounds(160, 230, 100, 30);
		contentPane.add(btnSignin);
		
		JButton btnSignup1 = new JButton("银行注册");
		btnSignup1.addActionListener(this);
		btnSignup1.setForeground(Color.WHITE);
		btnSignup1.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSignup1.setBackground(new Color(204, 204, 0));
		btnSignup1.setBounds(290, 230, 100, 30);
		contentPane.add(btnSignup1);
		
		JButton btnSignup2 = new JButton("企业注册");
		btnSignup2.addActionListener(this);
		btnSignup2.setForeground(Color.WHITE);
		btnSignup2.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSignup2.setBackground(new Color(204, 0, 0));
		btnSignup2.setBounds(420, 230, 100, 30);
		contentPane.add(btnSignup2);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "登录") {
			AssetClient admin = new AssetClient();
			try {
				admin.init(AssetClient.getAdminKey());
			} catch (Exception e) {
				System.out.println("Sign in failed:" + e.getMessage());
			}
			String addr = GenCredential.create(textField.getText()).getAddress();

			try {
				AssetClient client = new AssetClient();
				client.init(textField.getText());
				System.out.println(addr);
				new Menu(client);
				this.dispose();
			} catch (Exception e) {
				System.out.println("Sign in failed:" + e.getMessage());
			}
		}
		else if(arg0.getActionCommand() == "银行注册"){
			new BankSignUp();
			this.dispose();
		}
		else {
			new CommpanySignUp();
			this.dispose();
		}
	}
}

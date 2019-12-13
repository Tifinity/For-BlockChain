package org.fisco.bcos.asset.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fisco.bcos.asset.client.AssetClient;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Menu extends JFrame implements ActionListener{

	private JPanel contentPane;
	private AssetClient client;

	/**
	 * Create the frame.
	 */
	public Menu(AssetClient c) {
		client = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 680, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTransaction = new JButton("签订单据");
		btnTransaction.addActionListener(this);
		btnTransaction.setBackground(new Color(102, 204, 0));
		btnTransaction.setForeground(new Color(255, 255, 255));
		btnTransaction.setFont(new Font("Dialog", Font.BOLD, 16));
		btnTransaction.setBounds(140, 85, 120, 40);
		contentPane.add(btnTransaction);
		
		JButton btnTransfer = new JButton("转让单据");
		btnTransfer.addActionListener(this);
		btnTransfer.setForeground(new Color(255, 255, 255));
		btnTransfer.setBackground(new Color(102, 204, 0));
		btnTransfer.setFont(new Font("Dialog", Font.BOLD, 16));
		btnTransfer.setBounds(420, 85, 120, 40);
		contentPane.add(btnTransfer);
		
		JButton btnFinancing = new JButton("申请融资");
		btnFinancing.addActionListener(this);
		btnFinancing.setForeground(new Color(255, 255, 255));
		btnFinancing.setBackground(new Color(102, 204, 0));
		btnFinancing.setFont(new Font("Dialog", Font.BOLD, 16));
		btnFinancing.setBounds(140, 160, 120, 40);
		contentPane.add(btnFinancing);
		
		JButton btnSettlement = new JButton("结算支付");
		btnSettlement.addActionListener(this);
		btnSettlement.setForeground(new Color(255, 255, 255));
		btnSettlement.setBackground(new Color(102, 204, 0));
		btnSettlement.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSettlement.setBounds(420, 160, 120, 40);
		contentPane.add(btnSettlement);
		
		JButton btnLoginout = new JButton("退出登录");
		btnLoginout.addActionListener(this);
		btnLoginout.setForeground(new Color(255, 255, 255));
		btnLoginout.setBackground(new Color(204, 0, 0));
		btnLoginout.setFont(new Font("Dialog", Font.BOLD, 16));
		btnLoginout.setBounds(280, 270, 120, 40);
		contentPane.add(btnLoginout);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "签订单据") {
			new View(client, arg0.getActionCommand(), true, true);
		} else if(arg0.getActionCommand() == "转让单据") {
			new View(client, arg0.getActionCommand(), true, true);
		} else if(arg0.getActionCommand() == "申请融资") {
			new View(client, arg0.getActionCommand(), false, true);
		} else if(arg0.getActionCommand() == "结算支付") {
			new View(client, arg0.getActionCommand(), true, false);
		} else {
			new SignIn();
			this.dispose();
		}
		this.dispose();
	}
}

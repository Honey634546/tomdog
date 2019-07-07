package com.honey.ui;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Server;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private static TextArea textArea1;
	
	public ServerGUI()
	{
		super("MyServer");
		
		FlowLayout layout=new FlowLayout();
		JLabel lable1=new JLabel("根目录:");
		JLabel lable2=new JLabel("端口");
		JTextField textField1 = new JTextField(30);
		JTextField textField2=new JTextField(5);
		JButton button1=new JButton("浏览");
		JButton button2=new JButton();
		textArea1=new TextArea();
		JScrollPane logScrollPane=new JScrollPane();
		
		textArea1.setColumns(50);
		textArea1.setRows(30);
		logScrollPane.setViewportView(textArea1);
		
		
		
		button2.setText("开启");
		
		
		layout.setAlignment(FlowLayout.LEFT);
		this.setLayout(layout);
		this.setBounds(400, 400, 600,700);
		this.setVisible(true);
//		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		button1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser=new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);;
				chooser.showDialog(new JLabel(), "选择");
				File file=chooser.getSelectedFile();
				textField1.setText(file.getAbsoluteFile().toString());
			}
		});
		
		button2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				textField1.setText("E:\\eclipse-workspace\\Server9\\WebContent\\");
				String docroot=textField1.getText();
//				String	docroot="E:\\eclipse-workspace\\Server7\\WebContent\\";
//				textField2.setText("80");
				int port=Integer.parseInt(textField2.getText());
//				int	port=80;
				Server webserver=new Server(docroot,port);
				webserver.start();
			}
			
		});
		
		
		this.add(lable1);
		this.add(textField1);
		this.add(button1);
		this.add(lable2);
		this.add(textField2);
		this.add(button2);
		this.add(textArea1);
		
		

		
	}

	public static void main(String[] args)
	{
		new ServerGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void addlog(String log)
	{
		textArea1.append(new Date().toString());
		textArea1.append("\r\n");
		textArea1.append(log);
		textArea1.append("\r\n");

	}


	
}

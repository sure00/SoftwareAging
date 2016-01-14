package Y2U;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;


public class main extends JFrame{

	private JPanel mainPanel = new JPanel();
	private JButton openButton = new JButton();
	private JButton loadApplicationButton = new JButton();
	private JButton leftButton = new JButton();
	private JButton rightButton = new JButton();
	private JButton saveButton = new JButton();
	private JLabel label = new JLabel();
	File file = null;

	private JFileChooser fileChooser = new JFileChooser("C:\\Users\\shuo\\Desktop\\test\\bridge.xml");
	
	JTextArea lefttextarea = new JTextArea();	
	JScrollPane leftPane = new JScrollPane(lefttextarea);
		
	JTextArea righttextarea = new JTextArea();
	JScrollPane rightPane = new JScrollPane(righttextarea);


	public main(String title) {
		setTitle("UPPAAL YANKINDLE TRANSFOR");
		setPreferredSize(new Dimension(1280, 960));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponent();
		addData();
		pack();
		setVisible(true);
	}

	private static void createAndShowGUI() {
		new main("uppaal yankindel transfrom");
	}

	private void initComponent() {

		label.setText("");
		loadApplicationButton.setText("Load Model Application");

		openButton.setText("Open File");

		leftPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		leftPane.setPreferredSize(new Dimension(960,540));
		rightPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPane.setPreferredSize(new Dimension(960,540));
		
		leftButton.setText("UPPAAL to Yakindu");
		rightButton.setText("Yakindu to UPPAAL");
		saveButton.setText("Save target file");
		mainPanel.setBorder(BorderFactory.createTitledBorder("version 1.0"));
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
/*	
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		mainPanel.add(label, c);
*/		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.SOUTH;
		c.fill = GridBagConstraints.HORIZONTAL;		
		mainPanel.add(loadApplicationButton, c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(saveButton, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(openButton, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		mainPanel.add(leftPane, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.SOUTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(leftButton, c);

		c.gridx = 2;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(rightButton, c);

		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		mainPanel.add(rightPane, c);

		this.getContentPane().add(mainPanel);
	}

	private void addData() {
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openItem();
			}
		});
		
		loadApplicationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadApplicationButton() ;
			}
		});

		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Uppaal2YakindoItem();
			}
		});

		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Yakindo2UppaalItem();
			}
		});
		
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Yakindo2UppaalItem();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		
	}

	private void saveFile() {
		int result;
		File file = null;
				
		fileChooser.setApproveButtonText("Save");
		fileChooser.setDialogTitle("Save File");
		result = fileChooser.showOpenDialog(this);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			label.setText("The file which selected:" +file.getName());
		}
		
		else if(result == JFileChooser.CANCEL_OPTION)
		{
			label.setText("Cancel saving file");
		}

		FileOutputStream fileOutStream = null;
		if (file !=null)
		{
			try
			{
				fileOutStream = new FileOutputStream(file);
			}
			catch(FileNotFoundException fe)
			{
				label.setText("File not Found");
				return;
			}
			String content = righttextarea.getText();
			try
			{
				fileOutStream.write(content.getBytes());
			}
			catch(IOException ioe)
			{
				label.setText("File Writing fail");
			}
			finally
			{
				try
				{
					if(fileOutStream != null)
						fileOutStream.close();
				}
				catch(IOException ioe2){}
			}
		
		}

	}
	
	private void loadApplicationButton() {
		int result;
		File path = null;
		
		System.out.println("debug");
		
		fileChooser.setApproveButtonText("Confirm");
		fileChooser.setDialogTitle("Open Application");
		result = fileChooser.showOpenDialog(this);
				
		if(result == JFileChooser.APPROVE_OPTION)
		{
			path = fileChooser.getSelectedFile();
			label.setText("The file which selected:" +path.getName());
			//System.out.println(file.getPath());
			
			Process ps;
			try {
				ps = Runtime.getRuntime().exec(new String[]{"java","-jar", path.getPath()});
		        ps.waitFor();
		        java.io.InputStream is=ps.getInputStream();
		        byte b[]=new byte[is.available()];
		        is.read(b,0,b.length);
		        System.out.println(new String(b));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if(result == JFileChooser.CANCEL_OPTION)
		{
			label.setText("There no any file");
		}
	}
	
	private void openItem() {
		int result;
		
				
		fileChooser.setApproveButtonText("Confirm");
		fileChooser.setDialogTitle("Open File");
		result = fileChooser.showOpenDialog(this);
		lefttextarea.setText("");
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			label.setText("The file which selected:" +file.getName());
		}
		
		else if(result == JFileChooser.CANCEL_OPTION)
		{
			label.setText("There no any file");
		}
		
		FileInputStream fileInStream = null;
		if (file !=null)
		{
			try
			{
				fileInStream = new FileInputStream(file);
			}
			catch(FileNotFoundException fe)
			{
				label.setText("File not Found");
				return;
			}
			int readbyte;
			try
			{
				while((readbyte = fileInStream.read()) != -1)
				{
					lefttextarea.append(String.valueOf((char)readbyte));
				}
			}
			catch(IOException ioe)
			{
				label.setText("Read File fail");
			}
			finally
			{
				try
				{
					if(fileInStream !=null)
						fileInStream.close();
				}
				catch(IOException ioe2){}
			}
		}	
	}

	private void Uppaal2YakindoItem() {
		String inPath = "";
		String outPath = "";
		// mode 0 U2Y mode 1 Y2U
		int mode = 0;
		
		File outfile = new File("C:\\Users\\Public\\tmp.txt");
			
		inPath = file.getPath();
		outPath = outfile.getPath();	
		
		System.out.print(file.getPath());
		System.out.print(outfile.getPath());
		
		Translator translator = new Translator(inPath, outPath, mode);
		
		translator.U2Ytranslate();
		
		FileInputStream fileInStream = null;
		if (outfile !=null)
		{
			try
			{
				fileInStream = new FileInputStream(outfile);
			}
			catch(FileNotFoundException fe)
			{
				label.setText("File not Found");
				return;
			}
			int readbyte;
			try
			{
				while((readbyte = fileInStream.read()) != -1)
				{
					righttextarea.append(String.valueOf((char)readbyte));
				}
			}
			catch(IOException ioe)
			{
				label.setText("Read File fail");
			}
			finally
			{
				try
				{
					if(fileInStream !=null)
						fileInStream.close();
				}
				catch(IOException ioe2){}
			}
		}	
		
		outfile.delete();
		
	}

	private void Yakindo2UppaalItem() {
		String inPath = "";
		String outPath = "";
		int mode = 1;
		File outfile = new File("C:\\Users\\Public\\tmp.txt");
			
		inPath = file.getPath();
		outPath = outfile.getPath();	
		
		System.out.print(outfile.getPath());
		
		Translator translator = new Translator(inPath, outPath, mode);
		
		translator.Y2Utranslate();
		
		FileInputStream fileInStream = null;
		if (outfile !=null)
		{	
			//righttextarea.setText(null);
			try
			{
				fileInStream = new FileInputStream(outfile);
			}
			catch(FileNotFoundException fe)
			{
				label.setText("File not Found");
				return;
			}
			int readbyte;
			try
			{
				while((readbyte = fileInStream.read()) != -1)
				{
					righttextarea.append(String.valueOf((char)readbyte));
				}
			}
			catch(IOException ioe)
			{
				label.setText("Read File fail");
			}
			finally
			{
				try
				{
					if(fileInStream !=null)
						fileInStream.close();
				}
				catch(IOException ioe2){}
			}
		}	
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

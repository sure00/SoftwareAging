package Y2U;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;


public class Y2U {

	private JFrame frmYu;
	private JTextField textField;
	private JTextField textField_1;
	private JFileChooser fileChooser = new JFileChooser("C:\\");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Y2U window = new Y2U();
					window.frmYu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Y2U() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmYu = new JFrame();
		frmYu.setTitle("Y2U");
		frmYu.setBounds(100, 100, 481, 273);
		frmYu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmYu.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Choose UPPAAL File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File uppaal_patch = null;
				uppaal_patch = openItem();
				System.out.println(uppaal_patch.getPath());
			}
		});
		btnNewButton.setBounds(147, 11, 134, 23);
		frmYu.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("Choose Yakindu File");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File yakindu_patch = null;
				yakindu_patch = openItem();
				System.out.println(yakindu_patch.getPath());
			}
		});
		button.setBounds(10, 11, 127, 23);
		frmYu.getContentPane().add(button);
		
		JButton btnTransform = new JButton("Transform");
		btnTransform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTransform.setBounds(291, 11, 89, 23);
		frmYu.getContentPane().add(btnTransform);
		
		JLabel lblNewLabel = new JLabel("UPPAAL File:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 75, 70, 20);
		frmYu.getContentPane().add(lblNewLabel);
		
		JLabel lblYakinduFile = new JLabel("Yakindu File:");
		lblYakinduFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblYakinduFile.setBounds(10, 45, 70, 20);
		frmYu.getContentPane().add(lblYakinduFile);		
		
		textField = new JTextField();
		textField.setBounds(80, 45, 300, 20);
		frmYu.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(80, 75, 300, 20);
		frmYu.getContentPane().add(textField_1);
		
		JLabel lblTransformationResults = new JLabel("Transformation Results:");
		lblTransformationResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransformationResults.setBounds(10, 105, 120, 20);
		frmYu.getContentPane().add(lblTransformationResults);
		
		JTextArea txtrTransformSuccessfully = new JTextArea();
		txtrTransformSuccessfully.setText("Transform Successfully.");
		txtrTransformSuccessfully.setEditable(false);
		txtrTransformSuccessfully.setLineWrap(true);
		txtrTransformSuccessfully.setBounds(10, 125, 370, 99);
		frmYu.getContentPane().add(txtrTransformSuccessfully);

	}
	
	private File openItem() {
		int result;
		File file = null;
				
		fileChooser.setApproveButtonText("Confirm");
		fileChooser.setDialogTitle("Open File");
		result = fileChooser.showOpenDialog(frmYu);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();

		}
		

				
	/*	
		if (field.getText() != null && !field.getText().equals("")) {
			((DefaultListModel) leftList.getModel())
					.addElement(field.getText());
			field.setText("");
		}*/
	return file;
	}
}

package Client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Win {
	private static String host;
	private static int port;
	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JTextField textField_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		host=args[0];
		port=Integer.parseInt(args[1]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Win window = new Win();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Win() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Client user=new Client();
		textField = new JTextField();
		textField.setBounds(55, 27, 325, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				if(user.run(host,port))
					textField_1.setText("Connection Successful");
				else
					textField_1.setText("Connection Failed");
			}
		});
		btnNewButton.setBounds(74, 64, 113, 27);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Query");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				if(textField.getText().equals(""))
				{
					textField_1.setText("Please enter the correct input.");
				}
				else
				{
					String response=user.query(textField.getText());
					textField_1.setText(response);
				}
			}
		});
		btnNewButton_1.setBounds(74, 122, 113, 27);
		frame.getContentPane().add(btnNewButton_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(55, 162, 325, 78);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton_2 = new JButton("Add");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				if(textField.getText().equals(""))
				{
					textField_1.setText("Please enter the correct input.");
				}
				else
				{
					String response=user.add(textField.getText());
					textField_1.setText(response);
				}
			}
		});
		btnNewButton_2.setBounds(248, 64, 113, 27);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Remove");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
				if(textField.getText().equals(""))
				{
					textField_1.setText("Please enter the correct input.");
				}
				else
				{
					String response=user.remove(textField.getText());
					textField_1.setText(response);
				}
			}
		});
		btnNewButton_3.setBounds(248, 122, 113, 27);
		frame.getContentPane().add(btnNewButton_3);
	}
}

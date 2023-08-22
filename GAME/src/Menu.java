
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;


class Menu extends JPanel
{
	public Menu() {
		setLayout(null);
		setSize(1000, 150);
		
		
		JButton btnNewButton_1 = new JButton("\u041A\u0430\u0441\u0442\u043E\u043C\u0438\u0437\u0430\u0446\u0438\u044F");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 6));
		btnNewButton_1.setBounds(10, 13, 70, 23);
		add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Menu.class.getResource("/img/pers.png")));
		lblNewLabel_1.setBounds(423, 11, 118, 122);
		add(lblNewLabel_1);
		
		JButton button_1 = new JButton("\u043D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0438");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 6));
		button_1.setBounds(10, 45, 70, 23);
		add(button_1);
		
		JButton button_2 = new JButton("\u041A\u0430\u0441\u0442\u043E\u043C\u0438\u0437\u0430\u0446\u0438\u044F");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 6));
		button_2.setBounds(10, 79, 70, 23);
		add(button_2);
		
		JButton button_3 = new JButton("\u041A\u0430\u0441\u0442\u043E\u043C\u0438\u0437\u0430\u0446\u0438\u044F");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 6));
		button_3.setBounds(10, 110, 70, 23);
		add(button_3);
		
		JLabel lblNewLabel = new JLabel("HP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.RED);
		lblNewLabel.setBounds(304, 112, 118, 14);
		lblNewLabel.setOpaque(true);
		add(lblNewLabel);
		
		JLabel lblMana = new JLabel("Mana");
		lblMana.setHorizontalAlignment(SwingConstants.CENTER);
		lblMana.setForeground(Color.WHITE);
		lblMana.setBackground(Color.BLUE);
		lblMana.setBounds(304, 93, 118, 14);
		lblMana.setOpaque(true);
		add(lblMana);
	}
}

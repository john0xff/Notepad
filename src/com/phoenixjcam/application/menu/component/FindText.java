package com.phoenixjcam.application.menu.component;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class FindText extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Notepad notepad;

	private JLabel lblFind;
	private JLabel lblReplace;
	private JTextField txtfFind;
	private JTextField txtfReplace;

	private JButton btnFind;
	private JButton btnFindNext;
	private JButton btnReplace;
	private JButton btnReplaceAll;
	private JButton btnCancel;

	private int startIndex = 0;

	public FindText(Notepad nt) {
		setTitle("Finder");
		notepad = nt;
		init();

		add(lblFind());
		add(lblReplace());
		add(txtfFind());
		add(txtfReplace());
		add(btnFind());
		add(btnFindNext());
		add(btnReplace());
		add(btnReplaceAll());
		add(btnCancel());
	}

	public void init() {
		setLayout(null);

		int width = 400;
		int height = 230;
		setSize(width, height);
		setResizable(false);

		Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setLocation((centerPoint.x) - (width / 2), (centerPoint.y)
				- (height / 2));
	}

	public JLabel lblFind() {
		lblFind = new JLabel("Find:");
		lblFind.setBounds(10, 10, 120, 20);
		lblFind.setFont(new Font("Arial", Font.PLAIN, 16));
		return lblFind;
	}

	public JLabel lblReplace() {
		lblReplace = new JLabel("	Replace:");
		lblReplace.setBounds(10, 50, 120, 20);
		lblReplace.setFont(new Font("Arial", Font.PLAIN, 16));
		return lblReplace;
	}

	public JTextField txtfFind() {
		txtfFind = new JTextField(30);
		txtfFind.setBounds(80, 10, 120, 20);
		return txtfFind;
	}

	public JTextField txtfReplace() {
		txtfReplace = new JTextField(30);
		txtfReplace.setBounds(80, 50, 120, 20);
		return txtfReplace;
	}

	public JButton btnFind() {
		btnFind = new JButton("Find");
		btnFind.setBounds(240, 10, 120, 25);
		btnFind.addActionListener(this);
		return btnFind;
	}

	public JButton btnFindNext() {
		btnFindNext = new JButton("Find Next");
		btnFindNext.setBounds(240, 45, 120, 25);
		btnFindNext.addActionListener(this);
		return btnFindNext;
	}

	public JButton btnReplace() {
		btnReplace = new JButton("Replace");
		btnReplace.setBounds(240, 80, 120, 25);
		btnReplace.addActionListener(this);
		return btnReplace;
	}

	public JButton btnReplaceAll() {
		btnReplaceAll = new JButton("Replace All");
		btnReplaceAll.setBounds(240, 115, 120, 25);
		btnReplaceAll.addActionListener(this);
		return btnReplaceAll;
	}

	public JButton btnCancel() {
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(240, 150, 120, 25);
		btnCancel.addActionListener(this);
		return btnCancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();

		if (event == btnFind) {
			actionFind();
		} else if (event == btnFindNext) {
			actionFindNext();
		} else if (event == btnReplace) {
			actionReplace();
		} else if (event == btnReplaceAll) {
			actionReplaceAll();
		} else if (event == btnCancel) {
			actionCancel();
		}
	}

	public void actionFind() {
		String currentTxt = notepad.getTxtArea().getText();

		int start = currentTxt.indexOf(txtfFind.getText());

		if (start == -1) {
			startIndex = 0;
			JOptionPane.showMessageDialog(null,
					"Could not find " + txtfFind.getText() + "!");
			return;
		}
		if (start == currentTxt.lastIndexOf(txtfFind.getText())) {
			startIndex = 0;
		}

		int end = start + txtfFind.getText().length();
		notepad.getTxtArea().select(start, end);
	}

	public void actionFindNext() {
		String selectedTxt = notepad.getTxtArea().getSelectedText();

		try {
			selectedTxt.equals("");
		} catch (NullPointerException e) {
			selectedTxt = txtfFind.getText();
			try {
				selectedTxt.equals("");
			} catch (NullPointerException e2) {
				selectedTxt = JOptionPane.showInputDialog("Find:");
				txtfFind.setText(selectedTxt);
			}
		}
		try {
			String currentTxt = notepad.getTxtArea().getText();
			int start = currentTxt.indexOf(selectedTxt, startIndex);

			int end = start + selectedTxt.length();
			notepad.getTxtArea().select(start, end);
			startIndex = end + 1;

			if (start == currentTxt.lastIndexOf(selectedTxt)) {
				startIndex = 0;
			}
		} catch (NullPointerException e) {
		}
	}

	public void actionReplace() {
		try {
			actionFind();
			notepad.getTxtArea().replaceSelection(txtfReplace.getText());
		} catch (NullPointerException e) {
			System.out.print("Null Pointer Exception: " + e);
		}
	}

	public void actionReplaceAll() {
		JTextArea txtArea = notepad.getTxtArea();
		String currentTxt = notepad.getTxtArea().getText();

		String regex = txtfFind.getText();
		String replacement = txtfReplace.getText();

		txtArea.setText(currentTxt.replaceAll(regex, replacement));
	}

	public void actionCancel() {
		this.setVisible(false);
	}
}

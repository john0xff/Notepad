package com.phoenixjcam.application.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import com.phoenixjcam.application.Notepad;
import com.phoenixjcam.application.menu.component.FormatFont;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class FormatMenu implements ActionListener {
	private Notepad notepad;
	private FormatFont format;

	private JMenu mnFormat;
	private JMenuItem mntmFont;
	private JMenuItem mntmTxtColor;
	private JCheckBoxMenuItem checkbWrap;
	private JMenuItem mntmToUpper;
	private JMenuItem mntnToLower;

	public FormatMenu(Notepad nt) {
		notepad = nt;
		format = new FormatFont(nt);
	}

	public JMenu formatItems() {
		mnFormat = new JMenu("Format");

		mnFormat.add(formatFont());
		mnFormat.add(formatColor());
		mnFormat.add(formatWordWrap());
		mnFormat.add(formatConvertUpper());
		mnFormat.add(formatConvertLower());

		return mnFormat;
	}

	public JMenuItem formatFont() {
		mntmFont = new JMenuItem("Font Format");
		mntmFont.addActionListener(this);
		return mntmFont;
	}

	public JMenuItem formatColor() {
		mntmTxtColor = new JMenuItem("Choose Text Color");
		mntmTxtColor.addActionListener(this);
		return mntmTxtColor;
	}

	public JCheckBoxMenuItem formatWordWrap() {
		checkbWrap = new JCheckBoxMenuItem("WordWrap");
		checkbWrap.addActionListener(this);
		return checkbWrap;
	}

	public JMenuItem formatConvertUpper() {
		mntmToUpper = new JMenuItem("UPPERCASE");
		mntmToUpper.addActionListener(this);
		return mntmToUpper;
	}

	public JMenuItem formatConvertLower() {
		mntnToLower = new JMenuItem("lowercase");
		mntnToLower.addActionListener(this);
		return mntnToLower;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();

		if (event == mntmFont) {
			actionFormatFont();
		} else if (event == mntmTxtColor) {
			actionFormatColorChooser();
		} else if (event == checkbWrap) {
			actionWordWrap();
		} else if (event == mntmToUpper) {
			actionFormatConvertUpper();
		} else if (event == mntnToLower) {
			actionFormatConvertLower();
		}
	}

	public void actionFormatFont() {
		format.setVisible(true);
	}

	public void actionFormatColorChooser() {
		Color colorChooser = JColorChooser.showDialog(null, "Color Chooser",
				Color.BLACK);
		JTextArea txtArea = notepad.getTxtArea();
		txtArea.setForeground(colorChooser);
	}

	public void actionWordWrap() {
		JTextArea txtArea = notepad.getTxtArea();
		if (txtArea.getLineWrap() == false) {
			txtArea.setLineWrap(true);
		} else {
			txtArea.setLineWrap(false);
		}
	}

	public void actionFormatConvertUpper() {
		JTextArea txtArea = notepad.getTxtArea();
		String currentTxt = txtArea.getText();
		try {
			int startPos = txtArea.getSelectionStart();
			int endPos = txtArea.getSelectionEnd();
			String startStr = currentTxt.substring(0, startPos);
			String endStr = currentTxt.substring(endPos);
			String convert = txtArea.getSelectedText().toUpperCase();
			txtArea.setText(startStr + convert + endStr);
			txtArea.select(startPos, endPos);
		} catch (NullPointerException e) {
		}
	}

	public void actionFormatConvertLower() {
		JTextArea txtArea = notepad.getTxtArea();
		String currentTxt = txtArea.getText();
		try {
			int startPos = txtArea.getSelectionStart();
			int endPos = txtArea.getSelectionEnd();
			String startStr = currentTxt.substring(0, startPos);
			String endStr = currentTxt.substring(endPos);
			String convert = txtArea.getSelectedText().toLowerCase();
			txtArea.setText(startStr + convert + endStr);
			txtArea.select(startPos, endPos);
		} catch (NullPointerException e) {
		}
	}
}

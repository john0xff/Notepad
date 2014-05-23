package com.phoenixjcam.application.menu;

import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class PopupMenu {

	private Notepad notepad;
	private JPopupMenu popupMenu;
	private EditMenu mnEdit;
	private FormatMenu mnFormat;
	private JSeparator sep;

	public PopupMenu(Notepad nt) {
		notepad = nt;
		popupMenu = new JPopupMenu();
		mnEdit = new EditMenu(notepad);
		mnFormat = new FormatMenu(notepad);
		notepad.getTxtArea().setComponentPopupMenu(popupItems());
	}

	public JPopupMenu popupItems() {

		popupMenu.add(mnEdit.editUndo());
		popupMenu.add(mnEdit.editRedo());
		popupMenu.add(getSeparator());

		popupMenu.add(mnEdit.editCut());
		popupMenu.add(mnEdit.editCopy());
		popupMenu.add(mnEdit.editPaste());
		popupMenu.add(mnEdit.editDelete());
		popupMenu.add(getSeparator());

		popupMenu.add(mnEdit.editSelectAll());
		popupMenu.add(getSeparator());

		popupMenu.add(mnFormat.formatConvertUpper());
		popupMenu.add(mnFormat.formatConvertLower());
		popupMenu.add(getSeparator());

		popupMenu.add(mnFormat.formatFont());
		popupMenu.add(mnFormat.formatColor());
		return popupMenu;
	}

	public JSeparator getSeparator() {
		sep = new JSeparator();
		return sep;
	}
}

package com.phoenixjcam.application.menu;

import javax.swing.JMenuBar;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class BarMenu {
	private JMenuBar mnBar;

	private FileMenu mnFile;
	private EditMenu mnEdit;
	private FormatMenu mnFormat;
	private StyleMenu mnStyle;
	private HelpMenu mnHelp;

	public BarMenu(Notepad nt) {
		mnFile = new FileMenu(nt);
		mnEdit = new EditMenu(nt);
		mnFormat = new FormatMenu(nt);
		mnStyle = new StyleMenu(nt);
		mnHelp = new HelpMenu(nt);

		mnBar = new JMenuBar();
		nt.setJMenuBar(mnBar);

		mnBar.add(mnFile.fileItems());
		mnBar.add(mnEdit.editItems());
		mnBar.add(mnFormat.formatItems());
		mnBar.add(mnStyle.styleItems());
		mnBar.add(mnHelp.helpItems());
	}
}

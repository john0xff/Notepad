package com.phoenixjcam.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class HelpMenu implements ActionListener
{
	private Notepad notepad;
	private JMenu mnHelp;
	private JMenuItem showHelp;
	private JMenuItem about;

	public HelpMenu(Notepad nt)
	{
		notepad = nt;
	}

	public JMenu helpItems()
	{
		mnHelp = new JMenu("Help");
		mnHelp.add(helper());
		mnHelp.add(notepadInfo());
		return mnHelp;
	}

	public JMenuItem helper()
	{
		showHelp = new JMenuItem("Show Help");
		showHelp.setAccelerator(KeyStroke.getKeyStroke("shift F1"));
		showHelp.addActionListener(this);
		return showHelp;
	}

	public JMenuItem notepadInfo()
	{
		about = new JMenuItem("Notepad - Info");
		about.setAccelerator(KeyStroke.getKeyStroke("F1"));
		about.addActionListener(this);
		return about;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();

		if (event == showHelp)
		{
			actionHelp();
		}
		else if (event == about)
		{
			actionNotepadInfo();
		}
	}

	public void actionHelp()
	{
		
		JOptionPane.showMessageDialog(notepad, "phoenixjcam.com");
	}

	public void actionNotepadInfo()
	{
		JOptionPane.showMessageDialog(notepad, "author Bart Bien \nphoenixjcam.com");
	}
}

package com.phoenixjcam.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class StyleMenu implements ActionListener {
    private Notepad notepad;
    private JMenu mnStyleUI;
    private ButtonGroup btngStyle;
    private JCheckBoxMenuItem winStyle;
    private JCheckBoxMenuItem metalStyle;
    private JCheckBoxMenuItem nimbusStyle;

    public StyleMenu(Notepad nt) {
	notepad = nt;
	btngStyle = new ButtonGroup();
	actionWindowsOption();
    }

    public JMenu styleItems() {
	mnStyleUI = new JMenu("Style");
	mnStyleUI.add(windowsOption());
	mnStyleUI.add(metalOption());
	mnStyleUI.add(nimbusOption());

	return mnStyleUI;
    }

    public JCheckBoxMenuItem windowsOption() {
	winStyle = new JCheckBoxMenuItem("Windows Look");
	winStyle.addActionListener(this);
	btngStyle.add(winStyle);
	winStyle.setSelected(true);
	return winStyle;
    }

    public JCheckBoxMenuItem metalOption() {
	metalStyle = new JCheckBoxMenuItem("Metal Look");
	metalStyle.addActionListener(this);
	btngStyle.add(metalStyle);
	return metalStyle;
    }

    public JCheckBoxMenuItem nimbusOption() {
	nimbusStyle = new JCheckBoxMenuItem("Nimbus Look");
	nimbusStyle.addActionListener(this);
	btngStyle.add(nimbusStyle);
	return nimbusStyle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object event = e.getSource();

	if (event == winStyle) {
	    actionWindowsOption();
	} else if (event == metalStyle) {
	    actionMetalOption();
	} else if (event == nimbusStyle) {
	    actionNimbusOption();
	}
    }

    public void actionWindowsOption() {
	try {
	    UIManager
		    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}

	SwingUtilities.updateComponentTreeUI(notepad);
    }

    public void actionMetalOption() {
	try {
	    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}

	SwingUtilities.updateComponentTreeUI(notepad);
    }

    public void actionNimbusOption() {
	try {
	    UIManager
		    .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}

	SwingUtilities.updateComponentTreeUI(notepad);
    }
}

package com.phoenixjcam.application;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.phoenixjcam.application.menu.BarMenu;
import com.phoenixjcam.application.menu.PopupMenu;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public final class Notepad extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextArea txtArea;

	private JScrollPane scrollPane;
	private BarMenu mnBar;
	private PopupMenu mnPopup;

	private Notepad() {
		super("Untitled - Notepad");
		initFrame();
		initComponents();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void initFrame() {
		int width = 640;
		int height = 480;
		setSize(width, height);

		Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getCenterPoint();
		setLocation((centerPoint.x) - (width / 2), (centerPoint.y)
				- (height / 2));
	}

	private void initComponents() {
		txtArea = new JTextArea("", 5, 5);
		txtArea.setFont(new Font("Lucida Console", Font.PLAIN, 18));
		scrollPane = new JScrollPane(txtArea);
		add(scrollPane);

		mnBar = new BarMenu(this);
		mnPopup = new PopupMenu(this);
	}

	public BarMenu getNoteMenuBar() {
		return mnBar;
	}

	public PopupMenu getNoteMenuPopup() {
		return mnPopup;
	}

	public JTextArea getTxtArea() {
		return txtArea;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Notepad();
			}
		});
	}
}

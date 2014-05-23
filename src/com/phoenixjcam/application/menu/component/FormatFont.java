package com.phoenixjcam.application.menu.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class FormatFont extends JDialog implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;

	private Notepad notepad;

	private JLabel lblList;
	private JLabel lblStyle;
	private JLabel lblSize;
	private JLabel lblPreview;
	private JLabel lblFontPreview;

	private JList<String> fontList;
	private JList<String> styleList;
	private JList<String> sizeList;

	private ScrollPane scrollPane;

	private JButton btnOk;
	private JButton btnCancel;

	private String[] fontNames;
	private Font font;
	private String fontName;

	private int fontSize;
	private int fontStyle;

	public FormatFont(Notepad nt)
	{
		setTitle("Font");
		notepad = nt;
		init();

		add(labelFind());
		add(labelStyle());
		add(labelSize());
		add(labelPreview());
		add(labelFontPreview());
		listFont();
		listFontStyle();
		listFontSize();
		add(buttonOk());
		add(buttonCancel());
	}

	public void init()
	{
		setLayout(null);
		int width = 500;
		int height = 500;
		setSize(width, height);
		setResizable(false);

		Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation((centerPoint.x) - (width / 2), (centerPoint.y) - (height / 2));
	}

	public JLabel labelFind()
	{
		lblList = new JLabel("Font:");
		lblList.setBounds(10, 10, 120, 20);
		lblList.setFont(new Font("Arial", Font.PLAIN, 16));
		return lblList;
	}

	public JLabel labelStyle()
	{
		lblStyle = new JLabel("Style:");
		lblStyle.setBounds(220, 10, 120, 20);
		lblStyle.setFont(new Font("Arial", Font.PLAIN, 16));
		return lblStyle;
	}

	public JLabel labelSize()
	{
		lblSize = new JLabel("Size:");
		lblSize.setBounds(350, 10, 120, 20);
		lblSize.setFont(new Font("Arial", Font.PLAIN, 16));
		return lblSize;
	}

	public JLabel labelPreview()
	{
		lblPreview = new JLabel("Preview:");
		lblPreview.setBounds(220, 250, 120, 20);
		return lblPreview;
	}

	public JLabel labelFontPreview()
	{
		lblFontPreview = new JLabel("Sample Text");
		lblFontPreview.setBounds(220, 280, 250, 80);
		lblFontPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblFontPreview.setBackground(Color.white);
		lblFontPreview.setBorder(new LineBorder(Color.black, 1));
		return lblFontPreview;
	}

	public void listFont()
	{
		GraphicsEnvironment gEnviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontNames = gEnviroment.getAvailableFontFamilyNames();

		fontList = new JList<String>(fontNames);
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new ScrollPane();
		scrollPane.add(fontList);
		scrollPane.setBounds(10, 35, 200, 200);
		this.add(scrollPane);

		fontList.addListSelectionListener(this);
	}

	public void listFontStyle()
	{
		String styles[] =
		{ "Regular", "Bold", "Italic", "Bold Italic" };
		styleList = new JList<String>(styles);
		styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new ScrollPane();
		scrollPane.add(styleList);
		scrollPane.setBounds(220, 35, 120, 200);
		add(scrollPane);
		styleList.addListSelectionListener(this);
	}

	public void listFontSize()
	{
		Vector<String> fontList = new Vector<String>(40);
		for (int i = 8; i <= 100; i += 2)
			fontList.addElement(String.valueOf(i));

		sizeList = new JList<String>(fontList);
		sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new ScrollPane();
		scrollPane.add(sizeList);
		scrollPane.setBounds(350, 35, 120, 200);
		add(scrollPane);
		sizeList.addListSelectionListener(this);
	}

	public JButton buttonOk()
	{
		btnOk = new JButton("OK");
		btnOk.setBounds(270, 420, 100, 30);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 16));
		btnOk.addActionListener(this);
		return btnOk;
	}

	public JButton buttonCancel()
	{
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(380, 420, 100, 30);
		lblPreview.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCancel.addActionListener(this);
		return btnCancel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();

		if (event == btnOk)
		{
			actionOk();
		}
		else if (event == btnCancel)
		{
			actionCancel();
		}
	}

	public void actionOk()
	{
		try
		{
			notepad.getTxtArea().setFont(font);
		}
		catch (NullPointerException e)
		{
		}
		this.setVisible(false);
	}

	public void actionCancel()
	{
		this.setVisible(false);
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		Object event = e.getSource();

		if (event == fontList)
		{
			lblFontPreview.setText(fontList.getSelectedValue().toString());
			changeFontSample();
		}
		else if (event == sizeList)
		{
			changeFontSample();
		}
		else if (event == styleList)
		{
			changeFontSample();
		}
	}

	public void changeFontSample()
	{
		try
		{
			fontName = fontList.getSelectedValue().toString();
		}
		catch (NullPointerException e)
		{
			fontName = "Verdana";
		}

		try
		{
			fontStyle = getStyle();
		}
		catch (NullPointerException e)
		{
			fontStyle = Font.PLAIN;
		}

		try
		{
			fontSize = Integer.parseInt(sizeList.getSelectedValue().toString());
		}
		catch (NullPointerException e)
		{
			fontSize = 12;
		}

		font = new Font(fontName, fontStyle, fontSize);
		lblFontPreview.setFont(font);
	}

	public int getStyle()
	{
		String selectedValue = styleList.getSelectedValue().toString();

		if (selectedValue.equals("Bold"))
			return Font.BOLD;
		if (selectedValue.equals("Italic"))
			return Font.ITALIC;
		if (selectedValue.equals("Bold Italic"))
			return Font.BOLD + Font.ITALIC;

		return Font.PLAIN;
	}
}
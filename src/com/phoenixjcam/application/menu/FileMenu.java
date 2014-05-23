package com.phoenixjcam.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.phoenixjcam.application.Notepad;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class FileMenu implements ActionListener
{
	private Notepad notepad;
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmPageSettings;
	private JMenuItem mntmPrint;
	private JMenuItem mntmExit;
	private String filePath;
	private String fileContent;

	public FileMenu(Notepad nt)
	{
		notepad = nt;
		filePath = "";

		notepad.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				actionExit();
			}
		});
	}

	public JMenu fileItems()
	{
		mnFile = new JMenu("File");

		mnFile.add(fileNew());
		mnFile.add(fileOpen());
		mnFile.add(fileSave());
		mnFile.add(fileSaveAs());
		mnFile.add(filePageSettings());
		mnFile.add(filePrint());
		mnFile.add(fileExit());

		return mnFile;
	}

	public JMenuItem fileNew()
	{
		mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		mntmNew.addActionListener(this);
		return mntmNew;
	}

	public JMenuItem fileOpen()
	{
		mntmOpen = new JMenuItem("Open...");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		mntmOpen.addActionListener(this);
		return mntmOpen;
	}

	public JMenuItem fileSave()
	{
		mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		mntmSave.addActionListener(this);
		return mntmSave;
	}

	public JMenuItem fileSaveAs()
	{
		mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl alt S"));
		mntmSaveAs.addActionListener(this);
		return mntmSaveAs;
	}

	public JMenuItem filePageSettings()
	{
		mntmPageSettings = new JMenuItem("Page Settings...");
		mntmPageSettings.addActionListener(this);
		return mntmPageSettings;
	}

	public JMenuItem filePrint()
	{
		mntmPrint = new JMenuItem("Print...");
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		mntmPrint.addActionListener(this);
		return mntmPrint;
	}

	public JMenuItem fileExit()
	{
		mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
		mntmExit.addActionListener(this);
		return mntmExit;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();

		if (event == mntmNew)
		{
			actionFileNew();
		}
		else if (event == mntmOpen)
		{
			actionFileOpen();
		}
		else if (event == mntmSave)
		{
			actionFileSave();
		}
		else if (event == mntmSaveAs)
		{
			actionFileSaveAs();
		}
		else if (event == mntmPageSettings)
		{
			actionPageSettings();
		}
		else if (event == mntmPrint)
		{
			actionPrint();
		}
		else if (event == mntmExit)
		{
			actionExit();
		}
	}

	public void actionFileNew()
	{
		JTextArea txtArea = notepad.getTxtArea();
		String currentTxt = txtArea.getText();

		if (currentTxt.equals("") || currentTxt.equals(fileContent))
		{
			txtArea.setText("");
			fileContent = "";
			filePath = "";
			notepad.setTitle("Untitled - Notepad");
		}
		else
		{
			int dialog = JOptionPane.showConfirmDialog(null, "Would you like to save changes to file without title?");

			if (dialog == 0)
			{
				actionFileSave();
			}
			else if (dialog == 1)
			{
				txtArea.setText("");
				filePath = "";
				notepad.setTitle("Untitled - Notepad");
			}
			else if (dialog == 2)
			{
				return;
			}
		}
	}

	public void actionFileOpen()
	{
		JTextArea txtArea = notepad.getTxtArea();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int dialog = fileChooser.showOpenDialog(null);

		if (dialog == JFileChooser.CANCEL_OPTION)
		{
			return;
		}
		else if (dialog == JFileChooser.OPEN_DIALOG)
		{
			File myFile = fileChooser.getSelectedFile();
			try
			{
				@SuppressWarnings("resource")
				BufferedReader input = new BufferedReader(new FileReader(myFile));
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = input.readLine()) != null)
				{
					stringBuffer.append(line + "\n");
				}
				txtArea.setText(stringBuffer.toString());
				fileContent = txtArea.getText();
				filePath = myFile.toString();
				notepad.setTitle(myFile.getName() + " - Notepad");
			}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "File not found: " + e);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "IO ERROR: " + e);
			}
		}
	}

	public void actionFileSave()
	{
		JTextArea txtArea = notepad.getTxtArea();

		if (filePath.equals(""))
		{
			actionFileSaveAs();
			return;
		}

		try
		{
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(txtArea.getText());
			fileContent = txtArea.getText();
			fileWriter.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Failed to save the file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionFileSaveAs()
	{
		JTextArea txtArea = notepad.getTxtArea();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int dialog = fileChooser.showSaveDialog(null);

		if (dialog == JFileChooser.CANCEL_OPTION)
		{
			return;
		}
		else if (dialog == JFileChooser.OPEN_DIALOG)
		{

			File myFile = fileChooser.getSelectedFile();

			if (myFile.exists())
			{
				dialog = JOptionPane.showConfirmDialog(null, "A file with same name already exists!\nAre you sure want to overwrite?");
				if (dialog != 0)
				{
					return;
				}
			}
			try
			{
				FileWriter fileWriter = new FileWriter(myFile);
				fileWriter.write(txtArea.getText());
				fileContent = txtArea.getText();
				notepad.setTitle(myFile.getName() + " - Notepad");
				fileWriter.close();
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "Failed to save the file", "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	public void actionPageSettings()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.pageDialog(printJob.defaultPage());
	}

	public void actionPrint()
	{
		PrinterJob printer = PrinterJob.getPrinterJob();

		HashPrintRequestAttributeSet printAttr = new HashPrintRequestAttributeSet();
		if (printer.printDialog(printAttr))
		{
			try
			{
				printer.print(printAttr);
			}
			catch (PrinterException e)
			{
				JOptionPane.showMessageDialog(null, "Failed to print the file: " + e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void actionExit()
	{
		String currentTxt = notepad.getTxtArea().getText();

		if (currentTxt.equals("") || currentTxt.equals(fileContent))
		{
			System.exit(0);
		}
		else
		{
			int dialog = JOptionPane.showConfirmDialog(null, "Do you want to save changes to Untitled?");

			if (dialog == 0)
			{
				actionFileSave();
			}
			else if (dialog == 1)
			{
				System.exit(0);
			}
			else if (dialog == 2)
			{
				return;
			}
		}
	}

	public String getFileContent()
	{
		return fileContent;
	}

	public void setFileContent(String fileContent)
	{
		this.fileContent = fileContent;
	}
}

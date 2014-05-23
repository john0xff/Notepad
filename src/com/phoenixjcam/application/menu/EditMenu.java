package com.phoenixjcam.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import com.phoenixjcam.application.Notepad;
import com.phoenixjcam.application.menu.component.FindText;

/**
 * 
 * @author Bart Bien <br>
 *         source available at my web site <a
 *         href="http://phoenixjcam.com">phoenixjcam.com</a>
 */
public class EditMenu implements ActionListener {
    private Notepad notepad;
    private FindText finder;
    private JMenu mnEdit;
    private JMenuItem mntmUndo;
    private JMenuItem mntmRedo;
    private JMenuItem mntmCopy;
    private JMenuItem mntmCut;
    private JMenuItem mntmPaste;
    private JMenuItem mntmDelete;
    private JMenuItem mntmFind;
    private JMenuItem mntmFindNext;
    private JMenuItem mntmReplace;
    private JMenuItem mntmSelectAll;
    private JMenuItem mntmTimeData;

    private UndoManager undoManager;

    public EditMenu(Notepad nt) {
	notepad = nt;
	undoManager = new UndoManager();
	finder = new FindText(nt);
	actionBasicUndo();
    }

    public JMenu editItems() {
	mnEdit = new JMenu("Edit");

	mnEdit.add(editUndo());
	mnEdit.add(editRedo());
	mnEdit.add(editCopy());
	mnEdit.add(editCut());
	mnEdit.add(editPaste());
	mnEdit.add(editDelete());
	mnEdit.add(editFind());
	mnEdit.add(editFindNext());
	mnEdit.add(editReplace());
	mnEdit.add(editSelectAll());
	mnEdit.add(editTimeData());

	return mnEdit;
    }

    public JMenuItem editUndo() {
	mntmUndo = new JMenuItem("Undo");
	mntmUndo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
	mntmUndo.addActionListener(this);
	return mntmUndo;
    }

    public JMenuItem editRedo() {
	mntmRedo = new JMenuItem("Redo");
	mntmRedo.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
	mntmRedo.addActionListener(this);
	return mntmRedo;
    }

    public JMenuItem editCopy() {
	mntmCopy = new JMenuItem("Copy");
	mntmCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
	mntmCopy.addActionListener(this);
	return mntmCopy;
    }

    public JMenuItem editCut() {
	mntmCut = new JMenuItem("Cut");
	mntmCut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
	mntmCut.addActionListener(this);
	return mntmCut;
    }

    public JMenuItem editPaste() {
	mntmPaste = new JMenuItem("Paste");
	mntmPaste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
	mntmPaste.addActionListener(this);
	return mntmPaste;
    }

    public JMenuItem editDelete() {
	mntmDelete = new JMenuItem("Delete");
	mntmDelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
	mntmDelete.addActionListener(this);
	return mntmDelete;
    }

    public JMenuItem editFind() {
	mntmFind = new JMenuItem("Find");
	mntmFind.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
	mntmFind.addActionListener(this);
	return mntmFind;
    }

    public JMenuItem editFindNext() {
	mntmFindNext = new JMenuItem("Find Next");
	mntmFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
	mntmFindNext.addActionListener(this);
	return mntmFindNext;
    }

    public JMenuItem editReplace() {
	mntmReplace = new JMenuItem("Replace");
	mntmReplace.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
	mntmReplace.addActionListener(this);
	return mntmReplace;
    }

    public JMenuItem editSelectAll() {
	mntmSelectAll = new JMenuItem("Select All");
	mntmSelectAll.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
	mntmSelectAll.addActionListener(this);
	return mntmSelectAll;
    }

    public JMenuItem editTimeData() {
	mntmTimeData = new JMenuItem("Time/Date");
	mntmTimeData.setAccelerator(KeyStroke.getKeyStroke("F5"));
	mntmTimeData.addActionListener(this);
	return mntmTimeData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object event = e.getSource();

	if (event == mntmUndo) {
	    actionEditUndo();
	} else if (event == mntmRedo) {
	    actionEditRedo();
	} else if (event == mntmCopy) {
	    actionEditCopy();
	} else if (event == mntmCut) {
	    actionEditCut();
	} else if (event == mntmPaste) {
	    actionEditPaste();
	} else if (event == mntmDelete) {
	    actionEditDelete();
	} else if (event == mntmFind) {
	    actionEditFind();
	} else if (event == mntmFindNext) {
	    actionEditFindNext();
	} else if (event == mntmReplace) {
	    actionEditReplace();
	} else if (event == mntmSelectAll) {
	    actionEditSelectAll();
	} else if (event == mntmTimeData) {
	    actionEditTimeData();
	}
    }

    public void actionEditUndo() {
	if (undoManager.canUndo())
	    undoManager.undo();
    }

    public void actionEditRedo() {
	if (undoManager.canRedo())
	    undoManager.redo();
    }

    public void actionBasicUndo() {
	Document doc = notepad.getTxtArea().getDocument();
	doc.addUndoableEditListener(new UndoableEditListener() {
	    public void undoableEditHappened(UndoableEditEvent event) {
		undoManager.addEdit(event.getEdit());
	    }
	});
    }

    public void actionEditCopy() {
	notepad.getTxtArea().copy();
    }

    public void actionEditCut() {
	notepad.getTxtArea().cut();
    }

    public void actionEditPaste() {
	notepad.getTxtArea().paste();
    }

    public void actionEditDelete() {
	JTextArea txtArea = notepad.getTxtArea();

	String currentTxt = txtArea.getText();
	int beginIndex = 0;
	int endIndex = txtArea.getSelectionStart();
	String selectedTxt = currentTxt.substring(txtArea.getSelectionEnd());
	String txt = currentTxt.substring(beginIndex, endIndex) + selectedTxt;
	txtArea.setText(txt);
    }

    public void actionEditFind() {
	finder.setVisible(true);
    }

    public void actionEditFindNext() {
	finder.actionFindNext();
    }

    public void actionEditReplace() {
	finder.setVisible(true);
    }

    public void actionEditSelectAll() {
	notepad.getTxtArea().selectAll();
    }

    public void actionEditTimeData() {
	JTextArea txtArea = notepad.getTxtArea();

	try {
	    int txtStart = txtArea.getSelectionStart();
	    int txtEnd = txtArea.getSelectionEnd();
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dataFormat = new SimpleDateFormat(
		    "dd/mm/yyyy h:m a");
	    String data = dataFormat.format(calendar.getTime());
	    String txtAreaStart = txtArea.getText().substring(0, txtStart);
	    String txtAreaEnd = txtArea.getText().substring(txtEnd);
	    txtArea.setText(txtAreaStart + data + txtAreaEnd);
	    txtArea.select(txtStart, txtStart + data.length());
	} catch (NullPointerException e) {
	}
    }
}

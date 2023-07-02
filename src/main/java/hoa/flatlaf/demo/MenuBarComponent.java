package hoa.flatlaf.demo;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;

import javax.swing.Box;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatButton.ButtonType;

public class MenuBarComponent {

	private MainFrame mainFrameOwner;
	private JMenuBar menuBar1;

	public MenuBarComponent(MainFrame mainFrameOwner) {
		this.mainFrameOwner = mainFrameOwner;
		initComponents();
	}

	public JMenuBar getMenuBar() {
		return menuBar1;
	}

	private void initComponents() {
		menuBar1 = new JMenuBar();
		JMenu fileMenu = new JMenu();
		JMenuItem newMenuItem = new JMenuItem();
		JMenuItem openMenuItem = new JMenuItem();
		JMenuItem saveAsMenuItem = new JMenuItem();
		JMenuItem closeMenuItem = new JMenuItem();
		JMenuItem exitMenuItem = new JMenuItem();
		JMenu editMenu = new JMenu();
		JMenuItem undoMenuItem = new JMenuItem();
		JMenuItem redoMenuItem = new JMenuItem();
		JMenuItem cutMenuItem = new JMenuItem();
		JMenuItem copyMenuItem = new JMenuItem();
		JMenuItem pasteMenuItem = new JMenuItem();
		JMenuItem deleteMenuItem = new JMenuItem();
		JMenu viewMenu = new JMenu();
		JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem();
		JMenu menu1 = new JMenu();
		JMenu subViewsMenu = new JMenu();
		JMenu subSubViewsMenu = new JMenu();
		JMenuItem errorLogViewMenuItem = new JMenuItem();
		JMenuItem searchViewMenuItem = new JMenuItem();
		JMenuItem projectViewMenuItem = new JMenuItem();
		JMenuItem structureViewMenuItem = new JMenuItem();
		JMenuItem propertiesViewMenuItem = new JMenuItem();
		// ======== menuBar1 ========
		{

			// ======== fileMenu ========
			{
				fileMenu.setText("File");
				fileMenu.setMnemonic('F');

				// ---- newMenuItem ----
				newMenuItem.setText("New");
				newMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				newMenuItem.setMnemonic('N');
				newMenuItem.addActionListener(e -> newActionPerformed());
				fileMenu.add(newMenuItem);

				// ---- openMenuItem ----
				openMenuItem.setText("Open...");
				openMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				openMenuItem.setMnemonic('O');
				openMenuItem.addActionListener(e -> openActionPerformed());
				fileMenu.add(openMenuItem);

				// ---- saveAsMenuItem ----
				saveAsMenuItem.setText("Save As...");
				saveAsMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				saveAsMenuItem.setMnemonic('S');
				saveAsMenuItem.addActionListener(e -> saveAsActionPerformed());
				fileMenu.add(saveAsMenuItem);
				fileMenu.addSeparator();

				// ---- closeMenuItem ----
				closeMenuItem.setText("Close");
				closeMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				closeMenuItem.setMnemonic('C');
				closeMenuItem.addActionListener(e -> menuItemActionPerformed(e));
				fileMenu.add(closeMenuItem);
				fileMenu.addSeparator();

				// ---- exitMenuItem ----
				exitMenuItem.setText("Exit");
				exitMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				exitMenuItem.setMnemonic('X');
				exitMenuItem.addActionListener(e -> exitActionPerformed());
				fileMenu.add(exitMenuItem);
			}
			menuBar1.add(fileMenu);

			// ======== editMenu ========
			{
				editMenu.setText("Edit");
				editMenu.setMnemonic('E');

				// ---- undoMenuItem ----
				undoMenuItem.setText("Undo");
				undoMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				undoMenuItem.setMnemonic('U');
				undoMenuItem.addActionListener(e -> menuItemActionPerformed(e));
				editMenu.add(undoMenuItem);

				// ---- redoMenuItem ----
				redoMenuItem.setText("Redo");
				redoMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				redoMenuItem.setMnemonic('R');
				redoMenuItem.addActionListener(e -> menuItemActionPerformed(e));
				editMenu.add(redoMenuItem);
				editMenu.addSeparator();

				// ---- cutMenuItem ----
				cutMenuItem.setText("Cut");
				cutMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				cutMenuItem.setMnemonic('C');
				editMenu.add(cutMenuItem);

				// ---- copyMenuItem ----
				copyMenuItem.setText("Copy");
				copyMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				copyMenuItem.setMnemonic('O');
				editMenu.add(copyMenuItem);

				// ---- pasteMenuItem ----
				pasteMenuItem.setText("Paste");
				pasteMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				pasteMenuItem.setMnemonic('P');
				editMenu.add(pasteMenuItem);
				editMenu.addSeparator();

				// ---- deleteMenuItem ----
				deleteMenuItem.setText("Delete");
				deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
				deleteMenuItem.setMnemonic('D');
				deleteMenuItem.addActionListener(e -> menuItemActionPerformed(e));
				editMenu.add(deleteMenuItem);
			}
			menuBar1.add(editMenu);

			// ======== viewMenu ========
			{
				viewMenu.setText("View");
				viewMenu.setMnemonic('V');

				// ---- checkBoxMenuItem1 ----
				checkBoxMenuItem1.setText("Show Toolbar");
				checkBoxMenuItem1.setSelected(true);
				checkBoxMenuItem1.setMnemonic('T');
				checkBoxMenuItem1.addActionListener(e -> menuItemActionPerformed(e));
				viewMenu.add(checkBoxMenuItem1);

				// ======== menu1 ========
				{
					menu1.setText("Show View");
					menu1.setMnemonic('V');

					// ======== subViewsMenu ========
					{
						subViewsMenu.setText("Sub Views");
						subViewsMenu.setMnemonic('S');

						// ======== subSubViewsMenu ========
						{
							subSubViewsMenu.setText("Sub sub Views");
							subSubViewsMenu.setMnemonic('U');

							// ---- errorLogViewMenuItem ----
							errorLogViewMenuItem.setText("Error Log");
							errorLogViewMenuItem.setMnemonic('E');
							errorLogViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
							subSubViewsMenu.add(errorLogViewMenuItem);
						}
						subViewsMenu.add(subSubViewsMenu);

						// ---- searchViewMenuItem ----
						searchViewMenuItem.setText("Search");
						searchViewMenuItem.setMnemonic('S');
						searchViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
						subViewsMenu.add(searchViewMenuItem);
					}
					menu1.add(subViewsMenu);

					// ---- projectViewMenuItem ----
					projectViewMenuItem.setText("Project");
					projectViewMenuItem.setMnemonic('P');
					projectViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
					menu1.add(projectViewMenuItem);

					// ---- structureViewMenuItem ----
					structureViewMenuItem.setText("Structure");
					structureViewMenuItem.setMnemonic('T');
					structureViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
					menu1.add(structureViewMenuItem);

					// ---- propertiesViewMenuItem ----
					propertiesViewMenuItem.setText("Properties");
					propertiesViewMenuItem.setMnemonic('O');
					propertiesViewMenuItem.addActionListener(e -> menuItemActionPerformed(e));
					menu1.add(propertiesViewMenuItem);
				}
				viewMenu.add(menu1);
			}
			menuBar1.add(viewMenu);

			// ======== fontMenu ========
			JMenu fontMenu = new JMenu();
			JMenuItem restoreFontMenuItem = new JMenuItem();
			JMenuItem incrFontMenuItem = new JMenuItem();
			JMenuItem decrFontMenuItem = new JMenuItem();
			{
				fontMenu.setText("Font");
				// ---- restoreFontMenuItem ----
				restoreFontMenuItem.setText("Restore Font");
				restoreFontMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_0, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				// restoreFontMenuItem.addActionListener(e -> restoreFont());
				fontMenu.add(restoreFontMenuItem);

				// ---- incrFontMenuItem ----
				incrFontMenuItem.setText("Increase Font Size");
				incrFontMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				incrFontMenuItem.addActionListener(e -> incrFont());
				fontMenu.add(incrFontMenuItem);

				// ---- decrFontMenuItem ----
				decrFontMenuItem.setText("Decrease Font Size");
				decrFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
				decrFontMenuItem.addActionListener(e -> decrFont());
				fontMenu.add(decrFontMenuItem);
			}
			menuBar1.add(fontMenu);

			// ======== optionsMenu ========
			JMenu optionsMenu = new JMenu();
			JCheckBoxMenuItem windowDecorationsCheckBoxMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem menuBarEmbeddedCheckBoxMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem unifiedTitleBarMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem showTitleBarIconMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem underlineMenuSelectionMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem alwaysShowMnemonicsMenuItem = new JCheckBoxMenuItem();
			JCheckBoxMenuItem animatedLafChangeMenuItem = new JCheckBoxMenuItem();
			{
				optionsMenu.setText("Options");

				// ---- windowDecorationsCheckBoxMenuItem ----
				windowDecorationsCheckBoxMenuItem.setText("Window decorations");
				optionsMenu.add(windowDecorationsCheckBoxMenuItem);

				// ---- menuBarEmbeddedCheckBoxMenuItem ----
				menuBarEmbeddedCheckBoxMenuItem.setText("Embedded menu bar");
				optionsMenu.add(menuBarEmbeddedCheckBoxMenuItem);

				// ---- unifiedTitleBarMenuItem ----
				unifiedTitleBarMenuItem.setText("Unified window title bar");
				optionsMenu.add(unifiedTitleBarMenuItem);

				// ---- showTitleBarIconMenuItem ----
				showTitleBarIconMenuItem.setText("Show window title bar icon");
				optionsMenu.add(showTitleBarIconMenuItem);

				// ---- underlineMenuSelectionMenuItem ----
				underlineMenuSelectionMenuItem.setText("Use underline menu selection");
				optionsMenu.add(underlineMenuSelectionMenuItem);

				// ---- alwaysShowMnemonicsMenuItem ----
				alwaysShowMnemonicsMenuItem.setText("Always show mnemonics");
				optionsMenu.add(alwaysShowMnemonicsMenuItem);

				// ---- animatedLafChangeMenuItem ----
				animatedLafChangeMenuItem.setText("Animated Laf Change");
				animatedLafChangeMenuItem.setSelected(true);
				optionsMenu.add(animatedLafChangeMenuItem);

			}
			menuBar1.add(optionsMenu);

			// ======== helpMenu ========
			JMenu helpMenu = new JMenu();
			JMenuItem aboutMenuItem = new JMenuItem();
			{
				helpMenu.setText("Help");
				helpMenu.setMnemonic('H');

				// ---- aboutMenuItem ----
				aboutMenuItem.setText("About");
				aboutMenuItem.setMnemonic('A');
				aboutMenuItem.addActionListener(e -> aboutActionPerformed());
				helpMenu.add(aboutMenuItem);
			}
			menuBar1.add(helpMenu);
		}

		// add "Users" button to menubar
		FlatButton usersButton = new FlatButton();
		usersButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/users.svg"));
		usersButton.setButtonType(ButtonType.toolBarButton);
		usersButton.setFocusable(false);
		usersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Hello User! How are you?", "User",
				JOptionPane.INFORMATION_MESSAGE));
		menuBar1.add(Box.createGlue());
		menuBar1.add(usersButton);
		
		undoMenuItem.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/undo.svg" ) );
		redoMenuItem.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/redo.svg" ) );

		cutMenuItem.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/menu-cut.svg" ) );
		copyMenuItem.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/copy.svg" ) );
		pasteMenuItem.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/menu-paste.svg" ) );

	}

	private void newActionPerformed() {
		// NewDialog newDialog = new NewDialog( this );
		// newDialog.setVisible( true );
	}

	private void openActionPerformed() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(mainFrameOwner);
	}

	private void saveAsActionPerformed() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(mainFrameOwner);
	}

	private void exitActionPerformed() {
		mainFrameOwner.dispose();
	}

	private void aboutActionPerformed() {
		JLabel titleLabel = new JLabel("FlatLaf Demo");
		titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");

		String link = "https://www.formdev.com/flatlaf/";
		JLabel linkLabel = new JLabel("<html><a href=\"#\">" + link + "</a></html>");
		linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException ex) {
					JOptionPane.showMessageDialog(linkLabel, "Failed to open '" + link + "' in browser.", "About",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		JOptionPane.showMessageDialog(mainFrameOwner,
				new Object[] { titleLabel, "Demonstrates FlatLaf Swing look and feel", " ",
						"Copyright 2019-" + Year.now() + " FormDev Software GmbH", linkLabel, },
				"About", JOptionPane.PLAIN_MESSAGE);
	}

	private void menuItemActionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(mainFrameOwner, e.getActionCommand(), "Menu Item", JOptionPane.PLAIN_MESSAGE);
		});
	}

	private void incrFont() {
		Font font = UIManager.getFont("defaultFont");
		Font newFont = font.deriveFont((float) (font.getSize() + 1));
		UIManager.put("defaultFont", newFont);
		FlatLaf.updateUI();
	}

	private void decrFont() {
		Font font = UIManager.getFont("defaultFont");
		Font newFont = font.deriveFont((float) Math.max(font.getSize() - 1, 10));
		UIManager.put("defaultFont", newFont);
		FlatLaf.updateUI();
	}
}

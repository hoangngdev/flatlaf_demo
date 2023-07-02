
package hoa.flatlaf.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.icons.FlatAbstractIcon;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.ui.JBRCustomDecorations;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.util.SystemInfo;

import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;


class MainFrame	extends JFrame {

	public MainFrame() {
		int tabIndex = DemoPrefs.getState().getInt( MainApplication.KEY_TAB, 0 );

		
		initComponents();
		
		initAccentColors();
		//controlBar.initialize( this, tabbedPane );

		setIconImages( FlatSVGUtils.createWindowIconImages( "/com/formdev/flatlaf/demo/FlatLaf.svg" ) );

		if( tabIndex >= 0 && tabIndex < tabbedPane.getTabCount() && tabIndex != tabbedPane.getSelectedIndex() )
			tabbedPane.setSelectedIndex( tabIndex );	

		// integrate into macOS screen menu
		FlatDesktop.setAboutHandler( this::aboutActionPerformed );
		FlatDesktop.setPreferencesHandler( this::showPreferences );
		FlatDesktop.setQuitHandler( response -> {
			response.performQuit();
		} );

		SwingUtilities.invokeLater( () -> {
			//showHints();
		} );
	}

	@Override
	public void dispose() {
		super.dispose();
		FlatUIDefaultsInspector.hide();
	}

	/*
	 * private void showHints() { Hint fontMenuHint = new Hint(
	 * "Use 'Font' menu to increase/decrease font size or try different fonts.",
	 * fontMenu, SwingConstants.BOTTOM, "hint.fontMenu", null );
	 * 
	 * Hint optionsMenuHint = new Hint(
	 * "Use 'Options' menu to try out various FlatLaf options.", optionsMenu,
	 * SwingConstants.BOTTOM, "hint.optionsMenu", fontMenuHint );
	 * 
	 * Hint themesHint = new Hint( "Use 'Themes' list to try out various themes.",
	 * themesPanel, SwingConstants.LEFT, "hint.themesPanel", optionsMenuHint );
	 * 
	 * HintManager.showHint( themesHint ); }
	 */

	private void clearHints() {
		//HintManager.hideAllHints();

		Preferences state = DemoPrefs.getState();
		state.remove( "hint.fontMenu" );
		state.remove( "hint.optionsMenu" );
		state.remove( "hint.themesPanel" );
	}

	private void aboutActionPerformed() {
		JLabel titleLabel = new JLabel( "FlatLaf Demo" );
		titleLabel.putClientProperty( FlatClientProperties.STYLE_CLASS, "h1" );

		String link = "https://www.formdev.com/flatlaf/";
		JLabel linkLabel = new JLabel( "<html><a href=\"#\">" + link + "</a></html>" );
		linkLabel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		linkLabel.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				try {
					Desktop.getDesktop().browse( new URI( link ) );
				} catch( IOException | URISyntaxException ex ) {
					JOptionPane.showMessageDialog( linkLabel,
						"Failed to open '" + link + "' in browser.",
						"About", JOptionPane.PLAIN_MESSAGE );
				}
			}
		} );


		JOptionPane.showMessageDialog( this,
			new Object[] {
				titleLabel,
				"Demonstrates FlatLaf Swing look and feel",
				" ",
				"Copyright 2019-" + Year.now() + " FormDev Software GmbH",
				linkLabel,
			},
			"About", JOptionPane.PLAIN_MESSAGE );
	}

	private void showPreferences() {
		JOptionPane.showMessageDialog( this,
			"Sorry, but FlatLaf Demo does not have preferences. :(\n"
				+ "This dialog is here to demonstrate usage of class 'FlatDesktop' on macOS.",
			"Preferences", JOptionPane.PLAIN_MESSAGE );
	}

	private void selectedTabChanged() {
		DemoPrefs.getState().putInt( MainApplication.KEY_TAB, tabbedPane.getSelectedIndex() );
	}

	private void menuItemActionPerformed( ActionEvent e ) {
		SwingUtilities.invokeLater( () -> {
			JOptionPane.showMessageDialog( this, e.getActionCommand(), "Menu Item", JOptionPane.PLAIN_MESSAGE );
		} );
	}

	private void windowDecorationsChanged() {
		boolean windowDecorations = windowDecorationsCheckBoxMenuItem.isSelected();

		// change window decoration of all frames and dialogs
		FlatLaf.setUseNativeWindowDecorations( windowDecorations );

		menuBarEmbeddedCheckBoxMenuItem.setEnabled( windowDecorations );
		unifiedTitleBarMenuItem.setEnabled( windowDecorations );
		showTitleBarIconMenuItem.setEnabled( windowDecorations );
	}

	private void menuBarEmbeddedChanged() {
		UIManager.put( "TitlePane.menuBarEmbedded", menuBarEmbeddedCheckBoxMenuItem.isSelected() );
		FlatLaf.revalidateAndRepaintAllFramesAndDialogs();
	}

	private void unifiedTitleBar() {
		UIManager.put( "TitlePane.unifiedBackground", unifiedTitleBarMenuItem.isSelected() );
		FlatLaf.repaintAllFramesAndDialogs();
	}

	private void showTitleBarIcon() {
		boolean showIcon = showTitleBarIconMenuItem.isSelected();

		// for main frame (because already created)
		getRootPane().putClientProperty( FlatClientProperties.TITLE_BAR_SHOW_ICON, showIcon );

		// for other not yet created frames/dialogs
		UIManager.put( "TitlePane.showIcon", showIcon );
	}

	private void underlineMenuSelection() {
		UIManager.put( "MenuItem.selectionType", underlineMenuSelectionMenuItem.isSelected() ? "underline" : null );
	}

	private void alwaysShowMnemonics() {
		UIManager.put( "Component.hideMnemonics", !alwaysShowMnemonicsMenuItem.isSelected() );
		repaint();
	}

	private void animatedLafChangeChanged() {
		System.setProperty( "flatlaf.animatedLafChange", String.valueOf( animatedLafChangeMenuItem.isSelected() ) );
	}

	private void showHintsChanged() {
		clearHints();
		//showHints();
	}

	// the real colors are defined in
	// flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatLightLaf.properties and
	// flatlaf-demo/src/main/resources/com/formdev/flatlaf/demo/FlatDarkLaf.properties
	private static String[] accentColorKeys = {
		"Demo.accent.default", "Demo.accent.blue", "Demo.accent.purple", "Demo.accent.red",
		"Demo.accent.orange", "Demo.accent.yellow", "Demo.accent.green",
	};
	private static String[] accentColorNames = {
		"Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green",
	};
	private final JToggleButton[] accentColorButtons = new JToggleButton[accentColorKeys.length];
	private JLabel accentColorLabel;
	private Color accentColor;

	private void initAccentColors() {
		accentColorLabel = new JLabel( "Accent color: " );

		toolBar.add( Box.createHorizontalGlue() );
		toolBar.add( accentColorLabel );

		ButtonGroup group = new ButtonGroup();
		for( int i = 0; i < accentColorButtons.length; i++ ) {
			accentColorButtons[i] = new JToggleButton( new AccentColorIcon( accentColorKeys[i] ) );
			accentColorButtons[i].setToolTipText( accentColorNames[i] );
			accentColorButtons[i].addActionListener( this::accentColorChanged );
			toolBar.add( accentColorButtons[i] );
			group.add( accentColorButtons[i] );
		}

		accentColorButtons[0].setSelected( true );

		FlatLaf.setSystemColorGetter( name -> {
			return name.equals( "accent" ) ? accentColor : null;
		} );

		UIManager.addPropertyChangeListener( e -> {
			if( "lookAndFeel".equals( e.getPropertyName() ) )
				updateAccentColorButtons();
		} );
		updateAccentColorButtons();
	}

	private void accentColorChanged( ActionEvent e ) {
		String accentColorKey = null;
		for( int i = 0; i < accentColorButtons.length; i++ ) {
			if( accentColorButtons[i].isSelected() ) {
				accentColorKey = accentColorKeys[i];
				break;
			}
		}

		accentColor = (accentColorKey != null && accentColorKey != accentColorKeys[0])
			? UIManager.getColor( accentColorKey )
			: null;

		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		try {
			FlatLaf.setup( lafClass.getDeclaredConstructor().newInstance() );
			FlatLaf.updateUI();
		} catch( Exception ex ) {
			LoggingFacade.INSTANCE.logSevere( null, ex );
		}
	}

	private void updateAccentColorButtons() {
		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		boolean isAccentColorSupported =
			lafClass == FlatLightLaf.class ||
			lafClass == FlatDarkLaf.class ||
			lafClass == FlatIntelliJLaf.class ||
			lafClass == FlatDarculaLaf.class ||
			lafClass == FlatMacLightLaf.class ||
			lafClass == FlatMacDarkLaf.class;

		accentColorLabel.setVisible( isAccentColorSupported );
		for( int i = 0; i < accentColorButtons.length; i++ )
			accentColorButtons[i].setVisible( isAccentColorSupported );
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		toolBar = new JToolBar();
		JButton backButton = new JButton();
		JButton forwardButton = new JButton();
		JButton cutButton = new JButton();
		JButton copyButton = new JButton();
		JButton pasteButton = new JButton();
		JButton refreshButton = new JButton();
		JToggleButton showToggleButton = new JToggleButton();
		JPanel contentPanel = new JPanel();
		tabbedPane = new JTabbedPane();
		//BasicComponentsPanel basicComponentsPanel = new BasicComponentsPanel();
		//MoreComponentsPanel moreComponentsPanel = new MoreComponentsPanel();
		//DataComponentsPanel dataComponentsPanel = new DataComponentsPanel();
		//TabsPanel tabsPanel = new TabsPanel();
		//OptionPanePanel optionPanePanel = new OptionPanePanel();
		//ExtrasPanel extrasPanel1 = new ExtrasPanel();
		//controlBar = new ControlBar();
		//themesPanel = new IJThemesPanel();

		//======== this ========
		setTitle("My Usecase with FlatLaf");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		setJMenuBar(new MenuBarComponent(this).getMenuBar());
		
		//======== toolBar ========
		{
			toolBar.setMargin(new Insets(3, 3, 3, 3));

			//---- backButton ----
			backButton.setToolTipText("Back");
			toolBar.add(backButton);

			//---- forwardButton ----
			forwardButton.setToolTipText("Forward");
			toolBar.add(forwardButton);
			toolBar.addSeparator();

			//---- cutButton ----
			cutButton.setToolTipText("Cut");
			toolBar.add(cutButton);

			//---- copyButton ----
			copyButton.setToolTipText("Copy");
			toolBar.add(copyButton);

			//---- pasteButton ----
			pasteButton.setToolTipText("Paste");
			toolBar.add(pasteButton);
			toolBar.addSeparator();

			//---- refreshButton ----
			refreshButton.setToolTipText("Refresh");
			toolBar.add(refreshButton);
			toolBar.addSeparator();

			//---- showToggleButton ----
			showToggleButton.setSelected(true);
			showToggleButton.setToolTipText("Show Details");
			toolBar.add(showToggleButton);
		}
		contentPane.add(toolBar, BorderLayout.NORTH);

		//======== contentPanel ========
		{
			contentPanel.setLayout(new MigLayout(
				"insets dialog,hidemode 3",
				// columns
				"[grow,fill]",
				// rows
				"[grow,fill]"));

			//======== tabbedPane ========
			{
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				tabbedPane.addChangeListener(e -> selectedTabChanged());
				//tabbedPane.addTab("Basic Components", basicComponentsPanel);
				//tabbedPane.addTab("More Components", moreComponentsPanel);
				//tabbedPane.addTab("Data Components", dataComponentsPanel);
				//tabbedPane.addTab("Tabs", tabsPanel);
				//tabbedPane.addTab("Option Pane", optionPanePanel);
				//tabbedPane.addTab("Extras", extrasPanel1);
			}
			contentPanel.add(tabbedPane, "cell 0 0");
		}
		contentPane.add(contentPanel, BorderLayout.CENTER);
		//contentPane.add(controlBar, BorderLayout.SOUTH);
		//contentPane.add(themesPanel, BorderLayout.EAST);

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		
		// JFormDesigner - End of component initialization  //GEN-END:initComponents

		
		
		backButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/back.svg" ) );
		forwardButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/forward.svg" ) );
		cutButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/menu-cut.svg" ) );
		copyButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/copy.svg" ) );
		pasteButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/menu-paste.svg" ) );
		refreshButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/refresh.svg" ) );
		showToggleButton.setIcon( new FlatSVGIcon( "com/formdev/flatlaf/demo/icons/show.svg" ) );


		if( FlatLaf.supportsNativeWindowDecorations() || (SystemInfo.isLinux && JFrame.isDefaultLookAndFeelDecorated()) ) {
			if( SystemInfo.isLinux )
				unsupported( windowDecorationsCheckBoxMenuItem );
			else

			if( JBRCustomDecorations.isSupported() ) {
				// If the JetBrains Runtime is used, it forces the use of it's own custom
				// window decoration, which can not disabled.
				windowDecorationsCheckBoxMenuItem.setEnabled( false );
			}
		} else {
			unsupported( windowDecorationsCheckBoxMenuItem );
			unsupported( menuBarEmbeddedCheckBoxMenuItem );
			unsupported( unifiedTitleBarMenuItem );
			unsupported( showTitleBarIconMenuItem );
		}

		if( SystemInfo.isMacOS )
			unsupported( underlineMenuSelectionMenuItem );

		// remove contentPanel bottom insets
		MigLayout layout = (MigLayout) contentPanel.getLayout();
		LC lc = ConstraintParser.parseLayoutConstraint( (String) layout.getLayoutConstraints() );
		UnitValue[] insets = lc.getInsets();
		lc.setInsets( new UnitValue[] {
			insets[0],
			insets[1],
			new UnitValue( 0, UnitValue.PIXEL, null ),
			insets[3]
		} );
		layout.setLayoutConstraints( lc );
	}

	private void unsupported( JCheckBoxMenuItem menuItem ) {
		menuItem.setEnabled( false );
		menuItem.setSelected( false );
		menuItem.setToolTipText( "Not supported on your system." );
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuItem exitMenuItem;
	
	private JMenuItem htmlMenuItem;
	private JMenu fontMenu;
	private JMenu optionsMenu;
	private JCheckBoxMenuItem windowDecorationsCheckBoxMenuItem;
	private JCheckBoxMenuItem menuBarEmbeddedCheckBoxMenuItem;
	private JCheckBoxMenuItem unifiedTitleBarMenuItem;
	private JCheckBoxMenuItem showTitleBarIconMenuItem;
	private JCheckBoxMenuItem underlineMenuSelectionMenuItem;
	private JCheckBoxMenuItem alwaysShowMnemonicsMenuItem;
	private JCheckBoxMenuItem animatedLafChangeMenuItem;
	private JMenuItem aboutMenuItem;
	private JToolBar toolBar;
	private JTabbedPane tabbedPane;
	//private ControlBar controlBar;
	//IJThemesPanel themesPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	//---- class AccentColorIcon ----------------------------------------------

	private static class AccentColorIcon
		extends FlatAbstractIcon
	{
		private final String colorKey;

		AccentColorIcon( String colorKey ) {
			super( 16, 16, null );
			this.colorKey = colorKey;
		}

		@Override
		protected void paintIcon( Component c, Graphics2D g ) {
			Color color = UIManager.getColor( colorKey );
			if( color == null )
				color = Color.lightGray;
			else if( !c.isEnabled() ) {
				color = FlatLaf.isLafDark()
					? ColorFunctions.shade( color, 0.5f )
					: ColorFunctions.tint( color, 0.6f );
			}

			g.setColor( color );
			g.fillRoundRect( 1, 1, width - 2, height - 2, 5, 5 );
		}
	}
}

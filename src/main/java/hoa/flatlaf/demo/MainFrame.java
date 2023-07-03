
package hoa.flatlaf.demo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Locale;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.ui.JBRCustomDecorations;
import com.formdev.flatlaf.util.SystemInfo;

import hoa.flatlaf.demo.panel.BasicComponentsPanel;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

class MainFrame extends JFrame {

	private MenuBarComponent menuBarComponent;
	
	public MainFrame() {
		int tabIndex = DemoPrefs.getState().getInt(MainApplication.KEY_TAB, 0);

		initComponents();

		// controlBar.initialize( this, tabbedPane );

		setIconImages(FlatSVGUtils.createWindowIconImages("/com/formdev/flatlaf/demo/FlatLaf.svg"));

		if (tabIndex >= 0 && tabIndex < tabbedPane.getTabCount() && tabIndex != tabbedPane.getSelectedIndex())
			tabbedPane.setSelectedIndex(tabIndex);

		// integrate into macOS screen menu
		FlatDesktop.setAboutHandler(this::aboutActionPerformed);
		FlatDesktop.setPreferencesHandler(this::showPreferences);
		FlatDesktop.setQuitHandler(response -> {
			response.performQuit();
		});

	}

	public void changeApplicationLanguage(Locale newLanguage) {
		if(newLanguage != null) {
			ApplicationContext.getInstance().setLocale(newLanguage);
			System.out.println("changeApplicationLanguage " + newLanguage);	
			menuBarComponent.setOrUpdateLabelForCurrentLangue();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		FlatUIDefaultsInspector.hide();
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

		JOptionPane.showMessageDialog(this,
				new Object[] { titleLabel, "Demonstrates FlatLaf Swing look and feel", " ",
						"Copyright 2019-" + Year.now() + " FormDev Software GmbH", linkLabel, },
				"About", JOptionPane.PLAIN_MESSAGE);
	}

	private void showPreferences() {
		JOptionPane.showMessageDialog(this,
				"Sorry, but FlatLaf Demo does not have preferences. :(\n"
						+ "This dialog is here to demonstrate usage of class 'FlatDesktop' on macOS.",
				"Preferences", JOptionPane.PLAIN_MESSAGE);
	}

	private void selectedTabChanged() {
		DemoPrefs.getState().putInt(MainApplication.KEY_TAB, tabbedPane.getSelectedIndex());
	}

	private void initComponents() {

		JPanel contentPanel = new JPanel();
		tabbedPane = new JTabbedPane();
		BasicComponentsPanel basicComponentsPanel = new BasicComponentsPanel();
		// MoreComponentsPanel moreComponentsPanel = new MoreComponentsPanel();
		// DataComponentsPanel dataComponentsPanel = new DataComponentsPanel();
		// TabsPanel tabsPanel = new TabsPanel();
		// OptionPanePanel optionPanePanel = new OptionPanePanel();
		// ExtrasPanel extrasPanel1 = new ExtrasPanel();
		FooterBar controlBar = new FooterBar(this);
		// themesPanel = new IJThemesPanel();

		// ======== this ========
		setTitle("My Usecase with FlatLaf");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		menuBarComponent = new MenuBarComponent(this);
		setJMenuBar(menuBarComponent.getMenuBar());

		contentPane.add(new ToolBarComponent().getToolBar(), BorderLayout.NORTH);

		// ======== contentPanel ========
		{
			contentPanel.setLayout(new MigLayout("insets dialog,hidemode 3",
					// columns
					"[grow,fill]",
					// rows
					"[grow,fill]"));

			// ======== tabbedPane ========
			{
				tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				tabbedPane.addChangeListener(e -> selectedTabChanged());
				tabbedPane.addTab("Basic Components", basicComponentsPanel);
				// tabbedPane.addTab("More Components", moreComponentsPanel);
				// tabbedPane.addTab("Data Components", dataComponentsPanel);
				// tabbedPane.addTab("Tabs", tabsPanel);
				// tabbedPane.addTab("Option Pane", optionPanePanel);
				// tabbedPane.addTab("Extras", extrasPanel1);
			}
			contentPanel.add(tabbedPane, "cell 0 0");
		}
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(controlBar, BorderLayout.SOUTH);
		// contentPane.add(themesPanel, BorderLayout.EAST);

		// JFormDesigner - End of component initialization //GEN-END:initComponents

		if (FlatLaf.supportsNativeWindowDecorations()
				|| (SystemInfo.isLinux && JFrame.isDefaultLookAndFeelDecorated())) {
			if (SystemInfo.isLinux)
				unsupported(windowDecorationsCheckBoxMenuItem);
			else

			if (JBRCustomDecorations.isSupported()) {
				// If the JetBrains Runtime is used, it forces the use of it's own custom
				// window decoration, which can not disabled.
				windowDecorationsCheckBoxMenuItem.setEnabled(false);
			}
		} else {
			unsupported(windowDecorationsCheckBoxMenuItem);
			unsupported(menuBarEmbeddedCheckBoxMenuItem);
			unsupported(unifiedTitleBarMenuItem);
			unsupported(showTitleBarIconMenuItem);
		}

		if (SystemInfo.isMacOS)
			unsupported(underlineMenuSelectionMenuItem);

		// remove contentPanel bottom insets
		MigLayout layout = (MigLayout) contentPanel.getLayout();
		LC lc = ConstraintParser.parseLayoutConstraint((String) layout.getLayoutConstraints());
		UnitValue[] insets = lc.getInsets();
		lc.setInsets(new UnitValue[] { insets[0], insets[1], new UnitValue(0, UnitValue.PIXEL, null), insets[3] });
		layout.setLayoutConstraints(lc);
	}

	private void unsupported(JCheckBoxMenuItem menuItem) {
		menuItem.setEnabled(false);
		menuItem.setSelected(false);
		menuItem.setToolTipText("Not supported on your system.");
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables

	private JCheckBoxMenuItem windowDecorationsCheckBoxMenuItem;
	private JCheckBoxMenuItem menuBarEmbeddedCheckBoxMenuItem;
	private JCheckBoxMenuItem unifiedTitleBarMenuItem;
	private JCheckBoxMenuItem showTitleBarIconMenuItem;
	private JCheckBoxMenuItem underlineMenuSelectionMenuItem;

	private JTabbedPane tabbedPane;
	// private ControlBar controlBar;
	// IJThemesPanel themesPanel;
	// JFormDesigner - End of variables declaration //GEN-END:variables

}

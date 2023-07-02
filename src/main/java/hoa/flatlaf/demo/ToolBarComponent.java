package hoa.flatlaf.demo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatAbstractIcon;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.LoggingFacade;

public class ToolBarComponent {

	private static String[] accentColorKeys = { "Demo.accent.default", "Demo.accent.blue", "Demo.accent.purple",
			"Demo.accent.red", "Demo.accent.orange", "Demo.accent.yellow", "Demo.accent.green", };
	private static String[] accentColorNames = { "Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green", };
	private final JToggleButton[] accentColorButtons = new JToggleButton[accentColorKeys.length];

	private Color accentColor;

	private JToolBar toolBar;

	public ToolBarComponent() {
		initComponents();
		initAccentColors();
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	private void initComponents() {
		toolBar = new JToolBar();
		JButton backButton = new JButton();
		JButton forwardButton = new JButton();
		JButton cutButton = new JButton();
		JButton copyButton = new JButton();
		JButton pasteButton = new JButton();
		JButton refreshButton = new JButton();
		JToggleButton showToggleButton = new JToggleButton();
		// ======== toolBar ========
		{
			toolBar.setMargin(new Insets(3, 3, 3, 3));

			// ---- backButton ----
			backButton.setToolTipText("Back");
			toolBar.add(backButton);

			// ---- forwardButton ----
			forwardButton.setToolTipText("Forward");
			toolBar.add(forwardButton);
			toolBar.addSeparator();

			// ---- cutButton ----
			cutButton.setToolTipText("Cut");
			toolBar.add(cutButton);

			// ---- copyButton ----
			copyButton.setToolTipText("Copy");
			toolBar.add(copyButton);

			// ---- pasteButton ----
			pasteButton.setToolTipText("Paste");
			toolBar.add(pasteButton);
			toolBar.addSeparator();

			// ---- refreshButton ----
			refreshButton.setToolTipText("Refresh");
			toolBar.add(refreshButton);
			toolBar.addSeparator();

			// ---- showToggleButton ----
			showToggleButton.setSelected(true);
			showToggleButton.setToolTipText("Show Details");

			backButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/back.svg"));
			forwardButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/forward.svg"));
			cutButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-cut.svg"));
			copyButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/copy.svg"));
			pasteButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/menu-paste.svg"));
			refreshButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/refresh.svg"));
			showToggleButton.setIcon(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/show.svg"));

			toolBar.add(showToggleButton);
		}
	}

	private void initAccentColors() {
		JLabel accentColorLabel = new JLabel("Accent color: ");

		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(accentColorLabel);

		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < accentColorButtons.length; i++) {
			accentColorButtons[i] = new JToggleButton(new AccentColorIcon(accentColorKeys[i]));
			accentColorButtons[i].setToolTipText(accentColorNames[i]);
			accentColorButtons[i].addActionListener(this::accentColorChanged);
			toolBar.add(accentColorButtons[i]);
			group.add(accentColorButtons[i]);
		}

		accentColorButtons[0].setSelected(true);

		FlatLaf.setSystemColorGetter(name -> {
			return name.equals("accent") ? accentColor : null;
		});

		UIManager.addPropertyChangeListener(e -> {
			if ("lookAndFeel".equals(e.getPropertyName()))
				updateAccentColorButtons();
		});
		updateAccentColorButtons();
	}

	private void accentColorChanged(ActionEvent e) {
		String accentColorKey = null;
		for (int i = 0; i < accentColorButtons.length; i++) {
			if (accentColorButtons[i].isSelected()) {
				accentColorKey = accentColorKeys[i];
				break;
			}
		}

		accentColor = (accentColorKey != null && accentColorKey != accentColorKeys[0])
				? UIManager.getColor(accentColorKey)
				: null;

		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		try {
			FlatLaf.setup(lafClass.getDeclaredConstructor().newInstance());
			FlatLaf.updateUI();
		} catch (Exception ex) {
			LoggingFacade.INSTANCE.logSevere(null, ex);
		}
	}

	private void updateAccentColorButtons() {
		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		boolean isAccentColorSupported = lafClass == FlatLightLaf.class || lafClass == FlatDarkLaf.class
				|| lafClass == FlatIntelliJLaf.class || lafClass == FlatDarculaLaf.class
				|| lafClass == FlatMacLightLaf.class || lafClass == FlatMacDarkLaf.class;
		for (int i = 0; i < accentColorButtons.length; i++)
			accentColorButtons[i].setVisible(isAccentColorSupported);
	}

	// ---- class AccentColorIcon ----------------------------------------------

	private static class AccentColorIcon extends FlatAbstractIcon {
		private final String colorKey;

		AccentColorIcon(String colorKey) {
			super(16, 16, null);
			this.colorKey = colorKey;
		}

		@Override
		protected void paintIcon(Component c, Graphics2D g) {
			Color color = UIManager.getColor(colorKey);
			if (color == null)
				color = Color.lightGray;
			else if (!c.isEnabled()) {
				color = FlatLaf.isLafDark() ? ColorFunctions.shade(color, 0.5f) : ColorFunctions.tint(color, 0.6f);
			}

			g.setColor(color);
			g.fillRoundRect(1, 1, width - 2, height - 2, 5, 5);
		}
	}
}

package hoa.flatlaf.demo;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.formdev.flatlaf.util.UIScale;

import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

class FooterBar extends JPanel {

	private MainFrame mainFrameOwner;

	private JSeparator separator1;
	private LookAndFeelsComboBox lookAndFeelComboBox;
	private JCheckBox rightToLeftCheckBox;
	private JCheckBox enabledCheckBox;
	private JLabel infoLabel;
	private JButton closeButton;
	
	public FooterBar(MainFrame mainFrameOwner) {

		this.mainFrameOwner = mainFrameOwner;

		initComponents();

		// remove top insets
		MigLayout layout = (MigLayout) getLayout();
		LC lc = ConstraintParser.parseLayoutConstraint((String) layout.getLayoutConstraints());
		UnitValue[] insets = lc.getInsets();
		lc.setInsets(new UnitValue[] { new UnitValue(0, UnitValue.PIXEL, null), insets[1], insets[2], insets[3] });
		layout.setLayoutConstraints(lc);

		// initialize look and feels combo box
		DefaultComboBoxModel<LookAndFeelInfo> lafModel = new DefaultComboBoxModel<>();
		lafModel.addElement(new LookAndFeelInfo("FlatLaf Light (F1)", FlatLightLaf.class.getName()));
		lafModel.addElement(new LookAndFeelInfo("FlatLaf Dark (F2)", FlatDarkLaf.class.getName()));
		lafModel.addElement(new LookAndFeelInfo("FlatLaf IntelliJ (F3)", FlatIntelliJLaf.class.getName()));
		lafModel.addElement(new LookAndFeelInfo("FlatLaf Darcula (F4)", FlatDarculaLaf.class.getName()));

		UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo lookAndFeel : lookAndFeels) {
			String name = lookAndFeel.getName();
			String className = lookAndFeel.getClassName();
			if (className.equals("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel")
					|| className.equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel"))
				continue;

			if ((SystemInfo.isWindows && className.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
					|| (SystemInfo.isMacOS && className.equals("com.apple.laf.AquaLookAndFeel"))
					|| (SystemInfo.isLinux && className.equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")))
				name += " (F9)";
			else if (className.equals(MetalLookAndFeel.class.getName()))
				name += " (F12)";
			else if (className.equals(NimbusLookAndFeel.class.getName()))
				name += " (F11)";

			lafModel.addElement(new LookAndFeelInfo(name, className));
		}
		
		updateInfoLabel();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		separator1 = new JSeparator();
		lookAndFeelComboBox = new LookAndFeelsComboBox();
		rightToLeftCheckBox = new JCheckBox();
		enabledCheckBox = new JCheckBox();
		infoLabel = new JLabel();
		closeButton = new JButton();

		// ======== this ========
		setLayout(new MigLayout("insets dialog",
				// columns
				"[fill]" + "[fill]" + "[fill]" + "[grow,fill]" + "[button,fill]",
				// rows
				"[bottom]" + "[]"));
		add(separator1, "cell 0 0 5 1");

		// ---- lookAndFeelComboBox ----
		add(lookAndFeelComboBox, "cell 0 1");

		// ---- rightToLeftCheckBox ----
		rightToLeftCheckBox.setText("right-to-left");
		rightToLeftCheckBox.setMnemonic('R');
		
		add(rightToLeftCheckBox, "cell 1 1");

		// ---- enabledCheckBox ----
		enabledCheckBox.setText("enabled");
		enabledCheckBox.setMnemonic('E');
		enabledCheckBox.setSelected(true);
		
		add(enabledCheckBox, "cell 2 1");

		// ---- infoLabel ----
		infoLabel.setText("text");
		add(infoLabel, "cell 3 1,alignx center,growx 0");

		// ---- closeButton ----
		closeButton.setText("Close");
		closeButton.addActionListener(e -> closePerformed());
		add(closeButton, "cell 4 1");
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}


	private void updateInfoLabel() {
		String javaVendor = System.getProperty("java.vendor");
		if ("Oracle Corporation".equals(javaVendor))
			javaVendor = null;
		double systemScaleFactor = UIScale.getSystemScaleFactor(getGraphicsConfiguration());
		float userScaleFactor = UIScale.getUserScaleFactor();
		Font font = UIManager.getFont("Label.font");
		String newInfo = "(Java " + System.getProperty("java.version") + (javaVendor != null ? ("; " + javaVendor) : "")
				+ (systemScaleFactor != 1 ? (";  system scale factor " + systemScaleFactor) : "")
				+ (userScaleFactor != 1 ? (";  user scale factor " + userScaleFactor) : "")
				+ (systemScaleFactor == 1 && userScaleFactor == 1 ? "; no scaling" : "") + "; " + font.getFamily() + " "
				+ font.getSize() + (font.isBold() ? " BOLD" : "") + (font.isItalic() ? " ITALIC" : "") + ")";

		if (!newInfo.equals(infoLabel.getText()))
			infoLabel.setText(newInfo);
	}

	private void closePerformed() {
		mainFrameOwner.dispose();
	}
}

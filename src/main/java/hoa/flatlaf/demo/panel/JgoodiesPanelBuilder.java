package hoa.flatlaf.demo.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class JgoodiesPanelBuilder {

	public JPanel buildPanel() {
		FormLayout layout = new FormLayout(
				// columns
				"right:pref, 3dlu, 50dlu, 7dlu, right:pref, 3dlu, pref",
				// rows
				"p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");

		// Specify that columns 1 & 5 as well as 3 & 7 have equal widths.
		layout.setColumnGroups(new int[][] { { 1, 5 }, { 3, 7 } });

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		// Obtain a reusable constraints object to place components in the grid.
		CellConstraints cc = new CellConstraints();

		// Fill the grid with components; the builder can create
		// frequently used components, e.g. separators and labels.

		JTextField companyField = new JTextField();
		JTextField contactField = new JTextField();
		JTextField ptiField = new JTextField();
		JTextField powerField = new JTextField();
		JTextField radiusField = new JTextField();
		JTextField diameterField = new JTextField();

		// Add a titled separator to cell (1, 1) that spans 7 columns.
		builder.addSeparator("General", cc.xyw(1, 1, 7));
		builder.add(new JLabel("Company"), cc.xy(1, 3));
		builder.add(companyField, cc.xyw(3, 3, 5));
		builder.addLabel("Contact", cc.xy(1, 5));
		builder.add(contactField, cc.xyw(3, 5, 5));

		builder.addSeparator("Propeller", cc.xyw(1, 7, 7));
		builder.addLabel("PTI [kW]", cc.xy(1, 9));
		builder.add(ptiField, cc.xy(3, 9));
		builder.addLabel("Power [kW]", cc.xy(5, 9));
		builder.add(powerField, cc.xy(7, 9));
		builder.addLabel("R [mm]", cc.xy(1, 11));
		builder.add(radiusField, cc.xy(3, 11));
		builder.addLabel("D [mm]", cc.xy(5, 11));
		builder.add(diameterField, cc.xy(7, 11));

		builder.add(new JButton("Test"), cc.xy(1, 13));
		
		// The builder holds the layout container that we now return.
		return builder.getPanel();

	}

}

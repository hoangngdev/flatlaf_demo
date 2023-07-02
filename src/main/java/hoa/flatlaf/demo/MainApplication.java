package hoa.flatlaf.demo;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.util.SystemInfo;

public class MainApplication {

	static final String PREFS_ROOT_PATH = "/flatlaf-demo";
	static final String KEY_TAB = "tab";

	public static void main( String[] args ) {
		

		SwingUtilities.invokeLater( () -> {
			
			FlatLightLaf.setup();
			
			DemoPrefs.init( PREFS_ROOT_PATH );

			// install fonts for lazy loading
			//FlatInterFont.installLazy();
			//FlatJetBrainsMonoFont.installLazy();
			//FlatRobotoFont.installLazy();
			//FlatRobotoMonoFont.installLazy();

			// application specific UI defaults
			//FlatLaf.registerCustomDefaultsSource( "com.formdev.flatlaf.demo" );

			// set look and feel
			//DemoPrefs.setupLaf( args );

			// install inspectors
			FlatInspector.install( "ctrl shift alt X" );
			FlatUIDefaultsInspector.install( "ctrl shift alt Y" );

			// create frame
			DemoFrame frame = new DemoFrame();

			// show frame
			frame.pack();
			frame.setLocationRelativeTo( null );
			frame.setVisible( true );
		} );
	}
	
}

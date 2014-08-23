import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class CatchKeyboardEvent implements HotkeyListener {
	String RaccourciCapture = "Rien";
	GestionRaccourcis LesRaccourcis = new GestionRaccourcis();

	public void installKeyGrabber() {

		String RaccourciCapture = LesRaccourcis.DemandeRaccourci("Capture");
		String RaccourciConnexion = LesRaccourcis.DemandeRaccourci("Connexion");
		String RaccourciConsignes = LesRaccourcis.DemandeRaccourci("Consignes");

		JIntellitype.getInstance();
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_WIN, RaccourciCapture.charAt(0));
		JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_WIN, RaccourciConnexion.charAt(0));
		JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_WIN, RaccourciConsignes.charAt(0));
		JIntellitype.getInstance().addHotKeyListener(this);

	}

	@Override
	public void onHotKey(int aIdentifier) {

		if (aIdentifier == 1) {
			// Raccourci Capture
			RaccourciCapture = "Capture";

		}
		if (aIdentifier == 2) {
			// Raccourci Connexion
			RaccourciCapture = "Connexion";

		}
		if (aIdentifier == 3) {
			// Raccourci Connexion
			RaccourciCapture = "Consignes";

		}
	}

	public void UninstallKeyboard()

	{
		JIntellitype.getInstance().unregisterHotKey(1);
		JIntellitype.getInstance().unregisterHotKey(2);
		JIntellitype.getInstance().unregisterHotKey(3);
		// JIntellitype.getInstance().cleanUp();
		Thread.yield();

		// Thread.currentThread().stop();

	}

}

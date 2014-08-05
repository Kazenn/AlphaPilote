import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.Toolkit;


public class UserInterface extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ZoneIp;
	String CheminQuick3270 = "C:\\Program Files (x86)\\Quick3270 Secure\\Quick3270.exe";
	String CheminQuick3270ProfileGmvs = "D:\\AlphaPilote\\GMVS.ecf";
	String CheminPutty = "D:\\AlphaPilote\\Putty.exe";
	//CheminQuick3270ProfileGmvs

	public UserInterface() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserInterface.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		setTitle(" AlphaPilote v0.1    -     Etienne PFISTNER");
		
		JMenuBar BarreMenuPrincipale = new JMenuBar();
		setJMenuBar(BarreMenuPrincipale);
		
		JMenu MenuFichier = new JMenu("Fichier");
		BarreMenuPrincipale.add(MenuFichier);
		
		JMenuItem MenuQuitter = new JMenuItem("Quitter");
		MenuQuitter.setIcon(new ImageIcon(UserInterface.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		MenuQuitter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int code_retour = 0;
				System.exit(code_retour);
				}
		});
		
		
		MenuQuitter.setBackground(Color.RED);
		MenuFichier.add(MenuQuitter);
		
		JSeparator SeparateurFichierFavoris = new JSeparator();
		SeparateurFichierFavoris.setPreferredSize(new Dimension(0, 10));
		SeparateurFichierFavoris.setMaximumSize(new Dimension(5, 100));
		SeparateurFichierFavoris.setOrientation(SwingConstants.VERTICAL);
		BarreMenuPrincipale.add(SeparateurFichierFavoris);
		
		JMenu mnFavorisPilotage = new JMenu("Favoris Pilotage");
		BarreMenuPrincipale.add(mnFavorisPilotage);
		
		JScrollPane ScrollZoneTextLog = new JScrollPane();
		ScrollZoneTextLog.setViewportBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 69, 0)));
		ScrollZoneTextLog.setBounds(10, 268, 564, 201);
		getContentPane().add(ScrollZoneTextLog);
		
		JTextArea ZoneTextLog = new JTextArea();
		ScrollZoneTextLog.setViewportView(ZoneTextLog);
		ZoneTextLog.setWrapStyleWord(true);
		ZoneTextLog.setLineWrap(true);
		ZoneTextLog.setFont(new Font("Verdana", Font.PLAIN, 11));
		ZoneTextLog.setBackground(Color.LIGHT_GRAY);
		ZoneTextLog.setEditable(false);
		
		
		
		JMenu MenuConnexionMvs = new JMenu("Connexion");
		BarreMenuPrincipale.add(MenuConnexionMvs);
		
		JMenu SousMenuConnexionMvs = new JMenu("MVS");
		SousMenuConnexionMvs.setIcon(new ImageIcon(UserInterface.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		MenuConnexionMvs.add(SousMenuConnexionMvs);
		
		JMenuItem MenuGmvs = new JMenuItem("TPX Gmvs");
		SousMenuConnexionMvs.add(MenuGmvs);
		
		MenuGmvs.setIcon(new ImageIcon(UserInterface.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaVolumeThumb.png")));
		
		MenuGmvs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				GestionLog MaLog = new GestionLog();
				
								
				try {
					
					
					//String CheminQuick3270 = "C:\\Program Files (x86)\\Quick3270 Secure\\Quick3270.exe";
					//String CheminQuick3270ProfileGmvs = "D:\\AlphaPilote\\GMVS.ecf";
					ProcessBuilder builder = new ProcessBuilder(new String[] { CheminQuick3270, CheminQuick3270ProfileGmvs });
					Process p = builder.start();
					
					ZoneTextLog.append("Ouverture connexion 3270 vers " + MenuGmvs.getText());
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					
		            
		            
		            
		            UserInterfaceConfig UserInterfacePourDemande = new UserInterfaceConfig();
		            if ( UserInterfacePourDemande.DemandeEtatAutoLoginGmvs() == true)
		            {
		           
		            SmartRobot SuperRobot = new SmartRobot();
		            SuperRobot.delay(500);
		            SuperRobot.type("tpx");
		            SuperRobot.delay(50);
		            SuperRobot.keyPress(KeyEvent.VK_ENTER);
		            
		            GestionConfig MaConfig = new GestionConfig();
		            String UserDemande = MaConfig.DemandeUser("Gmvs");
		            String PassDemande = MaConfig.DemandePassword("Gmvs");
		            
		            SuperRobot.delay(250);
		            SuperRobot.type(UserDemande);
		            SuperRobot.keyPress(KeyEvent.VK_TAB);
		            SuperRobot.type(PassDemande);
		            SuperRobot.keyPress(KeyEvent.VK_ENTER);
		            }
					
				} catch (IOException e1) {
					
					MaLog.EcrireDansFichierLog("Erreur au lancement de quick3270 pour GMVS : " +e1);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e1.printStackTrace();
					
				} catch (AWTException e) {
					e.printStackTrace();
					MaLog.EcrireDansFichierLog("Erreur au lancement de quick3270 pour GMVS : " +e);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				}
				
			}
		});
		getContentPane().setLayout(null);
		
		JPanel PaneZoneConnexion = new JPanel();
		PaneZoneConnexion.setSize(new Dimension(10, 10));
		PaneZoneConnexion.setOpaque(false);
		PaneZoneConnexion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Connexion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 99, 71)));
		PaneZoneConnexion.setBounds(6, 2, 308, 224);
		getContentPane().add(PaneZoneConnexion);
		PaneZoneConnexion.setLayout(null);
		
		ZoneIp = new JTextField();
		
		ZoneIp.setBounds(6, 17, 147, 20);
		PaneZoneConnexion.add(ZoneIp);
		ZoneIp.setText("192.168.0.253");
		ZoneIp.setColumns(10);
		
		
		JButton BoutonValideIp = new JButton("Ping");
		BoutonValideIp.setBounds(226, 16, 72, 23);
		PaneZoneConnexion.add(BoutonValideIp);
		
		JButton BoutonValideTelnet = new JButton("Telnet");
		BoutonValideTelnet.setBounds(216, 50, 82, 23);
		PaneZoneConnexion.add(BoutonValideTelnet);
		
		
		
		
	
		JButton BoutonValideSsh = new JButton("SSH");
		BoutonValideSsh.setBounds(216, 84, 82, 23);
		PaneZoneConnexion.add(BoutonValideSsh);
		
		JMenuItem MenuOdip = new JMenuItem("ODIP");
		MenuOdip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				URI uri = URI.create("http://pilotage-upfc.supragrp.caisse-epargne.fr/pilotage/showChecklistAction.action");
				try {
					Desktop.getDesktop().browse(uri);
					
					ZoneTextLog.append("Ouverture de la page " + MenuOdip.getText() );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
		            
				} catch (IOException e) {
					GestionLog MaLog = new GestionLog();
					MaLog.EcrireDansFichierLog("Erreur au lancement d'ODIP : " +e);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e.printStackTrace();
				}
				
			}
		});
		
		MenuOdip.setIcon(new ImageIcon(UserInterface.class.getResource("/com/sun/javafx/scene/web/skin/IncreaseIndent_16x16_JFX.png")));
		mnFavorisPilotage.add(MenuOdip);
		
		JMenu MenuConfiguration = new JMenu("Configuration");
		BarreMenuPrincipale.add(MenuConfiguration);
		
		JMenuItem MenuModifierFichierConfig = new JMenuItem("Modifier fichier de config");
		MenuModifierFichierConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				UserInterfaceConfig PageConfig = new UserInterfaceConfig();
				
				PageConfig.setVisible(true);
				PageConfig.setSize(640,480);;
				
			}
		});
		MenuModifierFichierConfig.setIcon(new ImageIcon(UserInterface.class.getResource("/com/sun/javafx/scene/web/skin/Copy_16x16_JFX.png")));
		MenuConfiguration.add(MenuModifierFichierConfig);
		
		JMenu MenuAPropos = new JMenu("A propos d'AlphaPilote");
		BarreMenuPrincipale.add(MenuAPropos);
		
		JMenuItem MenuLogErreurs = new JMenuItem("Log d'erreurs");
		MenuLogErreurs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
	            //pb.CheminQuick3270ProfileGmvs(new File(CHEMIN));
								//ProcessBuilder builder = new ProcessBuilder(new String[] { CheminQuick3270, CheminQuick3270ProfileGmvs });
				try {
					ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "start D:\\AlphaPilote\\log.txt");
					Process p = builder.start();
				} catch (IOException e) {
					
					GestionLog MaLog = new GestionLog();
					MaLog.EcrireDansFichierLog("Erreur au lancement de la log : " +e);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e.printStackTrace();
				}
				
			}
		});
		MenuAPropos.add(MenuLogErreurs);
		
		JSeparator SeparateurApropos = new JSeparator();
		MenuAPropos.add(SeparateurApropos);
		
		JMenuItem MenuSignalerUnBug = new JMenuItem("Signaler un bug");
		MenuSignalerUnBug.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				Desktop desktop = Desktop.getDesktop(); 
			    String message = "mailto:epfistner@gmail.com?subject=Bug_ou_suggestion_pour_AlphaPilote&body=Fichier_log_attache&attachment=d:/AlphaPilote/log.txt"; 
			    URI uri = URI.create(message); 
			    try {
					desktop.mail(uri);
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
		MenuAPropos.add(MenuSignalerUnBug);
		
		JMenuItem MenuInterrogation = new JMenuItem("?");
		MenuAPropos.add(MenuInterrogation);
		
		
		JButton BoutonValideMstsc = new JButton("MSTSC");
		BoutonValideMstsc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				String TextZoneIp = ZoneIp.getText();
				
				try {
					Runtime.getRuntime().exec(String.format("mstsc.exe /v:" + TextZoneIp ));
					ZoneTextLog.append("Lancement mstsc pour " + ZoneIp.getText()  );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				} catch (IOException e1) {
					
					GestionLog MaLog = new GestionLog();
					MaLog.EcrireDansFichierLog("Erreur au lancement de la log : " +e1);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e1.printStackTrace();
				}
				
				
			}
		});
		BoutonValideMstsc.setBounds(216, 118, 82, 23);
		PaneZoneConnexion.add(BoutonValideMstsc);
		
		JLabel ResultatPing = new JLabel("");
		ResultatPing.setFont(new Font("Arial", Font.BOLD, 13));
		ResultatPing.setForeground(new Color(51, 153, 0));
		ResultatPing.setBounds(164, 17, 64, 20);
		PaneZoneConnexion.add(ResultatPing);
		
		JPanel ZoneConnexionAutoLogin = new JPanel();
		ZoneConnexionAutoLogin.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Auto login", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 204)));
		ZoneConnexionAutoLogin.setBounds(6, 44, 147, 114);
		PaneZoneConnexion.add(ZoneConnexionAutoLogin);
		ZoneConnexionAutoLogin.setLayout(null);
		
		
		
		JRadioButton BoutonRadioRoot = new JRadioButton("root");
		BoutonRadioRoot.setBounds(6, 16, 70, 23);
		ZoneConnexionAutoLogin.add(BoutonRadioRoot);
		
		
		JRadioButton BoutonRadioSalle = new JRadioButton("salle");
		BoutonRadioSalle.setBounds(6, 42, 70, 27);
		ZoneConnexionAutoLogin.add(BoutonRadioSalle);
		
		
		JRadioButton BoutonRadioUserPerso = new JRadioButton("j0*****");
		BoutonRadioUserPerso.setBounds(6, 72, 70, 18);
		ZoneConnexionAutoLogin.add(BoutonRadioUserPerso);
		
		
		//Ajout des bouton dans le ButtonGroup
				ButtonGroup MesBoutonsLogin = new ButtonGroup();
				MesBoutonsLogin.add(BoutonRadioRoot);
				MesBoutonsLogin.add(BoutonRadioSalle);
				MesBoutonsLogin.add(BoutonRadioUserPerso);
				
				Checkbox CheckBoxPassword = new Checkbox("+ pass");
				CheckBoxPassword.setBounds(83, 72, 54, 22);
				ZoneConnexionAutoLogin.add(CheckBoxPassword);
		
		
		
		
		JProgressBar MaBarreProgression = new JProgressBar();
		MaBarreProgression.setBounds(10, 237, 164, 20);
		getContentPane().add(MaBarreProgression);
		MaBarreProgression.setMaximum(20);
		MaBarreProgression.setIndeterminate(true);
		MaBarreProgression.setForeground(new Color(60, 179, 113));
		
		JPanel ZoneConfig = new JPanel();
		ZoneConfig.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 99, 71)));
		ZoneConfig.setBounds(486, 2, 216, 146);
		getContentPane().add(ZoneConfig);
		ZoneConfig.setLayout(null);
		
		JButton BoutonChargerConfig = new JButton("Charger config");
		BoutonChargerConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				int CodeRetour = 5;
				String UserG = "";
				String UserUnix = "";
				GestionConfig MaConfig = new GestionConfig();
				CodeRetour = MaConfig.LireConfig();
				UserG = MaConfig.DemandeUser("G");
				UserUnix = MaConfig.DemandeUser("Unix");
				if (CodeRetour == 0)
				{
					ZoneTextLog.append("Chargement config OK");
					ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					BoutonRadioUserPerso.setText(UserUnix);
					
					
				}
				if (CodeRetour == 1)
				{
					ZoneTextLog.append("Probl�me chargement config");
					ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					
				}
				
				
			}
		});
		BoutonChargerConfig.setBounds(6, 16, 126, 23);
		ZoneConfig.add(BoutonChargerConfig);
		BoutonValideSsh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
									
					//final String CHEMIN = "D:\\";
					String TextZoneIp = ZoneIp.getText();
					
					try {
						//Runtime.getRuntime().exec(String.format("cmd.exe /c start D:\\AlphaPilote\\Putty.exe -ssh " + TextZoneIp ));
						//ZoneTextLog.append("Lancement putty ssh pour " + ZoneIp.getText()  );
			            //ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
						
						Boolean LanceUneFois = true;
			            
			             if (BoutonRadioRoot.isSelected() == true && LanceUneFois == true ) {
			            	 
			            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -ssh -l root"));
			            	 	ZoneTextLog.append("Lancement putty ssh vers " + ZoneIp.getText() + " avec le user root" );
					            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					            LanceUneFois = false;
					            MesBoutonsLogin.clearSelection();
					       
			             }
			             		             		             
			             if (BoutonRadioSalle.isSelected() == true && LanceUneFois == true ) {
			            	 
			            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -ssh -l salle"));
			            	 	ZoneTextLog.append("Lancement putty ssh vers " + ZoneIp.getText() + " avec le user salle" );
					            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					            LanceUneFois = false;
					            MesBoutonsLogin.clearSelection();
			             }
					            
					     if (BoutonRadioUserPerso.isSelected() == true && LanceUneFois == true && CheckBoxPassword.getState() == false) {
					            	 
				            	 	String EtatBouton = BoutonRadioUserPerso.getText();
				            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -ssh -l "+EtatBouton));
				            	 	ZoneTextLog.append("Lancement putty ssh vers " + ZoneIp.getText() + " avec le user " + BoutonRadioUserPerso.getText() );
						            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
						            LanceUneFois = false;
						            MesBoutonsLogin.clearSelection();
				            	     
				             }
				             
				          if (BoutonRadioUserPerso.isSelected() == true && LanceUneFois == true && CheckBoxPassword.getState() == true) {
				            	 	
				            	 	String EtatBouton = BoutonRadioUserPerso.getText();
				            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -ssh -l "+EtatBouton));
				            	 	ZoneTextLog.append("Lancement putty ssh vers " + ZoneIp.getText() + " avec le user " +EtatBouton+" et le mot de passe" );
						            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
						            LanceUneFois = false;
						            MesBoutonsLogin.clearSelection();
						            
						            int CodeRetour = 0;
									String PasswordUnix = "";
									GestionConfig MaConfig = new GestionConfig();
									PasswordUnix = MaConfig.DemandePassword("Unix");
									
									if (CodeRetour == 0)
									{
										
										SmartRobot SuperRobot = new SmartRobot();
							            SuperRobot.delay(1500);
							            SuperRobot.writeToClipboard(PasswordUnix);
							            SuperRobot.mousePress(InputEvent.BUTTON3_MASK);
							            SuperRobot.delay(50);
							            SuperRobot.mouseRelease(InputEvent.BUTTON3_MASK);
							            SuperRobot.delay(50);
							            SuperRobot.keyPress(KeyEvent.VK_ENTER);
									
									}
									else
									{
										CheckBoxPassword.setState(false);
										ZoneTextLog.append("Probl�me lors de la demande du mot de passe");
										ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
										
									}
					
				             }
			      
			             
			             if (LanceUneFois == true){
			            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " "+ TextZoneIp + " -ssh" ));
			            	 	ZoneTextLog.append("Lancement putty ssh vers " + ZoneIp.getText());
			            	 	ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
			            	 	LanceUneFois = false;
			            	 	MesBoutonsLogin.clearSelection();
					         
			             }
			             CheckBoxPassword.setState(false);
						
					} catch (IOException e1) {
						
						GestionLog MaLog = new GestionLog();
						MaLog.EcrireDansFichierLog("Erreur au lancement de putty : " +e1);
						ZoneTextLog.append("Erreur au lancement : Consulter la log." );
			            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
						e1.printStackTrace();
					} catch (AWTException e1) {
						
						GestionLog MaLog = new GestionLog();
						MaLog.EcrireDansFichierLog("Erreur au lancement de putty : " +e1);
						ZoneTextLog.append("Erreur au lancement : Consulter la log." );
			            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
						e1.printStackTrace();
					}
			            
				
			}
		});
		BoutonValideTelnet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				//final String CHEMIN = "D:\\";
				String TextZoneIp = ZoneIp.getText();
				
				try {
					
		            Boolean LanceUneFois = true;
		            
		             if (BoutonRadioRoot.isSelected() == true && LanceUneFois == true ) {
		            	 
		            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " "+ TextZoneIp +" -telnet -l root"));
		            	 	ZoneTextLog.append("Lancement putty telnet vers " + ZoneIp.getText() + " avec le user root" );
				            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				            LanceUneFois = false;
				            MesBoutonsLogin.clearSelection();
				            
		            	     
		             }
		             		             		             
		             if (BoutonRadioSalle.isSelected() == true && LanceUneFois == true ) {
		            	 
		            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -telnet -l salle"));
		            	 	ZoneTextLog.append("Lancement putty telnet vers " + ZoneIp.getText() + " avec le user salle" );
				            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				            LanceUneFois = false;
				            MesBoutonsLogin.clearSelection();
		            	     
		             }
		             
		             if (BoutonRadioUserPerso.isSelected() == true && LanceUneFois == true && CheckBoxPassword.getState() == false) {
		            	 
		            	 	String EtatBouton = BoutonRadioUserPerso.getText();
		            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -telnet -l "+EtatBouton));
		            	 	ZoneTextLog.append("Lancement putty telnet vers " + ZoneIp.getText() + " avec le user " + BoutonRadioUserPerso.getText() );
				            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				            LanceUneFois = false;
				            MesBoutonsLogin.clearSelection();
		            	     
		             }
		             
		             if (BoutonRadioUserPerso.isSelected() == true && LanceUneFois == true && CheckBoxPassword.getState() == true) {
		            	 	
		            	 	String EtatBouton = BoutonRadioUserPerso.getText();
		            	 	Runtime.getRuntime().exec(String.format(CheminPutty + " " + TextZoneIp +" -telnet -l "+EtatBouton));
		            	 	ZoneTextLog.append("Lancement putty telnet vers " + ZoneIp.getText() + " avec le user " +EtatBouton+" et le mot de passe" );
				            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
				            LanceUneFois = false;
				            MesBoutonsLogin.clearSelection();
				            
				            int CodeRetour = 0;
							String PasswordUnix = "";
							GestionConfig MaConfig = new GestionConfig();
							
							PasswordUnix = MaConfig.DemandePassword("Unix");
							if (CodeRetour == 0)
							{
								CheckBoxPassword.setState(false);
								SmartRobot SuperRobot = new SmartRobot();
					            SuperRobot.delay(1500);
					            SuperRobot.type(PasswordUnix);
					            SuperRobot.keyPress(KeyEvent.VK_ENTER);
							
							}
							else
							{
								CheckBoxPassword.setState(false);
								ZoneTextLog.append("Probl�me lors de la demande du mot de pass");
								ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
								
							}
			
		             }
		             
		             if (LanceUneFois == true){
		            	 	//Runtime.getRuntime().exec(String.format("cmd.exe /c start D:\\AlphaPilote\\Putty.exe -telnet " + TextZoneIp ));
		            	 	
		            	 	Runtime.getRuntime().exec(String.format(CheminPutty+" " + TextZoneIp ));
		            	 	ZoneTextLog.append("Lancement putty telnet vers " + ZoneIp.getText());
		            	 	ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
		            	 	LanceUneFois = false;
		            	 	MesBoutonsLogin.clearSelection();
		             }
		             // Lancement avec User perso, � faire quand la config remontera le user UNIX
		             	             
		       		            
				} catch (IOException e) {
					
					GestionLog MaLog = new GestionLog();
					MaLog.EcrireDansFichierLog("Erreur au lancement de putty : " +e);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e.printStackTrace();
				} catch (AWTException e) {
					
					GestionLog MaLog = new GestionLog();
					MaLog.EcrireDansFichierLog("Erreur au lancement de putty : " +e);
					ZoneTextLog.append("Erreur au lancement : Consulter la log." );
		            ZoneTextLog.setText (ZoneTextLog.getText() + "\n");
					e.printStackTrace();
				}
				
				
		    
			}
	
		});
		BoutonValideIp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
								
				ResultatPing.setForeground(Color.GRAY);
				String TextZoneIp = ZoneIp.getText();
				
				TextZoneIp = TextZoneIp + "\"";
				//System.out.println(TextZoneIp);	
				
				StringBuffer StringFinal = new StringBuffer (TextZoneIp);  
				String guillemet = "\"ping ";  
				StringFinal.insert (0, guillemet);  
				TextZoneIp = StringFinal.toString();	
				//System.out.println (TextZoneIp); 	
				
				final String CHEMIN = "D:\\";
			    
			        try {
			        	//System.setOut(new PrintStream(System.out, true, "IBM850"));
			        	ResultatPing.setForeground(Color.GRAY);
			            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", TextZoneIp, " -n 1");
			            pb.directory(new File(CHEMIN));
			            
			            Map<String, String> env = pb.environment();
			            for (Entry<?, ?> entry : env.entrySet()) {
			                System.out.println(entry.getKey() + " : " + entry.getValue());
			            }
			            
			            env.put("MonArg", "Valeur");
			            
			            Process p = pb.start();
			            AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());
			            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream());
			            new Thread(fluxSortie).start();
			            new Thread(fluxErreur).start();
			            
			            ZoneTextLog.append("Ping vers " + ZoneIp.getText() + " en cours ...");
			            ZoneTextLog.setText(ZoneTextLog.getText() + "\n");
			         
			            //MaBarreProgression.setValue(0);
			            p.waitFor();
			            
			            Boolean resultping = fluxSortie.LecteurFind();
			            
			            if (resultping == true){
			            	
			            	//ResultatPing.setEnabled(true);
			            	ResultatPing.setText("Ping OK");
			        		
			            	ResultatPing.setForeground(new Color(51, 153, 0));
			        	}
			            
			            if (resultping == false){
			            	//ResultatPing.setEnabled(true);
			            	ResultatPing.setText("Ping KO");
			    
			        		ResultatPing.setForeground(Color.RED);
			        	}
			         
			            //MaBarreProgression.setValue(100);
			             
			            //ZoneTextLog.append(fluxSortie.RetourFluxAfficheur);
			            //String TESTTTEST = fluxSortie.LecteurDeFlux();
			            //ZoneTextLog.setForeground(Color.GREEN);
			            ZoneTextLog.append(fluxSortie.LecteurDeFlux());

			        } catch (IOException e1) {
			            e1.printStackTrace();
			            ZoneTextLog.append("PING KO - Echec de la commande");
			            ZoneTextLog.setText(ZoneTextLog.getText() + "\n");
			            GestionLog MaLog = new GestionLog();
						MaLog.EcrireDansFichierLog("Erreur au lancement du ping : " +e);
						
						e1.printStackTrace();
			            
			         } catch (InterruptedException e1) {
						
			        	 ZoneTextLog.append("PING KO - Echec de la commande");
				         ZoneTextLog.setText(ZoneTextLog.getText() + "\n");
				         GestionLog MaLog = new GestionLog();
						 MaLog.EcrireDansFichierLog("Erreur au lancement du ping : " +e1);
						 e1.printStackTrace();
					}
			    }				
		
		});
		
		ZoneIp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				AWTEvent mouseReleased;
				
				BoutonValideIp.getMouseListeners();
				
				//ZoneTextLog.append(BoutonValideIp.getMouseListeners());
				//BoutonValideIp.dispatchEvent();
				//mouseReleased(MouseEvent e) {
			}
		});
		
		
	}
		
	public String getZoneIpText() {
		return ZoneIp.getText();
		
	}
	public void setZoneIpText(String text) {
		ZoneIp.setText(text);
	}
}

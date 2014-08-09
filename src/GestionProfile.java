import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;


public class GestionProfile {
	
	GestionChemin RequeteChemin = new GestionChemin();
	
	public int ModifierPasswordProfile(String Machine, String Password) throws IOException
	
	{
		int CodeRetour = 5;
		String ConfigPath = RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc";
		
		String LignePassword = "SendKeys "+"\""+Password+"<"+"Enter"+">"+"\"";
		//BufferedWriter bw = new BufferedWriter(new FileWriter(ConfigPath));
		ArrayList<String> TableauArray = new ArrayList<String>();
		BufferedReader input = new BufferedReader(new FileReader(RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc"));
		String[] TableauTempo;
		String line = "vide class";
		
		try {
			while (( line = input.readLine()) != null)
			{
			
				TableauArray.add(line);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableauTempo = (String[]) TableauArray.toArray(new String[0]);
		TableauTempo[10] = LignePassword;
				File file = new File(RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc");
				PrintWriter out = new PrintWriter(new FileWriter(file));

				for (String LaLigne : TableauTempo) {
				    out.println(LaLigne);
				}

				out.close();
				input.close();
		return CodeRetour;
		
		
		
		
	}

public int ModifierUserProfile(String Machine, String User) throws IOException
	
	{
		
		int CodeRetour = 5;
		String ConfigPath = RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc";
		
		String LignePassword = "SendKeys "+"\""+User+"<"+"Tab"+">"+"\"";
		
		ArrayList<String> TableauArray = new ArrayList<String>();
		BufferedReader input = new BufferedReader(new FileReader(RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc"));
		String[] TableauTempo;
		String line = "vide class";
		
		try {
			while (( line = input.readLine()) != null)
			{
			
				TableauArray.add(line);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableauTempo = (String[]) TableauArray.toArray(new String[0]);
		TableauTempo[8] = LignePassword;
				File file = new File(RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".qmc");
				PrintWriter out = new PrintWriter(new FileWriter(file));

				for (String LaLigne : TableauTempo) {
				    out.println(LaLigne);
				}

				out.close();
				input.close();

		return CodeRetour;
		
		
		
		
	}
	
	
	public void ModifierUserNameStationProfile(String Machine) throws IOException {

		Path path = Paths.get(RequeteChemin.DemandeChemin("CheminQuick3270ProfileGeneral")+Machine+".ecf");
		Charset charset = StandardCharsets.UTF_8;
		String UserNameStation = System.getProperty("user.name");
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("ProfileStation",UserNameStation);
		Files.write(path, content.getBytes(charset));
	
}
}

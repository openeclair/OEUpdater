package openeclairupdaterapp.changelog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import openeclairupdaterapp.customTypes.UpdateInfo;
import openeclairupdaterapp.utils.Preferences;
import openeclairupdaterapp.misc.Log;
import openeclairupdaterapp.ui.MainActivity;

import android.app.Activity;
import android.os.Message;

public class Changelog implements Runnable
{
	private static final String TAG = "Changelog";

	private Preferences p;

	public enum ChangelogType
	{
		ROM,
		APP,
		THEME
	}

	public Changelog(Activity upi)
	{
		p = new Preferences(upi);
	}

	//Returns the RomChangelog without a Thread
	public static List<Version> getRomChangelog(UpdateInfo ui)
	{
		Version v = new Version();
		List<Version> returnValue = new LinkedList<Version>();
		v.Version = ui.getVersion();
		for (String str : ui.getDescription().split("\\|"))
		{
			if(str != "")
				v.ChangeLogText.add(str);
		}
		returnValue.add(v);
		return returnValue;
	}

	//Gets the AppChangelog in a Thread
	public void run()
	{
		URL url;
		InputSource i;

		Message m = MainActivity.ChangelogProgressHandler.obtainMessage();
		try
		{
			url = new URL(p.getChangelogURL());
			i = new InputSource(url.openStream());
        	SAXParserFactory spf = SAXParserFactory.newInstance(); 
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader(); 
        	ChangelogHandler ch = new ChangelogHandler(); 
        	xr.setContentHandler(ch); 
        	xr.parse(i);  
        	m.obj = ch.getParsedData();
        }
        catch (MalformedURLException e)
		{
        	m.obj = e.toString();
			Log.e(TAG, "Malformed URL!", e);
		}
		catch (IOException e)
		{
			m.obj = e.toString();
			Log.e(TAG, "Exception on opening Input Stream", e);
		}
		catch (ParserConfigurationException e)
		{
			m.obj = e.toString();
			Log.e(TAG, "Exception on parsing XML File", e);
		}
		catch (SAXException e)
		{
			m.obj = e.toString();
			Log.e(TAG, "Exception while creating SAXParser", e);
		}
		MainActivity.ChangelogProgressHandler.sendMessage(m);
	}
}
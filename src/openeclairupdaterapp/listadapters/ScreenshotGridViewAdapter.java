package openeclairupdaterapp.listadapters;

import java.util.LinkedList;
import java.util.List;

import openeclairupdaterapp.customExceptions.InvalidPictureException;
import openeclairupdaterapp.customTypes.Screenshot;
import openeclairupdaterapp.misc.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ScreenshotGridViewAdapter extends BaseAdapter
{
    private Context mContext;
    private int length;

    private static List<Screenshot> items = new LinkedList<Screenshot>();

    public ScreenshotGridViewAdapter(Context c, int numberOfItems)
    {
    	mContext = c;
    	length = numberOfItems;
    }

    public int getCount()
    {
    	return length;
    }

    public Object getItem(int position)
    {
    	return items.get(position);
    }

    public long getItemId(int position)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	boolean ImageLoaded = items.size() > position;

        ImageView imageView = null;
        if (convertView == null) // if it's not recycled, initialize some attributes
        {
	            imageView = new ImageView(mContext);
	    		imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	    		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    		imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
        	imageView = (ImageView) convertView;
        }

        if (!ImageLoaded)
        {
        	imageView.setImageResource(Constants.SCREENSHOTS_LOADING_IMAGE);
        }
        else
        {
	        try
	        {
	        	Bitmap temp = items.get(position).getBitmap();
	        	if (!temp.isRecycled())
	        		imageView.setImageBitmap(temp);
	        	else
	        		imageView.setImageResource(Constants.SCREENSHOTS_FALLBACK_IMAGE);
	        }
	        catch (InvalidPictureException ex)
	        {
	        	imageView.setImageResource(Constants.SCREENSHOTS_FALLBACK_IMAGE);
	        }
        }
        return imageView;
    }
    
    public void Destroy()
    {
    	for(Screenshot s : items)
		{
			s.DestroyImage();
		}
    }
    
    public void ClearScreenshots()
    {
    	items.clear();
    }
    
    public int GetRealScreenshotSize()
    {
    	return items.size();
    }
    
    public void AddScreenshot(Screenshot s)
    {
    	items.add(s);
    }
}
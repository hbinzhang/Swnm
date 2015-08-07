package taglib;

import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

public class SystemFunction {
	
	public static boolean clContains(Collection<Object> collection,Object object)
	{
		if(collection==null||collection.size()==0)
		{
			return false;
		}
		return collection.contains(object);
	}
	
	public static String systemConfig(String key)
	{
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("system", Locale.ENGLISH);
			return bundle.getString(key);
		} catch (Exception e) {
			return "";
		}
	}
	
}

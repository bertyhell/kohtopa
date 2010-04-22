package gui.actions.filefilters;

import Language.Language;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterImage extends FileFilter{

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String name = f.getName();
		int index = name.lastIndexOf(".") + 1;
		boolean correct = name.substring(index).toLowerCase().equals("jpg");
		if(correct)	return true;
		correct = name.substring(index).toLowerCase().equals("jpeg");
		if(correct)	return true;
		correct = name.substring(index).toLowerCase().equals("gif");
		if(correct)	return true;
		correct = name.substring(index).toLowerCase().equals("png");
		if(correct)	return true;
		return false;
	}

	@Override
	public String getDescription() {
		return Language.getString("allSupportedImageFormats");
	}
}

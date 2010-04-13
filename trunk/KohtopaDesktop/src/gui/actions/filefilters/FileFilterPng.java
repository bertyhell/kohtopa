package gui.actions.filefilters;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterPng extends FileFilter{

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String name = f.getName();
		int index = name.lastIndexOf(".") + 1;
		return name.substring(index).toLowerCase().equals("gif");
	}

	@Override
	public String getDescription() {
		return "Gif {*.gif}";
	}
}

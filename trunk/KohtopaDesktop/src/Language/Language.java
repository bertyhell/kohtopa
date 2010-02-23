package Language;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlRootElement
public class Language {
  public HashMap<String,String> strings;

	public Language(HashMap<String, String> strings) {
		this.strings = strings;
	}

	public Language() {
	}

  
}
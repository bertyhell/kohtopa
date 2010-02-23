package gui;

import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;

public class MyHashMapEntryType {
    @XmlAttribute // @XmlElement and @XmlValue are also fine
    public String key;

    @XmlAttribute // @XmlElement and @XmlValue are also fine
    public String value;

    public MyHashMapEntryType() {}
    public MyHashMapEntryType(Map.Entry<String,String> e) {
       key = e.getKey();
       value = e.getValue();
    }
}






package ru.skillbox.currency.exchange.xmlStructure;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@XmlRootElement(name = "ValCurs")
@ToString
@Setter
public class ValCursXml {
    private String date;
    private String name;
    private List<ValuteXml> valuteXmls;

    @XmlAttribute(name = "Date")
    public String getDate() {
        return date;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "Valute")
    public List<ValuteXml> getValuteXmls() {
        return valuteXmls;
    }
}
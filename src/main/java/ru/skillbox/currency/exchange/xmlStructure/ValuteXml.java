package ru.skillbox.currency.exchange.xmlStructure;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class ValuteXml {
    private String id;
    private String numCode;
    private String charCode;
    private Long nominal;
    private String name;
    private String value;
    private String vunitRate;

    @XmlAttribute(name = "ID")
    public String getId() {
        return id;
    }

    @XmlElement(name = "NumCode")
    public String getNumCode() {
        return numCode;
    }

    @XmlElement(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    @XmlElement(name = "Nominal")
    public Long getNominal() {
        return nominal;
    }

    @XmlElement(name = "Name")

    public String getName() {
        return name;
    }

    @XmlElement(name = "Value")
    public String getValue() {
        return value;
    }

    @XmlElement(name = "VunitRate")
    public String getVunitRate() {
        return vunitRate;
    }
}
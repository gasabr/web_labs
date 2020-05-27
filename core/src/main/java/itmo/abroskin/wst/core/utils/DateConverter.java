package itmo.abroskin.wst.core.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter {
    public static XMLGregorianCalendar dateToGregorian(Date date) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    public static Date gregorianToDate(XMLGregorianCalendar gregorianCalendar) {
        return gregorianCalendar.toGregorianCalendar().getTime();
    }

}

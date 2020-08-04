package com.b2wdigital.fazemu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

public class DateUtils {

    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String ISO_FORMAT_GMT = "yyyy-MM-dd'T'HH:mm:ss-HH:mm";
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String SIMPLE_FORMAT = "dd/MM/yyyy";
    public static final String INTERNATIONAL_FORMAT = "yyyyMMdd";
    public static final String XML_GREGORIAN_CALENDAR_FORMAT = "dd-MM-yyyy";
    public static final String UTC_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * Transform ISO 8601 string to Calendar.
     *
     * @param iso8601string - format "2018-12-16T16:52:00-03:00"
     * @return
     * @throws ParseException
     */
    public static Calendar iso8601ToCalendar(final String iso8601string) throws ParseException {
        Date date = iso8601ToDate(iso8601string);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("BRT"));
        return calendar;

    }

    public static Date iso8601ToDate(final String iso8601string) throws ParseException {
        if (iso8601string == null) {
            return null;
        }
        OffsetDateTime odt = OffsetDateTime.parse(iso8601string);
        Instant instant = odt.toInstant();  // Instant is always in UTC.
        Date date = Date.from(instant);
        return date;
    }

    /**
     * Returns a string formatted iso8601
     *
     * @param data
     * @return
     */
    public static String convertDateToIsoString(Date data) {
        if (data == null) {
            return null;
        }

        String formatted = new SimpleDateFormat(ISO_FORMAT).format(data);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Returns a string formatted iso8601
     *
     * @param calendar
     * @return
     */
    public static String convertCalendarToIsoString(final Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat(ISO_FORMAT).format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Returns new ISO8601 date String format
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String newIso8601Date() {
        return ISO8601Utils.format(new Date(), false, TimeZone.getDefault(), FazemuUtils.LOCALE);
    }

    /**
     * Returns a new date
     *
     * @param dataStr format (dd/MM/yyyy)
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String dataStr) throws ParseException {
        return convertStringToDate(dataStr, SIMPLE_FORMAT);
    }

    /**
     * Returns a new date
     *
     * @param dataStr format (dd/MM/yyyy)
     * @param dateformat
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String dataStr, String dateformat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        return new java.sql.Date(format.parse(dataStr).getTime());
    }

    /**
     * Returns a new XMLGregorianCalendar
     *
     * @param dataStr
     * @param dateformat
     * @return
     * @throws ParseException
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public static XMLGregorianCalendar convertStringToXMLGregorianCalendar(String dataStr, String dateformat) throws ParseException, DatatypeConfigurationException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(dataStr);

        GregorianCalendar utilGregorianCalendar = new GregorianCalendar();
        utilGregorianCalendar.setTimeInMillis(date.getTime());
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(utilGregorianCalendar.get(Calendar.YEAR), utilGregorianCalendar.get(Calendar.MONTH) + 1, utilGregorianCalendar.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
        return xmlGregorianCalendar;
    }

    /**
     * Returns a formatted string
     *
     * @param data
     * @return
     * @throws ParseException
     */
    public static String convertDateToString(Date data) throws ParseException {
        if (data != null) {
            DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(data);
        }
        return "-";
    }

    public static String changeDateFormat(String dateStr, String oldFormat, String newFormat) throws ParseException {
        DateFormat dfOld = new SimpleDateFormat(oldFormat);
        DateFormat dfNew = new SimpleDateFormat(newFormat);

        Date date = dfOld.parse(dateStr);
        return dfNew.format(date);
    }

    /**
     * Difference between dates
     *
     * @param data1
     * @param date2
     * @return Integer (days)
     */
    public static Integer difference(Date data1, Date date2) {
        long diffInMillies = Math.abs(data1.getTime()) - date2.getTime();

        Long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return days.intValue();
    }

    /**
     * Returns new DATE_FORMAT (dd/MM/yyyy HH:mm:ss) string format
     *
     * @return
     */
    public static String newDateFormat() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(new Date());
    }

    /**
     * Returns new SIMPLE_FORMAT (dd/MM/yyyy) string format
     *
     * @return
     */
    public static String newSimpleDateFormat() {
        DateFormat formatter = new SimpleDateFormat(SIMPLE_FORMAT);
        return formatter.format(new Date());
    }

    public static boolean isStartDateBeforeEndDate(Date dataInicio, Date dataFim) throws ParseException {
        return dataInicio.before(dataFim);
    }

}

package com.dali.didor.model;

import java.util.Calendar;
import java.util.Locale;

import com.dali.didor.R;

import android.content.Context;

public class Datetime {

	private final Locale locale = Locale.CHINA;

	private int year;
	private int month;
	private int dayOfMonth;
	private int dayOfWeek;
	private int hour;
	private int minute;

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setDate(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance(locale);
		calendar.set(year, month, day, hour, minute, 0);
		parseCalendar(calendar);
	} 

	public Datetime() {
		parseCalendar(Calendar.getInstance(locale));
	}
	
	public Datetime(long milliseconds) {
		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTimeInMillis(milliseconds);
		parseCalendar(calendar);
	}

	public Datetime(int year, int month, int day, int hour, int minute) {
		setDate(year, month, day, hour, minute);
	}
	
	public long getTimeInMillis() {
		Calendar calendar = Calendar.getInstance(locale);
		calendar.set(this.year, this.month, this.dayOfMonth, this.hour, this.minute, 0);
		return calendar.getTimeInMillis();
	}

	private void parseCalendar(Calendar calendar) {
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		this.minute = calendar.get(Calendar.MINUTE);
	}

	public String display(Context context) {
		return String.format(
				context.getResources().getString(R.string.date_template),
				this.year, this.month + 1, this.dayOfMonth,
				getDayOfWeekDisplay(context), this.hour, this.minute);
	}

	public String getDayOfWeekDisplay(Context context) {
		return context.getResources().getStringArray(R.array.day_of_week)[this.dayOfWeek - 1];
	}

	@Override
	public String toString() {
		return String.format(locale, "%d,%d,%d,%d,%d", this.year, this.month,
				this.dayOfMonth, this.hour, this.minute);
	}

	public static Datetime parse(String str) {
		if (str == null) {
			return null;
		}

		String[] data = str.split(",");
		if (data.length != 5) {
			return null;
		}

		return new Datetime(Integer.valueOf(data[0]), Integer.valueOf(data[1]),
				Integer.valueOf(data[2]), Integer.valueOf(data[3]),
				Integer.valueOf(data[4]));
	}
}

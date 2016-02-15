package com.dali.didor.model;

import android.util.SparseArray;

public enum AlarmRepeatType {
	OneTime(0), Day(1), Weekend(2), MondayToFriday(5), Week(7), Workday(20), Month(30), DayOff(114), Year(365),;

	private int value;
	private static SparseArray<AlarmRepeatType> intAlarmRepeatTypeMap = createIntegerAlarmRepeatTypeMapping();

	private AlarmRepeatType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static SparseArray<AlarmRepeatType> createIntegerAlarmRepeatTypeMapping() {
		SparseArray<AlarmRepeatType> result = new SparseArray<AlarmRepeatType>();
		for (AlarmRepeatType code : AlarmRepeatType.values()) {
			result.put(code.getValue(), code);
		}
		return result;
	}

	public static AlarmRepeatType valueOf(int value) {
		return valueOf(value, AlarmRepeatType.OneTime);
	}

	public static AlarmRepeatType valueOf(int value, AlarmRepeatType defaultIfMissing) {
		AlarmRepeatType code = intAlarmRepeatTypeMap.get(value);
		if (code == null)
			return defaultIfMissing;
		else
			return code;
	}

}

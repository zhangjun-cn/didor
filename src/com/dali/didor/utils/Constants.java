package com.dali.didor.utils;


public class Constants {

	public static final String DB_NAME = "dali.didor.sql";
	public static final long USER_NOT_EXIST = -1;

	public class AlarmTable {
		public static final String TABLE_NAME = "alarm";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_USER_ID = "user_id";
		public static final String COLUMN_TYPE = "type";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_CONTENT = "content";
		public static final String COLUMN_ATTACHMENT = "attachment";
		public static final String COLUMN_START_TIME = "start_time";
		public static final String COLUMN_END_TIME = "end_time";
		public static final String COLUMN_CREATE_TIME = "create_time";
		public static final String COLUMN_REPEAT_TYPE = "repeat_type";
		public static final String COLUMN_CREATOR_ID = "creator_id";
	}

	public class UserTable {
		public static final String TABLE_NAME = "user";
		public static final String COLUMN_ID = "user_id";
		public static final String COLUMN_NAME = "user_name";
		public static final String COLUMN_GENDER = "gender";
		public static final String COLUMN_BIRTHDAY = "birthday";
		public static final String COLUMN_AREA = "area";
		public static final String COLUMN_LOCALE = "locale";
	}

}

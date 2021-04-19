package com.example.diary_bae.database;

import android.provider.BaseColumns;

public final class DetailsContract {
        private DetailsContract(){}
        public static class DetailsEntry implements BaseColumns {
            public static final String DB_NAME = "JournalDB";
            public static final String TABLE_NAME = "JournalEntries";
            public static final String COLUMN_NAME_TITLE = "EntryTitle";
            public static final String COLUMN_NAME_DATE = "Date";
            public static final String COLUMN_NAME_TIME = "Time";
        }
    }


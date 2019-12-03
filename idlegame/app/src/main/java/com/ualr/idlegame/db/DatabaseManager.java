package com.ualr.idlegame.db;

import android.content.Context;
import android.provider.ContactsContract;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class DatabaseManager {
    private static DatabaseManager m_instance = null;
    private DB m_snappydb = null;

    private DatabaseManager () {}
    public static DatabaseManager getInstance () {
        if (m_instance == null) {
            m_instance = new DatabaseManager();
        }

        return m_instance;
    }

    public void open (Context context) throws SnappydbException {
        if (m_snappydb == null) {
            m_snappydb = DBFactory.open(context);
        }
    }

    public void close () throws SnappydbException {
        m_snappydb.close();
    }
}

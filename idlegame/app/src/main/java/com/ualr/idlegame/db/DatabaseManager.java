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

    public void open (Context context) {
        try {
            if (m_snappydb == null) {
                m_snappydb = DBFactory.open(context);
            }
        } catch (SnappydbException e) {
            System.out.print("Database Exception: \n\t" + e);
            System.out.println();
            System.exit(1);
        }
    }

    public void close () throws SnappydbException {
        m_snappydb.close();
    }

    public void putInt (String key, Integer value ) {
        try {
            m_snappydb.putInt(key, value);
        } catch (SnappydbException e) {
            System.out.print("Database Exception: \n\t" + e);
            System.out.println();
            System.exit(1);
        }
    }

    public Integer getInt (String key) {
        Integer value = null;

        try {
            value = m_snappydb.getInt(key);
        } catch (SnappydbException e) {
            System.out.print("Database Exception: \n\t" + e);
            System.out.println();
            System.exit(1);
        }

        return value;
    }

    public String[] getKeysByPrefix (String prefix) {
        String[] keys = null;

        try {
            keys = m_snappydb.findKeys(prefix);
        } catch (SnappydbException e) {
            System.out.print("Database Exception: \n\t" + e);
            System.out.println();
            System.exit(1);
        }

        return keys;
    }
}

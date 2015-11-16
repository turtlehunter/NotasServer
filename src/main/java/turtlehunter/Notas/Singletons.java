package turtlehunter.Notas;

import org.mapdb.BTreeMap;
import org.mapdb.DB;

import java.util.NavigableSet;

public class Singletons {
    public static Main main;

    public static DB db;
    public static BTreeMap<Object, Object> database;
    public static BTreeMap<Object, Object> userDB;
}

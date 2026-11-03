package net.me.daemon.classes;

import net.me.daemon.Main;
import net.me.daemon.classes.records.ClassRecord;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassRegistry {
    private static List<ClassRecord> CLASSES = new ArrayList<>();

    public static void register(ClassRecord classRecord) {
        CLASSES.add(classRecord);
    }

    public static List<ClassRecord> all() {
        return CLASSES;
    };
}

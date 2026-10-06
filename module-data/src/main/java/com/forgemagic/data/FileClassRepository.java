package com.forgemagic.data;

import com.forgemagic.api.ClassId;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public final class FileClassRepository {
    private final Path file;
    private final Map<UUID, ClassId> classes = new java.util.HashMap<>();
    public FileClassRepository(Path file) throws IOException { this.file = file; Files.createDirectories(file.toAbsolutePath().getParent()); load(); }
    public synchronized ClassId get(UUID player) { return classes.getOrDefault(player, ClassId.NOVICE); }
    public synchronized boolean choose(UUID player, ClassId classId) throws IOException {
        if (classId == null || classId == ClassId.NOVICE || get(player) != ClassId.NOVICE) return false;
        classes.put(player, classId); persist(); return true;
    }
    private void load() throws IOException {
        if (!Files.exists(file)) return; Properties p = new Properties(); try (var r=Files.newBufferedReader(file)) { p.load(r); }
        for (String key : p.stringPropertyNames()) classes.put(UUID.fromString(key), ClassId.valueOf(p.getProperty(key)));
    }
    private void persist() throws IOException {
        Properties p = new Properties(); classes.forEach((id, cls) -> p.setProperty(id.toString(), cls.name()));
        Path tmp=file.resolveSibling(file.getFileName()+".tmp"); try(var w=Files.newBufferedWriter(tmp)){p.store(w,"Forge Solo Subseason class choices");}
        Files.move(tmp,file,java.nio.file.StandardCopyOption.REPLACE_EXISTING,java.nio.file.StandardCopyOption.ATOMIC_MOVE);
    }
}

package com.forgemagic.data;

import static org.junit.jupiter.api.Assertions.*;
import com.forgemagic.api.ClassId;
import java.nio.file.Files;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FileClassRepositoryTest {
 @Test void classChoiceSurvivesRestartAndCannotChange() throws Exception {
  var file=Files.createTempFile("classes","properties"); var id=UUID.randomUUID();
  var first=new FileClassRepository(file); assertTrue(first.choose(id,ClassId.MINER));
  var second=new FileClassRepository(file); assertEquals(ClassId.MINER,second.get(id)); assertFalse(second.choose(id,ClassId.HEALER));
 }
}

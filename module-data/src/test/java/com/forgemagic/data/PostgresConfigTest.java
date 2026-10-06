package com.forgemagic.data;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PostgresConfigTest {
 @Test void rejectsMissingCredentials(){assertThrows(IllegalStateException.class,()->PostgresConfig.fromEnvironment(Map.of()).validate());}
 @Test void requiresTls(){var c=new PostgresConfig("jdbc:postgresql://z.example/db","user","secret",true);assertThrows(IllegalStateException.class,c::validate);}
 @Test void acceptsTlsUrl(){var c=new PostgresConfig("jdbc:postgresql://z.example/db?sslmode=require","user","secret",true);assertDoesNotThrow(c::validate);}
}

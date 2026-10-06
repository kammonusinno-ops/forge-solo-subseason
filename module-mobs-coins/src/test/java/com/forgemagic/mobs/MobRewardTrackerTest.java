package com.forgemagic.mobs;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
class MobRewardTrackerTest { @Test void playerAndServerCapsLimitIssuance(){var t=new MobRewardTracker();var a=UUID.randomUUID();var b=UUID.randomUUID();assertEquals(2_000_000,t.creditDaily(a,3_000_000,2_000_000,10_000_000,20));assertEquals(0,t.creditDaily(a,1,2_000_000,10_000_000,20));assertEquals(2_000_000,t.creditDaily(b,3_000_000,2_000_000,10_000_000,20));} @Test void serverCapAndShareCapStopOnePlayer(){var t=new MobRewardTracker();var a=UUID.randomUUID();assertEquals(200_000,t.creditDaily(a,1_000_000,2_000_000,1_000_000,20));assertEquals(0,t.creditDaily(a,1,2_000_000,1_000_000,20));} }

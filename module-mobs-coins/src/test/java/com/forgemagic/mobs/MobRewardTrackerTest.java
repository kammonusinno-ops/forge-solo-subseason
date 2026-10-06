package com.forgemagic.mobs;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
class MobRewardTrackerTest { @Test void dailyCapIsTwentyThousandTmt(){var t=new MobRewardTracker();var id=UUID.randomUUID();assertEquals(2_000_000,t.creditDaily(id,3_000_000));assertEquals(0,t.creditDaily(id,1));} }

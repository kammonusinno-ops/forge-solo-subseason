package com.forgemagic.boombai;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class LoanMathTest { @Test void interestIsCappedAtFourTimesPrincipal(){assertEquals(4000,LoanMath.owed(1000,100));} @Test void creditScoreIsClamped(){assertEquals(850,LoanMath.creditScore(100,0,0));assertEquals(300,LoanMath.creditScore(0,100,0));} }

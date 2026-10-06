package com.forgemagic.boombai;

public final class LoanMath {
 private LoanMath(){}
 public static long owed(long principal,long periods){if(principal<0||periods<0)throw new IllegalArgumentException();double value=principal*Math.pow(1.10,periods);return Math.min(principal*4,Math.round(value));}
 public static long interestFirstPayment(long owed,long principal,long payment){long interest=Math.max(0,owed-principal);long toInterest=Math.min(payment,interest);return payment-toInterest;}
 public static int creditScore(int onTime,int defaults,int lateDays){return Math.max(300,Math.min(850,600+onTime*10-defaults*80-lateDays*2));}
}

package com.forgemagic.api;

public record LedgerInvariantReport(boolean balanced, long totalDebits, long totalCredits, String message) { }

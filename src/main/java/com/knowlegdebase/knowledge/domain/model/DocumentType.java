package com.knowlegdebase.knowledge.domain.model;

/**
 * Classifies the category or domain of the {@link Document}'s content.
 * Used for filtering and organizing the knowledge base.
 * <ul>
 *     <li><b>CODING</b>: Content related to programming, software development, algorithms, etc.</li>
 *     <li><b>TRADING</b>: Content related to financial markets, trading strategies, etc.</li>
 *     <li><b>NOTE</b>: General personal or professional notes.</li>
 *     <li><b>ENGLISH</b>: English learning content.</li>
 *     <li><b>INSIGHT</b>: High-level analysis, summaries, or specific learnings.</li>
 * </ul>
 */
public enum DocumentType {
    CODING, TRADING, NOTE, ENGLISH, INSIGHT
}

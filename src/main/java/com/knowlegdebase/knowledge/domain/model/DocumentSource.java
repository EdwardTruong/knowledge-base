package com.knowlegdebase.knowledge.domain.model;

/**
 * Defines the origin or method of creation for a {@link Document}.
 * <ul>
 *     <li><b>MANUAL</b>: Created manually by the user via the system interface.</li>
 *     <li><b>IMPORT</b>: Imported from an external file or system.</li>
 *     <li><b>VOICE</b>: Generated through voice transcription.</li>
 * </ul>
 */
public enum DocumentSource {
    MANUAL, IMPORT, VOICE
}

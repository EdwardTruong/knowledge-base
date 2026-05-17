package com.knowlegdebase.knowledge.domain.model;

/**
 * Represents the lifecycle state of a {@link Document} in the processing pipeline.
 * <ul>
 *     <li><b>DRAFT</b>: Initial state when the document is created but processing hasn't started.</li>
 *     <li><b>PROCESSING</b>: The document is currently being processed (e.g., being chunked).</li>
 *     <li><b>CHUNKED</b>: The document has been successfully split into smaller {@link DocumentChunk}s.</li>
 *     <li><b>EMBEDDED</b>: All chunks of the document have been successfully embedded and stored in the vector database.</li>
 *     <li><b>FAILED</b>: An error occurred during the processing pipeline.</li>
 * </ul>
 */
public enum DocumentStatus {
    DRAFT, PROCESSING, CHUNKED, EMBEDDED, FAILED
}

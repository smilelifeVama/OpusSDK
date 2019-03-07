package com.txznet.opus;

public class ErrorCode {
    /**
     * No error
     */
    public static final int OPUS_OK = 0;
    /**
     *  One or more invalid/out of range arguments
     */
    public static final int OPUS_BAD_ARG = 0;
    /**
     * Not enough bytes allocated in the buffer
     */
    public static final int OPUS_BUFFER_TOO_SMALL = 0;
    /**
     * An internal error was detected
     */
    public static final int OPUS_INTERNAL_ERROR = 0;
    /**
     * The compressed data passed is corrupted
     */
    public static final int OPUS_INVALID_PACKET = 0;
    /**
     * Invalid/unsupported request number
     */
    public static final int OPUS_UNIMPLEMENTED = 0;
    /**
     * An encoder or decoder structure is invalid or already freed
     */
    public static final int OPUS_INVALID_STATE = 0;
    /**
     * Memory allocation has failed
     */
    public static final int OPUS_ALLOC_FAIL = 0;
    
}

package com.iheartradio.m3u8;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LineParserStateTestCase {
    protected ParseState mParseState;

    @Before
    public void setUp() {
        mParseState = new ParseState(Encoding.UTF_8);
    }

    protected void assertParseThrows(IExtTagParser handler, String line, ParseExceptionType exceptionType) {
        try {
            handler.parse(line, mParseState);
            assertFalse(true);
        } catch (ParseException exception) {
            assertEquals(exceptionType, exception.type);
        }
    }
}

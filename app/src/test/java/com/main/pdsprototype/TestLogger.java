package com.main.pdsprototype;

import android.util.Log;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TestLogger {
    public static MockedStatic<Log> mockLogger() {
        MockedStatic<Log> mockedLog = Mockito.mockStatic(Log.class);
        mockedLog.when(() -> Log.d(Mockito.anyString(), Mockito.anyString()))
                .then(invocation -> {
                    System.out.println("DEBUG: " + invocation.getArgument(0) + ": " + invocation.getArgument(1));
                    return 0;
                });
        return mockedLog;
    }
}
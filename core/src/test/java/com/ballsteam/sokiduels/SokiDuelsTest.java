package com.ballsteam.sokiduels;

import com.ballsteam.sokiduels.exceptions.ImportantAssetNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SokiDuelsTest {

    @Test
    void exceptionThrowCreate(){
        RuntimeException exception = assertThrows(ImportantAssetNotFoundException.class, ()->{
            new SokiDuels().checkForSoki();
        });
        String expected = "There's a vital file missing...";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}

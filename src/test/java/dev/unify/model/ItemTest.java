package dev.unify.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    @Test
    void fromLabelShouldThrowExceptionWhenLabelNotFound() {
        //Given
        var label = "Cookies";
        //Then
        assertThrows(IllegalArgumentException.class, () -> Item.fromLabel(label));
    }

    @Test
    void fromLabelShouldThrowExceptionWhenLabelIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Item.fromLabel(null));
    }

    @Test
    void fromLabelShouldReturnAPPLESItemWhenLabelIsApples() {
        //Given
        var label = "Apples";
        //When
        var actualItem = Item.fromLabel(label);
        //Then
        assertEquals(Item.APPLES, actualItem);
    }
}

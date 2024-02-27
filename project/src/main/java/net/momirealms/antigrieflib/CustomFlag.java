package net.momirealms.antigrieflib;

public interface CustomFlag {

    default void registerOnLoad(Flag customPlaceFlag, Flag customBreakFlag, Flag customInteractFlag) {

    }

    default void registerOnEnable(Flag customPlaceFlag, Flag customBreakFlag, Flag customInteractFlag) {

    }
}

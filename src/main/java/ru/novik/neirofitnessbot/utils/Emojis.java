package ru.novik.neirofitnessbot.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    SBER_EMOJI("\uD83D\uDFE2"),
    TBANK_EMOJI("\uD83D\uDFE1"),
    YKASSA_EMOJI("\uD83D\uDFE6");

    private String emoji;

    @Override
    public String toString() {
        return emoji;
    }
}

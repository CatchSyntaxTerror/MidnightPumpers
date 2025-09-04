package util;

/**
 * Author: Youssef Amin, Danny Phantom, Joel ...
 * This enum describes the type of message being sent
 * Used in Message record.
 */
public enum MessageType {
    // Main -> Pump
    PUMP_ON,
    PUMP_OFF,

    // Main -> Flowmeter
    RESET,

    // Main -> CardCompany
    CARD_INFO,

    // Main -> GasStation
    TRANSACTION_INFO,
    ERROR,

    // Hose -> Main
    CON_TO_CAR,
    CON_TO_PUMP,
    NOT_CON,
    TANK_FULL,

    // CCReader -> Main
    SEND_CARD,

    // GasStation -> Main
    GAS_PRICES,
    AVAILABLE_GAS,

    // Flowmeter -> Main
    GAS_VOLUME;
}

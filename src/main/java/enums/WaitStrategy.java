package enums;

/**
 * Enum to define various wait strategies that can be applied
 * before interacting with a web element. This promotes better
 * test stability and avoids flakiness due to timing issues.
 */
public enum WaitStrategy {

    CLICKABLE,  // Waits until the element is both visible and enabled (i.e., ready to be clicked)

    PRESENCE,   // Waits for the presence of the element in the DOM (not necessarily visible)

    VISIBLE,    // Waits until the element is visible on the UI

    NONE        // No wait strategy is applied; the element is fetched directly
}

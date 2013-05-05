package org.rubenrr.walkeitor.element.consumable;

import org.rubenrr.walkeitor.config.element.ConsumableConfig;

/**
 * Fuel is the product generated by the refinery after process the Crude Oil
 *
 * User: Ruben Rubio Rey
 * Date: 30/04/13
 * Time: 8:12 PM
 */
public class Fuel extends Consumable {

    public Fuel() {
        this(0f);
    }
    public Fuel(float value) {
        super(ConsumableConfig.FUEL, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fuel)) return false;

        Fuel fuel = (Fuel) o;

        if (consumableConfig != fuel.consumableConfig) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return consumableConfig != null ? consumableConfig.hashCode() : 0;
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}

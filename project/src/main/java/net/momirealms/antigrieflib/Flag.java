package net.momirealms.antigrieflib;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Flag {

    private String name;
    private boolean defaultValue;
    private String displayName;
    private List<String> description;
    private ItemStack displayItem;

    private Flag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private final Flag flag;

        public Builder(String name) {
            this.flag = new Flag(name);
        }

        public Builder name(String name) {
            flag.name = name;
            return this;
        }

        public Builder defaultValue(boolean value) {
            flag.defaultValue = value;
            return this;
        }

        public Builder displayItem(ItemStack itemStack) {
            flag.displayItem = itemStack;
            return this;
        }

        public Builder description(List<String> description) {
            flag.description = description;
            return this;
        }

        public Builder displayName(String displayName) {
            flag.displayName = displayName;
            return this;
        }

        public Flag build() {
            return flag;
        }
    }
}

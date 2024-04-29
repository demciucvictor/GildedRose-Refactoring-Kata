package com.gildedrose;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ItemSpecialRules {

    Map<String, Consumer<Item>> rules;

    private static final int QUALITY_DEGRADATION = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("quality_degradation"));

    private static final int QUALITY_DEGRADATION_EXPIRED = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("quality_degradation_expired"));

    private static final int MAXIMUM_QUALITY = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("maximum_quality_allowed"));

    private static final int MINIMUM_QUALITY = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("minimum_quality_allowed"));

    private static final int LEGENDARY_QUALITY = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("legendary_quality"));

    public ItemSpecialRules() {
        rules = new HashMap<>();
        loadSpecialRules();
    }

    public boolean degradeIfPresent(Item item) {
        Optional<String> optKey = rules.keySet().stream().filter(key -> item.name.contains(key)).findFirst();
        if (optKey.isPresent()) {
            rules.get(optKey.get()).accept(item);
            return true;
        } else {
            return false;
        }
    }


    private void loadSpecialRules() {
        rules.put("Aged Brie", item ->
        {
            if(item.sellIn <= 0) {
                item.quality = item.quality + QUALITY_DEGRADATION_EXPIRED;
            } else {
                item.quality = item.quality + QUALITY_DEGRADATION;
            }
            checkMaxQuality(item);
            item.sellIn--;
        });

        rules.put("Sulfuras", item -> {
            item.quality = LEGENDARY_QUALITY;
        });

        rules.put("Backstage passes", item -> {
            if(item.sellIn > 10) {
                item.quality = item.quality + QUALITY_DEGRADATION;
            } else if (item.sellIn <= 10 && item.sellIn > 5) {
                item.quality = item.quality + 2;
            } else if (item.sellIn <= 5 && item.sellIn > 0) {
                item.quality = item.quality + 3;
            } else {
                item.quality = 0;
            }
            checkMaxQuality(item);
            item.sellIn--;
        });

        rules.put("Conjured", item -> {
            if(item.sellIn <= 0) {
                item.quality = item.quality - 2 * QUALITY_DEGRADATION_EXPIRED;
            } else {
                item.quality = item.quality - 2 * QUALITY_DEGRADATION;
            }
            checkMinQuality(item);
            item.sellIn--;
        });
    }

    private void checkMaxQuality(Item item) {
        if(item.quality > MAXIMUM_QUALITY) {
            item.quality = MAXIMUM_QUALITY;
        }
    }
    private void checkMinQuality(Item item) {
        if(item.quality < MINIMUM_QUALITY) {
            item.quality = MINIMUM_QUALITY;
        }
    }
}

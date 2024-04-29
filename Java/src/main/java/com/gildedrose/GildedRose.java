package com.gildedrose;


class GildedRose {

    private static final int QUALITY_DEGRADATION = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("quality_degradation"));

    private static final int QUALITY_DEGRADATION_EXPIRED = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("quality_degradation_expired"));

    private static final int MAXIMUM_QUALITY = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("maximum_quality_allowed"));

    private static final int MINIMUM_QUALITY = Integer.parseInt(ItemQualityConfig.getInstance().getProperty("minimum_quality_allowed"));

    Item[] items;

    private ItemSpecialRules itemSpecialRules;

    public GildedRose(Item[] items) {
        this.items = items;
        itemSpecialRules = new ItemSpecialRules();
    }

    public void updateQuality() {
        boolean isSpecialRules = false;

        for(Item item : items) {

            isSpecialRules = itemSpecialRules.degradeIfPresent(item);

            if(!isSpecialRules){
                degradeItem(item);
            }
        }
    }

    private void degradeItem(Item item) {
        if(item.sellIn <= 0) {
            item.quality = item.quality - QUALITY_DEGRADATION_EXPIRED;
        } else {
            item.quality = item.quality - QUALITY_DEGRADATION;
        }
        checkMinQuality(item);
        checkMaxQuality(item);
        item.sellIn--;
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

    //    public void updateQuality() {
//        for (int i = 0; i < items.length; i++) {
//            if (!items[i].name.equals("Aged Brie")
//                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                if (items[i].quality > 0) {
//                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                        items[i].quality = items[i].quality - 1;
//                    }
//                }
//            } else {
//                if (items[i].quality < 50) {
//                    items[i].quality = items[i].quality + 1;
//
//                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (items[i].sellIn < 11) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1;
//                            }
//                        }
//
//                        if (items[i].sellIn < 6) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                items[i].sellIn = items[i].sellIn - 1;
//            }
//
//            if (items[i].sellIn < 0) {
//                if (!items[i].name.equals("Aged Brie")) {
//                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (items[i].quality > 0) {
//                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                                items[i].quality = items[i].quality - 1;
//                            }
//                        }
//                    } else {
//                        items[i].quality = items[i].quality - items[i].quality;
//                    }
//                } else {
//                    if (items[i].quality < 50) {
//                        items[i].quality = items[i].quality + 1;
//                    }
//                }
//            }
//        }
//    }
}

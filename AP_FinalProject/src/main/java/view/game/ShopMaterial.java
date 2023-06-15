package view.game;

public enum ShopMaterial {
    ALE(ShopMenu.class.getResource("/images/shop/ale.png").toString(), 1, "ale"),
    APPLE(ShopMenu.class.getResource("/images/shop/apple.png").toString(), 2, "apple"),
    BREAD(ShopMenu.class.getResource("/images/shop/bread.png").toString(), 3, "bread"),
    CHEESE(ShopMenu.class.getResource("/images/shop/cheese.png").toString(), 4, "cheese"),
    FLOUR(ShopMenu.class.getResource("/images/shop/flour.jpg").toString(), 5, "flour"),
    HOP(ShopMenu.class.getResource("/images/shop/hop.png").toString(), 6, "hop"),
    MEAT(ShopMenu.class.getResource("/images/shop/meat.png").toString(), 7, "meat"),
    WHEAT(ShopMenu.class.getResource("/images/shop/wheat.png").toString(), 8, "wheat"),
    GHIR(ShopMenu.class.getResource("/images/shop/ghir.png").toString(), 9, "ghir"),
    IRON(ShopMenu.class.getResource("/images/shop/iron.png").toString(), 10, "iron"),
    STONE(ShopMenu.class.getResource("/images/shop/stone.png").toString(), 11, "stone"),
    WOOD(ShopMenu.class.getResource("/images/shop/wood.png").toString(), 12, "wood"),
    GOLD(ShopMenu.class.getResource("/images/shop/gold.png").toString(), 13, "gold"),
    BOW(ShopMenu.class.getResource("/images/shop/bow.png").toString(), 14, "bow"),
    MACE(ShopMenu.class.getResource("/images/shop/mace.png").toString(), 15, "mace"),
    PIKE(ShopMenu.class.getResource("/images/shop/pike.png").toString(), 16, "pike"),
    SPEAR(ShopMenu.class.getResource("/images/shop/spear.png").toString(), 17, "spear"),
    SWORD(ShopMenu.class.getResource("/images/shop/sword.png").toString(), 18, "sword"),
    METAL_ARMOUR(ShopMenu.class.getResource("/images/shop/metal-armour.png").toString(), 19, "metal armour"),
    LEATHER_ARMOUR(ShopMenu.class.getResource("/images/shop/leather-armour.png").toString(), 20, "leather armour"),
    ;




    private int number;
    private String name;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    ShopMaterial(String link, int number, String name){
        this.link = link;
        this.number = number;
        this.name = name;
    }
}

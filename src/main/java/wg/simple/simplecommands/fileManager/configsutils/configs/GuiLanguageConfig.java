package wg.simple.simplecommands.fileManager.configsutils.configs;


import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.ConfigAccessor;
import wg.simple.simplecommands.fileManager.configsutils.resourcesConfigGenerator.LiteralType;

public class GuiLanguageConfig extends ConfigAccessor {

    private String warpsGuiPageName;
    private String warpsGuiItemName;
    private String singleWarpGuiWarpNameItem;
    private String singleWarpGuiLocationItemName;
    private String singleWarpGuiLocationItemX;
    private String singleWarpGuiLocationItemY;
    private String singleWarpGuiLocationItemZ;
    private String singleWarpGuiLocationItemClickToTeleport;
    private String singleWarpGuiRemoveWarpItem;
    private String singleHomeGuiHomeNameItemName;
    private String singleHomeGuiHomeNameItemOwner;
    private String singleHomeGuiLocationItemName;
    private String singleHomeGuiLocationItemX;
    private String singleHomeGuiLocationItemY;
    private String singleHomeGuiLocationItemZ;
    private String singleHomeGuiLocationItemClickToTeleport;
    private String singleHomeGuiRemoveHomeItem;
    private String homeGuiGuiPageName;
    private String homeGuiName;
    private String adminGuiBack;
    private String adminGuiClose;

    public void init() {
        super.init("GuiLanguageConfig");
        this.warpsGuiItemName = this.getStringPath("Guis.adminGui.warpsGui.itemName");
        this.warpsGuiPageName = this.getStringPath("Guis.adminGui.warpsGui.pageName");
        this.singleWarpGuiWarpNameItem = this.getStringPath("Guis.adminGui.singleWarpGui.warpNameItem");
        this.singleWarpGuiLocationItemName = this.getStringPath("Guis.adminGui.singleWarpGui.locationItem.name");
        this.singleWarpGuiLocationItemX = this.getStringPath("Guis.adminGui.singleWarpGui.locationItem.x");
        this.singleWarpGuiLocationItemY = this.getStringPath("Guis.adminGui.singleWarpGui.locationItem.y");
        this.singleWarpGuiLocationItemZ = this.getStringPath("Guis.adminGui.singleWarpGui.locationItem.z");
        this.singleWarpGuiLocationItemClickToTeleport = this.getStringPath("Guis.adminGui.singleWarpGui.locationItem.clickToTeleport");
        this.singleWarpGuiRemoveWarpItem = this.getStringPath("Guis.adminGui.singleWarpGui.removeWarpItem");
        this.singleHomeGuiHomeNameItemName = this.getStringPath("Guis.adminGui.singleHomesGui.homeNameItem.name");
        this.singleHomeGuiHomeNameItemOwner = this.getStringPath("Guis.adminGui.singleHomesGui.homeNameItem.owner");
        this.singleHomeGuiLocationItemName = this.getStringPath("Guis.adminGui.singleHomesGui.locationItem.name");
        this.singleHomeGuiLocationItemX = this.getStringPath("Guis.adminGui.singleHomesGui.locationItem.x");
        this.singleHomeGuiLocationItemY = this.getStringPath("Guis.adminGui.singleHomesGui.locationItem.y");
        this.singleHomeGuiLocationItemZ = this.getStringPath("Guis.adminGui.singleHomesGui.locationItem.z");
        this.singleHomeGuiLocationItemClickToTeleport = this.getStringPath("Guis.adminGui.singleHomesGui.locationItem.clickToTeleport");
        this.singleHomeGuiRemoveHomeItem = this.getStringPath("Guis.adminGui.singleHomesGui.removeHomeItem");
        this.homeGuiGuiPageName = this.getStringPath("Guis.adminGui.homesGui.guiPageName");
        this.homeGuiName = this.getStringPath("Guis.adminGui.homesGui.name");
        this.adminGuiBack = this.getStringPath("Guis.adminGui.back");
        this.adminGuiClose = this.getStringPath("Guis.adminGui.close");
    }

    public String getWarpsGuiItemName(String word) {
        return warpsGuiItemName.replaceAll(LiteralType.WARP_NAME, word);
    }

    public String getWarpsGuiPageName() {
        return warpsGuiPageName;
    }

    public String getSingleWarpGuiWarpNameItem(String word) {
        return singleWarpGuiWarpNameItem.replaceAll(LiteralType.WARP_NAME, word);
    }

    public String getSingleWarpGuiLocationItemName() {
        return singleWarpGuiLocationItemName;
    }

    public String getSingleWarpGuiLocationItemX(String word) {
        return singleWarpGuiLocationItemX.replaceAll(LiteralType.X, word);
    }

    public String getSingleWarpGuiLocationItemY(String word) {
        return singleWarpGuiLocationItemY.replaceAll(LiteralType.Y, word);
    }

    public String getSingleWarpGuiLocationItemZ(String word) {
        return singleWarpGuiLocationItemZ.replaceAll(LiteralType.Z, word);
    }

    public String getSingleWarpGuiLocationItemClickToTeleport() {
        return singleWarpGuiLocationItemClickToTeleport;
    }

    public String getSingleWarpGuiRemoveWarpItem() {
        return singleWarpGuiRemoveWarpItem;
    }

    public String getSingleHomeGuiHomeNameItemName(String word) {
        return singleHomeGuiHomeNameItemName.replaceAll(LiteralType.HOME_NAME, word);
    }

    public String getSingleHomeGuiHomeNameItemOwner(String word) {
        return singleHomeGuiHomeNameItemOwner.replaceAll(LiteralType.OWNER, word);
    }

    public String getSingleHomeGuiLocationItemName() {
        return singleHomeGuiLocationItemName;
    }

    public String getSingleHomeGuiLocationItemX(String word) {
        return singleHomeGuiLocationItemX.replaceAll(LiteralType.X, word);
    }

    public String getSingleHomeGuiLocationItemY(String word) {
        return singleHomeGuiLocationItemY.replaceAll(LiteralType.Y, word);
    }

    public String getSingleHomeGuiLocationItemZ(String word) {
        return singleHomeGuiLocationItemZ.replaceAll(LiteralType.Z, word);
    }

    public String getSingleHomeGuiLocationItemClickToTeleport() {
        return singleHomeGuiLocationItemClickToTeleport;
    }

    public String getSingleHomeGuiRemoveHomeItem() {
        return singleHomeGuiRemoveHomeItem;
    }

    public String getHomeGuiGuiPageName(String word) {
        return homeGuiGuiPageName.replaceAll(LiteralType.PLAYER_NAME, word);
    }

    public String getHomeGuiName(String word) {
        return homeGuiName.replaceAll(LiteralType.HOME_NAME, word);
    }


    public String getAdminGuiBack() {
        return adminGuiBack;
    }

    public String getAdminGuiClose() {
        return adminGuiClose;
    }
}

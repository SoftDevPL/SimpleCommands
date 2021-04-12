package wg.simple.simplecommands.managers.guiengine.basics;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wg.simple.simplecommands.SimpleCommands;
import wg.simple.simplecommands.managers.guiengine.interfaces.Action;

import java.util.*;

public class BasicGui {
    private static final String colorsChars = "0123456789aAbBcCdDeEfF";
    private static final String formattingChars = "kKlLmMnNoO";
    private static final String resetChars = "rR";
    protected Inventory gui;
    protected Map<Integer, Action> actions = new HashMap<>();
    @Getter
    protected Map<Integer, Action> rightClickActions = new HashMap<>();
    protected BasicGui previousGui;
    protected Player viewer;
    private boolean isOpen = false;
    private long lastClick = 0;

    public BasicGui(int rowsAmount, String title, BasicGui previousGui) throws IllegalArgumentException {
        this.previousGui = previousGui;
        if (rowsAmount > 6) throw new IllegalArgumentException("Wrong rowsAmount!");
        gui = Bukkit.createInventory(null, rowsAmount * 9, title);
    }


    protected BasicGui() {
    }

    public BasicGui(int rowsAmount, String title) throws IllegalArgumentException {
        this(rowsAmount, title, null);
    }

    public static Inventory createInventory(int rowsCount, String title) {
        return Bukkit.createInventory(null, 9 * rowsCount, title);
    }

    public static Inventory createFullInventory(String title) {
        return BasicGui.createInventory(6, title);
    }

    public static void returnItemToPlayer(Player player, ItemStack... item) {
        Map<Integer, ItemStack> itemsToDrop = player.getInventory().addItem(item);
        itemsToDrop.values().forEach(itemToDrop -> {
            Item droppedItem = player.getWorld().dropItem(player.getLocation(), itemToDrop);
            droppedItem.setPickupDelay(100);
        });
    }

    public static ItemStack modifyLore(ItemStack item, List<String> newLore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(newLore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBackItem(String name) {
        return BasicGui.createItem(Material.WOOD_DOOR, name);
    }

    public static List<String> splitLore(String lore, int characterLimit, char colorChar) {
        String[] splitedByLine = lore.split("\n");
        List<String> newLore = new ArrayList<>();
        for (String s : splitedByLine) {
            newLore.addAll(splitLoreBasic(s, characterLimit, colorChar));
        }
        return newLore;
    }

    public static ItemStack createItem(Material materialType, String name, List<String> lore, short data) {
        ItemStack item = new ItemStack(materialType, 1, data);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (name != null) meta.setDisplayName(name);
        if (lore != null) meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createSegmentedItem(Material materialType, String nameWithLore, short data) {
        List<String> splited = splitLore(nameWithLore, 25);
        if (splited.size() == 0) return BasicGui.createItem(materialType, nameWithLore, data);
        String name = splited.get(0);
        splited.remove(0);
        return BasicGui.createItem(materialType, name, splited, data);
    }

    public static ItemStack createSegmentedItem(Material materialType, String nameWithLore) {
        return createSegmentedItem(materialType, nameWithLore, (short) 0);
    }

    public static ItemStack createItem(Material materialType, String name, short data) {
        return createItem(materialType, name, null, data);
    }

    public static ItemStack createItem(Material materialType, String name, List<String> lore) {
        return createItem(materialType, name, lore, (short) 0);
    }

    public static ItemStack createItem(Material materialType, String name) {
        return createItem(materialType, name, null, (short) 0);
    }

    public static ItemStack createBackground(short color) {
        return createItem(Material.STAINED_GLASS_PANE, ChatColor.MAGIC + "", color);
    }

    public static ItemStack createExitItem(String name) {
        return BasicGui.createItem(Material.BARRIER, name);
    }

    public static String clearColors(String phrase) {
        return clearColors(phrase, '§');
    }

    public static String clearColors(String phrase, char colorChar) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar) {
                i++;
                continue;
            }
            builder.append(phrase.charAt(i));
        }
        return builder.toString();
    }

    public static List<String> simpleSplitLore(String... lore) {
        return new ArrayList<>(Arrays.asList(lore));
    }

    public static List<String> splitLore(String lore, int characterLimit) {
        return splitLore(lore, characterLimit, '§');
    }

    public static List<String> splitLoreWithConversion(String lore, int characterLimit) {
        return splitLore(lore.replace('&', '§'), characterLimit, '§');
    }

    private static List<String> splitLoreBasic(String lore, int characterLimit, char colorChar) {
        if (characterLimit <= 0) characterLimit = 1;
        if (lore.length() <= characterLimit) return new ArrayList<>(Collections.singleton(lore));

        String currentColors = "";
        String currentFormatting = "";
        String reset = "";

        StringBuilder singleLine = new StringBuilder();
        List<String> splitedLore = new ArrayList<>();

        String[] loreWords = lore.split(" ");
        int position = 0;

        for (int i = 0; i < loreWords.length; i++) {
            String word = loreWords[i];
            String[] wrappedWord;
            if (lengthWithoutSpecialCharacters(word, colorChar) > characterLimit) {
                wrappedWord = splitStartingFrom(word, characterLimit - position, characterLimit, colorChar);
            } else {
                wrappedWord = new String[1];
                wrappedWord[0] = word;
            }

            for (int j = 0; j < wrappedWord.length; j++) {
                word = wrappedWord[j];
                word = shortColors(word, colorChar);
                String[] fixedFormatting = fixFormatting(word, currentFormatting, colorChar);
                word = fixedFormatting[0];
                int wordLength = lengthWithoutSpecialCharacters(word, colorChar);

                if (singleLine.length() == 0) {
                    singleLine.append(reset).append(currentColors).append(currentFormatting).append(word);
                } else if (lengthWithoutSpecialCharacters(singleLine.toString(), colorChar) +
                        wordLength + 1 > characterLimit) {
                    splitedLore.add(shortColors(singleLine.toString(), colorChar));
                    singleLine.setLength(0);
                    --j;
                    continue;
                } else {
                    singleLine.append(' ').append(word);
                }

                if (reset.isEmpty()) reset = reloadReset(word, colorChar);

                currentFormatting = fixedFormatting[1];
                currentColors = getColorsFromPhrase(word, currentColors, colorChar);
                position = lengthWithoutSpecialCharacters(singleLine.toString(), colorChar);
            }
        }
        if (singleLine.length() != 0) splitedLore.add(shortColors(singleLine.toString(), colorChar));
        return splitedLore;
    }

    private static String shortColors(String phrase, char colorChar) {
        char currentColor = ' ';
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar && i + 1 < phrase.length()) {
                char specialChar = phrase.charAt(i + 1);
                if (isColor(specialChar)) {
                    if (specialChar == currentColor) {
                        i++;
                        continue;
                    }
                    currentColor = specialChar;
                } else if (isReset(specialChar)) {
                    currentColor = ' ';
                }
            }
            newString.append(phrase.charAt(i));
        }
        return newString.toString();
    }

    private static int lengthWithoutSpecialCharacters(String phrase, char colorChar) {
        int colorsCounter = 0;
        for (int i = 0; i + 1 < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar) {
                colorsCounter += 2;
            }
        }
        return phrase.length() - colorsCounter;
    }

    private static String customSubstring(String phrase, int start, int stop, char colorChar) {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        boolean ignore = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (counter >= start && counter < stop) {
                builder.append(phrase.charAt(i));
            }
            if (phrase.charAt(i) == colorChar) {
                ignore = true;
            } else if (ignore) {
                ignore = false;
            } else {
                counter++;
            }
        }
        return builder.toString();
    }

    private static String[] splitStartingFrom(String phrase, int start, int characterLimit, char colorChar) {
        int phraseLength = lengthWithoutSpecialCharacters(phrase, colorChar);
        if (characterLimit <= 0) characterLimit = 1;
        if (start > phraseLength) start = phraseLength;

        String[] splited = new String[((start != 0) ? 1 : 0) + (phraseLength - start) / characterLimit +
                (((phraseLength - start) % characterLimit != 0) ? 1 : 0)];
        splited[0] = customSubstring(phrase, 0, start, colorChar);
        int indexCounter = (start != 0) ? 1 : 0;

        for (int i = start; i < phraseLength; i += characterLimit) {
            splited[indexCounter] = (customSubstring(phrase, i, i + characterLimit, colorChar));
            indexCounter++;
        }
        return splited;
    }

    private static String getColorsFromPhrase(String phrase, String currentColors, char colorChar) {
        for (int i = 0; i + 1 < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar) {
                if (isReset(phrase.charAt(i + 1))) {
                    currentColors = "";
                } else if (isColor(phrase.charAt(i + 1))) {
                    currentColors = replaceColor(currentColors, phrase.charAt(i + 1), colorChar);
                }
            }
        }
        return currentColors;
    }

    private static String replaceColor(String phrase, char newColor, char colorChar) {
        for (int i = 0; i + 1 < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar && isColor(phrase.charAt(i + 1))) {
                return phrase.replace(phrase.charAt(i + 1), newColor);
            }
        }
        return phrase + colorChar + newColor;
    }

    private static String[] fixFormatting(String phrase, String formatting, char colorChar) {
        StringBuilder formattingBuilder = new StringBuilder(formatting);

        for (int i = 0; i + 1 < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar) {
                if (isColor(phrase.charAt(i + 1))) {
                    String regex = colorChar + "" + phrase.charAt(i + 1);
                    phrase = phrase.replaceAll(regex, regex + formattingBuilder.toString());
                } else if (isFormatting(phrase.charAt(i + 1)) && formattingBuilder.indexOf(phrase.charAt(i + 1) + "") < 0) {
                    formattingBuilder.append(colorChar).append(phrase.charAt(i + 1));
                } else if (isReset(phrase.charAt(i + 1))) {
                    formattingBuilder.setLength(0);
                }
            }
        }
        String[] returnedValue = new String[2];
        returnedValue[0] = phrase;
        returnedValue[1] = formattingBuilder.toString();
        return returnedValue;
    }

    private static boolean isColor(char character) {
        return colorsChars.indexOf(character) >= 0;
    }

    private static boolean isFormatting(char character) {
        return formattingChars.indexOf(character) >= 0;
    }

    private static boolean isReset(char character) {
        return resetChars.indexOf(character) >= 0;
    }

    private static String reloadReset(String phrase, char colorChar) {
        for (int i = 0; i + 1 < phrase.length(); i++) {
            if (phrase.charAt(i) == colorChar && isReset(phrase.charAt(i + 1))) {
                return colorChar + "" + resetChars.charAt(0);
            }
        }
        return "";
    }

    public long getLastClick() {
        return lastClick;
    }

    void setLastClick(long lastClick) {
        this.lastClick = lastClick;
    }

    public Player getLastViewer() {
        return viewer;
    }

    public void returnItemToPlayer(ItemStack... item) {
        if (this.getLastViewer() == null || !this.getLastViewer().isOnline())
            return;
        BasicGui.returnItemToPlayer(this.getLastViewer(), item);
    }

    public BasicGui getPreviousGui() {
        return previousGui;
    }

    public void setPreviousGui(BasicGui previousGui) {
        this.previousGui = previousGui;
    }

    public void autoFill(ItemStack item) {
        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, item);
            }
        }
    }

    public void autoFill(ItemStack item1, ItemStack item2) {
        boolean firts = true;
        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, (firts) ? item1 : item2);
            }
            firts = !firts;
        }
    }

    public boolean autoFrame(ItemStack item) {
        if (this.getRowsAmount() < 3) return false;
        for (int i = 0; i < 9; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, item);
            }
        }
        for (int i = 9; i < gui.getSize(); i += 9) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, item);
            }
        }
        for (int i = 17; i < gui.getSize(); i += 9) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, item);
            }
        }
        for (int i = gui.getSize() - 9; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, item);
            }
        }
        return true;
    }

    void setClosed() {
        this.isOpen = false;
    }

    public boolean addItem(ItemStack item, Action action) {
        return this.addItem(item, action, null);
    }

    public boolean addItem(ItemStack item, Action action, Action rightAction) {
        int firstEmptySlot = gui.firstEmpty();
        if (firstEmptySlot != -1) {
            gui.addItem(item);
            putToActions(firstEmptySlot, action);
            putToRightActions(firstEmptySlot, rightAction);
            return true;
        }
        return false;
    }

    protected boolean advancedClickHandler(InventoryClickEvent e, Action defaultAction) {
        return true;
    }

    public void closeLater() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SimpleCommands.getInstance(), () -> {
            List<HumanEntity> guiViewers = getGui().getViewers();
            for (int i = 0; i < guiViewers.size(); i++) {
                guiViewers.get(i).closeInventory();
            }
        });

    }

    public boolean setItem(int positionX, int positionY, ItemStack item, Action action) {
        return this.setItem(positionY * 9 + positionX, item, action);
    }

    public boolean setItem(int positionX, int positionY, ItemStack item, Action action, Action rightAction) {
        return this.setItem(positionY * 9 + positionX, item, action, rightAction);
    }

    protected boolean setItem(int position, ItemStack item, Action action) {
        return this.setItem(position, item, action, null);
    }

    protected boolean setItem(int position, ItemStack item, Action action, Action rightAction) {
        if (position >= gui.getSize()) return false;
        gui.setItem(position, item);
        putToActions(position, action);
        putToRightActions(position, rightAction);
        return true;
    }

    protected void replaceItem(int position, ItemStack newItem) {
        this.gui.setItem(position, newItem);
    }

    public void open(Player opener) {
        this.isOpen = true;
        viewer = opener;
        SimpleCommands.getInstance().getGuiListener().addGui(this, opener);
    }

    public Inventory getGui() {
        return gui;
    }

    public Map<Integer, Action> getActions() {
        return actions;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getRowsAmount() {
        return gui.getSize() / 9;
    }

    public void onClose() {
    }

    public void replaceItem(int positionX, int positionY, ItemStack newItem) {
        this.replaceItem(positionY * 9 + positionX, newItem);
    }

    protected void backOrClose(Player p) {
        if (previousGui != null) previousGui.open(p);
        else p.closeInventory();
    }

    private void putToActions(int position, Action action) {
        if (action == null) return;
        this.actions.put(position, action);
    }

    private void putToRightActions(int position, Action action) {
        if (action == null) return;
        this.rightClickActions.put(position, action);
    }

}

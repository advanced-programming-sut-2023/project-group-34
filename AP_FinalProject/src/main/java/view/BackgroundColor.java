package view;

import model.enums.BlockType;
import model.map.Block;

public class BackgroundColor {
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BRIGHT_BLACK_BACKGROUND = "\u001b[40;1m";
    public static final String ANSI_BRIGHT_GREEN_BACKGROUND = "\u001b[42;1m";
    public static final String ANSI_BRIGHT_BLUE_BACKGROUND = "\u001b[44;1m";
    public static final String ANSI_DARKER_WHITE = "\u001B[47m;0m";

    public static String dictionary(Block block){
        if (block.getBlockType().equals(BlockType.BEACH)) return ANSI_YELLOW_BACKGROUND;
        if (block.getBlockType().equals(BlockType.GRASS)) return ANSI_BRIGHT_GREEN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.IRON)) return ANSI_BRIGHT_BLACK_BACKGROUND;
        if (block.getBlockType().equals(BlockType.DENSE_MEADOW)) return ANSI_GREEN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.LAKE)) return ANSI_BLUE_BACKGROUND;
        if (block.getBlockType().equals(BlockType.RIVER)) return ANSI_BRIGHT_BLUE_BACKGROUND;
        if (block.getBlockType().equals(BlockType.SEA)) return ANSI_BLUE_BACKGROUND;
        if (block.getBlockType().equals(BlockType.STONY_GROUND)) return ANSI_WHITE_BACKGROUND;
        if (block.getBlockType().equals(BlockType.MEADOW)) return ANSI_GREEN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.GROUND)) return ANSI_BRIGHT_GREEN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.BOULDER)) return ANSI_WHITE_BACKGROUND;
        if (block.getBlockType().equals(BlockType.OIL)) return ANSI_BLACK_BACKGROUND;
        if (block.getBlockType().equals(BlockType.PLAIN)) return ANSI_CYAN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.SHALLOW_WATER)) return ANSI_CYAN_BACKGROUND;
        if (block.getBlockType().equals(BlockType.NORTH_ROCK) || block.getBlockType().equals(BlockType.SOUTH_ROCK) ||
                block.getBlockType().equals(BlockType.EAST_ROCK) || block.getBlockType().equals(BlockType.WEST_ROCK))
            return ANSI_DARKER_WHITE;
        return null;
    }
}

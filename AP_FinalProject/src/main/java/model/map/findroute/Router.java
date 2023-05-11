package model.map.findroute;

import model.Game;
import model.building.BuildingType;
import model.building.DefenciveBuilding;
import model.building.DefenciveBuildingType;
import model.building.GateType;
import model.enums.BlockType;
import model.government.Government;
import model.human.Troop;
import model.human.TroopType;
import model.map.Block;
import model.map.GameMap;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Router {
    private final ArrayList<Node> openList;
    private final ArrayList<Node> closedList;
    private Node destination;
    private final GameMap map;
    private final TroopType troopType;
    private final Government government;
    public Router(GameMap map, Block startingBlock, Block destinationBlock, Troop troop) {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        this.map = map;
        openList.add(new Node(null, startingBlock, destinationBlock));
        this.troopType = troop.getTroopType();
        government = troop.getGovernment();
    }
    public ArrayList<Block> findBestRoute () {
        if (finalNode() == null) return null;
        ArrayList<Block> tempResult = new ArrayList<>();
        Node iterator = destination;
        do {
            tempResult.add(iterator.getCurrentBlock());
            iterator = iterator.getParent();
        } while (iterator != null);
        ArrayList<Block> result = new ArrayList<>();
        for (int i = tempResult.size() - 1; i >= 0; i--) {
            result.add(tempResult.get(i));
        }
        return result;
    }
    private Node finalNode () {
        while (openList.size() != 0) {
            sortOpenList();
            Node q = openList.get(0);
            openList.remove(q);
            nodesProcessor(getNeighbors(q));
            if (destination != null) return destination;
            closedList.add(q);
        }
        return null;
    }
    private Node[] getNeighbors (Node node) {
        ArrayList<Node> result = new ArrayList<>();
        int[][] indexes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] index : indexes) {
            if (map.checkBounds(node.getCurrentBlock().getLocationI() + index[0],
                    node.getCurrentBlock().getLocationJ() + index[1]))
                result.add(new Node(node,
                                map.getABlock(node.getCurrentBlock().getLocationI() + index[0],
                                        node.getCurrentBlock().getLocationJ() + index[1]),
                        node.getDestination()));
        }
        Node[] output = new Node[result.size()];
        for (int i = 0; i < result.size(); i++) {
            output[i] = result.get(i);
        }
        return output;
    }
    private void nodesProcessor(Node[] nodes) {
        nodesFor:
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getCurrentBlock().equals(nodes[i].getDestination())) {
                destination = nodes[i];
                return;
            }
            for (Node openListNode : openList) {
                if (openListNode.getCurrentBlock().equals(nodes[i].getCurrentBlock()) && openListNode.getF() < nodes[i].getF()) continue nodesFor;
            }
            for (Node closedListNode : closedList) {
                if (closedListNode.getCurrentBlock().equals(nodes[i].getCurrentBlock()) && closedListNode.getF() < nodes[i].getF())
                    continue nodesFor;
            }
            if (!canGoThere(nodes[i])) continue;
            openList.add(nodes[i]);
        }
    }
    private boolean canGoThere (Node node) {
        //TODO: different troops canClimb & latter
        if (unpassable(node.getCurrentBlock())) return false;
        if (troopType.equals(TroopType.ASSASSIN)) return true;
        if (troopType.isCanClimb() && node.getCurrentBlock().getBuilding().size() != 0 && node.getCurrentBlock().getBuilding().get(0) instanceof DefenciveBuilding && ((DefenciveBuilding) node.getCurrentBlock().getBuilding().get(0)).isHasLadder()) return true;
        if (troopType.isCanClimb() && (node.getCurrentBlock().getBuilding().size() != 0) ||
                (node.getParent().getCurrentBlock().getBuilding().size() != 0 ))
            return true;
        if (node.getCurrentBlock().getBuilding().size() != 0 && node.getCurrentBlock().getBuilding().get(0) instanceof DefenciveBuilding) {
            return node.getParent().getCurrentBlock().getBuilding().size() != 0 && node.getParent().getCurrentBlock().getBuilding().get(0) instanceof DefenciveBuilding;
        }
        else if (node.getCurrentBlock().getBuilding().size() != 0 && (node.getCurrentBlock().getBuilding().get(0).getBuildingType().equals(GateType.SMALL_GATE_HOUSE) || node.getCurrentBlock().getBuilding().get(0).getBuildingType().equals(GateType.BIG_GATE_HOUSE))) {
            return node.getCurrentBlock().getBuilding().get(0).getGovernment().equals(government);
        }
        else {
            return !(node.getParent().getCurrentBlock().getBuilding().size() != 0 && node.getParent().getCurrentBlock().getBuilding().get(0) instanceof DefenciveBuilding);
        }
    }
    private void sortOpenList () {
        openList.sort(Comparator.comparingInt(Node::getF));
    }
    private boolean unpassable (Block block) {
        BlockType[] forbiddenTypes = {BlockType.RIVER, BlockType.BEACH, BlockType.SEA, BlockType.LAKE, BlockType.EAST_ROCK, BlockType.NORTH_ROCK, BlockType.WEST_ROCK, BlockType.SOUTH_ROCK, BlockType.DITCH};
        for (BlockType forbiddenType : forbiddenTypes) {
            if (block.getBlockType().equals(forbiddenType)) return true;
        }
        return false;
    }
}

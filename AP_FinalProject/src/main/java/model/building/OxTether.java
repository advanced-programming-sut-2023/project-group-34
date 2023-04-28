package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;

public class OxTether extends Building{
    protected OxTether(Government government, Block block, int HP, HashMap<Resources, Integer> cost) {
        super(government, block, HP, cost, OxTetherType.OX_TETHER);
    }

    @Override
    public void process() {

    }

    @Override
    public void destroy() {

    }

}

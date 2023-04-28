package model.building;

import model.enums.make_able.Resources;
import model.government.Government;
import model.map.Block;

import java.util.HashMap;
import java.util.Objects;

public class OxTether extends Building{
    protected OxTether(Government government, Block block, int HP, HashMap<Resources, Integer> cost) {
        super(government, block, HP, cost, OxTetherType.OX_TETHER);
    }

    @Override
    public void process() {
        double transferRate;
        int rate = 12;
        for (Building building : government.getBuildings()) {
            if(!building.getBuildingType().equals(MakerType.QUARRY) || ((Maker)building).getCurrentAmount() == 0) {
                continue;
            }
            transferRate = Double.min(rate, ((Maker) building).getCurrentAmount());
            ((Maker)building).use(transferRate);
            Resources.STONE.add(transferRate , government);
            break;
        }
    }

    @Override
    public void destroy() {
        block.getBuilding().remove(this);
        government.getBuildings().remove(this);
    }

}

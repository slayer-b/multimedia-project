package com.adv.service.payment;

import com.adv.payment.model.PricesSystem;
import common.dao.IGenericDAO;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 * saves min prices in the database.
 * @author demchuck.dima@gmail.com
 */
@Service("minPricesService")
public class MinPricesDB implements IMinPricesService {
    private IGenericDAO<PricesSystem, Long> pricesDAO;
    //as this service contains only one instance of MinPrices bean save its id
    private Long id = Long.valueOf(1);

    @Override
    public PricesSystem getMinPrices() {
        PricesSystem res = pricesDAO.getById(id);
        if (res == null) {
            res = createInstance();
            pricesDAO.makePersistent(res);
        }
        return res;
    }

    @Override
    public void setMinPrices(PricesSystem value) {
        value.setId(id);
        pricesDAO.makePersistent(value);
    }

    /**
     * Get default instance of a min price for a system.
     * @return prices for a system.
     */
    private PricesSystem createInstance() {
        PricesSystem min = new PricesSystem();
        min.setId(id);
        min.setClickCost(MinPricesSystem.MIN_CLICK_COST);
        min.setDurationCost(MinPricesSystem.MIN_DURATION_COST);
        min.setSupplantationCost(MinPricesSystem.MIN_SUPPLANTATION_COST);
        min.setViewCost(MinPricesSystem.MIN_VIEW_COST);
        min.setDefaultUnits(MinPricesSystem.DEFAULT_ADV_UNITS_TYPE);
        return min;
    }

    @Required
    @Resource(name = "pricesSystemDAO")
    public void setPricesDao(IGenericDAO<PricesSystem, Long> value) {
        this.pricesDAO = value;
    }
}

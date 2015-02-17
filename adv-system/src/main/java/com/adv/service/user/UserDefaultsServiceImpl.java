package com.adv.service.user;

import com.adv.core.model.Block;
import com.adv.core.model.BlockContent;
import com.adv.core.model.ConAdvProps;
import com.adv.core.model.ContentLink;
import com.adv.core.model.DefaultsConAdv;
import com.adv.core.model.DefaultsConLink;
import com.adv.core.model.DefaultsContent;
import com.adv.core.model.User;
import com.adv.core.model.UserDefaults;
import com.adv.core.types.ContentType;
import com.adv.payment.model.AdvPaymentUnits;
import com.adv.payment.model.BlockAdvCost;
import com.adv.payment.model.PricesSystem;
import com.adv.payment.model.UserAdvCost;
import com.adv.service.payment.IMinPricesService;
import common.dao.IGenericDAO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Service("userDefaultsService")
public class UserDefaultsServiceImpl implements IUserDefaultsService {
    public static final String DEFAULT_WIDTH = "400px";
    public static final String DEFAULT_HEIGHT = "200px";

    public static final int DEFAULT_ADV_QUANTITY = 5;
    public static final int DEFAULT_MAX_PAGES = 5;
    public static final Boolean DEFAULT_NAV_VISIBLE = Boolean.TRUE;

    private IGenericDAO<UserDefaults, Long> dao;
    private IMinPricesService pricesService;

    @Override
    public UserDefaults getUserDefaults(User user) {
        return dao.getById(user.getId());
    }

    @Override
    public UserDefaults getUserDefaults(String u_id) {
        List<UserDefaults> defaultses = dao.getByPropertyValue("u_id", u_id);
        if (defaultses.size() > 0) {
            return defaultses.get(0);
        } else {
            return null;
        }
    }

    @Override
    public synchronized UserDefaults createUserDefaults(User user) {
        UserDefaults u = dao.getById(user.getId());
        if (u != null) {
            return u;
        }

        u = new UserDefaults();
        u.setUser(user);
        u.setUseDefaults(Boolean.TRUE);
        u.setDefaultsContent(createContentDefaults());
        u.setAdvCost(createAdvCostDefault());

        dao.makePersistent(u);
        return u;
    }

    /**
     * create default adv cost for user.
     * @return adv cost initialized with default values
     */
    private UserAdvCost createAdvCostDefault() {
        PricesSystem prices = pricesService.getMinPrices();
        UserAdvCost cost = new UserAdvCost();
        cost.setClickCost(prices.getClickCost());
        cost.setDurationCost(prices.getDurationCost());
        cost.setSupplantationCost(prices.getSupplantationCost());
        cost.setViewCost(prices.getViewCost());
        cost.setDefaultUnit(prices.getDefaultUnits());
        return cost;
    }

    private DefaultsContent createContentDefaults() {
        DefaultsConLink content = new DefaultsConLink();
        content.setHeight(DEFAULT_WIDTH);
        content.setWidth(DEFAULT_HEIGHT);

        content.setProperties(createConAdvProps());

        content.setCss("");
        return content;
    }

    public ConAdvProps createConAdvProps() {
        ConAdvProps props = new ConAdvProps();
        props.setAdvQuantity(DEFAULT_ADV_QUANTITY);
        props.setMaxPages(DEFAULT_MAX_PAGES);
        props.setNavVisible(DEFAULT_NAV_VISIBLE);
        return props;
    }

    @Override
    public boolean saveDefaultsForUser(DefaultsConLink defaults, Boolean use, User user) {
        UserDefaults rez = dao.getById(user.getId());
        rez.setUseDefaults(use);
        rez.setDefaultsContent(defaults);
        dao.makePersistent(rez);
        return true;
    }

    @Override
    public BlockContent getDefaultContent(UserDefaults defaults, Block block) {
        ContentType type = defaults.getDefaultsContent().getContent_type();
        switch (type) {
            case LINK:
                ContentLink content = generateContentLink((DefaultsConLink) defaults.getDefaultsContent());
                content.setPaymentUnits(generateCost(block.getAdvCost()));
                return content;
            default:
                throw new UnsupportedOperationException("can not handle [" + type + "] content type");
        }
    }

    private ContentLink generateContentLink(DefaultsConLink defaults) {
        //IDefaultsConLink def_link = ;
        ContentLink content = new ContentLink();
        content.setProperties(getProperties(defaults));

        content.setHeight(defaults.getHeight());
        content.setWidth(defaults.getWidth());

        content.setCss(defaults.getCss());

        return content;
    }

    private AdvPaymentUnits generateCost(BlockAdvCost blockCost) {
        AdvPaymentUnits paymentUnits = new AdvPaymentUnits();
        paymentUnits.setType(blockCost.getDefaultUnit());
        switch (blockCost.getDefaultUnit()) {
            case CLICKS:
                paymentUnits.setCost(blockCost.getClickCost());
                break;
            case SUPPLANTATION:
                paymentUnits.setCost(blockCost.getSupplantationCost());
                break;
            default:
                throw new UnsupportedOperationException("can not handle [" + blockCost.getDefaultUnit() + "] unit type of payment");
        }
        return paymentUnits;
    }

    private ConAdvProps getProperties(DefaultsConAdv defaults) {
        ConAdvProps props = new ConAdvProps();
        props.setAdvQuantity(defaults.getProperties().getAdvQuantity());
        props.setMaxPages(defaults.getProperties().getMaxPages());
        props.setNavVisible(defaults.getProperties().getNavVisible());
        return props;
    }

    //------------------------------------- initialization -----------------------
    @Required
    @Resource(name = "userDefaultsDAO")
    public final void setDao(IGenericDAO<UserDefaults, Long> value) {
        this.dao = value;
    }

    @Required
    @Resource(name = "minPricesService")
    public final void setPricesService(IMinPricesService value) {
        this.pricesService = value;
    }
}

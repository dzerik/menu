/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dzerik.menu.api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dzerik.menu.dao.MenuDAO;
import ru.dzerik.menu.models.Dish;

/**
 *
 * @author Strobo
 */
@RestController
@RequestMapping("/api")
public class MenuAPI {

    @Autowired
    MenuDAO mDao;

    @RequestMapping(value = "/getDishes", method = GET, produces = "application/json;charset=UTF-8")
    public String getDishes() {
        try {
            List<Dish> list = mDao.getDish();
            String data = "";
            for (Dish d : list) {
                data += "," + d.toJSON();
            }

            return "{\"success\":true,"
                    + "\"data\":["
                    + data.substring(1)
                    + "]}";

        } catch (Exception ex) {
            return "{\"success\":false,"
                    + "\"msg\":\"" + ex.getMessage() + "\""
                    + "}";
        }
    }

    @RequestMapping(value = "/getDishesById", method = GET, produces = "application/json;charset=UTF-8")
    public String getDishesByID(
            @RequestParam(value = "id", defaultValue = "0", required = true) String id,
            HttpServletResponse response
    ) {
        try {

            Integer dId = Integer.valueOf(id);

            Dish dish = mDao.getDishByID(dId);
            if (dish == null) {
                response.setStatus(400);
                throw new Exception("dish this id=" + id + " not found");

            }
            return "{\"success\":true,"
                    + "\"data\":["
                    + dish.toJSON()
                    + "]}";

        } catch (Exception ex) {
            return "{\"success\":false,"
                    + "\"msg\":\"" + ex.getMessage() + "\""
                    + "}";
        }
    }

    @RequestMapping(value = "/addDish", method = POST, produces = "application/json;charset=UTF-8")
    public String addDishe(
            @RequestParam(value = "productname", defaultValue = "0", required = true) String productname,
            @RequestParam(value = "weight", defaultValue = "0", required = true) String weight,
            @RequestParam(value = "fullcost", defaultValue = "0", required = true) String fullcost,
            @RequestParam(value = "halfcost", defaultValue = "0", required = true) String halfcost,
            @RequestParam(value = "description", defaultValue = "0", required = true) String description,
            HttpServletResponse response
    ) {
        try {

            Integer dweight = Integer.valueOf(weight);
            Integer dfullcost = Integer.valueOf(fullcost);
            Integer dhalfcost = Integer.valueOf(halfcost);

            Integer id = mDao.addDish(productname, dweight, dfullcost, dhalfcost, description);

            return "{\"success\":true,"
                    + "\"data\":["
                    + id
                    + "]}";

        } catch (Exception ex) {
            response.setStatus(400);
            return "{\"success\":false,"
                    + "\"msg\":\"" + ex.getMessage() + "\""
                    + "}";
        }
    }
}

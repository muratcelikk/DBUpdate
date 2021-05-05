package com.cal.pro;

import model.TablePro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.DB;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Murat Çelik Pegamis
 */
@Controller
public class UpdatePageController {

    DB db = new DB();

    @RequestMapping(value = "/updatePage/{crid}", method = RequestMethod.GET)
    public String UpdatePage(Model model, @PathVariable int crid, HttpServletRequest req ) {
        req.getSession().setAttribute("crid", crid);
        model.addAttribute("tableData", tableResult(crid));
        model.addAttribute("cartData", new HomeController().cartResult());
        return "update";
    }
    //Listele
    public TablePro tableResult(int crid) {
        try {
            String query = "SELECT * FROM cart c INNER JOIN categories as ct ON c.cid = ct.cid where c.crid = ?";
            PreparedStatement pre = db.connect(query);
            pre.setInt(1, crid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                TablePro tb = new TablePro();
                tb.setCname(rs.getString("cname"));
                tb.setCsurname(rs.getString("csurname"));
                tb.setCrid(rs.getInt("crid"));
                tb.setCphone(rs.getString("cphone"));
                tb.setCtitle(rs.getString("ctitle"));
                return tb;
            }
        } catch (Exception e) {
            System.err.println("UpdatePage error : " + e);
        }
        return null;
    }

    //Güncelle
    @RequestMapping(value = "/cartUpdate", method = RequestMethod.POST)
    public String cartUpdate(TablePro tbl, HttpServletRequest req) { //Update için listelenen veri DEPENDENCY İNJECTİON ile getirilir.
        int crid = Integer.valueOf(""+req.getSession().getAttribute("crid"));
        try {
            String query = "update cart set cname = ?, csurname = ?, cphone = ?, cid = ? where crid = ? ";
            PreparedStatement pre = db.connect(query);
            pre.setString(1,tbl.getCname());
            pre.setString(2,tbl.getCsurname());
            pre.setString(3,tbl.getCphone());
            pre.setInt(4, tbl.getCid());
            pre.setInt(5, crid);
            pre.executeUpdate();
        }catch (Exception e){
            System.err.println("CartUpdate error : " + e);
        }
        return "redirect:/";
    }
}

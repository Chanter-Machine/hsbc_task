package servlet;



import com.alibaba.fastjson.JSONObject;
import common.beans.Ret;
import common.utils.GetRequestJsonUtils;
import common.utils.HttpUtil;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject json  = GetRequestJsonUtils.getRequestJsonObject(req);
        String name = (String)json.get("name");

        Ret ret = new Ret();
        try {
            UserService.deleteUser(name);
            ret.setData("ok");
        } catch (Exception e) {
            ret.setErrMsg(e.getMessage());
            HttpUtil.SetResp(resp, ret);
            return;
        }

        HttpUtil.SetResp(resp, ret);
    }
}

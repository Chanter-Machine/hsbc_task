package servlet;



import com.alibaba.fastjson.JSONObject;
import common.beans.Ret;
import common.utils.GetRequestJsonUtils;
import common.utils.HttpUtil;
import service.RoleMgmtService;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckRoleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject json  = GetRequestJsonUtils.getRequestJsonObject(req);
        String token = (String)json.get("token");
        String role = (String)json.get("role");

        Ret ret = new Ret();
        try {
            boolean res = RoleMgmtService.checkUserRole(token, role);
            ret.setData(res);
        } catch (Exception e) {
            ret.setErrMsg(e.getMessage());
            HttpUtil.SetResp(resp, ret);
            return;
        }

        HttpUtil.SetResp(resp, ret);
    }
}
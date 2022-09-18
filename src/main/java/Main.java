import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server=new Server(Integer.parseInt("11688"));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        server.setHandler(context);

        context.addServlet(new ServletHolder(new UserCreateServlet()), "/create_user");
        context.addServlet(new ServletHolder(new UserDeleteServlet()), "/delete_user");
        context.addServlet(new ServletHolder(new RoleCreateServlet()), "/create_role");
        context.addServlet(new ServletHolder(new RoleDeleteServlet()), "/delete_role");
        context.addServlet(new ServletHolder(new AssignRoleServlet()), "/assign_role");
        context.addServlet(new ServletHolder(new AuthServlet()), "/auth");
        context.addServlet(new ServletHolder(new InvalidateServlet()), "/invalidate");
        context.addServlet(new ServletHolder(new CheckRoleServlet()), "/check_role");
        context.addServlet(new ServletHolder(new GetAllRoleServlet()), "/all_roles");

        server.start();
        server.join();
    }
}
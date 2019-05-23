package smgmt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/role-based-shop-management-userinterface-0.0.1-SNAPSHOT")
public class AppController {

    @RequestMapping(value = {"/userpage"}, method = RequestMethod.GET)
    public ModelAndView userPage() {

        if (isAdminPage())
            return adminPage();
        else if (isBillingUserPage())
            return billingUserPage();
        ModelAndView model = new ModelAndView();
        model.addObject("user", getUser());
        model.addObject("role", "USER");
        model.setViewName("user");
        return model;
    }

    @RequestMapping(value = "/adminpage", method = RequestMethod.GET)
    public ModelAndView adminPage() {
            ModelAndView model = new ModelAndView();
            model.addObject("user", getUser());
            model.addObject("role", "ADMIN");
            model.setViewName("admin");
            return model;
    }
    
    @RequestMapping(value = "/billinguserpage", method = RequestMethod.GET)
    public ModelAndView billingUserPage() {
            ModelAndView model = new ModelAndView();
            model.addObject("user", getUser());
            model.addObject("role", "BILLINGUSER");
            model.setViewName("billinguser");
            return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Login Page");
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return login(request,response);
    }

    @RequestMapping(value = {"/accessdenied"}, method = RequestMethod.GET)
    public ModelAndView accessDeniedPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "Either username or password is incorrect.");
        model.setViewName("accessdenied");
        return model;
    }
    
    @RequestMapping(value = {"/accessforbidden"}, method = RequestMethod.GET)
    public ModelAndView accessForbiddenPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "You are not allowed to view this page.");
        model.setViewName("accessforbidden");
        return model;
    }

    private String getUser() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }


    private boolean isAdminPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            if (authorities.size() == 1) {
                final Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
                GrantedAuthority grantedAuthority = iterator.next();
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isBillingUserPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            if (authorities.size() == 1) {
                final Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
                GrantedAuthority grantedAuthority = iterator.next();
                if (grantedAuthority.getAuthority().equals("BILLINGUSER")) {
                    return true;
                }
            }
        }
        return false;
    }
   
    @Autowired
    UserRepo profileRepository;
    
    @RequestMapping(value = { "/usermgmt" }, method = RequestMethod.GET)
    public String usermgmt(Authentication authentication, Model model) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        if (isAdmin(roles)) {
//            ModelAndView model = new ModelAndView("usermgmt");
            model.addAttribute("userProfiles", profileRepository.getAllProfiles());
            return "usermgmt";
        }
        return "accessdenied";
    }

    @RequestMapping(value = { "/billing" }, method = RequestMethod.GET)
    public ModelAndView billing(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        if (isBillingUser(roles) || isAdmin(roles)) {
            ModelAndView model = new ModelAndView();
            model.setViewName("billing");
            return model;
        }
        return accessForbiddenPage();
    }

    @RequestMapping(value = { "/data" }, method = RequestMethod.GET)
    public ModelAndView data(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        if (!isBillingUser(roles)) {
            ModelAndView model = new ModelAndView();
            model.setViewName("data");
            return model;
        }
        return accessForbiddenPage();
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isBillingUser(List<String> roles) {
        if (roles.contains("ROLE_BILLINGUSER")) {
            return true;
        }
        return false;
    }
}

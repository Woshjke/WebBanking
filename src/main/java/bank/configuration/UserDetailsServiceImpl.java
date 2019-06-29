//package bank.configuration;
//
//import bank.database.dao.UserDao;
//import bank.database.entity.Role;
//import bank.database.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//
//    private UserDao userDao;
//
//    @Autowired
//    public UserDetailsServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) {
//        User user = userDao.getUserByUsername(username);
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
//    }
//
//    public void setUserDAO(UserDao userDao) {
//        this.userDao = userDao;
//    }
//}

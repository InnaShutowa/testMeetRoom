//package com.example.demo.service;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//public class SecurityUserDetailsService implements UserDetailsService {
//
//    public UserDetails loadUserByUsername(String login)
//            throws UsernameNotFoundException, DataAccessException {
//        Session session = null;
//        try {
//            log.debug("loadUserByUsername("+login+");");
//            session = HibernateUtil.getSessionFactory().openSession();
//            Agent agent = DAOFactory.getInstance().getAgentDAO()
//                    .getAgentByLogin(session, login);
//            if (agent == null)
//                throw new UsernameNotFoundException("Login " + login
//                        + " doesn't exist!");
//
//            String username = agent.getLogin();
//            String password = agent.getPassword();
//            boolean notLocked = !agent.isLocked();
//            boolean notExpired = !agent.isExpired();
//            boolean enabled = notLocked && notExpired;
//            GrantedAuthority auth = new GrantedAuthority() {
//                private static final long serialVersionUID = 1L;
//
//                public String getAuthority() {
//                    return "ROLE_USER";
//                }
//            };
//            Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
//            set.add(auth);
//
//            UserDetails details = new User(username, password, enabled,
//                    notExpired, true, notLocked, set);
//            return details;
//        } finally {
//            if (session != null && session.isOpen()) {
//
//                    session.close();
//            }
//        }
//    }
//
//}

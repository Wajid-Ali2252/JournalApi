package JournalApp.journalApp.Service;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=usersRepo.findByUserName(username);
        if(user !=null)
        {
             return  User.builder()
                     .username(user.getUserName())
                     .password(user.getPassword())
                     .roles(user.getRoles().toArray(new String[0]))
                             .build();


        }
        throw  new UsernameNotFoundException("User not found");

    }
}

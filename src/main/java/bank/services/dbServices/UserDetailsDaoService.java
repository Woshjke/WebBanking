package bank.services.dbServices;

import bank.model.entity.UserDetails;
import bank.model.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsDaoService {

    private UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsDaoService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public void save(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }
    public UserDetails findById(Long id) {
        return userDetailsRepository.findById(id);
    }
}

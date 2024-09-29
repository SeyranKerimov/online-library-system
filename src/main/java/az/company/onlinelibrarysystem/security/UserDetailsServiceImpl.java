package az.company.onlinelibrarysystem.security;

import az.company.onlinelibrarysystem.enums.EnumAvailableStatus;
import az.company.onlinelibrarysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndStatus(username, EnumAvailableStatus.ACTIVE.getCode())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}


package com.template.users.shared.security.services;

import com.template.users.modules.roles.entity.ActionEntity;
import com.template.users.modules.roles.entity.RoleAction;
import com.template.users.modules.roles.repository.IRoleActionRepository;
import com.template.users.modules.users.entity.UserEntity;
import com.template.users.modules.users.repository.IUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleActionRepository roleActionRepository;

    public CustomUserDetailsService(IUserRepository userRepository,
            IRoleActionRepository roleActionRepository) {
        this.userRepository = userRepository;
        this.roleActionRepository = roleActionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Collection<GrantedAuthority> authorities = getAuthorities(user);

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities);
    }

    public List<Long> getUserActionIds(String username) {
        UserEntity user = userRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Long> actionIds = new ArrayList<>();
        user.getRoles().forEach(role -> {
            List<RoleAction> roleActions = roleActionRepository.findByRoleIdRoleAndActiveTrue(role.getIdRole());
            roleActions.forEach(roleAction -> {
                actionIds.add(roleAction.getAction().getIdAction());
            });
        });

        return actionIds;
    }

    public List<Long> getUserActionIds(Long userId) {
        UserEntity user = userRepository.findByIdUserAndActive(userId, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Long> actionIds = new ArrayList<>();
        user.getRoles().forEach(role -> {
            List<RoleAction> roleActions = roleActionRepository.findByRoleIdRoleAndActiveTrue(role.getIdRole());
            roleActions.forEach(roleAction -> {
                actionIds.add(roleAction.getAction().getIdAction());
            });
        });

        return actionIds;
    }


    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));

            List<RoleAction> roleActions = roleActionRepository.findByRoleIdRoleAndActiveTrue(role.getIdRole());
            roleActions.forEach(roleAction -> {
                ActionEntity action = roleAction.getAction();
                authorities.add(new SimpleGrantedAuthority("ACTION_" + action.getIdAction()));
            });
        });

        return authorities;
    }
}
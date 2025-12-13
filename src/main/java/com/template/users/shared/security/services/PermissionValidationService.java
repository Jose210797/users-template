package com.template.users.shared.security.services;

import com.template.users.shared.security.annotations.Permissions;
import com.template.users.shared.security.dto.UserAuthInfo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PermissionValidationService {

    public boolean hasPermissions(UserAuthInfo userInfo, Permissions permissions) {
        if (userInfo == null || userInfo.getActions() == null) {
            return false;
        }

        List<Long> userActions = userInfo.getActions();

        if (permissions.anyOf().length > 0) {
            List<Long> requiredAnyActions = Arrays.stream(permissions.anyOf()).boxed().toList();
            boolean hasAnyPermission = requiredAnyActions.stream().anyMatch(userActions::contains);
            if (!hasAnyPermission) {
                return false;
            }
        }

        if (permissions.allOf().length > 0) {
            List<Long> requiredAllActions = Arrays.stream(permissions.allOf()).boxed().toList();
            boolean hasAllPermissions = userActions.containsAll(requiredAllActions);
            if (!hasAllPermissions) {
                return false;
            }
        }

        return true;
    }
}
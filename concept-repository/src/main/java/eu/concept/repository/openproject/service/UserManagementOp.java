package eu.concept.repository.openproject.service;

import eu.concept.repository.openproject.domain.MemberRoleOp;
import eu.concept.repository.openproject.domain.PasswordOp;
import eu.concept.repository.openproject.domain.UserOp;
import eu.concept.response.ApplicationResponse;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

/**
 *
 * @author Christos Paraskeva
 */
@Service
@Transactional
public interface UserManagementOp {

    public ApplicationResponse addUserToOpenproject(UserOp user, PasswordOp userPassword, MemberRoleOp memberRole);

    public ApplicationResponse changeUserPassword(int userID, String currentPassword, String newPassword);

}

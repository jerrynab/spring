package gt.app.modules.user;

import gt.app.config.security.AppUserDetails;
import gt.app.domain.Note;
import gt.app.modules.note.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("appSecurity")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserAuthorityService {

    private final NoteService noteService;

    public boolean hasAccess(AppUserDetails curUser, Long id, String entity) {

        if (curUser.isSystemAdmin()) {
            return true;
        }


        if (Note.class.getSimpleName().equalsIgnoreCase(entity)) {

            UUID createdById = noteService.findCreatedByUserIdById(id);

            return createdById.equals(curUser.getUser().getId());
        }


        /*
        add more rules
         */

        return false;
    }

}

package at.schaefer.david.Communication.DTO;

import java.sql.SQLException;

public class DTOUserContainer {
    public String serverId;
    public DTOUser user;

    public static DTOUserContainer GetDTOUserContainer(String serverId, Long userId) throws SQLException {
        DTOUserContainer container = new DTOUserContainer();
        container.serverId = serverId;
        container.user = DTOUser.GetDTOUser(userId);
        return container;
    }
}

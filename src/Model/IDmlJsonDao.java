package Model;

import Domain.ConfigDTO;
import java.io.IOException;
import java.util.List;
import java.io.IOException;
/**
 *
 * @author Mauricio Herrera
 */
public interface IDmlJsonDao {

    public boolean createFileJson() throws IOException;

    public List<ConfigDTO> select();

    public int insert(ConfigDTO config);

    public int update(ConfigDTO config);

    public int delete(ConfigDTO config);

    public int changeBrowser(ConfigDTO config);

    public ConfigDTO activeNav();

    public List<String> listNav();

}
